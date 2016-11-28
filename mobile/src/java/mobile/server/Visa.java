/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mobile.server;

import com.iveri.enterprise.Enterprise;
import com.iveri.enterprise.ResultException;
import com.iveri.enterprise.ResultExceptionAction;
import com.iveri.enterprise.ResultStatus;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Joseph
 */
@WebServlet(name = "Visa", urlPatterns = {"/Visa"})
public class Visa extends HttpServlet {

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
            String mname = request.getParameter("mname");
            String appid = request.getParameter("appid");
            String certid = "098F0ADA-FCA8-46FA-BD4B-924CAED8D5C1";
            String gateway = "host";
            String mode = "Test";
            String tamt = request.getParameter("tamt");
            String reference = request.getParameter("reference");
            String email = request.getParameter("email");
            String desc = request.getParameter("desc");
            String cardno = request.getParameter("cardno");
            String date = request.getParameter("date");

            Enterprise e = new Enterprise(gateway, certid, ResultExceptionAction.ResultExceptionThrowingOff);

            e.prepare("Transaction", "Debit", appid, mode);
            e.setAttribute("MerchantReference", String.valueOf(System.currentTimeMillis()));
            e.setTag("Amount", tamt); // R1.23
            e.setTag("CCNumber", cardno);
            e.setTag("ExpiryDate", date);
            //System.out.println(e.getLoggableRequest());
            out.println(e.getLoggableRequest());
            ResultStatus resultStatus = e.execute();
            out.println("Result : " + resultStatus.getValue());
            if (resultStatus == ResultStatus.UNSUCCESSFUL) {
                // Display the result (eg. DENIED or ApplicationID not configured)
                out.println("Result code : " + e.getResultCode());
                out.println("Result source : " + e.getResultSource());
                out.println("Result description : " + e.getResultDescription());
            } else if (resultStatus == ResultStatus.SUCCESSFUL || resultStatus == ResultStatus.SUCCESSFUL_WITH_WARNING) {
                out.println("Successful Transaction Test");
                out.println("RequestID : " + e.getAttribute("RequestID"));
                out.println("Acquirer Reference : " + e.getTag("AcquirerReference"));
                out.println("Acquirer Date : " + e.getTag("AcquirerDate"));
                out.println("Acquirer Time : " + e.getTag("AcquirerTime"));
                out.println("Authorisation Code : " + e.getTag("AuthorisationCode"));
                out.println("Amount : " + e.getTag("Amount"));
                out.println("Terminal : " + e.getTag("Terminal"));
                out.println("Transaction Index : " + e.getTag("TransactionIndex"));
                out.println(System.getProperty("java.home"));
            }
        } catch (ResultException ex) {
            Logger.getLogger(Visa.class.getName()).log(Level.SEVERE, null, ex);
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
}
