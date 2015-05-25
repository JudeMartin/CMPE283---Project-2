package ExamplePackage;

import java.util.ArrayList;

public class UserModel1
{

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String vmName;
    public boolean isValid;
    private ArrayList<VMModel> vmlistOfUser = new ArrayList<VMModel>();

    public ArrayList<VMModel> getVmlistOfUser(){
        return vmlistOfUser;
    }

    public void setVmlistOfUser(ArrayList<VMModel> vmlistOfUser){
        this.vmlistOfUser = vmlistOfUser;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String newFirstName) {
        firstName = newFirstName;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String newLastName) {
        lastName = newLastName;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String newPassword) {
        password = newPassword;
    }


    public String getUsername() {
        return username;
    }

    public void setUserName(String newUsername) {
        username = newUsername;
    }

    public String getVMName() {
        return vmName;
    }

    public void setVMName(String vmName) {
        this.vmName = vmName;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean newValid) {
        isValid = newValid;
    }
}