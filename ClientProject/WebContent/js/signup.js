$(document).ready(function() {
	var contentType ="application/x-www-form-urlencoded; charset=utf-8";
	
	
	function auth_check(d){
		if(JSON.stringify(d).includes("[-2]")){
			alert("authentication problem/error at server");
		}
	}
	$("#RegistrationForm").submit(function(e){
		e.preventDefault();
		var username=$("#EnterName").val();
		var email=$("#EnterEmail").val();
		var password=$("#EnterPass").val();
		var question=$('#sel1 :selected').index();
		var answer=$("#EnterAnswer").val();
		if(answer===""){
			answer="NONE";
		}
		if(question==0 &&answer==""){
			alert("Please write an answer");
		}
		else{
			$.ajax({
	        	type: "POST",
	            url: "http://localhost:8444/WebServiceMaven/backend/RegisterUser",
	            dataType: "json",
	            data: {
	            	userIDcheck:"121"+123+"1234",
	            	userID:123,
	            	username: username,
	            	password: password,
	            	email:email,
	            	question: question,
	            	answer: answer	            	
	            },
	            contentType:contentType,
	            success: function( data, textStatus, jqXHR) {
	            	console.log(data);
	            	if(data.result[0]>=0){
	            		alert("registration successfull");
	            		window.location="index.jsp";
	            	}
	            	if(data.result[0]==-1){
	            		alert("username already exists");
	            	}
	            	auth_check(data);
//	            	console.log(data);
	            },
	            error: function(jqXHR, textStatus, errorThrown){
	            	console.log("Something really bad happened " + jqXHR + errorThrown);
	            }
	        }); 
		}

	});
})