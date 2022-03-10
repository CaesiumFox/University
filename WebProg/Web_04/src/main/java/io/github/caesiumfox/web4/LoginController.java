package io.github.caesiumfox.web4;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.ApplicationScope;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
@ApplicationScope
public class LoginController {
    static class LoginRegisterResponse {
        private boolean success;
        private String message;
        private String token;
        public LoginRegisterResponse(boolean success, String message, String token) {
            this.success = success;
            this.message = message;
            this.token = token;
        }
    }
    private UserRepository userRepository;
    private AuthTokenRepository authTokenRepository;

    @PostMapping(value="/register", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public LoginRegisterResponse register(
            @RequestParam String username,
            @RequestParam String password,
            HttpServletResponse response) {
        if (userRepository.existsById(username)) {
            return new LoginRegisterResponse(false, "User named '" + username + "' already exists", "");
        }
        if (password.length() < 4) {
            return new LoginRegisterResponse(false, "The password must have at least 4 characters", "");
        }
        String salt = PasswordManager.generateSalt();
        User user = new User();
        user.setUsername(username);
        user.setPassHashStr(PasswordManager.hashPassword(password, salt));
        user.setPassSaltStr(salt);
        userRepository.save(user);

        AuthToken token = new AuthToken();
        token.setUsername(username);
        String tokenStr = authTokenRepository.save(token).getToken();
        response.addCookie(new Cookie("authToken", tokenStr));
        return new LoginRegisterResponse(true, "Success", tokenStr);
    }

    @PostMapping(value="/login", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public LoginRegisterResponse login(
            @RequestParam String username,
            @RequestParam String password,
            HttpServletResponse response) {
        Optional<User> userGot = userRepository.findById(username);
        if (userGot.isPresent()) {
            if (userGot.get().getPassHashStr()
                    .equals(PasswordManager
                            .hashPassword(password, userGot.get().getPassSaltStr()))) {
                AuthToken token = new AuthToken();
                token.setUsername(username);
                String tokenStr = authTokenRepository.save(token).getToken();
                response.addCookie(new Cookie("authToken", tokenStr));
                return new LoginRegisterResponse(true, "Success", tokenStr);
            }
            return new LoginRegisterResponse(false, "Wrong password", "");
        }
        return new LoginRegisterResponse(false, "There's no user named '" + username + "'", "");
    }
}
