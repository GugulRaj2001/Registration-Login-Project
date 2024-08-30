package test;

import jakarta.servlet.ServletException;		
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */

@SuppressWarnings("unused")
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
     
	 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String uemail=request.getParameter("username");
		String upwd=request.getParameter("password");
		
		RequestDispatcher dispatcher=null;
		HttpSession session=request.getSession();
		
		
		if(uemail==null || uemail.equals("")) 
		{
			 request.setAttribute("status", "invalidEmail");
			  dispatcher=request.getRequestDispatcher("login.jsp");
			  dispatcher.forward(request, response);
		}

        if(upwd==null || upwd.equals("")) 
		{
			 request.setAttribute("name","invalidPassword");
			  dispatcher=request.getRequestDispatcher("login.jsp");
			  dispatcher.forward(request, response);
		}
		
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL","System","Rajkumar");
			 PreparedStatement pst=con.prepareStatement("select * from users where uemail=? and upwd=?");
			 
			 pst.setString(1, uemail);
			 pst.setString(2, upwd);
			 
			 ResultSet rs=pst.executeQuery();
			 
			 dispatcher=request.getRequestDispatcher("login.jsp");
			 
			 if(rs.next()) 
			 {
				  
				  session.setAttribute("name", rs.getString("uname"));
				  dispatcher=request.getRequestDispatcher("index.jsp");
				 
			 }
			 else 
			 {
				 request.setAttribute( "status", "failed");
				 dispatcher=request.getRequestDispatcher("login.jsp");
			 }
			 
			 dispatcher.forward(request, response);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			
		}
		 
	}

}
