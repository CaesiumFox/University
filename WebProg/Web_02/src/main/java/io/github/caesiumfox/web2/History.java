package io.github.caesiumfox.web2;

public class History extends java.util.ArrayList<History.Entry> {
    public static class Entry implements java.io.Serializable {
        public double x, y, r;
        public boolean hit;
        public java.time.LocalDateTime script_start;
        public java.time.Duration script_duration;
        public String toHtml() {
            return "<tr><td>" + String.valueOf(x) +
                    "</td><td>" + String.valueOf(y) +
                    "</td><td>" + String.valueOf(y) +
                    "</td><td>" + (hit ? "Да" : "Нет") +
                    "</td><td>" + script_start.format(new java.time.format.DateTimeFormatterBuilder()
                            .appendPattern("dd.MM.yyyy HH:mm:ss")
                            .toFormatter()) +
                    "</td><td>" + String.valueOf(script_duration.toNanos()) +
                    "</td></tr>";
        }
    }
}
