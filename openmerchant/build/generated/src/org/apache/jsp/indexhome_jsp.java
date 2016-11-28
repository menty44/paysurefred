package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class indexhome_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.Vector _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>Open Merchant Payment Form</title>\n");
      out.write("        <link rel=\"stylesheet\" href=\"css/style.css\"/>\n");
      out.write("        <script src=\"js/jquery.js\"></script>\n");
      out.write("        <script src=\"js/custom.js\"></script>\n");
      out.write("\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <div id=\"logo\">\n");
      out.write("            <img src=\"https://epayments.paysure.co.ke/logo/openmerchant/logo.png\"> \n");
      out.write("        </div>\n");
      out.write("                               \n");
      out.write("        ");
                                String nm = request.getParameter("mname");
        String mn="https://epayments.paysure.co.ke/logo/"+nm.toLowerCase()+".png";
        
        
        

      out.write("                                                                       \n");
      out.write("                            \n");
      out.write("        <table align=\"center\" id=\"tblone\">\n");
      out.write("            <tr>\n");
      out.write("                <td>\n");
      out.write("                    <img src=\"");
      out.print(mn);
      out.write("\">\n");
      out.write("                </td>\n");
      out.write("            </tr>                                            \n");
      out.write("        </table><p>\n");
      out.write("        <form action=\"Process\" name=\"formone\" id=\"formone\" method=\"POST\">\n");
      out.write("            <table align=\"center\" id=\"frmtbl\">\n");
      out.write("                <tr>\n");
      out.write("                    <td>\n");
      out.write("                        Name:<p>\n");
      out.write("                    </td>\n");
      out.write("                    <td>\n");
      out.write("                        <input type=\"hidden\" name=\"mname\" value=\"");
      out.print(request.getParameter("mname"));
      out.write("\"/>\n");
      out.write("                        <input type=\"text\" name=\"name\" value=\"\" required=\"true\"/><p>\n");
      out.write("                    </td>\n");
      out.write("                </tr>   \n");
      out.write("                <tr>\n");
      out.write("                    <td>\n");
      out.write("                        Email:<p>\n");
      out.write("                    </td>\n");
      out.write("                    <td>\n");
      out.write("                        <input type=\"text\" name=\"email\" value=\"\" required=\"true\"/><p>\n");
      out.write("                    </td>\n");
      out.write("                </tr>        \n");
      out.write("                <tr>\n");
      out.write("                    <td>\n");
      out.write("                        Amount:<p>\n");
      out.write("                    </td>\n");
      out.write("                    <td>\n");
      out.write("                        <input type=\"number\" name=\"amount\" value=\"\" required=\"true\" title=\"Amount Should Include Cents! e.g. 100.00 or 100.85\"/><p>\n");
      out.write("                    </td>\n");
      out.write("                </tr>  \n");
      out.write("                <tr>\n");
      out.write("                    <td>\n");
      out.write("                        Description:<p>\n");
      out.write("                    </td>\n");
      out.write("                    <td>\n");
      out.write("                        <input type=\"text\" name=\"description\" value=\"\" required=\"true\"/><p>\n");
      out.write("                    </td>\n");
      out.write("                </tr>                                 \n");
      out.write("                <tr>\n");
      out.write("                    <td><input type=\"radio\" name=\"payoption\" value=\"1\">Kenswitch<br><img src=\"https://epayments.paysure.co.ke/logo/openmerchant/kenswitch.png\"></td>\n");
      out.write("                    <td><input type=\"radio\" name=\"payoption\" value=\"2\" style=\"margin-left: 12px\">VISA/MasterCard<br><br><img src=\"https://epayments.paysure.co.ke/logo/openmerchant/visa-mastercard.png\" style=\"padding-left: 12px\"></td><p>\n");
      out.write("                </tr>\n");
      out.write("                <tr>\n");
      out.write("                    <td colspan=\"2\"><p><input type=\"checkbox\" id=\"chkAgree\" onclick=\"$('#btnSubmit').attr('disabled', !$(this).is(':checked'));\"><a href=\"terms.jsp\">I Agree with the terms and conditions</a><br></td><p>\n");
      out.write("\n");
      out.write("                </tr>\n");
      out.write("                <tr>\n");
      out.write("                    <td></td>\n");
      out.write("                    <td><p><input type=\"submit\" id=\"btnSubmit\" value=\"Make Payment\" name=\"submit\" /></td>\n");
      out.write("                </tr>             \n");
      out.write("            </table>\n");
      out.write("        </form>    \n");
      out.write("    </body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
