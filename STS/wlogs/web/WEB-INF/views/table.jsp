<%@ page import="java.util.ArrayList" %>
<%@ page import="io.khasang.wlogs.model.TestTableModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>table</title>
</head>
<body>
<p>Hello!</p>
<h1> ${table} </h1>
<%
    ArrayList<TestTableModel> listTable = (ArrayList<TestTableModel>) request.getAttribute("listTable");
    String error = (String) request.getAttribute("error");
    if (error == null) {
        if (listTable != null) {
%>
<table>
    <tr>
        <th>server</th>
        <th>date</th>
        <th>issue</th>
        <th>comment</th>
    </tr>
    <%
            for (TestTableModel testTableModel : listTable) {
    %>
    <tr>
        <td><%=testTableModel.getServer()%>
        </td>
        <td><%=testTableModel.getDate()%>
        </td>
        <td><%=testTableModel.getIssue()%>
        </td>
        <td><%=testTableModel.getComment()%>
        </td>
    </tr>
    <%
            }
    %>
</table>
<%
        }
    } else {
%>
    <p><%=error%></>
<%
    }
%>

</body>
</html>