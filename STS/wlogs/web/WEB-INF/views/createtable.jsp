<%@ page import="io.khasang.wlogs.model.StatisticModel" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: vvv
  Date: 12.02.16
  Time: 23:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:directive.include file="part_header.jsp"/>

<div>
    <table class="table table-striped table-bordered table-hover logs_list">
        <thead>
        <tr>
            <th>№</th>
            <th>Server</th>
            <th>Дата события</th>
            <th>issue</th>
            <th>Comment</th>
        </tr>
        </thead>
        <tbody>
        <%
            ArrayList<StatisticModel> listTable = (ArrayList<StatisticModel>) request.getAttribute("createtable");
            if (listTable != null) {
            int i = 1;
            for (StatisticModel statisticModel : listTable) {
        %>
        <tr>
            <td><%=i++%>
            </td>
            <td><%=statisticModel.getServer()%>
            </td>
            <td><%=statisticModel.getDate()%>
            </td>
            <td class="break_all"><%=statisticModel.getIssue()%>
            </td>
            <td class="break_all"><%=statisticModel.getComment()%>
            </td>
        </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>
</div>
<jsp:directive.include file="part_footer.jsp"/>