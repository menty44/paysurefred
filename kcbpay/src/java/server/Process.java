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

/**
 *
 * @author Joseph
 */
@WebServlet(name = "Process", urlPatterns = {"/Process"})
public class Process extends HttpServlet {

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
            //out.println("<h1>Servlet Process at " + request.getContextPath() + "</h1>");            
            
            out.println("<h1>Receipt Details Below</h1><br>");
            
            String refno = request.getParameter("vpc_MerchTxnRef");
            String orderinfo = request.getParameter("vpc_OrderInfo");
            String amount = request.getParameter("vpc_Amount");
            String vpcmsg = request.getParameter("vpc_Message");
            String responsecode = request.getParameter("vpc_TxnResponseCode");
            String threeDEnrolled = request.getParameter("vpc_3DSenrolled");
            String threeDStatus = request.getParameter("vpc_3DSstatus");
            String receiptno = request.getParameter("vpc_ReceiptNo");
            String acqresponsecode = request.getParameter("vpc_AcqResponseCode");
            String transactionno = request.getParameter("vpc_TransactionNo");
            String batchno = request.getParameter("vpc_BatchNo");
            //String  = request.getParameter("");
            //String  = request.getParameter("");                                  
            out.println("Reference Number = "+refno+"<br>");
            out.println("Order Info = "+orderinfo+"<br>");
            out.println("Amount = "+amount+"<br>");
            out.println("Returned Message = "+vpcmsg+"<br>");
            out.println("Response Code = "+responsecode+"<br>");
            out.println("Receipt Number = "+receiptno+"<br>");
            out.println("Acquirer Response Code = "+acqresponsecode+"<br>");
            out.println("Transaction Number = "+transactionno+"<br>");
            out.println("Batch Number = "+batchno+"<br>");
            out.println("3D Enrolled = "+threeDEnrolled+"<br>");
            out.println("3D Status = "+threeDStatus+"<br>");
                        
            
                        
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
}
