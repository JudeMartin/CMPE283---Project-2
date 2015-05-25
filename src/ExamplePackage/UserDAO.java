package ExamplePackage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

   public class UserDAO 	
   {
      static Connection currentCon = null;
      static ResultSet result = null;

       public static void signIn(UserModel bean) {

           //preparing some objects for connection
           Statement stmt = null;

           String username = bean.getUsername();
           String password = bean.getPassword();
           String firstName = bean.getFirstName();
           String lastName = bean.getLastName();

           String addQuery =

                   "insert into users values (" + username + "," + password + "," + firstName + "," + lastName + ");";


           System.out.println("Your user name is " + username);
           System.out.println("Your password is " + password);
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
                   bean.setValid(false);
               }

               //if user exists set the isValid variable to true
               else if (more) {


                   System.out.println("Welcome " + firstName + "" + lastName);

                   bean.setValid(true);

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
	
      public static UserModel login(UserModel bean) {
	
         //preparing some objects for connection 
         Statement stmt = null;    
	
         String username = bean.getUsername();    
         String password = bean.getPassword();   
	    
         String searchQuery =
               "select * from users where username='"
                        + username
                        + "' AND password='"
                        + password
                        + "'";
	    
      // "System.out.println" prints in the console; Normally used to trace the process
      System.out.println("Your user name is " + username);          
      System.out.println("Your password is " + password);
      System.out.println("Query: "+searchQuery);

	    
      try 
      {
         //connect to DB 
         currentCon = ConnectionManager.getConnection();
         stmt=currentCon.createStatement();
      
         result = stmt.executeQuery(searchQuery);
         boolean more = result.next();
	       
         // if user does not exist set the isValid variable to false
         if (!more) 
         {
            System.out.println("Sorry, you are not a registered user! Please sign up first");
            bean.setValid(false);
         } 
	        
         //if user exists set the isValid variable to true
         else if (more) 
         {
            String firstName = result.getString("FirstName");
            String lastName = result.getString("LastName");
           
            
            System.out.println("Welcome " + firstName+""+lastName);
            bean.setFirstName(firstName);
            bean.setLastName(lastName);
            bean.setValid(true);

         }
      } 

      catch (Exception ex) 
      {
         System.out.println("Log In failed: An Exception has occurred! " + ex);
      } 
	    
      //some exception handling
      finally 
      {
         if (result != null)	{
            try {
               result.close();
            } catch (Exception e) {}
               result = null;
            }
	
         if (stmt != null) {
            try {
               stmt.close();
            } catch (Exception e) {}
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

return bean;
	
      }	
   }
