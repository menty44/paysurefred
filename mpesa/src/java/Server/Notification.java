/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import bean.Transaction;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import objects.Merchant;
import objects.Transactionmpesa;

/**
 *
 * @author Joseph
 */
@WebServlet(name = "Notification", urlPatterns = {"/Notifications"})
public class Notification extends HttpServlet {

    @PersistenceContext(unitName = "mpesaPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;
    @EJB
    private Transaction transaction;

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
            //out.println("<html>");
            //out.println("<head>");
            //out.println("<title>Servlet Notification</title>");
            //out.println("</head>");
            //out.println("<body>");
            //out.println("<h1>Servlet Notification at " + request.getContextPath() + "</h1>");

            
              Merchant merchant = new Merchant(); String id =
              request.getParameter("id"); String mpesa_acc =
              request.getParameter("mpesa_acc");
              merchant=transaction.pullMerchant(mpesa_acc); Transactionmpesa
              transactionmpesa = new Transactionmpesa(); BigInteger ipn = new
              BigInteger(id); transactionmpesa.setIpn(ipn);
              //transactionmpesa.setMerchantid(merchant); //String orig =
              request.getParameter("orig"); transactionmpesa.setOrig("MPESA");
              String destination = request.getParameter("dest"); 
              //String destination = "254724384843"; 
              BigInteger dest = new BigInteger(destination); transactionmpesa.setDest(dest); Date
              date = new Date(); transactionmpesa.setTstamp(date);
              transactionmpesa.setText(request.getParameter("text"));
              transactionmpesa.setCustomerid(2);
              transactionmpesa.setUser(request.getParameter("user"));
              transactionmpesa.setPass(request.getParameter("pass"));
              transactionmpesa.setRoutemethodid(2);
              transactionmpesa.setRoutemethodname("HTTP");
              transactionmpesa.setMpesacode(request.getParameter("mpesa_code"));
              transactionmpesa.setMpesaacc(mpesa_acc);
              //String source = "254724384843";
              String source = request.getParameter("mpesa_msisdn");
              BigInteger mpesamsisdn = new BigInteger(source);
              transactionmpesa.setMpesamsisdn(mpesamsisdn);
              transactionmpesa.setMpesatrxdate(date);
              transactionmpesa.setMpesatrxtime(date);
              transactionmpesa.setMpesaamt(new BigInteger((request.getParameter("mpesa_amt")).replace(".", "")));
              transactionmpesa.setMpesasender(request.getParameter("mpesa_sender"));
              transactionmpesa.setMerchantid(merchant); 
              transaction.persist(transactionmpesa);
             
              //out.println(merchant.getName());
             
              //out.println("Mpesa Transaction Details Saved Successfully");
              out.println("OK| Your M-PESA payment, ref: " + transactionmpesa.getMpesacode() + " for KES " + request.getParameter("mpesa_amt") + " to merchant " + merchant.getName() + " has been processed.");
             

            /*Transactionmpesa transactionmpesa = new Transactionmpesa();
            //out.println("Id is: " + request.getParameter("id") + "<br>");
            Merchant merchant = new Merchant();
            String id = request.getParameter("id");
            BigInteger ipn = new BigInteger(id);
            transactionmpesa.setIpn(ipn);
            String mpesa_acc = request.getParameter("mpesa_acc");
            //merchant = transaction.pullMerchant(mpesa_acc);


            try {

                merchant = (Merchant) em.createNamedQuery("Merchant.findByMpesaid").setParameter("mpesaid", mpesa_acc).getSingleResult();
                transactionmpesa.setMerchantid(merchant);
                //out.println("Origin is: " + request.getParameter("orig") + "<br>");
                String orig = request.getParameter("orig");
                transactionmpesa.setOrig(orig);
                //out.println("Destination is: " + request.getParameter("dest") + "<br>");
                String destination = request.getParameter("dest");
                BigInteger dest = new BigInteger(destination);
                transactionmpesa.setDest(dest);
                //out.println("Time Stamp is: " + request.getParameter("tstamp") + "<br>");
                Date date = new Date();
                transactionmpesa.setTstamp(date);
                //out.println("Text is: " + request.getParameter("text") + "<br>");
                transactionmpesa.setText(request.getParameter("text"));
                //out.println("Customer Id is: " + request.getParameter("customer_id") + "<br>");
                transactionmpesa.setCustomerid(Integer.parseInt(request.getParameter("customer_id")));
                //out.println("User is: " + request.getParameter("user") + "<br>");
                transactionmpesa.setUser(request.getParameter("user"));
                //out.println("Password is: " + request.getParameter("pass") + "<br>");
                transactionmpesa.setPass(request.getParameter("pass"));
                //out.println("Route Method Id is: " + request.getParameter("routemethod_id") + "<br>");
                transactionmpesa.setRoutemethodid(Integer.parseInt(request.getParameter("routemethod_id")));
                //out.println("Route Method Name is: " + request.getParameter("routemethod_name") + "<br>");
                transactionmpesa.setRoutemethodname(request.getParameter("routemethod_name"));
                //out.println("Mpesa Code is: " + request.getParameter("mpesa_code") + "<br>");
                transactionmpesa.setMpesacode(request.getParameter("mpesa_code"));
                //out.println("Mpesa Account is: " + request.getParameter("mpesa_acc") + "<br>");
                transactionmpesa.setMpesaacc(mpesa_acc);
                //out.println("Mpesa Msisdn is: " + request.getParameter("mpesa_msisdn") + "<br>");
                transactionmpesa.setMpesamsisdn(new BigInteger(request.getParameter("mpesa_msisdn")));
                //out.println("Mpesa Transaction Date is: " + request.getParameter("mpesa_trx_date") + "<br>");
                transactionmpesa.setMpesatrxdate(date);
                transactionmpesa.setMpesatrxtime(date);
                //out.println("Mpesa Amount is: " + request.getParameter("mpesa_amt") + "<br>");
                String mullah = request.getParameter("mpesa_amt");
                mullah = mullah.replace(".", "");
                //out.println(mullah);
                transactionmpesa.setMpesaamt(new BigInteger(mullah));
                //out.println("Mpesa Sender is: " + request.getParameter("mpesa_sender") + "<br>");
                transactionmpesa.setMpesasender(request.getParameter("mpesa_sender"));
                //out.println("Business Number is: " + request.getParameter("business_number") + "<br>");  
                transaction.persist(transactionmpesa);
                //out.println("Mpesa Transaction Details Saved Successfully");
                out.println("OK| Your M-PESA payment, ref: " + transactionmpesa.getMpesacode() + " for KES " + request.getParameter("mpesa_amt") + " to merchant " + merchant.getName() + " has been processed.");
                System.out.println("Request Time: " + new Date().getSeconds());
                RequestDispatcher dispatcher = request.getRequestDispatcher("/success.jsp");
                dispatcher.forward(request, response);

            } catch (Exception e) {

                //transactionmpesa.setMerchantid(merchant);
                //out.println("Origin is: " + request.getParameter("orig") + "<br>");
                String orig = request.getParameter("orig");
                transactionmpesa.setOrig(orig);
                //out.println("Destination is: " + request.getParameter("dest") + "<br>");
                String destination = request.getParameter("dest");
                BigInteger dest = new BigInteger(destination);
                transactionmpesa.setDest(dest);
                //out.println("Time Stamp is: " + request.getParameter("tstamp") + "<br>");
                Date date = new Date();
                transactionmpesa.setTstamp(date);
                //out.println("Text is: " + request.getParameter("text") + "<br>");
                transactionmpesa.setText(request.getParameter("text"));
                //out.println("Customer Id is: " + request.getParameter("customer_id") + "<br>");
                transactionmpesa.setCustomerid(Integer.parseInt(request.getParameter("customer_id")));
                //out.println("User is: " + request.getParameter("user") + "<br>");
                transactionmpesa.setUser(request.getParameter("user"));
                //out.println("Password is: " + request.getParameter("pass") + "<br>");
                transactionmpesa.setPass(request.getParameter("pass"));
                //out.println("Route Method Id is: " + request.getParameter("routemethod_id") + "<br>");
                transactionmpesa.setRoutemethodid(Integer.parseInt(request.getParameter("routemethod_id")));
                //out.println("Route Method Name is: " + request.getParameter("routemethod_name") + "<br>");
                transactionmpesa.setRoutemethodname(request.getParameter("routemethod_name"));
                //out.println("Mpesa Code is: " + request.getParameter("mpesa_code") + "<br>");
                transactionmpesa.setMpesacode(request.getParameter("mpesa_code"));
                //out.println("Mpesa Account is: " + request.getParameter("mpesa_acc") + "<br>");
                transactionmpesa.setMpesaacc(mpesa_acc);
                //out.println("Mpesa Msisdn is: " + request.getParameter("mpesa_msisdn") + "<br>");
                transactionmpesa.setMpesamsisdn(new BigInteger(request.getParameter("mpesa_msisdn")));
                //out.println("Mpesa Transaction Date is: " + request.getParameter("mpesa_trx_date") + "<br>");
                transactionmpesa.setMpesatrxdate(date);
                transactionmpesa.setMpesatrxtime(date);
                //out.println("Mpesa Amount is: " + request.getParameter("mpesa_amt") + "<br>");
                String mullah = request.getParameter("mpesa_amt");
                mullah = mullah.replace(".", "");
                //out.println(mullah);
                transactionmpesa.setMpesaamt(new BigInteger(mullah));
                //out.println("Mpesa Sender is: " + request.getParameter("mpesa_sender") + "<br>");
                transactionmpesa.setMpesasender(request.getParameter("mpesa_sender"));
                //out.println("Business Number is: " + request.getParameter("business_number") + "<br>");  
                transaction.persist(transactionmpesa);
                //out.println("Mpesa Transaction Details Saved Successfully");
                out.println("OK| Your M-PESA payment, ref: " + transactionmpesa.getMpesacode() + " for KES " + request.getParameter("mpesa_amt") + " to merchant " + merchant.getName() + " has been processed.");
                System.out.println("Request Time: " + new Date().getSeconds());
                RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
                dispatcher.forward(request, response);

            }


            /*
             * transactionmpesa.setMerchantid(merchant); //out.println("Origin
             * is: " + request.getParameter("orig") + "<br>"); String orig =
             * request.getParameter("orig"); transactionmpesa.setOrig(orig);
             * //out.println("Destination is: " + request.getParameter("dest") +
             * "<br>"); String destination = request.getParameter("dest");
             * BigInteger dest = new BigInteger(destination);
             * transactionmpesa.setDest(dest); //out.println("Time Stamp is: " +
             * request.getParameter("tstamp") + "<br>"); Date date = new Date();
             * transactionmpesa.setTstamp(date); //out.println("Text is: " +
             * request.getParameter("text") + "<br>");
             * transactionmpesa.setText(request.getParameter("text"));
             * //out.println("Customer Id is: " +
             * request.getParameter("customer_id") + "<br>");
             * transactionmpesa.setCustomerid(Integer.parseInt(request.getParameter("customer_id")));
             * //out.println("User is: " + request.getParameter("user") +
             * "<br>"); transactionmpesa.setUser(request.getParameter("user"));
             * //out.println("Password is: " + request.getParameter("pass") +
             * "<br>"); transactionmpesa.setPass(request.getParameter("pass"));
             * //out.println("Route Method Id is: " +
             * request.getParameter("routemethod_id") + "<br>");
             * transactionmpesa.setRoutemethodid(Integer.parseInt(request.getParameter("routemethod_id")));
             * //out.println("Route Method Name is: " +
             * request.getParameter("routemethod_name") + "<br>");
             * transactionmpesa.setRoutemethodname(request.getParameter("routemethod_name"));
             * //out.println("Mpesa Code is: " +
             * request.getParameter("mpesa_code") + "<br>");
             * transactionmpesa.setMpesacode(request.getParameter("mpesa_code"));
             * //out.println("Mpesa Account is: " +
             * request.getParameter("mpesa_acc") + "<br>");
             * transactionmpesa.setMpesaacc(mpesa_acc); //out.println("Mpesa
             * Msisdn is: " + request.getParameter("mpesa_msisdn") + "<br>");
             * transactionmpesa.setMpesamsisdn(new
             * BigInteger(request.getParameter("mpesa_msisdn")));
             * //out.println("Mpesa Transaction Date is: " +
             * request.getParameter("mpesa_trx_date") + "<br>");
             * transactionmpesa.setMpesatrxdate(date);
             * transactionmpesa.setMpesatrxtime(date); //out.println("Mpesa
             * Amount is: " + request.getParameter("mpesa_amt") + "<br>");
             * String mullah = request.getParameter("mpesa_amt"); mullah =
             * mullah.replace(".", ""); //out.println(mullah);
             * transactionmpesa.setMpesaamt(new BigInteger(mullah));
             * //out.println("Mpesa Sender is: " +
             * request.getParameter("mpesa_sender") + "<br>");
             * transactionmpesa.setMpesasender(request.getParameter("mpesa_sender"));
             * //out.println("Business Number is: " +
             * request.getParameter("business_number") + "<br>");
             * transaction.persist(transactionmpesa); //out.println("Mpesa
             * Transaction Details Saved Successfully"); out.println("OK| Your
             * M-PESA payment, ref: " + transactionmpesa.getMpesacode() + " for
             * KES " + request.getParameter("mpesa_amt") + " to merchant " +
             * merchant.getName() + " has been processed.");
             * System.out.println("Request Time: " + new Date().getSeconds());
             * RequestDispatcher dispatcher =
             * request.getRequestDispatcher("/success.jsp");
             * dispatcher.forward(request, response);
             */




            //out.println("</body>");
            //out.println("</html>");
        } finally {
            out.println("FAIL| Invalid Account Number. Please Contact Paysure Ltd via 0708299367");
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

    public void persist(Object object) {
        try {
            utx.begin();
            em.persist(object);
            utx.commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }
}
