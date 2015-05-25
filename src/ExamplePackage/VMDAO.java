package ExamplePackage;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class VMDAO
{
   static Connection currentCon = null;
   static ResultSet result = null;

    public static void addVM(VMModel vmModel) {


        Statement stmt = null;
        String username = vmModel.getUserName();
        String vmName = vmModel.getVmName();
        String vmStatus = vmModel.getVmStatus();
        String vmValid = vmModel.getIsValid();

        String addQuery =

                "insert into vm_info values (" + username + "," + vmName + "," + vmStatus + "," + vmValid + ");";


        System.out.println("Your user name is " + username);

        System.out.println("Query: " + addQuery);


        try {
            //connect to DB
            currentCon = ConnectionManager.getConnection();
            stmt = currentCon.createStatement();

            result = stmt.executeQuery(addQuery);
            boolean more = result.next();

            // if user does not exist set the isValid variable to false
            if (!more) {
                System.out.println("Sorry, not Valid results");
                vmModel.setIsValid("false");

            }

            //if user exists set the isValid variable to true
            else if (more) {
                System.out.println("adding vm");

                vmModel.setIsValid("true");

            }
        } catch (Exception ex) {
            System.out.println("Sign In failed: An Exception has occurred! " + ex);
        }

        //some exception handling
        finally {
            if (result != null) {
                try {
                    result.close();
                } catch (Exception e) {
                }
                result = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                }
                stmt = null;
            }

            if (currentCon != null) {
                try {
                    currentCon.close();
                } catch (Exception e) {
                }

                currentCon = null;
            }
        }

    }




   }

