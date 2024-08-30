package test;

import jakarta.servlet.ServletException;		
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import jakarta.servlet.*;

/**
 * Servlet implementation class Registartion
 */ 
@WebServlet("/Registartion")
public class Registartion extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		 String uname=request.getParameter("name");
		 String uemail=request.getParameter("email");
		 String upwd=request.getParameter("passw");
		 String reupwd=request.getParameter("re_passw");
		 String umobile=request.getParameter("contact");
		 
		 RequestDispatcher dispatcher=null;
		 Connection con=null;
		 
		 if(uname==null || uname.equals("")) 
			{
				 request.setAttribute("status", "invalidUname");
				  dispatcher=request.getRequestDispatcher("registration.jsp");
				  dispatcher.forward(request, response);
			}
			

			if(uemail==null || uemail.equals("")) 
			{
				 request.setAttribute("status", "invalidEmail");
				  dispatcher=request.getRequestDispatcher("registration.jsp");
				  dispatcher.forward(request, response);
			}
			

			if(upwd==null || upwd.equals("")) 
			{
				 request.setAttribute("status", "invalidPassword");
				  dispatcher=request.getRequestDispatcher("registration.jsp");
				  dispatcher.forward(request, response);
			}
			else if(!upwd.equals(reupwd)) 
			{
				 request.setAttribute("status", "invalidconfirmpassword");
				  dispatcher=request.getRequestDispatcher("registration.jsp");
				  dispatcher.forward(request, response);
			}
			

			if(umobile==null || umobile.equals("")) 
			{
				 request.setAttribute("status", "invalidmobile");
				  dispatcher=request.getRequestDispatcher("registration.jsp");
				  dispatcher.forward(request, response);
			}else if(umobile.length()>10 || umobile.length()<10)   
			       {
				request.setAttribute("status", "invalidmobilelength");
				  dispatcher=request.getRequestDispatcher("registration.jsp");
				  dispatcher.forward(request, response);
			}
		 
		 
		 try {
			 
			 Class.forName("oracle.jdbc.driver.OracleDriver");
			  con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL","System","Rajkumar");
			 PreparedStatement pst=con.prepareStatement("insert into users(uname,upwd,uemail,umobile) values(?,?,?,?)");
			 
			 pst.setString(1, uname);
			 pst.setString(2, upwd);
			 pst.setString(3, uemail);
			 pst.setString(4, umobile);
			 
			 int rowCount=pst.executeUpdate();
			 dispatcher=request.getRequestDispatcher("registration.jsp");
			 

				 
			 
			 if(rowCount > 0) 
			 {
				 request.setAttribute( "status", "success");
				 
			 }
			 else 
			 {
				 request.setAttribute( "status", "failed");
			 }
			 
			 dispatcher.forward(request, response);
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 finally 
		 {
			 try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		 }
	}

}
