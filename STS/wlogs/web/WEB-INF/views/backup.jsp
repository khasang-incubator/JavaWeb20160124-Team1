<%--
  Created by IntelliJ IDEA.
  User: Azon
  Date: 08.02.2016
  Time: 20:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
    <title>Table View</title>
</head>
<body>

<center>

    <table border="0" width="70%">
        <tr>
            <td valign="top">
                ${backup}
            </td>
            <td align="right">
                <form>
                    <p><button formaction="/">Logoff</button></p>
                </form>
                <form>
                    <p><button formaction="/">Return</button></p>
                </form>
            </td>
        </tr>
        <tr>
            <td colspan="2">

            </td>
        </tr>
    </table>

</center>
</body>
</html>
