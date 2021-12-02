package atdd.auth.application;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import atdd.auth.dto.TokenRequest;
import atdd.auth.dto.TokenResponse;
import atdd.auth.exception.AuthorizationException;
import atdd.auth.infrastructure.JwtTokenProvider;
import atdd.member.constant.UserType;
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
        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new AuthorizationException();
        }

        String token = jwtTokenProvider.createToken(request.getEmail());
        return new TokenResponse(token);
    }

    public Member findMemberByToken(String credentials) {
        if (!jwtTokenProvider.validateToken(credentials)) {
            return Member.EMTPY;
        }

        String email = jwtTokenProvider.getPayload(credentials);
        return memberRepository.findByEmail(email).orElseThrow(RuntimeException::new);
    }

    public Member findMemberByTokenOrElseGuest(String credentials) {
        if (!jwtTokenProvider.validateToken(credentials)) {
            throw new AuthorizationException();
        }

        String email = jwtTokenProvider.getPayload(credentials);
        return memberRepository.findByEmail(email).orElseThrow(RuntimeException::new);
    }

    public Member findMemberByTokenAndAdmin(String credentials) {
        if (!jwtTokenProvider.validateToken(credentials)) {
            throw new AuthorizationException();
        }

        String email = jwtTokenProvider.getPayload(credentials);
        return memberRepository.findByEmailAndUserType(email, UserType.ADMIN).orElseThrow(RuntimeException::new);
    }

}
