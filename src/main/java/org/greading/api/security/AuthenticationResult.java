package org.greading.api.security;

import org.greading.api.member.Member;

public class AuthenticationResult {

    private String apiToken;

    private Member member;

    public AuthenticationResult(String apiToken, Member member) {
        this.apiToken = apiToken;
        this.member = member;
    }

    public String getApiToken() {
        return apiToken;
    }

    public Member getMember() {
        return member;
    }
}
