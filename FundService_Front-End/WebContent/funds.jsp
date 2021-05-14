<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Request For Fund</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.6.0.min.js"></script>
<script src="Components/funds.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Request For Fund</h1>
				<form id="formFund" name="formFund">
					Requester Name: <input id="requesterName" name="requesterName" type="text" class="form-control form-control-sm"> 
					<br> 
					Requester Phone: <input id="requesterPhone" name="requesterPhone" type="text" class="form-control form-control-sm" pattern="07[1,2,5,6,7,8][0-9]+" maxlength="10"> 
					<br> 
					Requester Mail: <input id="requesterMail" name="requesterMail" type="email" class="form-control form-control-sm"> 
					<br>
					Description: <input id="description" name="description" type="text" class="form-control form-control-sm"> 
					<br> 
					Requester NIC: <input id="requesterNIC" name="requesterNIC" type="text" class="form-control form-control-sm"> 
					<br> 
					
					
					<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary"> 
					<input type="hidden" id="hidFundIDSave" name="hidFundIDSave" value="">
				</form>
				<br>
				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
				<br>
				<div id="divFundsGrid">
					<%
						Fund fundObj = new Fund();
						out.print(fundObj.readFunds());
					%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
