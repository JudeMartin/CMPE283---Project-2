<!-- 
<%@ page language="java" 
    contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"
%>
 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
                                <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
		<title>Login Page</title>
	
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
  <style>
  .col-md-4 {
    border: 2px solid #eee;
    border-radius: 7px;
    margin-top: 5%;
    padding: 1%;
}
body {
    background-color: #3498db !important;
    color: #ecf0f1 !important;
    background-image: url(1.jpg);
    }
  </style>
	</head>

	<body>	

		<div class="container-fluid">
		<div class="row">
		<div class="col-md-offset-4 col-md-4">
		  <h2><font color="black">Sign In</font></h2>
		<form action="LoginServlet" method="get" role="form">
		    <div class="form-group">
		      <label for="un"><font color = "black">Username</font></label>
		      <input type="text" name="un" value="test" id="un" required  class="form-control" placeholder="Enter username"/ >
		    </div>
		    <div class="form-group">
		      <label for="pw"><font color="black">Password:</font></label>
		      <input type="password" class="form-control" id="pw" placeholder="Enter password" name="pw" value="test" required/>
		    <a href="http://www.w3schools.com">Register Here</a>
		    </div>
		    <center>
		    <button type="submit" class="btn btn-default btn-lg">Login</button>
		    </center>
		  </form>
		  </div>
		</div>
			

	</body>
</html>