package server;

import bean.Visa;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
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
import paygate.objects.Purchase;
import paygate.objects.Transaction;
import utils.CloseCart;
import utils.Email;

@WebServlet(name = "process", urlPatterns = {"/process"})
public class process extends HttpServlet {

    @PersistenceContext(unitName = "iveriPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;
    @EJB
    private Visa visa;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //response.setContentType("text/plain");
        //PrintWriter out = response.getWriter();
        try {
            String referenceno = request.getParameter("MERCHANTREFERENCE");
            //int amount= Integer.valueOf(request.getParameter("LITE_ORDER_AMOUNT"));
            String code = request.getParameter("LITE_PAYMENT_CARD_STATUS");
            String rcode = "0";
            Transaction transaction = new Transaction();
            //Purchase purchase=visa.findPurchase(referenceno);
            List results = visa.findP(referenceno);
            if (results.isEmpty()) {
                Purchase purchase = new Purchase();
                transaction.setAmount(BigInteger.ZERO);
            } else if (results.size() == 1) {
                if (code.compareTo(rcode) == 0) {
                    Purchase purchase = (Purchase) results.get(0);
                    transaction.setAmount(BigInteger.valueOf(purchase.getAmount()).divide(BigInteger.valueOf(100)));
                    transaction.setRefno(purchase.getReferenceno());
                    transaction.setStatusid(visa.findStatus());
                    transaction.setResponsecodeid(visa.findResponsecode(rcode));
                    transaction.setCreated(new Date());
                    transaction.setDescription(purchase.getDescription());
                    transaction.setModified(new Date());
                    transaction.setMerchantid(purchase.getMerchantid());
                    transaction.setMerchantname(purchase.getMerchantid().getName());
                    transaction.setTransactionsourceid(purchase.getTransactionsourceid());
                    transaction.setCardtype("visa");
                    transaction.setClientname(purchase.getClientname());
                    transaction.setEmail(purchase.getEmail());
                    transaction.setCurrency(purchase.getCurrency());                    
                    String url = purchase.getMerchantid().getUrl() + "?amount=" + purchase.getAmount() + "&transmission=" + purchase.getTransmission() + "&systraceno=" + purchase.getSystemtraceno() + "&refno=" + purchase.getReferenceno();
                    visa.createTransaction(transaction);
                    Email email = new Email();
                    email.sendEmail(purchase, url);
                    CloseCart closeCart = new CloseCart();
                    closeCart.callUrl(url);
                    request.setAttribute("transaction", transaction);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/success.jsp");                    
                    //URL oracle = new URL("http://prestashop.paysurekenya.com/modules/paysurevisa/closecart.php?amount=700500&transmission=1219163658&systraceno=000036&refno=30000000001");
                    /*URL oracle = new URL(url);
                    URLConnection yc = oracle.openConnection();
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            yc.getInputStream()));
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        System.out.println(inputLine);
                    }
                    in.close();*/
                    /*Email email = new Email();
                    email.sendEmail(purchase, url);*/
                    System.out.println("Redirect Url:==>" + url);
                    dispatcher.forward(request, response);
                } else {
                    Purchase purchase = (Purchase) results.get(0);
                    transaction.setAmount(BigInteger.valueOf(purchase.getAmount()).divide(BigInteger.valueOf(100)));
                    transaction.setRefno(purchase.getReferenceno());
                    transaction.setStatusid(visa.findStatus());
                    transaction.setResponsecodeid(visa.findResponsecode(rcode));
                    transaction.setCreated(new Date());
                    transaction.setDescription(purchase.getDescription());
                    transaction.setModified(new Date());
                    transaction.setMerchantid(purchase.getMerchantid());
                    transaction.setMerchantname(purchase.getMerchantid().getName());
                    transaction.setTransactionsourceid(purchase.getTransactionsourceid());
                    transaction.setCardtype("visa");
                    transaction.setClientname(purchase.getClientname());
                    transaction.setEmail(purchase.getEmail());
                    transaction.setCurrency(purchase.getCurrency());
                    request.setAttribute("transaction", transaction);
                    request.setAttribute("code", code);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
                    String url = purchase.getMerchantid().getReturnurl() + "?amount=" + purchase.getAmount() + "&transmission=" + purchase.getTransmission() + "&systraceno=" + purchase.getSystemtraceno() + "&refno=" + purchase.getReferenceno();
                    //CloseCart closeCart = new CloseCart();
                    //closeCart.callUrl(url);
                    URL oracle = new URL(url);
                    URLConnection yc = oracle.openConnection();
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            yc.getInputStream()));
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        System.out.println(inputLine);
                    }
                    in.close();
                    dispatcher.forward(request, response);
                }

                // out.println("</body>");
                // out.println("</html>");

            } //
            // out.close();
        } finally {
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
