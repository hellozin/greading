package org.greading.api.config;

import org.greading.api.member.MemberService;
import org.greading.api.security.EntryPointUnauthorizedHandler;
import org.greading.api.security.JWT;
import org.greading.api.security.JwtAuthenticationProvider;
import org.greading.api.security.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

@Configuration
@EnableWebSecurity
public class SecurityConfigure extends WebSecurityConfigurerAdapter {

    @Value("${jwt.token.issuer}")
    String issuer;

    @Value("${jwt.token.clientSecret}")
    String clientSecret;

    @Value("${jwt.token.expirySeconds}")
    int expirySeconds;

    private final EntryPointUnauthorizedHandler entryPointUnauthorizedHandler;

    public SecurityConfigure(EntryPointUnauthorizedHandler entryPointUnauthorizedHandler) {
        this.entryPointUnauthorizedHandler = entryPointUnauthorizedHandler;
    }

    @Bean
    public JWT jwt() {
        return new JWT(issuer, clientSecret, expirySeconds);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider(JWT jwt, MemberService memberService) {
        return new JwtAuthenticationProvider(jwt, memberService);
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder builder, JwtAuthenticationProvider authenticationProvider) {
        builder.authenticationProvider(authenticationProvider);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/auth")
                .antMatchers("/member/signup");
//                .antMatchers("/static/**")
//                .antMatchers("/h2/**")
//                .antMatchers("/api/hcheck")
//                .antMatchers("/api/member/confirm")
//                .antMatchers("/api/recruit-types");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .exceptionHandling()
//                .accessDeniedHandler(accessDeniedHandlerImpl)
                .authenticationEntryPoint(entryPointUnauthorizedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/api/auth").permitAll()
                .antMatchers("/api/member").permitAll()
                .antMatchers("/api/member/confirm/send").hasRole("UNCONFIRMED")
                .antMatchers("/api/review/**").hasRole("MEMBER")
                .antMatchers("/api/tip/**").hasRole("MEMBER")
                .anyRequest().authenticated()
//                .accessDecisionManager(accessDecisionManager())
                .and()
                .formLogin()
                .disable();
        http
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
