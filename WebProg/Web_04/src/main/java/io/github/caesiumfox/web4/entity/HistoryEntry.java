package io.github.caesiumfox.web4.entity;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@lombok.Getter
@lombok.Setter
@lombok.EqualsAndHashCode
@lombok.ToString
public class HistoryEntry {
    private @Id @GeneratedValue long id;
    private double x;
    private double y;
    private double r;
    private boolean hit;
    private @Column(name = "server_time") ZonedDateTime time;
    private long duration;
    private @Column(name = "username") String user;

    public HistoryEntry() {}

    public HistoryEntry(double x,
                        double y,
                        double r,
                        boolean hit,
                        ZonedDateTime time,
                        long duration,
                        String user) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = hit;
        this.time = time;
        this.duration = duration;
        this.user = user;
    }
}
