<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/style.css" />
<title>wlogs</title>
</head>
<body>
	<%@ include file="/WEB-INF/views/header.jsp"%>
	<div class="container">
		<div class="content" align="center">
			<h1>Regestration page</h1>
			<form action="registration" method="post">
				Account:<input type="text" name="username" id="username"/>
				Password:<input type="password" name="password" id="password"/>
				<input type="submit" value="Done"/>
			</form>
		</div>
	</div>
</body>
</html>