package ExamplePackage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.vmware.vim25.PerfCompositeMetric;
import com.vmware.vim25.PerfEntityMetric;
import com.vmware.vim25.PerfEntityMetricBase;
import com.vmware.vim25.PerfEntityMetricCSV;
import com.vmware.vim25.PerfMetricId;
import com.vmware.vim25.PerfMetricIntSeries;
import com.vmware.vim25.PerfMetricSeries;
import com.vmware.vim25.PerfMetricSeriesCSV;
import com.vmware.vim25.PerfQuerySpec;
import com.vmware.vim25.PerfSampleInfo;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.PerformanceManager;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;

/**
 * Servlet implementation class CreateCPUStatistics
 */

public class CreateCPUStatistics extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ArrayList<String> xco = new ArrayList<String>();
	private static ArrayList<String> ycoCPU = new ArrayList<String>();
	private static ArrayList<String> ycoMemory = new ArrayList<String>();
	private static HttpSession session;
	private static ArrayList<Date> date = new ArrayList<Date>();
	private static ArrayList<String> cpuPerformance = new ArrayList<String>();
	private static ArrayList<String> memoryPerformance = new ArrayList<String>();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateCPUStatistics() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 session = request.getSession(true);	    
		    
		try
		{
			
		System.out.println("CPU Statistics");	
		
		
		
		 URL url = new URL("https://130.65.132.115/sdk");
			ServiceInstance si = new ServiceInstance(url, "administrator", "12!@qwQW", true);
			
		    String vmname = "T15-VM01-Windows";
		   
		    VirtualMachine vm = (VirtualMachine) new InventoryNavigator(
		      si.getRootFolder()).searchManagedEntity(
		        "VirtualMachine", vmname);

		    if(vm == null)
		    {
		      System.out.println("Virtual Machine " + vmname 
		          + " cannot be found.");
		      si.getServerConnection().logout();
		      return;
		    }

		    PerformanceManager perfMgr = si.getPerformanceManager();

		    int perfInterval = 86400; // 30 minutes for PastWeek
		    
		    
		    // retrieve all the available perf metrics for vm
		    PerfMetricId[] pmis = perfMgr.queryAvailablePerfMetric(
		        vm, null, null, perfInterval);

		    for(PerfMetricId id : pmis)
		    {
		    	id.setInstance("");
		   	}
		    	
		    
		    Calendar curTime = si.currentTime();
		    
		    PerfQuerySpec qSpec = new PerfQuerySpec();
		    qSpec.setEntity(vm.getRuntime().getHost());
		    //metricIDs must be provided, or InvalidArgumentFault 
		    qSpec.setMetricId(pmis);
		    qSpec.setFormat("normal"); //optional since it's default
		    qSpec.setIntervalId(perfInterval); 

		    Calendar startTime = (Calendar) curTime.clone();
		    startTime.roll(Calendar.DATE, -10);
		    System.out.println("start:" + startTime.getTime());
		    qSpec.setStartTime(startTime);
		    
		    Calendar endTime = (Calendar) curTime.clone();
		    endTime.roll(Calendar.DATE, 0);
		    System.out.println("end:" + endTime.getTime());
		    qSpec.setEndTime(endTime);
		    
		 
		    
		    PerfCompositeMetric pv = perfMgr.queryPerfComposite(qSpec);
		    if(pv != null)
		    {
		      printPerfMetric(pv.getEntity());
		      PerfEntityMetricBase[] pembs = pv.getChildEntity();
		      System.out.println(" size pebs : "+pembs.length);
		      for(int i=0; pembs!=null && i< pembs.length; i++)
		      { System.out.println(" I : "+i);
		        printPerfMetric(pembs[i]);
		       
		      }
		    
		      
		    }
	
	//    	System.out.println("**********************Stats****************** ");
	    /*	System.out.println("time :");	
		    for(String s : xco)
		    	System.out.print(s + " ");
		    
		   System.out.println("");
		    System.out.println("CPU Usage : ");
		    
		    for(Long s : ycoCPU)
		    	System.out.print(s + " ");

		    System.out.println("");
		   System.out.println("Memory Usage ");
		    for(Long s : ycoMemory)
		    	System.out.print(s + " "); */
	
		  
	         
	       //  session.setAttribute("currentSessionUserYcpu",ycoCPU);
	         
	      //   System.out.println("currentSessionUserXtime :" + ((ArrayList)session.getAttribute("currentSessionUserXtime"))+ xco.size());	
		    
	    //	for(int x=0; x<(xco.size()-1) && x<(ycoCPU.size());x++)
		    si.getServerConnection().logout();
		    for(int x=0; x<xco.size();x++)
	    	{
	    		cpuPerformance.add("[new Date("+xco.get(x)+"), "+ycoCPU.get(x)+"]");
	    		memoryPerformance.add("[new Date("+xco.get(x)+"), "+ycoMemory.get(x)+"]");
	    	
	    	}
	    	session.setAttribute("CPUPerformance",cpuPerformance);
	    	
		
		    	session.setAttribute("memoryPerformance",memoryPerformance);
	    	
	    
	    System.out.println("CPU Perf "+cpuPerformance);
	    System.out.println("memoryPerformance Perf "+memoryPerformance);
		    
		    response.sendRedirect("/CMPE283_-_Project_2/CPUStatisticsJsp.jsp");
		
		}
		catch(Exception e)
		{
			System.out.println("CPU Statistics Exception "+e);	
		}
	}

	 static void printPerfMetric(PerfEntityMetricBase val)
	  {
	    if(val instanceof PerfEntityMetric)
	    {
	    	printPerfMetric((PerfEntityMetric)val);
	    }
	  }
	  
	  static void printPerfMetric(PerfEntityMetric pem)
	  {
	    PerfMetricSeries[] vals = pem.getValue();
	    System.out.println("vals length: "+vals.length);
	    PerfSampleInfo[]  infos = pem.getSampleInfo();
	    System.out.println("infos length"+infos.length);
	    
	    for(int i=0; infos!=null && i<infos.length; i++)
	    {
	       //String temp = infos[i].getTimestamp().getTime().toString();
	      //System.out.println("temp "+temp);
	       SimpleDateFormat formatter = new SimpleDateFormat("yyyy, MM, dd");
	       try{
	      //Date date1 = new Date(temp);
	      String dateWithoutTime = formatter.format(infos[i].getTimestamp().getTime());
	     // System.out.println("Date "+dateWithoutTime);
	      xco.add(dateWithoutTime);
	    
	       }
	       catch(Exception e){}
	    }
	  //  System.out.println("XCO "+xco+" i "+xco.size());
	    
	    for(int j=0; vals!=null && j<vals.length; ++j)
	    {
	    	if(vals[j].getId().getCounterId()==2)
	    	{
		      if(vals[j] instanceof PerfMetricIntSeries)
		      {
		        PerfMetricIntSeries val = (PerfMetricIntSeries) vals[j];
		        long[] longs = val.getValue();
		        for(int k=0; k<longs.length; k++) 
		        {
		          //System.out.print(longs[k] + " ");
		          ycoCPU.add(String.valueOf(longs[k]));
		          
		        }
		      }
	    	}
	    	
	    }
	  //  System.out.println("Hi ycpu  "+ycoCPU+" size "+ycoCPU.size());
	    

	    for(int j=0; vals!=null && j<vals.length; ++j)
	    {
	    	if(vals[j].getId().getCounterId()==24)
	    	{
		      if(vals[j] instanceof PerfMetricIntSeries)
		      {
		        PerfMetricIntSeries val = (PerfMetricIntSeries) vals[j];
		        long[] longs = val.getValue();
		        for(int k=0; k<longs.length; k++) 
		        {
		         // System.out.print(longs[k] + " ");
		          ycoMemory.add(String.valueOf(longs[k]));
		        }
		      }
	    	}
	    }
	    //System.out.println("Hi Memory  "+ycoMemory+"size "+ycoMemory.size());
	  }
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}