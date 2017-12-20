<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Content-Security-Policy" content="upgrade-insecure-requests">
<title>Homepage</title>
<link href="css/homepage.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">


<script>
   global_data = '<%=session.getAttribute("nameobj")%>';
</script>

</head>
<body>
	<input type="hidden" id="json_data"></input>
	<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
	<a class="navbar-brand" style="color: white" id="welcome_print">Welcome</a>
	<div class="collapse navbar-collapse" id="navbarCollapse">
		<ul class="navbar-nav mr-auto">
			<li class="nav-item active"><a class="nav-link" href="#View">View
					My Registeries <span class="sr-only">(current)</span>
			</a></li>
			<li class="nav-item"><a class="nav-link" href="#Search">Add
					New Items</a></li>
			<li class="nav-item"><a class="nav-link" href="#ViewOthers">View
					Others</a></li>
		</ul>
		<div id="myprofile">
		<ul class="navbar-nav mr-auto">
			<li class="nav-item "><a class="nav-link" href="profilechange.jsp">My Profile <span class="sr-only">(current)</span></a></li>
		</ul>
		 </div>
		<form action="Logout" method="post">
			<button type="submit" class="btn btn btn-primary" id="logout">Log Out</button>
		</form>
	</div>
	</nav>
	<div id="paddingDiv"></div>
	<div class="container" id="View" style="height: auto; min-height: 300px;">
		<div  style="background-color: white;">
			<span class="glyphicon glyphicon-asterisk"></span><h3 class="featurette-heading">Select a registry to view</h3>
			<div><select class="form-group" id="registry_list">
			</select>
			</div>
			<div class="col-md-12">
				<table id="user_table" class="table table-condensed table-bordered">
					<thead>
						<tr>
							<th></th>
							<th>Item Code</th>
							<th>Name</th>
							<th>Cost</th>
							<th>Type</th>
							<th>Description</th>
							<th>Assigned User</th>
						</tr>
					</thead>
					<tbody id="View_Items">
					</tbody>
				</table>
				<hr>
				<br/><br/>
				<span>To make a new registry- click on share status of the
					new registry you want to create and then click add</span> <select
					id="select_visibility">
					<option>No one</option>
					<option>Specify Users</option>
					<option>Everyone</option>
				</select> <select id="select_visibility_users" multiple>
				</select>
				<button id="add_registry" class="btn btn btn-primary">Add</button>
			</div>
		</div>
	</div>
	<hr>
	<div class="container" id="Search" style="height: auto; min-height: 350px;" >
		<div >
			<div style="display:block"><span class="glyphicon glyphicon-asterisk"></span><h3 class="featurette-heading">Search:</h3> </div><br/>
			<p>Put value in double quotes to search as one.</p>
			<p>Each word separated by a space will be searched separately</p>
			<input id="search_text" type="text" class="form-control" style="width: 40%" width="40%" name="search_text" /> 
			<input id="search_button" type="button" class="btn btn btn-primary" value="Submit" />
			<div class="col-md-12" id="search_div"></div>
		</div>
		
	</div>
	<hr>
	<div class="container" id="ViewOthers" style="height: auto; min-height: 350px;" >
		<div >
			<span class="glyphicon glyphicon-asterisk"></span><h3 class="featurette-heading">List of registries visible to you</h3>
			<div class="col-md-5">
				<table id="visibleU_list">
					<thead>
						<tr>
							<th></th>
							<th>User</th>
							<th>Registry</th>
							<th>Count of Items</th>
						</tr>
					</thead>
					<tbody id="visibleU_list_body">
					</tbody>
				</table>
			</div>
			<div class="col-md-12">
			<br/>
			<p>Click on plus button to assign the item to yourself</p>
				<table id="others_table" data-registry=0;>
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
					<tbody id="others_items">
					</tbody>
				</table>
				<br/><br/>
			</div>
		</div>
</div>
		<script
			src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.js"
			type="text/javascript"></script>
		<script type='text/javascript'
			src="http://cdnjs.cloudflare.com/ajax/libs/jquery-ajaxtransport-xdomainrequest/1.0.1/jquery.xdomainrequest.min.js"></script>
		<script type="text/javascript" src="js/script.js"></script>
		<script type="text/javascript" src="js_library/ddtf.js"></script>
</body>
</html>