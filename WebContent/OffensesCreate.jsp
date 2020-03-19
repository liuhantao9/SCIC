<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create An Offense</title>
<link rel="stylesheet" 
          href="https://cdnjs.cloudflare.com/ajax/libs/bulma/0.7.5/css/bulma.css">
</head>
<body>
	<h1 class="title is-1" >Create Offense</h1>
	<form action="offensescreate" method="post">
		<div class="field">
		  <label class="label" for="offensetype">OffenseType</label>
		  <div class="control">
		    <input class="input" id="offensetype" name="offensetype" type="text" placeholder="Text input">
		  </div>
		</div>
		<div class="field">
		  <label class="label" for="offensedescription">OffenseDescription</label>
		  <div class="control">
		    <input class="input" id="offensedescription" name="offensedescription" type="text" placeholder="Text input">
		  </div>
		</div>
		<div class="field">
		  <label class="label" for="reportdate">ReportDate (yyyy-mm-dd)</label>
		  <div class="control">
		    <input class="input" id="reportdate" name="reportdate" type="text" placeholder="Text input">
		  </div>
		</div>
		<div class="field">
		  <label class="label" for="offensestartdate">OffenseStartDate (yyyy-mm-dd)</label>
		  <div class="control">
		    <input class="input" id="offensestartdate" name="offensestartdate" type="text" placeholder="Text input">
		  </div>
		</div>
		<div class="field">
		  <label class="label" for="offenseenddate">OffenseEndDate (yyyy-mm-dd)</label>
		  <div class="control">
		    <input class="input" id="offenseenddate" name="offenseenddate" type="text" placeholder="Text input">
		  </div>
		</div>
		<div class="field">
		  <label class="label" for="positionid">PositionId</label>
		  <div class="control">
		    <input class="input" id="positionid" name="positionid" type="text" placeholder="Text input">
		  </div>
		</div>
		<div class="field">
		  <label class="label" for="username">UserName</label>
		  <div class="control">
		    <input class="input" id="username" name="username" type="text" placeholder="Text input">
		  </div>
		</div>
		<div class="field">
		  <label class="label" for="authenticated">Authenticated</label>
		  <div class="control">
		    <input class="input" id="authenticated" name="authenticated" type="text" placeholder="Text input">
		  </div>
		</div>
		<div class="field">
		  <label class="label" for="archived">Archived</label>
		  <div class="control">
		    <input class="input" id="archived" name="archived" type="text" placeholder="Text input">
		  </div>
		</div>
		<div class="field">
		  <label class="label" for="severity">Severity</label>
		  <div class="control">
		    <input class="input" id="severity" name="severity" type="text" placeholder="Text input">
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
</body>
</html>