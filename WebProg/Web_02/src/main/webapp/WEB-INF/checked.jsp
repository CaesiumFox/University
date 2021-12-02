<%@ page import="io.github.caesiumfox.web2.History" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%! boolean error = false; %>
<%! String errs = ""; %>

<%
    History history = (History) session.getAttribute("history");
    Integer recentCountPtr = (Integer) session.getAttribute("recent_count");
    int recentCount = 0;
    if (recentCountPtr != null)
        recentCount = recentCountPtr;
    errs = (String)session.getAttribute("error");
    error = errs != null;
%>

<!DOCTYPE html>
<html>
    <head>
        <title>Авдеев Степан P3214</title>
        <link rel="icon" href="./favicon.png">
        <link rel="stylesheet" href="./allstyles.css">
    </head>
    <body onload="<%= error ? "alert('Server received wrong data: " + errs + "')" : "" %>">
        <header>
            <p>
            Авдеев Степан Сергеевич <span id="group_label">(P3214)</span>
            </p>
            <p>
            <span id="variant_label">Вариант: 3662</span>
            </p>
        </header>
        <div class="main">

            <div class="link_panel">
                Ниже представлена таблица результатов для
                <%= recentCount == 1 ? "последнего запроса." : "последних запросов." %>
                Для отправки нового запроса нажмите <a href="${pageContext.request.contextPath}">сюда</a>.
            </div>

            <div class="table_panel">
                <table id="result_table">
                    <col width="10%" span="4">
                    <col width="30%" span="2">
                    <thead>
                        <tr>
                            <th class="inverse_selected">X</th>
                            <th class="inverse_selected">Y</th>
                            <th class="inverse_selected">R</th>
                            <th class="inverse_selected">A</th>
                            <th class="inverse_selected">T</th>
                            <th class="inverse_selected">&Delta;T, ns</th>
                        </tr>
                    </thead>
                    <%
                        if (history != null) {
                            for (int i = history.size() - recentCount; i < history.size(); i++) {
                                History.Entry entry = history.get(i);
                                if (entry != null)
                                    out.println(entry.toHtml());
                            }
                        }
                    %>
                </table>
                <p>
                    X, Y, R&nbsp;&mdash; выбранные числа;<br>
                    A&nbsp;&mdash; попадание;<br>
                    T&nbsp;&mdash; текущее время;<br>
                    &Delta;T&nbsp;&mdash; время работы сервера от начала работы до получения этого результата.
                </p>
            </div>
        </div>
    </body>
</html>
