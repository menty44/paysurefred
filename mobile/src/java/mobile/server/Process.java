/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mobile.server;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;
import service.Onlinepay_Service;

/**
 *
 * @author Joseph
 */
@WebServlet(name = "Process", urlPatterns = {"/Process"})
public class Process extends HttpServlet {
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/196.216.64.237_8080/webdirect/onlinepay.wsdl")
    private Onlinepay_Service service;    
    
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /*
             * TODO output your page here. You may use following sample code.
             */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Process</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Process at " + request.getContextPath() + "</h1>");
            
            String name = "joseph";
            String result = hello(name);
            out.println(result+"<br>");
            
            String mname = request.getParameter("mname");
            String namee = request.getParameter("name");
            String email = request.getParameter("email");
            int amount = Integer.parseInt(request.getParameter("amount"));
            String description = request.getParameter("description");
            int payoption = Integer.parseInt(request.getParameter("payoption"));
            
            out.println(mname+"<br>");
            out.println(namee+"<br>");
            out.println(email+"<br>");
            out.println(amount+"<br>");
            out.println(description+"<br>");
            out.println(payoption+"<br>");
            
            String link = formData(mname, name, email, amount, description, payoption);
            out.println(link);
            
            
            
            
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private String hello(java.lang.String name) {
        service.Onlinepay port = service.getOnlinepayPort();
        return port.hello(name);
    }

    private String formData(java.lang.String mname, java.lang.String name, java.lang.String email, int amount, java.lang.String description, int payoption) {
        service.Onlinepay port = service.getOnlinepayPort();
        return port.formData(mname, name, email, amount, description, payoption);
    }
}
