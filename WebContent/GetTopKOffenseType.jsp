<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Get Top K Offense Type</title>
<link rel="stylesheet" 
          href="https://cdnjs.cloudflare.com/ajax/libs/bulma/0.7.5/css/bulma.css">
</head>
<body>
	<h1 class="title is-1" >Get Top K Offense Type</h1>
	<form action="gettopkoffensetype" method="post">
		<div class="field">
		  <label class="label" for="k">Number of K</label>
		  <div class="control">
		    <input class="input" id="k" name="k" type="text" placeholder="Please enter a number...">
		  </div>
		</div>
		<p>
			<button class="button is-primary" type="submit">submit</button>
		</p>
	</form>
	<br/><br/>
	<p>
		<span id="successMessage"><b>${messages.success}</b></span>
	</p>
	<br/>
	<h1>Find Top K Offense Type</h1>
        <table border="1">
            <tr>
                <th>OffenseType</th>
            </tr>
            <c:forEach items="${topKOffenseType}" var="type" >
                <tr>
                    <td><c:out value="${type}" /></td>
                </tr>
            </c:forEach>
       </table>
</body>
</html>