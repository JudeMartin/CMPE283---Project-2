package ExamplePackage;
import java.util.ArrayList;

/**
 * Created by Spurthy on 4/14/2015.
 */
public  class UserRepository {

    private UserModel userModel;
    private   ArrayList<UserModel> userModels = new ArrayList<UserModel>();



    public UserRepository() {
    }

    public void addUserModelToRepo(UserModel userModel){
        this.userModels.add(userModel);
    }
    public  ArrayList<UserModel> getUserModels() {
        return userModels;
    }





    public void setUserModels(ArrayList<UserModel> userModels) {
        this.userModels = userModels;
    }



}
