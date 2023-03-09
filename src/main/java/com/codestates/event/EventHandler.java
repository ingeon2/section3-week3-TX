package com.codestates.event;


import com.codestates.exception.BusinessLogicException;
import com.codestates.exception.ExceptionCode;
import com.codestates.helper.EmailSender;
import com.codestates.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventHandler {
    private final EmailSender emailSender;
    private final MemberService memberService;

    public EventHandler(EmailSender emailSender, MemberService memberService) {
        this.emailSender = emailSender;
        this.memberService = memberService;
    }

    @EventListener //MemberService에서 회원 등록 이벤트를 비동기적으로 먼저 보내고
    // 이 이벤트를 리스닝(Listening)하는 곳에서 이메일을 보낼 수 있습니다.
    // 즉, MemberService의 ceateMember 매서드는 여기로 보냄.
    @Async
    public void handleEmailSending(MemberEvent memberEvent) { //MemberEvent 클래스는 순전히 전달 역할만 충실
        try {
            log.info("# Send email");
            emailSender.sendEmail("any email message");
        }
        catch (Exception e) { //이메일 전송 실패한다면!
            log.error("MailSendException happended : ", e);
            memberService.deleteMember(memberEvent.getMember().getMemberId()); //롤백 지점
            throw new BusinessLogicException(ExceptionCode.FAIL_TO_SEND_EMAIL); //이거를 위해 ExceptionCode 에서 새로 만들엇자넝
        }
    }

}
