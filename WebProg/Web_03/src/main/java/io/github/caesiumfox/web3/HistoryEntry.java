package io.github.caesiumfox.web3;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class HistoryEntry implements Serializable {
    private double x;
    private double y;
    private double r;
    private boolean hit;
    private ZonedDateTime time;
    private long duration;

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
    public boolean isHit() {
        return hit;
    }
    public void setHit(boolean hit) {
        this.hit = hit;
    }
    public ZonedDateTime getTime() {
        return time;
    }
    public void setTime(ZonedDateTime time) {
        this.time = time;
    }
    public long getDuration() {
        return duration;
    }
    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getHitMessage() {
        return hit ? "Да" : "Нет";
    }
    public String getTimeMessage() {
        return time.format(new java.time.format.DateTimeFormatterBuilder()
                .appendPattern("dd.MM.yyyy HH:mm:ss (O)")
                .toFormatter());
    }
    public String getDurationMessage() {
        return String.valueOf(duration);
    }

}
