<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Security-Policy"
	content="upgrade-insecure-requests">
<title>Admin Console</title>
<link href="css/homepage.css" rel="stylesheet" type="text/css" />
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
			<a class="navbar-brand" href="#">Gift Registry</a>
		</div>

		<div style="float: right">
			
		</div>
		<form action="Logout" method="post">
			<button type="submit" class="btn btn btn-primary" id="logout">Log
				Out</button>
		</form>
	</div>
	</nav>
	<div class="container" id="View" style="margin-top: 50px">
		<div class="row featurette">
			<h1 class="featurette-heading">Items in Inventory</h1>
			<div class="col-md-12">
				<table id="inventory_table">
					<thead>
						<tr>
							<th></th>
							<th>Item Code</th>
							<th>Name</th>
							<th>Cost</th>
							<th>Type</th>
							<th>Description</th>
						</tr>
					</thead>
					<tbody id="inventory_items">
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="container" id="Add"
		style="margin-top: 50px; height: auto; min-height: 300px;">
		<div class="row featurette">
			<h1 class="featurette-heading">Add to inventory</h1>
			<form id="AddInventoryItem">
				<div class="form-group">
					<label for="name">Name:</label><br /> <input id="nameAdd"
						type="text" required name="name" value="" />
				</div>
				<div class="form-group">
					<label for="cost">Cost</label><br /> <input id="costAdd"
						type="number" required name="cost" value="" step="0.01" min="1" max="9999" />
				</div>
				<div class="form-group">
					<label for="type">Type:</label><br /> <input id="typeAdd"
						type="text" required name="type" value="" />
				</div>
				<div class="form-group">
					<label for="description">Description:</label><br />
					<textarea id="DescriptionAdd" class="form_input" maxlength="200"
						name="DescriptionAdd"></textarea>
				</div>
				<div class="form-group">
					<input id="AddI_button" type="submit" class="btn btn btn-primary"
						value="Add" />
				</div>
			</form>

		</div>
	</div>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="js/admin.js"></script>
</body>
</html>