<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>

<head>
    <meta charset="UTF-8">
    <title>Join tables</title>
    <link href="css/join.css" rel="stylesheet" type="text/css">

</head>
<body>

<table>
    <tr >
        <td id="header"colspan="2"><form class="home" action="/" method="get">
            <input class="buttons" type="submit" name="menu" value="Back to Menu">
        </form></td>

    </tr>


    <tr >
        <td id="main">
            <form  action="/showJoinedTables" method="get" enctype="text/plain" name="join">
                <fieldset>
                    <legend align="center"> ${join} </legend>
                    <label>
                        <select required multiple name="selection">
                            <option name="tbl1" value="0">${tblOne}</option>
                            <option name="tbl2" value="1">${tblTwo}</option>
                           <%-- <option value="3">Table 3</option>--%>
                        </select><br>
                    </label><br>

                    <button class="join_tables" type="submit"><img src="css/img/join.png" alt="join "
                                                                   style="vertical-align: middle;">&nbspJoin Tables
                    </button>
                </fieldset></form>
           </td>
        <td></td>


    </tr>

    <tr>
        <td colspan="2" id="cellar"><form class="logoff" action="logoff.php" method="get">
            <input class="buttons" type="submit" name="logoff" value="Log Off">
        </form></td>
        </tr>


</table>



</body>
</html>
