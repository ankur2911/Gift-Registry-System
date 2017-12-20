<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Security-Policy" content="upgrade-insecure-requests">
<title>Login User</title>

<link href="css/giftregistry.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>
	<div class="container login">
		<div class="row">
			<div class="main">
				<h3>
					Please Log In, or <a href="signup.jsp">Sign Up</a>
				</h3>
				
				<div class="login-or">
					<hr class="hr-or">
					<span class="span-or"></span>
				</div>

				<form method="post" action="ClientClass">
					<div class="form-group">
						<label for="inputUsernameEmail">Username or email</label> <input
							type="text" required class="form-control" id="user_input" name="username">
					</div>
					<div class="form-group">
						<a class="pull-right" href="forgetpassword.jsp">Forgot password?</a> <label
							for="inputPassword">Password</label> <input type="password"
							class="form-control" required id="password_input" name="password">
					</div>
					<div class="checkbox pull-right">
						<label> <input type="checkbox"> Remember me
						</label>
					</div>
					<button type="submit" class="btn btn btn-primary">
						Log In</button>

				</form>
				<br />
				<%
					if (null != session.getAttribute("invalid") && (String) session.getAttribute("invalid") == "invalid") {
						session.removeAttribute("invalid");
						session.setAttribute("invalid", "refresh");
				%>
				<p id="invalid">Invalid User name or password!</p>
				<%
					}
				%>
			</div>

		</div>
	</div>
	
	<script
		src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.js"
		type="text/javascript"></script>
	<script type='text/javascript'
		src="http://cdnjs.cloudflare.com/ajax/libs/jquery-ajaxtransport-xdomainrequest/1.0.1/jquery.xdomainrequest.min.js"></script>

</body>
</html>

