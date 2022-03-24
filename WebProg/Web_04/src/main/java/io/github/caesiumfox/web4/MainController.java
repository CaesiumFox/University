package io.github.caesiumfox.web4;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.github.caesiumfox.web4.entity.AuthToken;
import io.github.caesiumfox.web4.entity.AuthTokenRepository;
import io.github.caesiumfox.web4.entity.HistoryEntry;
import io.github.caesiumfox.web4.entity.HistoryEntryRepository;
import io.github.caesiumfox.web4.entity.User;
import io.github.caesiumfox.web4.entity.UserRepository;
import io.github.caesiumfox.web4.rest.HistoryResponse;
import io.github.caesiumfox.web4.rest.LoginRegisterRequest;
import io.github.caesiumfox.web4.rest.LoginRegisterResponse;
import io.github.caesiumfox.web4.rest.PostDataRequest;
import io.github.caesiumfox.web4.util.Calculator;
import io.github.caesiumfox.web4.util.PasswordManager;

@Controller
public class MainController {
    private final String invalidAuthToken = "nan";

    private @Autowired HistoryEntryRepository historyEntryRepository;
    private @Autowired AuthTokenRepository authTokenRepository;
    private @Autowired UserRepository userRepository;

    private String resolveUserReturnNull(String authToken) {
        if (authToken == null)
            return null;
        if (authToken.equals(invalidAuthToken))
            return null;
        Optional<AuthToken> tokenUserMap = authTokenRepository.findById(authToken);
        if (tokenUserMap.isPresent())
            return tokenUserMap.get().getUsername();
        return null;
    }

    @RequestMapping(value = "/")
    public String index(@CookieValue(defaultValue = invalidAuthToken) String authToken) {
        String user = resolveUserReturnNull(authToken);
        if (user == null)
            return "welcome";
        return "index";
    }

    @RequestMapping(value = "/current-data", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HistoryResponse currentData(@CookieValue(defaultValue = invalidAuthToken) String authToken) {
        String user = resolveUserReturnNull(authToken);
        if (user == null)
            return new HistoryResponse();
        return new HistoryResponse(user, historyEntryRepository.findAll());
    }

    @PostMapping(value = "/post-data", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HistoryResponse postData(
            @CookieValue(defaultValue="nan") String authToken,
            @RequestBody PostDataRequest postDataRequest,
            HttpServletResponse response) {
        String user = resolveUserReturnNull(authToken);
        if (user == null)
            return new HistoryResponse();
        try {
            if (postDataRequest.getX().size() > 9 || postDataRequest.getR().size() > 9)
                return new HistoryResponse();
            List<HistoryEntry> result = Calculator.calculateAndSave(
                    postDataRequest.getX(),
                    postDataRequest.getY(),
                    postDataRequest.getR(),
                    user);
            result.forEach(historyEntry -> historyEntryRepository.save(historyEntry));
            return new HistoryResponse(user, result);
        }
        catch (NumberFormatException ignored) {}
        return new HistoryResponse();
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public LoginRegisterResponse register(
            @RequestBody LoginRegisterRequest registerRequest,
            HttpServletResponse response) {
        Optional<User> user = userRepository.findById(registerRequest.getUsername());
        if (user.isPresent()) {
            return new LoginRegisterResponse(false, "USERNAME_EXISTS", null);
        }
        if (registerRequest.getPassword().length() < 4) {
            return new LoginRegisterResponse(false, "SHORT_PASSWORD", null);
        }
        if (registerRequest.getPassword().length() > 64) {
            return new LoginRegisterResponse(false, "LONG_PASSWORD", null);
        }
        if (registerRequest.getUsername().length() < 3) {
            return new LoginRegisterResponse(false, "SHORT_USERNAME", null);
        }
        if (registerRequest.getUsername().length() > 64) {
            return new LoginRegisterResponse(false, "LONG_USERNAME", null);
        }
        if (!registerRequest.getUsername().matches("\\w{3,64}")) {
            return new LoginRegisterResponse(false, "INVALID_USERNAME", null);
        }
        String salt = PasswordManager.generateSalt();
        User newUser = new User(registerRequest.getUsername(),
                PasswordManager.hashPasswordSalt(registerRequest.getPassword(), salt),
                salt);
        userRepository.save(newUser);

        AuthToken authToken = new AuthToken(registerRequest.getUsername());
        String tokenStr = authTokenRepository.save(authToken).getToken();
        Cookie authCookie = new Cookie("authToken", tokenStr);
        authCookie.setMaxAge(24 * 60 * 60);
        response.addCookie(authCookie);
        return new LoginRegisterResponse(true, "OK", tokenStr);
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public LoginRegisterResponse login(
            @RequestBody LoginRegisterRequest loginRequest,
            HttpServletResponse response) {
        Optional<User> user = userRepository.findById(loginRequest.getUsername());
        if (!user.isPresent()) {
            return new LoginRegisterResponse(false, "WRONG_USERNAME", null);
        }
        if (!loginRequest.getUsername().matches("[a-zA-Z0-9_][a-zA-Z0-9_\\-]{1,62}[a-zA-Z0-9_]")) {
            return new LoginRegisterResponse(false, "INVALID_USERNAME", null);
        }
        if (user.get().getPassHashStr().equals(
                    PasswordManager.hashPasswordSalt(loginRequest.getPassword(),
                    user.get().getPassSaltStr()))) {
            AuthToken authToken = new AuthToken(loginRequest.getUsername());
            String tokenStr = authTokenRepository.save(authToken).getToken();
            Cookie authCookie = new Cookie("authToken", tokenStr);
            authCookie.setMaxAge(24 * 60 * 60);
            response.addCookie(authCookie);
            return new LoginRegisterResponse(true, "OK", tokenStr);
        }

        return new LoginRegisterResponse(false, "WRONG_PASSWORD", null);
    }

    @PostMapping(value = "/logout")
    @ResponseBody
    public String logout(@CookieValue(defaultValue = invalidAuthToken) String authToken,
            HttpServletResponse response) {
        Cookie authCookie = new Cookie("authToken", "");
        authCookie.setMaxAge(0);
        response.addCookie(authCookie);
        return "success";
    }
}
