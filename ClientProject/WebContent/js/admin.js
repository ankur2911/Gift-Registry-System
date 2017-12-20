$(document).ready(function() {
	var contentType ="application/x-www-form-urlencoded; charset=utf-8";
	init();
	var items;
	function init(){
		global_data=JSON.parse(global_data);
		$("#welcome_print").text("Welcome "+global_data.result[1]);
		console.log(global_data);
		getItems();
	}
	function getItems(){
		$.ajax({
        	type: "POST",
            url: "http://localhost:8444/WebServiceMaven/backend/InventoryItems",
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
            	console.log(data);
            	populateInventoryTable(data.result);
            },
            error: function(jqXHR, textStatus, errorThrown){
            	console.log("Something really bad happened " + jqXHR + errorThrown);
            },
            complete: function(jqXHR, textStatus){
            	$('#add_registry').attr("disabled", false);
            }
        }); 
	}
	function populateInventoryTable(data){
    	$("#inventory_items").empty();
    	items=data.length;
    	for(var i=0; i<data.length; i++){
    			$("#inventory_items").append('<tr><td> <img class="plus_image" id="minus'+i +'" src="images/minus.jpg" alt="add"></td>'+
				  '<td>'+ data[i].item_code +'</td>'+
				  '<td id="Itemname'+i+'">'+ data[i].name +'</td>'+
				  '<td>'+ data[i].cost +'</td>'+
				  '<td>'+ data[i].type +'</td>'+
				  '<td>'+ data[i].description +'</td></tr>');
    		
	    
    	}
    }
	function auth_check(d){
		if(JSON.stringify(d).includes("[-2]")){
			alert("authentication problem");
		}
	}
	$(document).on("click",'#inventory_table tbody tr td img',function(){
    	var row=$(this).closest("tr");
    	var item_code;
    	var count=0;
    	row.children("td").each(function() {
			if(count==1){
				item_code=$(this).text();
			}
			count++;
		});
    	row.remove();
    	console.log(item_code);
    	delItem(item_code);
	});
	function delItem(item_code){
		$.ajax({
        	type: "POST",
            url: "http://localhost:8444/WebServiceMaven/backend/DeInventoryItem",
            dataType: "json",
            data: {
            	userIDcheck:"121"+global_data.result[0]+"1234",
            	userID:global_data.result[0],
            	item_code:item_code
            },
            contentType:contentType,
            success: function( data, textStatus, jqXHR) {
            	auth_check(data);            	
            },
            error: function(jqXHR, textStatus, errorThrown){
            	console.log("Something really bad happened " + jqXHR + errorThrown);
            }
        }); 
	}
	$("#AddInventoryItem").submit(function(e){
		e.preventDefault();
		console.log("dfd");
		name=$("#nameAdd").val();
		var check=0;
		var description=$("#DescriptionAdd").val()
		for(var i=0; i<items; i++){
			if($("#Itemname"+i).text()===name){
				alert("duplicate name");
				check=1;
			}
		}
		if(description===""){
			console.log("nothing");
			description="-";
		}
		if(check==0){
			$.ajax({
	        	type: "POST",
	            url: "http://localhost:8444/WebServiceMaven/backend/AddToInventory",
	            dataType: "json",
	            data: {
	            	userIDcheck:"121"+global_data.result[0]+"1234",
	            	userID:global_data.result[0],
	            	name:$("#nameAdd").val(),
	            	cost:$("#costAdd").val(),
	            	type:$("#typeAdd").val(),
	            	description:description
	            },
	            contentType:contentType,
	            success: function( data, textStatus, jqXHR) {
	            	console.log(data);
	            	auth_check(data);  
	            	getItems();
	            },
	            error: function(jqXHR, textStatus, errorThrown){
	            	console.log("Something really bad happened " + jqXHR + errorThrown);
	            }
	        }); 
		}
		
	})
})