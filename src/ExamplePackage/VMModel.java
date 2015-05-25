package ExamplePackage;
/**
 * Created by Spurthy on 4/14/2015.
 */
public class VMModel {

    private String userName ;
    private String vmName;
    private String vmStatus;
    private String isValid;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public String getVmStatus() {
        return vmStatus;
    }

    public void setVmStatus(String vmStatus) {
        this.vmStatus = vmStatus;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }
}
