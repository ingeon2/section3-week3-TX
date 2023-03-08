package com.codestates.event;

import com.codestates.member.entity.Member;
import lombok.Getter;

@Getter
public class MemberEvent { //EventHandler의 handleEmailSending 매서드를 위해 전달역할만 하는 클래스.
    private Member member;

    public MemberEvent(Member member) {
        this.member = member;
    }
}
