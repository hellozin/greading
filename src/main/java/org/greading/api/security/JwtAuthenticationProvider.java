package org.greading.api.security;

import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

import org.greading.api.member.Member;
import org.greading.api.member.MemberService;
import org.greading.api.member.Role;
import org.greading.api.request.AuthenticationRequest;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JWT jwt;

    private final MemberService memberService;

    public JwtAuthenticationProvider(JWT jwt, MemberService memberService) {
        this.jwt = jwt;
        this.memberService = memberService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) authentication;
        AuthenticationRequest request = authenticationToken.authenticationRequest();
        try {
            Member member = memberService.login(request.getPrincipal(), request.getCredentials());
            Role role = member.isEmailConfirmed() ? Role.MEMBER : Role.UNCONFIRMED;
            JwtAuthenticationToken authenticated = new JwtAuthenticationToken(member.getId(), null, createAuthorityList(role.value()));
            String apiToken = jwt.newToken(JWT.Claims.of(member.getId(), member.getUsername(), member.getEmail(), new String[]{role.value()}));
            authenticated.setDetails(new AuthenticationResult(apiToken, member));
            return authenticated;
//        } catch (NotFoundException e) {
//            throw new UsernameNotFoundException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException(e.getMessage());
        } catch (DataAccessException e) {
            throw new AuthenticationServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(JwtAuthenticationToken.class);
    }
}
