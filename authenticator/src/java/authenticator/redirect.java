/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package authenticator;

import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import objects.Cardtype;
import objects.Purchase;
import objects.Responsecode;

/**
 *
 * @author gachanja
 */
public class redirect extends HttpServlet {   
    @EJB
    private Db db;
    Purchase purchase = new Purchase();
    
    
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //EntityManagerFactory emf = Persistence.createEntityManagerFactory("authenticatorPU");
        String referenceno = request.getParameter("ref");
        PrintWriter out = response.getWriter();        
        try {
                                  
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet redirect</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet redirect at " + request.getContextPath() + "</h1>");
            
            Cardtype cardtype = new Cardtype(1);
            System.out.println("Card Type : "+cardtype);
            out.println("CardType : "+cardtype);
            Responsecode responsecode = new Responsecode();
            responsecode = db.findResponsecode("204");
            //System.out.println("Response Code : "+responsecode);
            out.println(responsecode);
            responsecode = db.findResCode(cardtype, "12");
            //System.out.println(responsecode);
            out.println(responsecode);
            purchase = db.findByRefNo(referenceno);
              
            //System.out.println("Purchase is : "+purchase);
            out.println("Purchase is : "+purchase);
            
            //String url = "http://localhost:8080/kcbpay/receive.jsp?vpc_Version=1&vpc_Command=pay&vpc_AccessCode=46B4DDFD&vpc_MerchTxnRef=1R0000000075&vpc_Merchant=61012001&vpc_OrderInfo=1R0000000075&vpc_Amount=325&vpc_Locale=en&vpc_ReturnAuthResponseData=Y&vpc_ReturnURL=https://epayments.paysure.co.ke/kcbpay/Process&=virtualPaymentClientURL=https://migs-mtf.mastercard.com.au/vpcpay";
            
            String url = "https://epayments.paysure.co.ke/kcbpay/receive.jsp?vpc_Version=1&vpc_Command=pay&vpc_AccessCode=46B4DDFD&vpc_MerchTxnRef=1R0000000075&vpc_Merchant=61012001&vpc_OrderInfo=1R0000000075&vpc_Amount=325&vpc_Locale=en&vpc_ReturnAuthResponseData=Y&vpc_ReturnURL=https://epayments.paysure.co.ke/kcbpay/Process&=virtualPaymentClientURL=https://migs-mtf.mastercard.com.au/vpcpay";
            
            //String url = "vpc_AccessCode=46B4DDFD&vpc_ReturnAuthResponseData=Y&vpc_Version=1&vpc_OrderInfo=1R0000000058&vpc_Command=pay&vpc_Locale=en&vpc_Merchant=61012001&vpc_Amount=10000&vpc_SecureHash=DF1D9DA691718560DF8D5F4750ABC238&vpc_MerchTxnRef=1R0000000058";
            
            
            response.sendRedirect(url);
            
            out.println("</body>");
            out.println("</html>");

        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    
}
