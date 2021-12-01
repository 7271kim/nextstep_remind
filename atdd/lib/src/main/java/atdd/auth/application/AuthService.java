package atdd.auth.application;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import atdd.auth.domain.LoginMember;
import atdd.auth.dto.TokenRequest;
import atdd.auth.dto.TokenResponse;
import atdd.auth.exception.AuthorizationException;
import atdd.auth.infrastructure.JwtTokenProvider;
import atdd.member.domain.Member;
import atdd.member.domain.MemberRepository;

@Service
public class AuthService {
    private MemberRepository memberRepository;
    private JwtTokenProvider jwtTokenProvider;
    private PasswordEncoder passwordEncoder;

    public AuthService(MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public TokenResponse login(TokenRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail()).orElseThrow(AuthorizationException::new);
        member.checkPassword(passwordEncoder.encode(request.getPassword()));

        String token = jwtTokenProvider.createToken(request.getEmail());
        return new TokenResponse(token);
    }

    public LoginMember findMemberByToken(String credentials) {
        if (!jwtTokenProvider.validateToken(credentials)) {
            return new LoginMember();
        }

        String email = jwtTokenProvider.getPayload(credentials);
        Member member = memberRepository.findByEmail(email).orElseThrow(RuntimeException::new);
        return new LoginMember(member.getId(), member.getEmail(), member.getAge());
    }
}
