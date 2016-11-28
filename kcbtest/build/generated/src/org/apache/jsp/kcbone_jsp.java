package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class kcbone_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("    <head><title>Virtual Payment Client Example</title>\n");
      out.write("        <meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'>\n");
      out.write("        <style type='text/css'>\n");
      out.write("            <!--\n");
      out.write("            h1       { font-family:Arial,sans-serif; font-size:24pt; color:#08185A; font-weight:100}\n");
      out.write("            h2.co    { font-family:Arial,sans-serif; font-size:24pt; color:#08185A; margin-top:0.1em; margin-bottom:0.1em; font-weight:100}\n");
      out.write("            h3.co    { font-family:Arial,sans-serif; font-size:16pt; color:#000000; margin-top:0.1em; margin-bottom:0.1em; font-weight:100}\n");
      out.write("            body     { font-family:Verdana,Arial,sans-serif; font-size:10pt; color:#08185A background-color:#FFFFFF }\n");
      out.write("            p        { font-family:Verdana,Arial,sans-serif; font-size:10pt; color:#FFFFFF }\n");
      out.write("            p.bl     { font-family:Verdana,Arial,sans-serif; font-size:8pt; color:#08185A }\n");
      out.write("            a:link   { font-family:Verdana,Arial,sans-serif; font-size:8pt; color:#08185A }\n");
      out.write("            a:visited{ font-family:Verdana,Arial,sans-serif; font-size:8pt; color:#08185A }\n");
      out.write("            a:hover  { font-family:Verdana,Arial,sans-serif; font-size:8pt; color:#FF0000 }\n");
      out.write("            a:active { font-family:Verdana,Arial,sans-serif; font-size:8pt; color:#FF0000 }\n");
      out.write("            td       { font-family:Verdana,Arial,sans-serif; font-size:8pt; color:#08185A }\n");
      out.write("            th       { font-family:Verdana,Arial,sans-serif; font-size:10pt; color:#08185A; font-weight:bold; background-color:#CED7EF; padding-top:0.5em; padding-bottom:0.5em}\n");
      out.write("            input    { font-family:Verdana,Arial,sans-serif; font-size:8pt; color:#08185A; background-color:#CED7EF; font-weight:bold }\n");
      out.write("            select   { font-family:Verdana,Arial,sans-serif; font-size:8pt; color:#08185A; background-color:#CED7EF; font-weight:bold }\n");
      out.write("            textarea { font-family:Verdana,Arial,sans-serif; font-size:8pt; color:#08185A; background-color:#CED7EF; font-weight:normal; scrollbar-arrow-color:#08185A; scrollbar-base-color:#CED7EF }\n");
      out.write("            -->\n");
      out.write("        </style></head>\n");
      out.write("    <body>\n");
      out.write("\n");
      out.write("        <!-- branding table -->\n");
      out.write("        <table width='100%' border='2' cellpadding='2' bgcolor='#0074C4'><tr><td bgcolor='#CED7EF' width='90%'><h2 class='co'>&nbsp;Virtual Payment Client - Version 1</h2></td><td bgcolor='#0074C4' align='center'><h3 class='co'>MIGS</h3></td></tr></table>\n");
      out.write("\n");
      out.write("    <center><h1>JSP 3-Party Basic Example - Request Details</H1></center>\n");
      out.write("\n");
      out.write("    <!-- The \"Pay Now!\" button submits the form, transferring control -->\n");
      out.write("    <form action=\"VPC_JSP_3P_DO.jsp\" method=\"post\">\n");
      out.write("\n");
      out.write("        <!-- get user input -->\n");
      out.write("        <table width=\"80%\" align=\"center\" border=\"0\" cellpadding='0' cellspacing='0'>\n");
      out.write("\n");
      out.write("            <input type=\"hidden\" name=\"Title\" value=\"JSP VPC 3-Party\">\n");
      out.write("\n");
      out.write("            <tr bgcolor=\"#CED7EF\">\n");
      out.write("                <td width=\"1%\">&nbsp;</td>\n");
      out.write("                <td width=\"40%\" align=\"right\"><b><i>Virtual Payment Client URL:&nbsp;</i></b></td>\n");
      out.write("                <td width=\"59%\"><input type=\"text\" name=\"virtualPaymentClientURL\" size=\"63\" value=\"https://migs.mastercard.com.au/vpcpay\" maxlength=\"250\"></td>\n");
      out.write("            </tr>\n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"3\">&nbsp;<hr width=\"75%\">&nbsp;</td>\n");
      out.write("            </tr>\n");
      out.write("            <tr bgcolor=\"#0074C4\">\n");
      out.write("                <td colspan=\"3\" height=\"25\"><p><b>&nbsp;Basic 3-Party Transaction Fields</b></p></td>\n");
      out.write("            </tr>\n");
      out.write("            <tr>\n");
      out.write("                <td>&nbsp;</td>\n");
      out.write("                <td align=\"right\"><b><i> VPC Version: </i></b></td>\n");
      out.write("                <td><input type=\"text\" name=\"vpc_Version\" value=\"1\" size=\"20\" maxlength=\"8\"></td>\n");
      out.write("            </tr>\n");
      out.write("            <tr bgcolor=\"#CED7EF\">\n");
      out.write("                <td>&nbsp;</td>\n");
      out.write("                <td align=\"right\"><b><i>Command Type: </i></b></td>\n");
      out.write("                <td><input type=\"text\" name=\"vpc_Command\" value=\"pay\" size=\"20\" maxlength=\"16\"></td>\n");
      out.write("            </tr>\n");
      out.write("            <tr>\n");
      out.write("                <td>&nbsp;</td>\n");
      out.write("                <td align=\"right\"><b><i>Merchant AccessCode: </i></b></td>\n");
      out.write("                <td><input type=\"text\" name=\"vpc_AccessCode\" value=\"\" size=\"20\" maxlength=\"8\"></td>\n");
      out.write("            </tr>\n");
      out.write("            <tr bgcolor=\"#CED7EF\">\n");
      out.write("                <td>&nbsp;</td>\n");
      out.write("                <td align=\"right\"><b><i>Merchant Transaction Reference: </i></b></td>\n");
      out.write("                <td><input type=\"text\" name=\"vpc_MerchTxnRef\" value=\"\" size=\"20\" maxlength=\"40\"></td>\n");
      out.write("            </tr>\n");
      out.write("            <tr>\n");
      out.write("                <td>&nbsp;</td>\n");
      out.write("                <td align=\"right\"><b><i>MerchantID: </i></b></td>\n");
      out.write("                <td><input type=\"text\" name=\"vpc_Merchant\" value=\"\" size=\"20\" maxlength=\"16\"></td>\n");
      out.write("            </tr>\n");
      out.write("            <tr bgcolor=\"#CED7EF\">\n");
      out.write("                <td>&nbsp;</td>\n");
      out.write("                <td align=\"right\"><b><i>Transaction OrderInfo: </i></b></td>\n");
      out.write("                <td><input type=\"text\" name=\"vpc_OrderInfo\" value=\"VPC Example\" size=\"20\" maxlength=\"34\"></td>\n");
      out.write("            </tr>\n");
      out.write("            <tr>\n");
      out.write("                <td>&nbsp;</td>\n");
      out.write("                <td align=\"right\"><b><i>Purchase Amount: </i></b></td>\n");
      out.write("                <td><input type=\"text\" name=\"vpc_Amount\" value=\"100\" size=\"20\" maxlength=\"10\"></td>\n");
      out.write("            </tr>\n");
      out.write("            <tr>\n");
      out.write("                <td>&nbsp;</td>\n");
      out.write("                <td align=\"right\"><b><i>Currency: </i></b></td>\n");
      out.write("                <td><input type=\"text\" name=\"vpc_Currency\" value=\"KES\" size=\"20\" maxlength=\"10\" readonly=\"readonly\"></td>\n");
      out.write("            </tr>    \n");
      out.write("            <tr bgcolor=\"#CED7EF\">\n");
      out.write("                <td>&nbsp;</td>\n");
      out.write("                <td align=\"right\"><b><i>Payment Server Display Language Locale: </i></b></td>\n");
      out.write("                <td><input type=\"text\" name=\"vpc_Locale\" value=\"en\" size=\"20\" maxlength=\"5\"></td>\n");
      out.write("            </tr>\n");
      out.write("            <tr bgcolor=\"#CED7EF\">\n");
      out.write("                <td>&nbsp;</td>\n");
      out.write("                <td align=\"right\"><b><i>Return Auth Response Data: </i></b></td>\n");
      out.write("                <td><input type=\"text\" name=\"vpc_ReturnAuthResponseData\" value=\"Y\" size=\"20\" maxlength=\"5\"></td>\n");
      out.write("            </tr>\n");
      out.write("            <tr>\n");
      out.write("                <td>&nbsp;</td>\n");
      out.write("                <td align=\"right\"><b><i>Receipt ReturnURL: </i></b></td>\n");
      out.write("                <td><input type=\"text\" name=\"vpc_ReturnURL\" size=\"63\" value=\"http://196.216.64.237:8080/kcbtest/Process\" maxlength=\"250\"></td>\n");
      out.write("            </tr>\n");
      out.write("            <tr>    <td colspan=\"3\">&nbsp;</td></tr>\n");
      out.write("\n");
      out.write("            <tr>    <td colspan=\"2\">&nbsp;</td> \n");
      out.write("                <td><input type=\"submit\" name=\"SubButL\" value=\"Pay Now!\"></td></tr>\n");
      out.write("\n");
      out.write("            <tr><td colspan=\"3\">&nbsp;<hr width=\"75%\">&nbsp;</td></tr>\n");
      out.write("            <tr bgcolor=\"#0074C4\">\n");
      out.write("                <td colspan=\"3\" height=\"25\"><p><b>&nbsp;Optional Ticket Number Field</b></p></td>\n");
      out.write("            </tr>\n");
      out.write("            <tr>\n");
      out.write("                <td>&nbsp;</td>\n");
      out.write("                <td align=\"right\"><b><i>TicketNo: </i></b></td>\n");
      out.write("                <td><input type=\"text\" name=\"vpc_TicketNo\" maxlength=\"15\"></td>\n");
      out.write("            </tr>\n");
      out.write("\n");
      out.write("\n");
      out.write("            <tr><td colspan=\"3\">&nbsp;<hr width=\"75%\">&nbsp;</td></tr>\n");
      out.write("        </table><br/>\n");
      out.write("    </form>\n");
      out.write("</body>\n");
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
