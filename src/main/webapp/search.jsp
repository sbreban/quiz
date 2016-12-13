<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Search</title>
    </head>
    <body>
    	<form action="${pageContext.request.contextPath}/searchServlet" method="get">
        Enter Column: <input type="text" name="column"/> <br/><br/>
        Enter Value: <input type="text" name="value"/> <br/><br/>
        <input type="submit" value="Search"/>
        </form>
    </body>
</html>