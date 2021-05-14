package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class Fund {
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paf", "root", "");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return con;
	}

	public String readFunds() {
		String output = ""; 
		 try
		 { 
			 Connection con = connect(); 
			 if (con == null) 
			 { 
			 return "Error while connecting to the database for reading."; 
			 } 
			 // Prepare the html table to be displayed
			 output = "<table border='1'><tr>"
			 		+ "<th>Requester Name</th> "
			 		+ "<th>Requester Phone</th>"
			 		+ "<th>Requester Mail</th>"
			 		+ "<th>Description</th> "
			 		+ "<th>Requester NIC</th> "
			 		+ "<th>Update</th><th>Remove</th></tr>"; 
			 String query = "select * from fund"; 
			 Statement stmt = con.createStatement(); 
			 ResultSet rs = stmt.executeQuery(query); 
			 // iterate through the rows in the result set
			 while (rs.next()) 
			 { 
				 String fundID = Integer.toString(rs.getInt("fundID")); 
				 String requesterName = rs.getString("requesterName"); 
				 String requesterPhone = rs.getString("requesterPhone");
				 String requesterMail = rs.getString("requesterMail"); 
				 String description = rs.getString("requesterDesc"); 
				 String requesterNIC = rs.getString("requesterNIC");
				 // Add into the html table
				 output += "<tr><td><input id='hidItemIDUpdate' "
				 		+ "name='hidItemIDUpdate' "
				 		+ "type='hidden' value='" + fundID
				 		+ "'>" + requesterName + "</td>"; 
				 output += "<td>" + requesterPhone + "</td>"; 
				 output += "<td>" + requesterMail + "</td>"; 
				 output += "<td>" + description + "</td>"; 
				 output += "<td>" + requesterNIC + "</td>"; 
				 // buttons
				 output += "<td><input name='btnUpdate' type='button' value='Update' "
						 + "class='btnUpdate btn btn-secondary' data-fundid='" + fundID + "'></td>"
						 + "<td><input name='btnRemove' type='button' value='Remove' "
						 + "class='btnRemove btn btn-danger' data-fundid='" + fundID + "'></td></tr>";
			 } 
			 con.close(); 
			 // Complete the html table
			 output += "</table>"; 
		 } 
		 catch (Exception e) 
		 { 
			 output = "Error while reading the funds.   "+e.getMessage(); 
			 System.err.println(e.getMessage()); 
		 } 
		 return output; 
	}

	public String insertFund(String requesterName, String requesterPhone, String requesterMail, String description, String requesterNIC) {
		String output = ""; 
		 try
		 { 
			 Connection con = connect(); 
			 if (con == null) 
			 { 
				 return "Error while connecting to the database for inserting."; 
			 } 
			 // create a prepared statement
			String query = "insert into fund (fundID,requesterName,requesterPhone,requesterMail,requesterDesc,requesterNIC) values (? ,?, ?, ?, ?, ?);"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
			// binding values
			preparedStmt.setInt(1, 0); 
			preparedStmt.setString(2, requesterName); 
			preparedStmt.setString(3, requesterPhone); 
			preparedStmt.setString(4, requesterMail); 
			preparedStmt.setString(5, description);
			preparedStmt.setString(6, requesterNIC);
			// execute the statement
			preparedStmt.execute(); 
			con.close(); 
			String newFunds = readFunds(); 
			output = "{\"status\":\"success\", \"data\": \""
					+ "" +newFunds + "\"}"; 
			} 
			catch (Exception e) 
			{ 
				System.err.println("ERORR : "+e.getMessage());
				output = "{\"status\":\"error\", \"data\":"
					 + "\"Error while inserting the Fund.\"}"; 
				 
			} 
			return output;
		 }

	public String updateFund(String ID, String requesterName, String requesterPhone, String requesterMail, String description, String requesterNIC) {
		String output = ""; 
		 try
		 { 
				 Connection con = connect(); 
			 if (con == null) 
			 { 
				 return "Error while connecting to the database for updating."; 
			 } 
			 // create a prepared statement
			 String query = "UPDATE fund SET "
			 		+ "requesterName=?,requesterPhone=?,requesterMail=?,requesterDesc=?,requesterNIC=? WHERE fundID=?"; 
			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 // binding values
			 preparedStmt.setString(1, requesterName); 
			 preparedStmt.setString(2, requesterPhone); 
			 preparedStmt.setString(3, requesterMail); 
			 preparedStmt.setString(4, description); 
			 preparedStmt.setString(5, requesterNIC); 
			 preparedStmt.setString(6, ID); 
			// execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 String newFunds = readFunds(); 
			 output = "{\"status\":\"success\", \"data\": \"" + 
					 newFunds + "\"}"; 
		 } 
		 catch (Exception e) 
		 { 
			 output = "{\"status\":\"error\", \"data\": "
			 		+ "\"Error while updating the item.\"}"; 
			 System.err.println(e.getMessage()); 
		 } 
		 return output;
	}

	public String deleteFund(String fundID) {
		String output = ""; 
		 try
		 { 
			 Connection con = connect(); 
			 if (con == null) 
			 { 
				 return "Error while connecting to the database for deleting."; 
			 } 
			 // create a prepared statement
			 String query = "delete from fund where fundID=?"; 
			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 // binding values
			 preparedStmt.setInt(1, Integer.parseInt(fundID)); 
			 // execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 String newFunds = readFunds(); 
			 output = "{\"status\":\"success\", \"data\": \"" + 
					 newFunds + "\"}"; 
		 } 
		 catch (Exception e) 
		 { 
			 output = "{\"status\":\"error\", \"data\": "
			 		+ "\"Error while deleting the fund.\"}"; 
			 System.err.println(e.getMessage()); 
		 } 
		 return output;
	}
}
