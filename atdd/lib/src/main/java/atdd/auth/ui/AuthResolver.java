package atdd.auth.ui;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import atdd.auth.application.AuthService;
import atdd.auth.domain.AdminAuthenticationPrincipal;
import atdd.auth.domain.AuthenticationPrincipal;
import atdd.auth.domain.NoneAuthenticationPrincipal;
import atdd.auth.infrastructure.AuthorizationExtractor;

public class AuthResolver implements HandlerMethodArgumentResolver {
    private AuthService authService;

    public AuthResolver(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class) ||
            parameter.hasParameterAnnotation(NoneAuthenticationPrincipal.class) ||
            parameter.hasParameterAnnotation(AdminAuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String credentials = AuthorizationExtractor.extract(webRequest.getNativeRequest(HttpServletRequest.class));

        if (parameter.hasParameterAnnotation(AuthenticationPrincipal.class)) {
            return authService.findMemberByToken(credentials);
        }

        if (parameter.hasParameterAnnotation(NoneAuthenticationPrincipal.class)) {
            return authService.findMemberByTokenOrElseGuest(credentials);
        }

        if (parameter.hasParameterAnnotation(AdminAuthenticationPrincipal.class)) {
            return authService.findMemberByTokenAndAdmin(credentials);
        }

        return authService.findMemberByTokenOrElseGuest(credentials);
    }
}
