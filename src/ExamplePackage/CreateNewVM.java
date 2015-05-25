package ExamplePackage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.net.URL;

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
 * Servlet implementation class CreateNewVM
 */

public class CreateNewVM extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateNewVM() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try{
			
			URL url = new URL("https://130.65.132.115/sdk");
			ServiceInstance si = new ServiceInstance(url,"administrator", "12!@qwQW", true);
			
			Folder rootFolder;
			ResourcePool rp;
			Datacenter dc;
			ManagedEntity[] hosts;
			ManagedEntity[] vms;
			
		
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

		    long memorySizeMB = 500;
		    int cupCount = 1;
		    String guestOsId = "sles10Guest";
		    long diskSizeKB = 1000000;
		    // mode: persistent|independent_persistent,
		    // independent_nonpersistent
		    String diskMode = "persistent";
		    String datastoreName = "nfs3team15";
		    String netName = "VM Network";
		    String nicName = "Network Adapter 1";
		    
		    HttpSession session = request.getSession(true);	
		    // create vm config spec
		    VirtualMachineConfigSpec vmSpec = 
		      new VirtualMachineConfigSpec();
		    UserModel currentUser = (UserModel) (session.getAttribute("currentSessionUser"));
		    currentUser.setVMName(request.getParameter("vm-Name"));
		   
		    //String vmName = request.getAttribute("vm-name");
		    vmSpec.setName(currentUser.getVMName());
		    vmSpec.setAnnotation("VirtualMachine Annotation");
		    vmSpec.setMemoryMB(memorySizeMB);
		    vmSpec.setNumCPUs(cupCount);
		    vmSpec.setGuestId(guestOsId);

		    
		    // create virtual devices
		    int cKey = 1000;
		    VirtualDeviceConfigSpec scsiSpec = createScsiSpec(cKey);
		    VirtualDeviceConfigSpec diskSpec = createDiskSpec(
		        datastoreName, cKey, diskSizeKB, diskMode);
		    VirtualDeviceConfigSpec nicSpec = createNicSpec(
		        netName, nicName);

		    vmSpec.setDeviceChange(new VirtualDeviceConfigSpec[] 
		        {scsiSpec, diskSpec, nicSpec});
		    
		    // create vm file info for the vmx file
		    VirtualMachineFileInfo vmfi = new VirtualMachineFileInfo();
		    vmfi.setVmPathName("["+ datastoreName +"]");
		    vmSpec.setFiles(vmfi);

		    // call the createVM_Task method on the vm folder
		    Task task = vmFolder.createVM_Task(vmSpec, rp, null);
		    String result = task.waitForMe();       
		    if(result == Task.SUCCESS) 
		    {
		      System.out.println("VM Created Sucessfully");
		    }
		    else 
		    {
		      System.out.println("VM could not be created. ");
		    }
		    	
			
			response.sendRedirect("/CMPE283_-_Project_2/CreateVMJsp.jsp");
		}
		catch(Exception e){
			System.out.println("Exception New VM "+e);
		}
	}
	
	
	static VirtualDeviceConfigSpec createScsiSpec(int cKey)
	  {
	    VirtualDeviceConfigSpec scsiSpec = 
	      new VirtualDeviceConfigSpec();
	    scsiSpec.setOperation(VirtualDeviceConfigSpecOperation.add);
	    VirtualLsiLogicController scsiCtrl = 
	        new VirtualLsiLogicController();
	    scsiCtrl.setKey(cKey);
	    scsiCtrl.setBusNumber(0);
	    scsiCtrl.setSharedBus(VirtualSCSISharing.noSharing);
	    scsiSpec.setDevice(scsiCtrl);
	    return scsiSpec;
	  }
	  
	  static VirtualDeviceConfigSpec createDiskSpec(String dsName, 
	      int cKey, long diskSizeKB, String diskMode)
	  {
	    VirtualDeviceConfigSpec diskSpec = 
	        new VirtualDeviceConfigSpec();
	    diskSpec.setOperation(VirtualDeviceConfigSpecOperation.add);
	    diskSpec.setFileOperation(
	        VirtualDeviceConfigSpecFileOperation.create);
	    
	    VirtualDisk vd = new VirtualDisk();
	    vd.setCapacityInKB(diskSizeKB);
	    diskSpec.setDevice(vd);
	    vd.setKey(0);
	    vd.setUnitNumber(0);
	    vd.setControllerKey(cKey);

	    VirtualDiskFlatVer2BackingInfo diskfileBacking = 
	        new VirtualDiskFlatVer2BackingInfo();
	    String fileName = "["+ dsName +"]";
	    diskfileBacking.setFileName(fileName);
	    diskfileBacking.setDiskMode(diskMode);
	    diskfileBacking.setThinProvisioned(true);
	    vd.setBacking(diskfileBacking);
	    return diskSpec;
	  }
	  
	  static VirtualDeviceConfigSpec createNicSpec(String netName, 
	      String nicName) throws Exception
	  {
	    VirtualDeviceConfigSpec nicSpec = 
	        new VirtualDeviceConfigSpec();
	    nicSpec.setOperation(VirtualDeviceConfigSpecOperation.add);

	    VirtualEthernetCard nic =  new VirtualPCNet32();
	    VirtualEthernetCardNetworkBackingInfo nicBacking = 
	        new VirtualEthernetCardNetworkBackingInfo();
	    nicBacking.setDeviceName(netName);

	    Description info = new Description();
	    info.setLabel(nicName);
	    info.setSummary(netName);
	    nic.setDeviceInfo(info);
	    
	    // type: "generated", "manual", "assigned" by VC
	    nic.setAddressType("generated");
	    nic.setBacking(nicBacking);
	    nic.setKey(0);
	   
	    nicSpec.setDevice(nic);
	    return nicSpec;
	  }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
