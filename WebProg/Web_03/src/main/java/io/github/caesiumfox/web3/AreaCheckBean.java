package io.github.caesiumfox.web3;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.time.*;

@Named("areaCheck")
@RequestScoped
public class AreaCheckBean implements Serializable {
    private double x;
    private double y;
    private double r;
    private String hitMessage;
    private String timeMessage;
    private String durationMessage;

    @PostConstruct
    public void init() {
        x = 0;
        y = 0;
        r = 2;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        if (x == Math.round(x))
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

    public String getHitMessage() {
        return hitMessage;
    }

    public String getTimeMessage() {
        return timeMessage;
    }

    public String getDurationMessage() {
        return durationMessage;
    }

    public String checkHit() {
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
        hitMessage = hit ? "Да" : "Нет";
        durationMessage = String.valueOf(System.nanoTime() - start);
        timeMessage = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("Europe/Moscow"))
                .format(new java.time.format.DateTimeFormatterBuilder()
                        .appendPattern("dd.MM.yyyy HH:mm:ss (O)")
                        .toFormatter());
        return "success";
    }
}
