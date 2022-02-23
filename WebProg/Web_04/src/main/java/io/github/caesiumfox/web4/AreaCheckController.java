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
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Controller
@ApplicationScope
public class AreaCheckController {
    static class ResponseData {
        private Iterable<HistoryEntry> entries;
        public ResponseData(Iterable<HistoryEntry> entries) {
            this.entries = entries;
        }
    }

    @Autowired
    private HistoryEntryRepository historyEntryRepository;

    @GetMapping(value="/")
    public String welcome() {
        return "welcome";
    }

    @GetMapping(value="/app")
    public String app() {
        return "app";
    }

    @PostMapping(value="/post-data", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseData postData(
            @RequestParam String user,
            @RequestParam String password,
            @RequestParam String type,
            @RequestParam List<Double> x,
            @RequestParam Double y,
            @RequestParam List<Double> r) {
        // TODO user repository
        calculateAndSave(x, y, r);
        return new ResponseData(historyEntryRepository.findAll());
    }

    @GetMapping(value="/current-data", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseData currentData(
            @RequestParam String user,
            @RequestParam String password) {
        // TODO user repository
        return new ResponseData(historyEntryRepository.findAll());
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
