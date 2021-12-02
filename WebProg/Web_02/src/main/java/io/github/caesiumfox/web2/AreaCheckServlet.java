package io.github.caesiumfox.web2;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class AreaCheckServlet extends HttpServlet {
    Map<String, Double> xValues;
    List<String> xNames;

    @Override
    public void init() {
        xValues = new TreeMap<>();
        xValues.put("xm3", -3.0);
        xValues.put("xm2", -2.0);
        xValues.put("xm1", -1.0);
        xValues.put("x0", 0.0);
        xValues.put("x1", 1.0);
        xValues.put("x2", 2.0);
        xValues.put("x3", 3.0);
        xValues.put("x4", 4.0);
        xValues.put("x5", 5.0);
        xValues.put("x", 0.0);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        try {
            long servletStart = System.nanoTime();
            ZonedDateTime servletStartDateTime = ZonedDateTime.of(
                    LocalDateTime.now(), ZoneId.of("Europe/Moscow"));

            double x, y, r;
            int recentCount = 0;

            try {
                y = Double.parseDouble(request.getParameter("y"));
            } catch (NullPointerException | NumberFormatException ignore) {
                throw new Exception("No Y value, or it has wrong format");
            }
            try {
                r = Double.parseDouble(request.getParameter("r"));
            } catch (NullPointerException | NumberFormatException ignore) {
                throw new Exception("No R value, or it has wrong format");
            }

            if (!(r == 1 || r == 2 || r == 3 || r == 4 || r == 5))
                throw new Exception("R value is not an integer between 1 and 5");

            History history = (History) session.getAttribute("history");
            if (history == null) {
                history = new History();
            }

            boolean hasXValue = false;
            try {
                double extraX = Double.parseDouble(request.getParameter("x"));
                hasXValue = true;
                xValues.replace("x", extraX);
            } catch (NullPointerException | NumberFormatException ignore) {}
            for (String xName : xValues.keySet()) {
                String xParam = request.getParameter(xName);
                if (xParam != null) {
                    hasXValue = true;
                    x = xValues.get(xName);
                    History.Entry nextEntry = new History.Entry();
                    nextEntry.x = x;
                    nextEntry.y = y;
                    nextEntry.r = r;
                    nextEntry.hit = checkHit(x, y, r);
                    nextEntry.servletStart = servletStartDateTime;
                    nextEntry.servletDuration = Duration.of(
                            System.nanoTime() - servletStart, ChronoUnit.NANOS);
                    history.add(nextEntry);
                    recentCount++;
                }
            }

            if (!hasXValue)
                throw new Exception("No X value");

            session.setAttribute("history", history);
            session.setAttribute("recent_count", Integer.valueOf(recentCount));
            session.removeAttribute("error");
        }
        catch (Exception e) {
            session.setAttribute("error", e.getMessage());
        }
        request.getRequestDispatcher("/WEB-INF/checked.jsp").forward(request, response);
    }

    private boolean checkHit(double x, double y, double r) {
        if (x == 0)
            return y <= r && 2 * y >= -r;
        if (y == 0)
            return x <= r && x >= -r;
        if (x > 0 && y > 0)
            return x + y <= r;
        if (x < 0 && y > 0)
            return x >= -r && y <= r;
        if (x > 0 && y < 0)
            return 4 * (x * x + y * y) <= r * r;
        return false;
    }
}
