<%--
  Created by IntelliJ IDEA.
  User: vvv
  Date: 14.02.16
  Time: 12:38
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
            <ul class="dropdown">
                <li class="dropdown-top">
                    <a class="dropdown-top" href="">Menu</a>
                    <ul class="dropdown-inside">
                        <li><a href="">Table1</a></li>
                        <li><a href="">Table2</a></li>
                        <li><a href="">Table3</a></li>
                    </ul>
                </li>
            </ul>
        </td>
        <td align="right">
            <form>
                <p><button formaction="/">Logoff</button></p>
            </form>            <form>
                <p><button formaction="/tableview">Backup</button></p>
            </form>
            <form>
                <p><button formaction="/">Return</button></p>
            </form>
        </td>
    </tr>
        <tr>
            <td colspan="2">

                ${tableview}

            </td>
        </tr>
    </table>

</center>
</body>
</html>
