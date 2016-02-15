<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
</head>

<body>
<h1> ${greeting} </h1>
<center><img alt="Enter to system!" src="css/button_enter_blue.gif"></center>
<center>
    <table>
        <tr>
            <td>
                <input type="text" size="12">
            </td>
            <td>
                <button class="button">Registration</button>
            </td>
        </tr>
        <tr>
            <td>
                <input type="password" size="12">
            </td>
            <td>
                <button>Restore password</button>
            </td>
        </tr>
        <tr>
            <td>

            </td>
            <td>
                <button formaction="/tableview">Enter</button>
            </td>
        </tr>
    </table>
</center>


<p> ${tagline} </p>

</body>