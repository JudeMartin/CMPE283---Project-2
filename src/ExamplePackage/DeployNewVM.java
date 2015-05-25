package ExamplePackage;

import java.io.IOException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ResourcePool;
import com.vmware.vim25.mo.ServiceInstance;


import com.vmware.vim25.Description;
import com.vmware.vim25.ElementDescription;
import com.vmware.vim25.PerfCounterInfo;
import com.vmware.vim25.PerfEntityMetric;
import com.vmware.vim25.PerfEntityMetricBase;
import com.vmware.vim25.PerfEntityMetricCSV;
import com.vmware.vim25.PerfInterval;
import com.vmware.vim25.PerfMetricId;
import com.vmware.vim25.PerfMetricIntSeries;
import com.vmware.vim25.PerfMetricSeries;
import com.vmware.vim25.PerfMetricSeriesCSV;
import com.vmware.vim25.PerfQuerySpec;
import com.vmware.vim25.PerfSampleInfo;
import com.vmware.vim25.PerformanceDescription;
import com.vmware.vim25.VirtualDeviceConfigSpec;
import com.vmware.vim25.VirtualDeviceConfigSpecFileOperation;
import com.vmware.vim25.VirtualDeviceConfigSpecOperation;
import com.vmware.vim25.VirtualDisk;
import com.vmware.vim25.VirtualDiskFlatVer2BackingInfo;
import com.vmware.vim25.VirtualEthernetCard;
import com.vmware.vim25.VirtualEthernetCardNetworkBackingInfo;
import com.vmware.vim25.VirtualLsiLogicController;
import com.vmware.vim25.VirtualMachineCloneSpec;
import com.vmware.vim25.VirtualMachineConfigSpec;
import com.vmware.vim25.VirtualMachineFileInfo;
import com.vmware.vim25.VirtualMachineRelocateSpec;
import com.vmware.vim25.VirtualPCNet32;
import com.vmware.vim25.VirtualSCSISharing;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.PerformanceManager;
import com.vmware.vim25.PerfProviderSummary;
import com.vmware.vim25.mo.ResourcePool;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;
import com.vmware.vim25.mo.util.CommandLineParser;


/**
 * Servlet implementation class DeployVM
 */

public class DeployNewVM extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeployNewVM() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try{
			System.out.println("Deploying VM ");	

			
			URL url = new URL("https://130.65.132.115/sdk");
			ServiceInstance si = new ServiceInstance(url,"administrator", "12!@qwQW", true);
			
			Folder rootFolder;
			ResourcePool rp;
			Datacenter dc;
			ManagedEntity[] hosts;
			ManagedEntity[] vms;
			VirtualMachine vm=null;
					
		
		ServiceInstance vCenterManagerSi;
		Folder vCenterManagerRootFolder;
		String dcName = "T15-DC";
		Folder vmFolder;
			
			
			System.out.println("Hello New CreateVM");
			
			rootFolder = si.getRootFolder();		    
		    dc = (Datacenter) new InventoryNavigator(
		        rootFolder).searchManagedEntity("Datacenter", dcName);
		    rp = (ResourcePool) new InventoryNavigator(
		        dc).searchManagedEntities("ResourcePool")[0];
		    
		    vmFolder = dc.getVmFolder();
			

			VirtualMachineCloneSpec cloneSpec = new VirtualMachineCloneSpec();
				    cloneSpec.setLocation(new VirtualMachineRelocateSpec());
				    cloneSpec.setPowerOn(true);
				    cloneSpec.setTemplate(false);
				    
				    
				    String OSType = request.getParameter("choiceOS");
				    if ("Windows".equals(OSType)){
 vm = (VirtualMachine) new InventoryNavigator(rootFolder).searchManagedEntity("VirtualMachine", "T15-VM01-Windows");
				    }
				    if ("Ubuntu".equals(OSType)){
vm = (VirtualMachine) new InventoryNavigator(rootFolder).searchManagedEntity("VirtualMachine", "T15-VM02-Ubu");
			    }
			Task task = vm.cloneVM_Task((Folder) vm.getParent(),(request.getParameter("vm-deployName")), cloneSpec);
			    System.out.println("Launching the VM clone task. " +"Please wait ...");

			    String status = task.waitForMe();
			    System.out.println(status);
			    if(status==Task.SUCCESS)
			    {
			      System.out.println("VM got cloned successfully.");
			    }
			    else
			    {
			      System.out.println("Failure -: VM cannot be cloned");
			    }
		}
		catch(Exception e)
		{
			System.out.println("Exception in deploying VM "+e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
