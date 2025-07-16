package io.github.Guimaraes131.libraryapi.security;

import io.github.Guimaraes131.libraryapi.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityService {

    public User getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth instanceof CustomAuthentication customAuth) {
            return customAuth.getUser();
        }

        return null;
    }
}
