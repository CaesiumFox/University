package io.github.caesiumfox.web4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.annotation.ApplicationScope;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Controller
@ApplicationScope
public class AreaCheckController {
    static class ResponseData {
        private boolean success;
        private String message;
        private Iterable<HistoryEntry> entries;
        public ResponseData(boolean success, String message, Iterable<HistoryEntry> entries) {
            this.success = success;
            this.message = message;
            this.entries = entries;
        }
    }
    static class RegisterResponse {
        private boolean success;
        private String message;
        public RegisterResponse(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
    }

    private HistoryEntryRepository historyEntryRepository;
    private UserRepository userRepository;

    @Autowired
    public AreaCheckController(HistoryEntryRepository historyEntryRepository, UserRepository userRepository) {
        this.historyEntryRepository = historyEntryRepository;
        this.userRepository = userRepository;
    }

    @GetMapping(value="/")
    public String welcome() {
        return "welcome";
    }

    @GetMapping(value="/app")
    public String app(
            @RequestParam String username,
            @RequestParam String password) {
        Optional<User> userGot = userRepository.findById(username);
        if (!userGot.isPresent())
            return "no-app";
        if (userGot.get().getPassHashStr()
                .equals(PasswordManager
                        .hashPassword(password, userGot.get().getPassSaltStr())))
            return "app";
        return "no-app";
    }

    @PostMapping(value="/post-data", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseData postData(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String type,
            @RequestParam List<Double> x,
            @RequestParam Double y,
            @RequestParam List<Double> r) {
        Optional<User> userGot = userRepository.findById(username);
        if (!userGot.isPresent())
            return new ResponseData(false, "No user with name '" + username + "'", null);
        if (userGot.get().getPassHashStr()
                .equals(PasswordManager
                        .hashPassword(password, userGot.get().getPassSaltStr()))) {
            calculateAndSave(x, y, r);
            return new ResponseData(true, "Success", historyEntryRepository.findAll());
        }
        return new ResponseData(false, "Wrong password", null);
    }

    @GetMapping(value="/current-data", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseData currentData(
            @RequestParam String username,
            @RequestParam String password) {
        Optional<User> userGot = userRepository.findById(username);
        if (!userGot.isPresent())
            return new ResponseData(false, "No user with name '" + username + "'", null);
        if (userGot.get().getPassHashStr()
                .equals(PasswordManager
                        .hashPassword(password, userGot.get().getPassSaltStr())))
            return new ResponseData(true, "Success", historyEntryRepository.findAll());
        return new ResponseData(false, "Wrong password", null);
    }

    @PostMapping(value="/register", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public RegisterResponse register(
            @RequestParam String username,
            @RequestParam String password) {
        if (userRepository.existsById(username)) {
            return new RegisterResponse(false, "User named '" + username + "' already exists");
        }
        if (password.length() < 4) {
            return new RegisterResponse(false, "The password must have at least 4 characters");
        }
        String salt = PasswordManager.generateSalt();
        User user = new User();
        user.setUsername(username);
        user.setPassHashStr(PasswordManager.hashPassword(password, salt));
        user.setPassSaltStr(salt);
        userRepository.save(user);
        return new RegisterResponse(true, "Success");
    }

    private void calculateAndSave(
            List<Double> x,
            Double y,
            List<Double> r) {
        long arrSize = x.size();
        if (arrSize != r.size())
            return;
        long start = System.nanoTime();
        for (int i = 0; i < arrSize; i++) {
            HistoryEntry historyEntry = new HistoryEntry();
            historyEntry.setX(x.get(i));
            historyEntry.setY(y);
            historyEntry.setR(r.get(i));
            historyEntry.setHit(hit(x.get(i), y, r.get(i)));
            historyEntry.setTime(ZonedDateTime.of(LocalDateTime.now(),
                    ZoneId.of("Europe/Moscow")));
            historyEntry.setDuration(System.nanoTime() - start);
            historyEntryRepository.save(historyEntry);
        }
    }

    private boolean hit(double x, double y, double r) {
        if (x == 0)
            return y >= -r && y <= r;
        if (y == 0)
            return 2 * x >= -r && x <= r;
        if (x > 0 && y > 0)
            return x * x + y * y <= r * r;
        if (x > 0 && y < 0)
            return x <= r && y >= -r;
        if (x < 0 && y > 0)
            return 2 * (y - x) <= r;
        return false;
    }
}
