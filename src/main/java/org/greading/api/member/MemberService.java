package org.greading.api.member;

import java.util.List;
import java.util.Optional;
import org.greading.api.request.JoinRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final Logger logger = LoggerFactory.getLogger(MemberService.class);

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Member login(String username, String password) {
        Member member = memberRepository.findByUsername(username).orElseThrow();
//                .orElseThrow(() -> new NotFoundException(Member.class, username));

        if (!member.checkPassword(passwordEncoder, password)) {
            throw new RuntimeException("Incorrect password.");
//            throw new UnauthorizedException("Incorrect password.");
        }
        logger.info("Login with [{}]", member.getUsername());
        return member;
    }

    @Transactional
    public Member signUp(JoinRequest request) {
//        if (confirmationTokenRepository.findByUserEmail(request.getEmail()).isPresent()) {
//            throw new DuplicatedException(Member.class, request.getEmail());
//        }
        if (findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException(request.getUsername());
//            throw new DuplicatedException(Member.class, request.getUsername());
        }
        if (findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException(request.getEmail());
//            throw new DuplicatedException(Member.class, request.getEmail());
        }

        Member member = save(new Member(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                request.getEmail()));

        logger.info("New member joined. [{}]", member.getUsername());

        return member;
    }

    @Transactional(readOnly = true)
    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Member save(Member member) {
        return memberRepository.save(member);
    }
}
