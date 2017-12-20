$(document).ready(function() {
	var contentType ="application/x-www-form-urlencoded; charset=utf-8";
	
	
	function auth_check(d){
		if(JSON.stringify(d).includes("[-2]")){
			alert("authentication problem/error at server");
		}
	}
	$("#ForgotForm").submit(function(e){
		e.preventDefault();
		var email=$("#EnterEmail").val();
		var question=$('#sel1 :selected').index();
		var answer=$("#EnterAnswer").val();
		if(answer===""){
			answer="NONE";
		}	
		
			$.ajax({
	        	type: "POST",
	            url: "http://localhost:8444/WebServiceMaven/backend/forgotPassword",
	            dataType: "json",
	            data: {
	            	userIDcheck:"121"+123+"1234",
	            	userID:123,
	            	email:email,
	            	question: question,
	            	answer: answer	            	
	            },
	            contentType:contentType,
	            success: function( data, textStatus, jqXHR) {
	            	console.log(data);
	            	auth_check(data);
	            	data=JSON.parse(data.result);
	            	if(data.result[0]===""){
	            		alert("incorrect details");
	            	}
	            	else{
	            		alert("password = "+ data.result[0])
	            	}
	            },
	            error: function(jqXHR, textStatus, errorThrown){
	            	console.log("Something really bad happened " + jqXHR + errorThrown);
	            }
	        }); 
		

	});
})