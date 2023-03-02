package com.codestates.backup.service;

import com.codestates.backup.entity.BackupMember;
import com.codestates.backup.repository.BackupMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BackupMemberService {
    private final BackupMemberRepository backupMemberRepository;

    public BackupMemberService(BackupMemberRepository backupMemberRepository) {
        this.backupMemberRepository = backupMemberRepository;
    }

    @Transactional
    public void createBackupMember(BackupMember backupMember) {
        backupMemberRepository.save(backupMember);

        // 회원 정보 저장 중에 예외 발생을 시뮬레이션하기 위해 RuntimeException을 발생
        throw new RuntimeException("multi datasource rollback test");
    }
}
