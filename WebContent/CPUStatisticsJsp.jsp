<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<head>
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
  <style>
 body {
    background-color: #3498db !important;
    color: #ecf0f1 !important;
     background-image: url(2.jpg);
    }
    </style>
 <script type="text/javascript" src="https://www.google.com/jsapi"></script>


 <%
 ArrayList CPUPerformance = (ArrayList)(session.getAttribute("CPUPerformance"));
 ArrayList MemPerformance = (ArrayList)(session.getAttribute("MemPerformance"));
 
 
 
 System.out.println("CPU session "+CPUPerformance);
 System.out.println("CPU session "+MemPerformance);
// for(int i=0; i<CPUPerformance.size();i++)
 //	System.out.println(CPUPerformance.get(i));
 %>
 
 
 <script>
 var xTime = <%= session.getAttribute("CPUPerformance")%>;
 var yCPU = <%= session.getAttribute("memoryPerformance")%>;
 /*//alert(xTime);
 alert(yCPU);*/
 google.load('visualization', '1', {packages: ['corechart', 'line']});
    google.load('visualization', '1', {packages: ['corechart']});
      google.setOnLoadCallback(drawChart);

    
      
      function drawChart() {

        var data = new google.visualization.DataTable();
        var dataMemory = new google.visualization.DataTable();
     
        data.addColumn('date', 'Time of Day');
        data.addColumn('number', 'CPU Usage');

        dataMemory.addColumn('date', 'Time of Day');
        dataMemory.addColumn('number', 'Memory Usage');    
        
        data.addRows(xTime);
        dataMemory.addRows(yCPU);
       /* for(var i=0; i<CPUPerformance.size();i++)
        	{
        	alert(CPUPerformance.get(i));
        data.addRows([
CPUPerformance.get(i)
                     ]);
        	}*/
        var options = {
          title: 'CPU Usage Chart',
          width: 400,
          height: 300,
          hAxis: {
            format: 'yyyy, MM, dd',
            gridlines: {count: 15}
          },
          vAxis: {
            gridlines: {color: 'none'},
            minValue: 0
          }
        };

        	 var optionsMemory = {
        	          title: 'Memory Usage Chart',
        	          width: 400,
        	          height: 300,
        	          hAxis: {
        	            format: 'yyyy, MM, dd',
        	            gridlines: {count: 15}
        	          },
        	          vAxis: {
        	            gridlines: {color: 'none'},
        	            minValue: 0
        	          }
        	        };

        	
        var chart = new google.visualization.LineChart(document.getElementById('chart_div'));

        chart.draw(data, options);

        var chartMemory = new google.visualization.LineChart(document.getElementById('chart_div2'));

        chartMemory.draw(dataMemory, optionsMemory);

        
      
      }

   
 
 </script> 
 </head>

<body style="background-color:Beige">
<table>
<tr>
<td>

  <div id="chart_div"></div>
</td>

<td>
    <div id="chart_div2"></div>
    </td>
    </tr>
    
 
</table> 
<div id="myDiv"></div>
<script>
         $(document).ready(function(){
 var table = $( "<table></table>" );

for ( var i = 0; i < xTime.length; i++ ) {
    var $line = $( "<tr></tr>" );
    $line.append( $( "<td></td>" ).html( xTime[i] ) );
    $line.append( $( "<td></td>" ).html( yCPU[i] ) );
    $table.append( $line );
}
      	}); 
         table.appendTo( $( "#myDiv" ) );
</script>
</body>
</html>
