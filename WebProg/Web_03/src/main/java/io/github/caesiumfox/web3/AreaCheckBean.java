package io.github.caesiumfox.web3;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.time.*;
import java.util.List;

@Named("areaCheck")
@ApplicationScoped
public class AreaCheckBean implements Serializable {
    private double x;
    private double y;
    private double r;
    private double imgX;
    private double imgY;
    private final HistoryEntryManager historyEntryManager = new HistoryEntryManager();
    private List<HistoryEntry> history;

    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }
    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }
    public double getR() {
        return r;
    }
    public void setR(double r) {
        this.r = r;
    }

    public double getImgX() {
        return imgX;
    }
    public void setImgX(double imgX) {
        this.imgX = imgX;
    }
    public double getImgY() {
        return imgY;
    }
    public void setImgY(double imgY) {
        this.imgY = imgY;
    }

    public List<HistoryEntry> getHistory() {
        history = historyEntryManager.findHistoryEntries();
        return history;
    }

    public boolean isHistoryEmptyAfterFetch() {
        return history.isEmpty();
    }

    @PostConstruct
    public void init() {
        x = 0;
        y = 0;
        imgX = 0;
        imgY = 0;
        r = 2;
    }


    public String checkHit() {
        return checkArgumentHit(x, y, r);
    }

    public String checkImgHit() {
        return checkArgumentHit(imgX, imgY, r);
    }

    private String checkArgumentHit(double x, double y, double r) {
        long start = System.nanoTime();
        boolean hit = false;
        if (x == 0)
            hit = y <= r && y >= -r;
        else if (y == 0)
            hit = 2 * x <= r && x >= -r;
        else if (x > 0 && y > 0)
            hit = 2 * x <= r && y <= r;
        else if (x < 0 && y < 0)
            hit = x + y >= -r;
        else if (x > 0 && y < 0)
            hit = 4 * (x * x + y * y) <= r * r;

        HistoryEntry entry = new HistoryEntry();

        entry.setX(x);
        entry.setY(y);
        entry.setR(r);
        entry.setHit(hit);
        entry.setDuration(System.nanoTime() - start);
        entry.setTime(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("Europe/Moscow")));

        historyEntryManager.addNew(entry);

        return "success";
    }
}
