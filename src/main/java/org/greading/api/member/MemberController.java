package org.greading.api.member;

import org.greading.api.request.AuthenticationRequest;
import org.greading.api.request.JoinRequest;
import org.greading.api.security.AuthenticationResult;
import org.greading.api.security.JwtAuthentication;
import org.greading.api.security.JwtAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    private final AuthenticationManager authenticationManager;

    private final MemberService memberService;

    public MemberController(AuthenticationManager authenticationManager, MemberService memberService) {
        this.authenticationManager = authenticationManager;
        this.memberService = memberService;
    }

    @PostMapping("/auth")
    public String auth(AuthenticationRequest request) {
        JwtAuthenticationToken token = new JwtAuthenticationToken(request.getPrincipal(), request.getCredentials());
        AuthenticationResult authenticationResult = (AuthenticationResult) authenticationManager.authenticate(token).getDetails();
        return authenticationResult.getApiToken();
    }

    @PostMapping("/member/signup")
    public Member signUp(@RequestBody JoinRequest joinRequest) {
        return memberService.signUp(joinRequest);
    }

    @GetMapping("/member/me")
    public JwtAuthentication getMember(@AuthenticationPrincipal JwtAuthentication jwtAuthentication) {
        return jwtAuthentication;
    }

}
