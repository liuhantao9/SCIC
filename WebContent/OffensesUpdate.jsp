<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update An Offense</title>
<link rel="stylesheet" 
          href="https://cdnjs.cloudflare.com/ajax/libs/bulma/0.7.5/css/bulma.css">
</head>
<body>
	<h1 class="title is-1" >Update Offense Description</h1>
	<form action="offensesupdate" method="post">
		<div class="field">
		  <label class="label" for="offenseid">OffenseId</label>
		  <div class="control">
		    <input class="input" id="offenseid" name="offenseid" type="text" placeholder="Please enter username">
		  </div>
		</div>
		<div class="field">
		  <label class="label" for="offensedescription">New Description</label>
		  <div class="control">
		    <input class="input" id="offensedescription" name="offensedescription" type="text" placeholder="Please enter new description">
		  </div>
		</div>
		<button class="button is-primary" type="submit">submit</button>
	</form>
	<br/><br/>
	<p>
		<span id="successMessage"><b>${messages.success}</b></span>
	</p>
</body>
</html>