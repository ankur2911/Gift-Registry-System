$(document).ready(function() {
	var contentType ="application/x-www-form-urlencoded; charset=utf-8";
	var no_of_registries=0;
	var item_list=[];
	var selected_userID;
	global_data= global_data.replace('"{', '{');
	global_data= global_data.replace('"{', '{');
	global_data= global_data.replace('}"', '}');
	global_data= global_data.replace('}"', '}');
	console.log(global_data);
	init();
	
	function init(){
		global_data=JSON.parse(global_data);
		console.log(global_data);
		$("#welcome_print").text("Welcome "+global_data.result[1]);
		getUserRegistries();
		populate_addR_users();
		populate_OthersList();
		get_UserItems(); 
	}
	function getUserRegistries(){
		$("#registry_list").empty();
		$.ajax({
        	type: "POST",
            url: "https://localhost:8444/WebServiceMaven/backend/GetRegistry",
            dataType: "json",
            async:false,
            data: {
            	userIDcheck:"121"+global_data.result[0]+"1234",
            	userID:global_data.result[0]
            },
            contentType:contentType,
            success: function( data, textStatus, jqXHR) {
            	console.log(data);
            	auth_check(data);
            	data=JSON.parse(data.result[0]);
            	for(i=0; i<data.registry.length; i++){
        			$("#registry_list").append("<option id='option"+(i+1)+"'>"+ data.registry[i]+"</option>");
        		}
            	
            },
            error: function(jqXHR, textStatus, errorThrown){
            	console.log("Something really bad happened " + jqXHR + errorThrown);
            },
            complete: function(jqXHR, textStatus){
            	$('#add_registry').attr("disabled", false);
            }
        }); 
	}
	function get_UserItems(){
		$.ajax({
        	type: "POST",
            url: "https://localhost:8444/WebServiceMaven/backend/RegistryItems",
            dataType: "json",
            async:false,
            data: {
            	userIDcheck:"121"+global_data.result[0]+"1234",
            	userID:global_data.result[0],
            	registry_id:$('#registry_list :selected').text()
            	
            },
            contentType:contentType,
            success: function( data, textStatus, jqXHR) {
            	console.log(data);
            	auth_check(data);
            	data=JSON.parse(data.result[0])
            	populateViewItems(data.result)
            	
            },
            error: function(jqXHR, textStatus, errorThrown){
            	console.log("Something really bad happened " + jqXHR + errorThrown);
            },
            complete: function(jqXHR, textStatus){
            	$('#add_registry').attr("disabled", false);
            }
        });
	}
	function populate_addR_users(){
		for(i=0; i<global_data.result[3].users.length; i++){
			$("#select_visibility_users").append("<option id='user"+(i+1)+"'>"+ global_data.result[3].users[i][0]+"</option>");
		}
	}
	$('#select_visibility').change(function() {
		if($('#select_visibility :selected').index()==1){
			$("#select_visibility_users").show();
		}
		else{
			$("#select_visibility_users").hide();
		}
    });
	function populateViewItems(data){
    	$("#View_Items").empty();
    	item_list=[];
    	for(var i=0; i<data.length; i++){
			$("#View_Items").append('<tr><td> <img class="plus_image" id="minus'+i +'" src="images/minus.jpg" alt="delete"></td>'+
				  '<td>'+ data[i].item_code +'</td>'+
				  '<td>'+ data[i].name +'</td>'+
				  '<td>'+ data[i].cost +'</td>'+
				  '<td>'+ data[i].type +'</td>'+
				  '<td>'+ data[i].description +'</td>'+
				  '<td>'+ data[i].assigned_user +'</td></tr>');
			item_list.push(data[i].item_code);
    	}
    }
	$('#registry_list').change(function() {
		get_UserItems();
		$("#search_button").click();
    });
	$("#add_registry").click(function(e){	
		var share_status=$('#select_visibility :selected').index();
		var share_list=0;
		var share_list_final=[];
		if($('#select_visibility :selected').index()==1){
			share_list=$('#select_visibility_users').val();
		}
		console.log("-----",share_list);
		if(share_list==null){
			alert("Please select atleast one user");
		}
		else{
			if(share_status==1){
				share_list.forEach(function(name){
					global_data.result[3].users.forEach(function(user){
						if(user[0]===name){
							share_list_final.push(user[1])
						}
					})
				})
			}
			else{
				share_list_final=0;
			}
			$.ajax({
	        	type: "POST",
	            url: "https://localhost:8444/WebServiceMaven/backend/AddRegistry",
	            dataType: "json",
	            data: {
	            	userIDcheck:"121"+global_data.result[0]+"1234",
	            	userID:global_data.result[0],
	            	share_status:share_status,
	            	share_list:JSON.stringify(share_list_final)
	            },
	            contentType:contentType,
	            success: function( data, textStatus, jqXHR) {
	            	auth_check(data);
	            	alert("New registry created. Registry id= "+data.result[0]);
	            	getUserRegistries();
	            	$("#registry_list").val(data.result[0]);
	            	$('#registry_list').change();
	            },
	            error: function(jqXHR, textStatus, errorThrown){
	            	console.log("Something really bad happened " + jqXHR + errorThrown);
	            },
	            complete: function(jqXHR, textStatus){
	            	$('#add_registry').attr("disabled", false);
	            }
	        });  
		}
		
	})
	$(document).on("click",'#user_table tbody tr td img',function(){
    	var row=$(this).closest("tr");
    	var registry_id=$('#registry_list :selected').text()
    	var item_code;
    	var count=0;
    	row.children("td").each(function() {
			if(count==1){
				item_code=$(this).text();
			}
			count++;
		});
    	row.remove();
    	console.log(registry_id);
    	console.log(item_code);
    	deleteItem(registry_id, item_code);
	});
	function deleteItem(registry_id, item_code){
		$.ajax({
        	type: "POST",
            url: "https://localhost:8444/WebServiceMaven/backend/DeleteItem",
            dataType: "json",
            data: {
            	userIDcheck:"121"+global_data.result[0]+"1234",
            	userID:global_data.result[0],
            	registry_id:registry_id,
            	item_code:item_code
            },
            contentType:contentType,
            success: function( data, textStatus, jqXHR) {
            	auth_check(data);
            	for(i=0; i<item_list.length;i++){
            		if(item_code==item_list[i]){
            			item_list.splice(i,1);
            			
            		}
            	}
            	
            },
            error: function(jqXHR, textStatus, errorThrown){
            	console.log("Something really bad happened " + jqXHR + errorThrown);
            },
            complete: function(jqXHR, textStatus){
            	$('#add_registry').attr("disabled", false);
            }
        }); 
	}
	function auth_check(d){
		if(JSON.stringify(d).includes("[-2]")){
			alert("authentication problem");
		}
	}
	$("#search_button").click(function(){
		var search = $("#search_text").val(); 
		search = search.replace(" ","+");
		console.log("dddd"+search);
		$.ajax({
        	type: "POST",
            url: "https://localhost:8444/WebServiceMaven/backend/Search",
            dataType: "json",
            data: {
            	userIDcheck:"121"+global_data.result[0]+"1234",
            	userID:global_data.result[0],
            	search:search
            },
            contentType:contentType,
            success: function( data, textStatus, jqXHR) {
            	console.log(data);
            	auth_check(data);
            	if(data.result.length!=0){
            		data=JSON.parse(data.result[0]);
                	populateSearchItems(data.result);
            	}
            },
            error: function(jqXHR, textStatus, errorThrown){
            	console.log("Something really bad happened " + jqXHR + errorThrown);
            },
            complete: function(jqXHR, textStatus){
            	$('#add_registry').attr("disabled", false);
            }
        }); 
	});
	function populateSearchItems(data){
    	$("#search_table").remove();
    	$("#search_div").append('<table id="search_table"><thead><tr><th></th><th>Item Code</th>'+
				'<th>Name</th><th>Cost</th><th>Type</th><th>Description</th></tr></thead>'+
				'<tbody id="Search_Items"></tbody></table>')
    	var item=[]
    	for(var i=0; i<data.length; i++){
    		cur_item=data[i].item_code;
    		if(item.indexOf(cur_item)== -1 && item_list.indexOf(cur_item)== -1){
    			item.push(cur_item);
    			$("#Search_Items").append('<tr><td> <img class="plus_image" id="minus'+i +'" src="images/plus.jpg" alt="add"></td>'+
				  '<td>'+ data[i].item_code +'</td>'+
				  '<td>'+ data[i].name +'</td>'+
				  '<td>'+ data[i].cost +'</td>'+
				  '<td>'+ data[i].type +'</td>'+
				  '<td>'+ data[i].description +'</td></tr>');
    		}
	    
    	}
    	var options = {
    			sortOptCallback: true
    		    };
    	$('#search_table').ddTableFilter(options); 
    }
	$(document).on("click",'#search_table tbody tr td img',function(){
    	var row=$(this).closest("tr");
    	var registry_id=$('#registry_list :selected').text()
    	var item_code;
    	var count=0;
    	row.children("td").each(function() {
//    		if(count==0){
//    			$(this).children("img").hide();
//			}
			if(count==1){
				item_code=$(this).text();
			}
			count++;
		});
    	row.remove();
    	console.log(registry_id);
    	console.log(item_code);
    	addItem(registry_id, item_code);
	});
	function addItem(registry_id, item_code){
		$.ajax({
        	type: "POST",
            url: "https://localhost:8444/WebServiceMaven/backend/AddItem",
            dataType: "json",
            data: {
            	userIDcheck:"121"+global_data.result[0]+"1234",
            	userID:global_data.result[0],
            	registry_id:registry_id,
            	item_code:item_code
            },
            contentType:contentType,
            success: function( data, textStatus, jqXHR) {
            	auth_check(data);
            	$('#registry_list').change();
            },
            error: function(jqXHR, textStatus, errorThrown){
            	console.log("Something really bad happened " + jqXHR + errorThrown);
            },
            complete: function(jqXHR, textStatus){
            	$('#add_registry').attr("disabled", false);
            }
        }); 
	}
	function populate_OthersList(){
		for(i=0; i<global_data.result[2].visible.length; i++){
			$("#visibleU_list_body").append('<tr><td> <img class="view_image" id="minus'+i +'" src="images/view.jpg" alt="add"></td>'+
					  '<td>'+ global_data.result[2].visible[i][0] +'</td>'+
					  '<td>'+ global_data.result[2].visible[i][1] +'</td>'+
					  '<td>'+ global_data.result[2].visible[i][2] +'</td></tr>');
		}
	}
	$(document).on("click",'#visibleU_list tbody tr td img',function(){
    	var row=$(this).closest("tr");
    	var username;
    	var uID;
    	var reg;
    	var count=0;
    	row.children("td").each(function() {
			if(count==1){
				username=$(this).text();
			}
			if(count==2){
				reg=$(this).text();
			}
			count++;
		});
    	for(i=0; i<global_data.result[3].users.length; i++){
    		if(global_data.result[3].users[i][0]===username)
    			uID=global_data.result[3].users[i][1]
    	}
    	selected_userID=uID;
    	$('#others_table').data('registry',reg);
    	console.log(uID+ "  "+ username+ " "+ reg);
    	view_OtherRegistry(uID, reg);
	});
	function view_OtherRegistry(userID, registry_id){
		$.ajax({
        	type: "POST",
            url: "https://localhost:8444/WebServiceMaven/backend/RegistryItems",
            dataType: "json",
            async:false,
            data: {
            	userIDcheck:"121"+userID+"1234",
            	userID:userID,
            	registry_id:registry_id
            },
            contentType:contentType,
            success: function( data, textStatus, jqXHR) {
            	console.log(data);
            	auth_check(data);
            	data=JSON.parse(data.result[0])
            	populateOtherUserItems(data.result)
            	
            },
            error: function(jqXHR, textStatus, errorThrown){
            	console.log("Something really bad happened " + jqXHR + errorThrown);
            },
            complete: function(jqXHR, textStatus){
            	$('#add_registry').attr("disabled", false);
            }
        });
	}
	function populateOtherUserItems(data){
		$("#others_items").empty();
    	for(var i=0; i<data.length; i++){
			$("#others_items").append('<tr><td> <img class="plus_image" id="assign'+i +'" src="images/plus.jpg" alt="delete"></td>'+
				  '<td>'+ data[i].item_code +'</td>'+
				  '<td>'+ data[i].name +'</td>'+
				  '<td>'+ data[i].cost +'</td>'+
				  '<td>'+ data[i].type +'</td>'+
				  '<td>'+ data[i].description +'</td></tr>');
			console.log(data[i].assigned_user);
			if(data[i].assigned_user!="NONE"){
				$("#assign"+i).hide();
			}
    	}
	}
	$(document).on("click",'#others_table tbody tr td img',function(){
    	var row=$(this).closest("tr");
    	var item_code;
    	var count=0;
    	row.children("td").each(function() {
    		if(count==0){
    			$(this).children("img").hide();
    		}
			if(count==1){
				item_code=$(this).text();
			}
			if(count==2){
				reg=$(this).text();
			}
			count++;
		});
    	assignToMe(item_code);
	});
	function assignToMe(item_code){
		var registry_id=$('#others_table').data('registry');
		$.ajax({
        	type: "POST",
            url: "https://localhost:8444/WebServiceMaven/backend/AssignToMe",
            dataType: "json",
            data: {
            	userIDcheck:"121"+global_data.result[0]+"1234",
            	userID:global_data.result[0],
            	username:global_data.result[1],
            	registry_id:registry_id,
            	item_code:item_code
            },
            contentType:contentType,
            success: function( data, textStatus, jqXHR) {
            	auth_check(data);
            },
            error: function(jqXHR, textStatus, errorThrown){
            	console.log("Something really bad happened " + jqXHR + errorThrown);
            },
            complete: function(jqXHR, textStatus){
            	$('#add_registry').attr("disabled", false);
            }
        }); 
	}
	
	
})