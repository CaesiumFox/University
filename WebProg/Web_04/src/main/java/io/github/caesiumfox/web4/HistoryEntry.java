package io.github.caesiumfox.web4;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity
@Table(name="HistoryEntry", schema = "public")
public class HistoryEntry implements Serializable {
    public final static String namedQueryName = "findHistoryEntries";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", unique = true)
    private long id;

    @Column(name="x")
    private double x;

    @Column(name="y")
    private double y;

    @Column(name="r")
    private double r;

    @Column(name="hit")
    private boolean hit;

    @Column(name="server_time")
    private ZonedDateTime time;

    @Column(name="server_duration")
    private long duration;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
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
    public String getHitStyle() {
        return "fill: var(--graph-" + (hit ? "hit" : "no-hit") + ");";
    }

}
