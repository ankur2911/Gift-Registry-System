<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Content-Security-Policy"
	content="upgrade-insecure-requests">
<title>Profile Update</title>
<link href="css/giftregistry.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script>
   global_data = '<%=session.getAttribute("nameobj")%>';
	console.log(global_data);
</script>
</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="home.jsp">Gift Registry</a>
		</div>

	</div>
	</nav>

	<div class="container" style="margin-top: 50px">

		<div class="row">
			<div class="col-sm-4"></div>
			<div class="col-sm-4">
				<h2>Profile Information</h2>
				<div class="form-group">
					<label for="showID">UserID</label> <input type="text" id="showID"
							class="form-control" name="showID" readonly>
				</div>
				
				<form role="form" id="EmailUpdate">
					<div class="form-group">
						<label for="showEmail">Email</label> <input type="email"
							id="showEmail" class="form-control" name="showEmail">
					</div>
					<div class="form-group">
						<input id="EmailButton" type="submit" required class="btn btn btn-primary"
							value="Update" />
					</div>
				</form>
				<form role="form"  id="AnswerUpdate">
					<div class="form-group">
						<label for="showAns">Security Answer</label> <input type="text"
							id="showAnswer" class="form-control" required name="showAns">
					</div>
					<div class="form-group">
						<input id="AnswerButton" type="submit" class="btn btn btn-primary"
							value="Update" />
					</div>

				</form>
			</div>
			<div class="col-sm-4"></div>
		</div>

	</div>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="js/profileChange.js"></script>
</body>
</html>