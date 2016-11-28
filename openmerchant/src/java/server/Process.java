/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

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
 * @author paysure
 */
@WebServlet(name = "Process", urlPatterns = {"/Process"})
public class Process extends HttpServlet {
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/epayments.paysure.co.ke/webdirect/onlinepay.wsdl")
    private Onlinepay_Service service;    
    
    /*@WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/webdirect/onlinepay.wsdl")
    private Onlinepay_Service service;*/

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

            try {

                String mname = request.getParameter("mname");
                String name = request.getParameter("name");
                String email = request.getParameter("email");
                String amt = request.getParameter("amount");
                //int amount = Integer.parseInt(amt);
                int amount = 0;
                String description = request.getParameter("description");
                String po = request.getParameter("payoption");
                String currency = request.getParameter("currency");
                if(null == currency){
                    currency="KES";
                }
                int payoption = Integer.parseInt(po);
                if (amt.contains(".")) {
                    out.println("Amount has cents");
                    amount = Integer.parseInt(amt.replaceAll("\\.", ""));
                    out.println(amount);
                } else {
                    out.println("Amount has no cents");
                    amount = Integer.valueOf(amt) * 100;
                    out.println(Integer.valueOf(amt) * 100);
                }
                
                System.out.println("Currency=====>"+currency);

                //Submitting form values to out servlet                
                //String result = formData(mname, name, email, amount, description, payoption);
                
                String result = formData(mname, name, email, amount, description, payoption, currency);     
                
                //System.out.println(result);
                
                /*
                 * The formData webservice methods returns either a kenswitch or
                 * visa link depeding on the payoption the customer check
                 */
                response.sendRedirect(result);
                
                /*
                 * The above line of code the redirects you to either a
                 * kenswitch or visa payment form
                 */

            } catch (Exception e) {
                out.println("Exception" + e);
            }

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

    /*private String formData(java.lang.String mname, java.lang.String name, java.lang.String email, int amount, java.lang.String description, int payoption) {
        service.Onlinepay port = service.getOnlinepayPort();
        return port.formData(mname, name, email, amount, description, payoption);
    }*/

    private String formData(java.lang.String mname, java.lang.String name, java.lang.String email, int amount, java.lang.String description, int payoption, java.lang.String currency) {
        service.Onlinepay port = service.getOnlinepayPort();
        return port.formData(mname, name, email, amount, description, payoption, currency);
    }
}
