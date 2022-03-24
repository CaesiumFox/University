package io.github.caesiumfox.web4.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import io.github.caesiumfox.web4.entity.HistoryEntry;

public class Calculator {
    public static List<HistoryEntry> calculateAndSave(
            List<Double> x,
            Double y,
            List<Double> r,
            String username) {
        List<HistoryEntry> result = new ArrayList<>(x.size() * r.size());
        long start = System.nanoTime();
        for (int ix = 0; ix < x.size(); ix++) {
            for (int ir = 0; ir < r.size(); ir++) {
                HistoryEntry historyEntry = new HistoryEntry(
                        x.get(ix), y, r.get(ir),
                        hit(x.get(ix), y, r.get(ir)),
                        ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("Europe/Moscow")),
                        System.nanoTime() - start,
                        username);
                result.add(historyEntry);
            }
        }
        return result;
    }

    private static boolean hit(double x, double y, double r) {
        if (r == 0)
            return x == 0 && y == 0;
        
        if (r < 0) {
            r = -r;
            x = -x;
            y = -y;
        }

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
