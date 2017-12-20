$(document).ready(function() {
	var contentType ="application/x-www-form-urlencoded; charset=utf-8";
	
	var items;
	global_data= global_data.replace('"{', '{');
	global_data= global_data.replace('"{', '{');
	global_data= global_data.replace('}"', '}');
	global_data= global_data.replace('}"', '}');
	global_data=JSON.parse(global_data);
	init();
	function init(){
		$("#showID").val(global_data.result[0]);
		console.log(global_data);
		getInfo();
	}
	function getInfo(){
		$.ajax({
        	type: "POST",
            url: "http://localhost:8444/WebServiceMaven/backend/UserInfo",
            dataType: "json",
            data: {
            	userIDcheck:"121"+global_data.result[0]+"1234",
            	userID:global_data.result[0]
            },
            contentType:contentType,
            success: function( data, textStatus, jqXHR) {
            	console.log(data);
            	auth_check(data);
            	data=JSON.parse(data.result);
            	$("#showEmail").val(data.result[0][0]);
            	$("#showAnswer").val(data.result[0][1]);
//            	console.log(data);
            },
            error: function(jqXHR, textStatus, errorThrown){
            	console.log("Something really bad happened " + jqXHR + errorThrown);
            }
        }); 
	}
	function auth_check(d){
		if(JSON.stringify(d).includes("[-2]")){
			alert("authentication problem");
		}
	}
	$("#EmailUpdate").submit(function(e){
		e.preventDefault();
		$.ajax({
        	type: "POST",
            url: "http://localhost:8444/WebServiceMaven/backend/UpdateEmail",
            dataType: "json",
            data: {
            	userIDcheck:"121"+global_data.result[0]+"1234",
            	userID:global_data.result[0],
            	email:$("#showEmail").val()
            },
            contentType:contentType,
            success: function( data, textStatus, jqXHR) {
            	console.log(data);
            	auth_check(data);
            	if(data.result[0]==1){
            		alert("Update successful");
            	}
//            	console.log(data);
            },
            error: function(jqXHR, textStatus, errorThrown){
            	console.log("Something really bad happened " + jqXHR + errorThrown);
            }
        }); 
	});
	$("#AnswerUpdate").submit(function(e){
		e.preventDefault();
		$.ajax({
        	type: "POST",
            url: "http://localhost:8444/WebServiceMaven/backend/UpdateAnswer",
            dataType: "json",
            data: {
            	userIDcheck:"121"+global_data.result[0]+"1234",
            	userID:global_data.result[0],
            	answer:$("#showAnswer").val()
            },
            contentType:contentType,
            success: function( data, textStatus, jqXHR) {
            	console.log(data);
            	auth_check(data);
            	if(data.result[0]==1){
            		alert("Update successful");
            	}
            		
//            	console.log(data);
            },
            error: function(jqXHR, textStatus, errorThrown){
            	console.log("Something really bad happened " + jqXHR + errorThrown);
            }
        }); 
	});
})