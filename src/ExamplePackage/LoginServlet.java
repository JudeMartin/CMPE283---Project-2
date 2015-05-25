package ExamplePackage;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {


public void doGet(HttpServletRequest request, HttpServletResponse response) 
			           throws ServletException, java.io.IOException {

try
{	    

     UserModel user = new UserModel();
     user.setUserName(request.getParameter("un"));
     user.setPassword(request.getParameter("pw"));

     user = UserDAO.login(user);
	   		    
     if (user.isValid())
     {
	        
          HttpSession session = request.getSession(true);	    
          session.setAttribute("currentSessionUser",user); 
          System.out.println("In LoginServlet user session set");
          response.setContentType("text/html");
          
//          RequestDispatcher rd = request
//					.getRequestDispatcher("/UserLogged.jsp");
//			rd.forward(request, response);
          
          response.sendRedirect("/CMPE283_-_Project_2/UserLogged.jsp"); //logged-in page      		
     }
	        
     else 
          response.sendRedirect("/CMPE283_-_Project_2/InvalidLogin.jsp"); //error page 
} 
		
		
catch (Throwable theException) 	    
{
     System.out.println(theException); 
}
       }
	}