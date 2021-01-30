//package servlets;
//
//import java.io.IOException;
//import java.util.List;
//
//import javax.ejb.EJB;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import beans.persistent.Guest;
//import dao.GuestDao;
//
///**
// * Servlet implementation class GuestController
// */
//@WebServlet({ "", "/guest/*" })
//public class GuestController extends HttpServlet
//{
//	private static final long	serialVersionUID	= 1L;
//	//private static final String	PAGE_ACCUEIL		= "/WEB-INF/accueil.jsp";
//	private static final String	PAGE_ACCUEIL		= "/WEB-INF/index-color.jsp";
//	@EJB
//	private GuestDao			dao;
//
//	/**
//	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
//	 *      response)
//	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
//	{
//		System.out.println("Dans le doGet...");
//		List<Guest> guests = dao.lister();
//		request.setAttribute("guests", guests);
//		getServletContext().getRequestDispatcher(PAGE_ACCUEIL).forward(request, response);
//
//	}
//
//	/**
//	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
//	 *      response)
//	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
//	{
//		String nom = request.getParameter("nom");
//		dao.ajouter(new Guest(nom));
//		response.sendRedirect("guest");
//	}
//
//}
