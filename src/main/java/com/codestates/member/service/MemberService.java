package com.codestates.member.service;

//import com.codestates.backup.entity.BackupMember;
//import com.codestates.backup.repository.BackupMemberRepository;
//import com.codestates.backup.service.BackupMemberService; //백업 추가 여긴 분산 트랜잭션파트 (mysql 이용)
import com.codestates.event.MemberEvent;
import com.codestates.exception.BusinessLogicException;
import com.codestates.exception.ExceptionCode;
import com.codestates.member.entity.Member;
import com.codestates.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 *  - 메서드 구현
 *  - DI 적용
 *  - Spring Data JPA 적용
 */
@Service
@Slf4j
@Transactional //@Transactional 애너테이션을 클래스 레벨에 추가하면 기본적으로 해당 클래스에서 MemberRepository의 기능을 이용하는 모든 메서드에 트랜잭션이 적용
public class MemberService {

    //private final MemberRepository memberRepository;
    //private final BackupMemberRepository backupMemberRepository; //백업 추가 여긴 분산 트랜잭션파트 (mysql 이용)
    //private final BackupMemberService backupMemberService; //백업 추가 여긴 분산 트랜잭션파트 (mysql 이용)

//    public MemberService( MemberRepository memberRepository) {
//                         //BackupMemberService backupMemberService, // BackupMemberService를 DI
//                         //BackupMemberRepository backupMemberRepository*/) { //BackupMemberRepository 이건 내가 추가해준것
//        this.backupMemberService = backupMemberService;
//        this.memberRepository = memberRepository;
//        this.backupMemberRepository = backupMemberRepository;
//    }

    //여기 위의 주석들은 분산 트랜잭션 실습. (생성자도 좀 이상하니까 눈치채보기)

    private final MemberRepository memberRepository;
    private final ApplicationEventPublisher applicationEventPublisher; //createMember 매서드를 위해

    public MemberService(MemberRepository memberRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.memberRepository = memberRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }




    public Member createMember(Member member) {
        // 이미 등록된 이메일인지 확인
        verifyExistsEmail(member.getEmail());
        Member savedMember = memberRepository.save(member);

         //if문 부분만! rollback 테스트를 위한 createMember() 메서드 수정
         //(회원 정보를 저장하고 메서드가 종료되기 전에 강제로 RuntimeException 이 발생하도록 수정)

        //예외 터뜨렸으니 H2 웹 콘솔을 통해서 post핸들러매서드 사용 이후에도 MEMBER 테이블을 조회해도 저장된 회원 정보가 없는 것 역시 확인가능.

        //backupMemberService.createBackupMember(new BackupMember(member.getEmail(), member.getName(), member.getPhone()));
        //MemberService의 createMember() 메서드는 위와 같이 데이터베이스에 회원 정보를 등록할 때, 백업용 회원 정보 데이터베이스에 추가로 회원 정보를 저장하도록 구현
        //테스트 결과
        //이제 애플리케이션을 실행시키고, Postman으로 postMember()에 요청을 전송하면
        //두 개의 데이터베이스에 각각 회원 정보를 저장하기 위한 작업을 하다가 BackupMemberService에서 예외가 발생하기 때문에
        //두 개의 데이터베이스에는 모두 데이터가 저장되지 않습니다.
        //이처럼 분산 트랜잭션을 적용하면 서로 다른 리소스에 대한 작업을 수행하더라도
        //하나의 작업처럼 관리하기 때문에 원자성을 보장할 수 있다는 사실을 기억하기.
        //여기 위의 8줄은 백업 추가 여긴 분산 트랜잭션파트 (mysql 이용)

        log.info("# Saved member");
        applicationEventPublisher.publishEvent(new MemberEvent(savedMember));


        return savedMember;
    }






    @Transactional(propagation = Propagation.REQUIRED)
    //propagation 애트리뷰트의 값으로 Propagation.REQUIRED를 지정하면 메서드 실행 시,
    //현재 진행 중인 트랜잭션이 존재하면 해당 트랜잭션을 사용하고, 존재하지 않으면 새 트랜잭션을 생성
    public Member updateMember(Member member) {
        Member findMember = findVerifiedMember(member.getMemberId());

        Optional.ofNullable(member.getName())
                .ifPresent(name -> findMember.setName(name));
        Optional.ofNullable(member.getPhone())
                .ifPresent(phone -> findMember.setPhone(phone));
        Optional.ofNullable(member.getMemberStatus())
                .ifPresent(memberStatus -> findMember.setMemberStatus(memberStatus));

        return memberRepository.save(findMember);
        //@Transactional 붙어있으니 
        //그냥 return findMember 해도 db에 저장 된다.(애너테이션 안에 있는 merge의 역할, 변경된 내용을 반영)
    }




    @Transactional(readOnly = true) //클래스 레벨보다 먼저 적용
    //조회 메서드에는 readonly 속성을 true로 지정해서 JPA가 자체적으로 성능 최적화 과정을 거치도록 하는것 (불필요 추가동작 줄임)
    public Member findMember(long memberId) {
        return findVerifiedMember(memberId);
    }

    public Page<Member> findMembers(int page, int size) {
        return memberRepository.findAll(PageRequest.of(page, size,
                Sort.by("memberId").descending()));
    }

    public void deleteMember(long memberId) {
        Member findMember = findVerifiedMember(memberId);

        memberRepository.delete(findMember);
    }

    public Member findVerifiedMember(long memberId) {
        Optional<Member> optionalMember =
                memberRepository.findById(memberId);
        Member findMember =
                optionalMember.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return findMember;
    }

    private void verifyExistsEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent())
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
    }
}
