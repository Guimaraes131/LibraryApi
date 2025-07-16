package io.github.Guimaraes131.libraryapi.security;

import io.github.Guimaraes131.libraryapi.model.User;
import io.github.Guimaraes131.libraryapi.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SocialLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final String DEFAULT_PASSWORD = "123";

    private final UserService service;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = token.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        User user = service.getByEmail(email);

        if (user == null) {
            user = User.builder()
                    .login(formatEmailToLogin(email))
                    .password(DEFAULT_PASSWORD)
                    .email(email)
                    .roles(List.of("OPERATOR"))
                    .build();

            service.create(user);
        }

        authentication = new CustomAuthentication(user);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        super.onAuthenticationSuccess(request, response, authentication);
    }

    private String formatEmailToLogin(String email) {
        return email.substring(0, email.indexOf("@"));
    }
}
