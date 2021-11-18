package io.github.caesiumfox.web2;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class History extends ArrayList<History.Entry> {
    public static class Entry implements Serializable {
        public double x;
        public double y;
        public double r;
        public boolean hit;
        public LocalDateTime script_start;
        public Duration script_duration;
    }

    public History(int capacity) {
        super(capacity);
    }

    public History() {
        super();
    }
}
