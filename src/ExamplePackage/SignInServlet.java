package ExamplePackage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Servlet implementation class LoginServlet
 */
public class SignInServlet extends HttpServlet {

     UserRepository userRepository = new UserRepository();


    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            UserModel user = new UserModel();
            ArrayList<VMModel> vmsOfUser = new ArrayList<VMModel>();
            VMModel vmModel = new VMModel();
            user.setUserName(request.getParameter("un"));
            vmModel.setUserName(request.getParameter("un"));
            user.setFirstName(request.getParameter("uf"));
            user.setLastName(request.getParameter("ln"));
            user.setPassword(request.getParameter("pw"));
            vmModel.setVmName("vm1");
            vmModel.setVmStatus("poweredOff");
            vmModel.setIsValid("true");
            vmsOfUser.add(vmModel);
            user.setVmlistOfUser(vmsOfUser);
            userRepository.addUserModelToRepo(user);
            UserDAO.signIn(user);
            VMDAO.addVM(vmModel);


                HttpSession session = request.getSession(true);
                session.setAttribute("currentSessionUser", user.getUsername());
                System.out.println("In SignInServlet user session set");
                response.setContentType("text/html");
                response.sendRedirect("/CMPE283_-_Project_2/UserLogged.jsp"); //logged-in page

        } catch (Throwable theException) {
            System.out.println(theException);
        }
    }
}