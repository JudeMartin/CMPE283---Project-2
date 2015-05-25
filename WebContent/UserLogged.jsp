<%@ page language="java" contentType="text/html; charset=windows-1256" pageEncoding="windows-1256"
         import="ExamplePackage.UserBean"%>
 
   <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
   "http://www.w3.org/TR/html4/loose.dtd">

   <html>

      <head>
         <meta http-equiv="Content-Type" 
            content="text/html; charset=windows-1256">
         <title>   User Logged Successfully   </title>

  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
  <style>
 body {
    background-color: #3498db !important;
    color: #ecf0f1 !important;
     background-image: url(2.jpg);
    }
        .hide{display:none}
    .container-fluid{padding:0px !important}
    .nav {float:right;padding-left:40px;width:100%}
    .nav li{width :15%;display:inline-flex}
 </style>
      </head>
	
      <body>
			
            <nav class="navbar inverse">
  <div class="container-fluid">
    <div class="navbar-header">
         <h3>  <% UserBean currentUser = (UserBean) (session.getAttribute("currentSessionUser"));%>
			
            Welcome <%= currentUser.getFirstName() + " " + currentUser.getLastName() %> </h3>
    </div>
    <div>
      <ul class="nav">
        <li>
        <form action="DisplayVMDetails" method="get">
        <button class="btn btn-default" type="submit" name="Instance" value="Instance" onclick="location.href='/CMPE283_-_Project_2/VMInfo.jsp'">VM List</button>
        </form>
        </li>
        <li><a class="btn btn-default"  id="CreateVmBtn">Create VM</a></li>
        <li><a class="btn btn-default" id="DeployVmBtn">Deploy VM</a></li>
        <li>
		<form action="CreateCPUStatistics" method="get">
        <button class="btn btn-default" type="submit" type="button" name="Create-CPUStatistics" value="View CPU Statistics" onclick="location.href='/CMPE283_-_Project_2/CPUStatisticsJsp.jsp'">VM Statistics</button>
        </form>
        </li>
      </ul>
    </div>
  </div>
</nav>
		<div class="container-fluid hide" id="CreateVM" >
		<div class="row">
		<div class="col-md-offset-4 col-md-4 " >
		  <h2>Create new VM</h2>
		<form action="CreateNewVM" method="get" role="form">
		    <div class="form-group">
		      <label for="vm-Name">VM name:</label>
		      <input type="text" id="vm-Name" name="vm-Name" value="" required  class="form-control" placeholder="Enter VM name"/ >
		    </div>
		    <center>
		    <button type="submit" class="btn btn-default btn-lg" name="Create-VM" value="Create VM">Create VM</button>
		    </center>
		  </form>		  
		  </div>
		  </div>
		  </div>
		  
		<div class="container-fluid hide" id="DeployVM" >
		<div class="row">
		<div class="col-md-offset-4 col-md-4 " >
		  <h2>Deploy new VM</h2>
		<form action="DeployNewVM" method="get" role="form">
		    <div class="form-group">
		      <label for="name">VM Name:</label>
		      <input type="text" id="vm-deployName" name="vm-deployName" value="" required  class="form-control" placeholder="Enter VM name"/ >
		    </div>
		    <div class="radio">
			  <label><input type="radio" name="choiceOS" value="Ubuntu" id="opt1">Ubuntu</label>
			</div>
			<div class="radio">
			  <label><input type="radio" name="choiceOS" value="Windows" id="opt2">Windows</label>
			</div>
		    <center>
		    <button type="submit" class="btn btn-default btn-lg" name="Deploy-VM" value="Deploy VM">Deploy VM</button>
		    </center>
		  </form>		  
		  </div>
		  </div>
		  </div>
          <script>
         $(document).ready(function(){
        	    $("#CreateVmBtn").click(function(){

        	        $("#CreateVM").removeClass( "hide" );
        	    });
        	    $("#DeployVmBtn").click(function(){
        	        $("#DeployVM").removeClass( "hide" );
        	    });        	    
        	}); 
        </script>     
  
</body>
	
   </html>
