package io.github.caesiumfox.web2;

public class History extends java.util.ArrayList<History.Entry> {
    public static class Entry implements java.io.Serializable {
        public double x, y, r;
        public boolean hit;
        public java.time.ZonedDateTime servletStart;
        public java.time.Duration servletDuration;
        public String toHtml() {
            return "<tr><td>" + String.valueOf(x) +
                    "</td><td>" + String.valueOf(y) +
                    "</td><td>" + String.valueOf(r) +
                    "</td><td>" + (hit ? "Да" : "Нет") +
                    "</td><td>" + servletStart.format(new java.time.format.DateTimeFormatterBuilder()
                            .appendPattern("dd.MM.yyyy HH:mm:ss (O)")
                            .toFormatter()) +
                    "</td><td>" + String.valueOf(servletDuration.toNanos()) +
                    "</td></tr>";
        }
    }
}
