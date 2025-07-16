package io.github.Guimaraes131.libraryapi.security;

import io.github.Guimaraes131.libraryapi.model.User;
import io.github.Guimaraes131.libraryapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService service;
    private final PasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String rawPassword = authentication.getCredentials().toString();
        User user = service.getByLogin(login);

        String cripPassword = user.getPassword();
        boolean passwordMatches = encoder.matches(rawPassword, cripPassword);

        if (passwordMatches) {
            return new CustomAuthentication(user);
        }

        throw new BadCredentialsException("Invalid username and/or password");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
