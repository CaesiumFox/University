package io.github.caesiumfox.web4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.ApplicationScope;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

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

    private HistoryEntryRepository historyEntryRepository;
    private UserRepository userRepository;
    private AuthTokenRepository authTokenRepository;

    @Autowired
    public AreaCheckController(HistoryEntryRepository historyEntryRepository, UserRepository userRepository) {
        this.historyEntryRepository = historyEntryRepository;
        this.userRepository = userRepository;
    }

    @GetMapping(value="/")
    public String welcome(@CookieValue String authToken, HttpServletRequest request) {
        if (authTokenRepository.existsById(authToken)) {
            return "redirect:/app";
        }
        return "welcome";
    }

    @GetMapping(value="/app")
    public String app(@CookieValue String authToken) {
        if (authTokenRepository.existsById(authToken))
            return "app";
        return "welcome";
    }

    @PostMapping(value="/post-data", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseData postData(
            @CookieValue String authToken,
            @RequestParam List<Double> x,
            @RequestParam Double y,
            @RequestParam List<Double> r) {
        if (authTokenRepository.existsById(authToken)) {
            calculateAndSave(x, y, r);
            return new ResponseData(true, "Success", historyEntryRepository.findAll());
        }
        return new ResponseData(false, "Wrong authToken", null);
    }

    @GetMapping(value="/current-data", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseData currentData(@CookieValue String authToken) {
        if (authTokenRepository.existsById(authToken))
            return new ResponseData(true, "Success", historyEntryRepository.findAll());
        return new ResponseData(false, "Wrong authToken", null);
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
