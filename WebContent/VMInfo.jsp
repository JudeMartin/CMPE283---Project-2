<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
   %>
    <%@ page import="java.util.ArrayList"%>
   
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

 <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>

<style>
table, th, td {
    border: 1px solid black;
}
body {
    background-color: #3498db !important;
    color: #ecf0f1 !important;
     background-image: url(2.jpg);
    }

</style>





</head>
<body>
<h1>VMInfo page</h1>
<table style="width:100%">
<tr>
<th>VM Name</th>
<th> VM OS Installed</th>
<th> IP Address </th>
<th> VM PowerState </th>
</tr>

<tr>
<font color="black"></font>
<% 
int VMCount = (Integer)(session.getAttribute("VMCount"));
ArrayList VMName = (ArrayList)(session.getAttribute("currentVMName"));
ArrayList VMGuestOS = (ArrayList)(session.getAttribute("currentGuestName"));
ArrayList VMIp = (ArrayList)(session.getAttribute("currentVMIp"));
ArrayList VMPowerState = (ArrayList)(session.getAttribute("currentVMPowerState"));
System.out.println(VMName);
for(int i=0;i<VMCount;i++)
{
	
%>
<tr>
 <td> <%= VMName.get(i) %> </td>
<td>  <%= VMGuestOS.get(i) %></td>
<td>  <%= VMIp.get(i) %></td>
<td>  <%= VMPowerState.get(i) %></td>
</tr>
<%} %>

</tr>

</table>
</body>
</html>