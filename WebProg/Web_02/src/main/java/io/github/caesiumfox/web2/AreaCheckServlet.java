package io.github.caesiumfox.web2;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.*;
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
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        try {
            Instant servletStart = Instant.now();
            ZonedDateTime servletStartDateTime = ZonedDateTime.of(
                    LocalDateTime.now(), ZoneId.of("Europe/Moscow"));

            double x, y, r;
            try {
                y = Double.parseDouble(request.getParameter("y"));
            } catch (NullPointerException | NumberFormatException ignore) {
                throw new IOException("No Y value, or it has wrong format");
            }
            try {
                r = Double.parseDouble(request.getParameter("r"));
            } catch (NullPointerException | NumberFormatException ignore) {
                throw new IOException("No R value, or it has wrong format");
            }

            if (y < -3 || y > 5)
                throw new IOException("Y value is out of range [-3, 5]");
            if (!(r == 1 || r == 2 || r == 3 || r == 4 || r == 5))
                throw new IOException("R value is not an integer between 1 and 5");

            History history = (History) session.getAttribute("history");
            if (history == null) {
                history = new History();
            }

            boolean hasXValue = false;
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
                    nextEntry.servletDuration = Duration.between(servletStart, Instant.now());
                    history.add(nextEntry);
                }
            }

            if (!hasXValue)
                throw new IOException("No X value");

            session.setAttribute("history", history);
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
