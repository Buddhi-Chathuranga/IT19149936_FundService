$(document).ready(function() {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});
// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	// Form validation-------------------
	var status = validateFundForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	//If valid------------------------
	var type = ($("#hidFundIDSave").val() == "") ? "POST" : "PUT";
	$.ajax({
		url : "FundsAPI",
		type : type,
		data : $("#formFund").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onFundSaveComplete(response.responseText, status);
		}
	});
});
// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event) {
	$("#hidFundIDSave").val($(this).data("fundid"));
	$("#requesterName").val($(this).closest("tr").find('td:eq(0)').text());
	$("#requesterPhone").val($(this).closest("tr").find('td:eq(1)').text());
	$("#requesterMail").val($(this).closest("tr").find('td:eq(2)').text());
	$("#description").val($(this).closest("tr").find('td:eq(3)').text());
	$("#requesterNIC").val($(this).closest("tr").find('td:eq(4)').text());
});

//Delete

$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "FundsAPI",
		type : "DELETE",
		data : "fundID=" + $(this).data("fundid"),
		dataType : "text",
		complete : function(response, status) {
			onFundDeleteComplete(response.responseText, status);
		}
	});
});
// CLIENT-MODEL================================================================
function validateFundForm() {

	if ($("#requesterName").val().trim() == "") {
		return "Insert Requester Name.";
	}
	
	
	if ($("#requesterPhone").val().trim() == "") {
		return "Insert Phone Number.";
	}
	if (!validatePhone($("#requesterPhone").val().trim())) {
			return "Insert Valid Phone Number.";
		}
	
	
	
	if ($("#requesterMail").val().trim() == "") {
		return "Insert Requester Mail.";
	}

	 
	
	if ($("#description").val().trim() == "") {
		return "Insert Description.";
	}
	
	
	if ($("#requesterNIC").val().trim() == "") {
		return "Insert Requester NIC.";
	}
	var id = $("#requesterNIC").val().trim();
	var k = id.length;
	if(k == 11){}
	else{return "Invaid length of NIC."}
	
	for(var i=0 ; i<11 ; i++){
		  var j = id.charAt(i);

		  if(i==10){
			  if(j=='V'||j=='v'){}
			  else{  return "NIC should be end with 'V' or 'v'";  }
		  }
		  else{
			  if(j==0||j==1||j==2||j==3||j==4||j==5||j==6||j==7||j==8||j==9){}
			  else{  return "Invlid NIC";   }
		  }
	}

	
	
	
	return true;
}

function validateNIC(nic) 
{
    var re = /^\d{11}$/;
    return re.test(email);
}

function validatePhone(phone) 
{
    var re = /^(0)?\d{10}$/;
    return re.test(phone);
}



function onFundSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divFundsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}

	$("#hidFundIDSave").val("");
	$("#formFund")[0].reset().Status();
}

function onFundDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divFundsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}