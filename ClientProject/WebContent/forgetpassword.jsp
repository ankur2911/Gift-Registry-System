<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Content-Security-Policy"
	content="upgrade-insecure-requests">
<title>Forgot Password</title>
<link href="css/giftregistry.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="index.jsp">Gift Registry</a>
		</div>

	</div>
	</nav>

	<div class="container" style="margin-top: 50px">

		<div class="row">
			<div class="col-sm-4"></div>
			<div class="col-sm-4">
				<form role="form" id="ForgotForm">
					
					<div class="form-group">
						<label for="inputEmail">Email</label> <input type="email"
							class="form-control" id="EnterEmail" name="email">
					</div>
					
					<div class="form-group">
						<label for="inputPassword">Select a Security Question</label>
						<div class="form-group">
							<select class="form-control" id="sel1">
								<option>Select</option>
								<option>What is your Mother's Maiden Name</option>
								<option>What is your Father's Middle Name</option>
							</select> 
							<input type="text" class="form-control" id="EnterAnswer" name="answer">
						</div>
					</div>
					<button type="submit" id="EnterSubmit" class="btn btn btn-primary">
						Submit</button>
				</form>
				<br />
				<div id="passwordfetch"></div>
			</div>
			<div class="col-sm-4"></div>
		</div>

	</div>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="js/forgotPassword.js"></script>
</body>
</html>