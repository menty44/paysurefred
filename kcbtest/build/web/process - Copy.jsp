<%-- 
    Document   : process
    Created on : Jan 14, 2014, 3:14:32 PM
    Author     : Joseph
--%>

<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.math.BigInteger"%>
<%@page import="java.security.MessageDigest"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            Map fields = new HashMap();
            String SECURE_SECRET = "B4C68D86E0A8C6F0BEDC65946BFCBC65";
            byte[] ba = null;

            char[] HEX_TABLE = new char[]{
                '0', '1', '2', '3', '4', '5', '6', '7',
                '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

            for (Enumeration enumm = request.getParameterNames();
                    enumm.hasMoreElements();) {
                String fieldName = (String) enumm.nextElement();
                String fieldValue = request.getParameter(fieldName);
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    fields.put(fieldName, fieldValue);
                }
            }
            //String vpcURL = (String) fields.toString();
            String vpcURL = (String) fields.remove("virtualPaymentClientURL");
            fields.remove("vpc_ReturnURL");
            fields.remove("sbtn");
            if (SECURE_SECRET
                    != null && SECURE_SECRET.length()
                    > 0) {
                //String secureHash = hashAllFields(fields);
                // create a list and sort it
                List fieldNames = new ArrayList(fields.keySet());
                Collections.sort(fieldNames);

                // create a buffer for the md5 input and add the secure secret first
                StringBuffer buf = new StringBuffer();
                buf.append(SECURE_SECRET);

                // iterate through the list and add the remaining field values
                Iterator itr = fieldNames.iterator();

                while (itr.hasNext()) {
                    String fieldName = (String) itr.next();
                    String fieldValue = (String) fields.get(fieldName);
                    if ((fieldValue != null) && (fieldValue.length() > 0)) {
                        buf.append(fieldValue);
                    }
                }

                MessageDigest md5 = null;
                //byte[] ba = null;

                try {
                    md5 = MessageDigest.getInstance("MD5");
                    //ba = md5.digest(buf.toString().getBytes("UTF-8"));
                    ba = md5.digest(buf.toString().getBytes());
                } catch (Exception e) {
                } // wont happen

                // create a StringBuffer 2x the size of the hash array
                StringBuffer sb = new StringBuffer(ba.length * 2);

                for (int i = 0; i < ba.length; i++) {
                    sb.append(HEX_TABLE[(ba[i] >> 4) & 0xf]);
                    sb.append(HEX_TABLE[ba[i] & 0xf]);
                }

                String secureHash = sb.toString();
                fields.put("vpc_SecureHash", secureHash);
            }
            // Create a redirection URL
            StringBuffer buf = new StringBuffer();

            buf.append(vpcURL).append('?');
            //appendQueryFields(buf, fields);

            // create a list
            List fNames = new ArrayList(fields.keySet());
            Iterator it = fNames.iterator();

            // move through the list and create a series of URL key/value pairs
            while (it.hasNext()) {
                String fName = (String) it.next();
                String fValue = (String) fields.get(fName);

                if ((fValue != null) && (fValue.length() > 0)) {
                    // append the URL parameters
                    buf.append(URLEncoder.encode(fName));
                    buf.append('=');
                    buf.append(URLEncoder.encode(fValue));
                }

                // add a '&' to the end if we have more fields coming.
                if (it.hasNext()) {
                    buf.append('&');
                }
            }
            // Redirect to Virtual PaymentClient
            response.sendRedirect(buf.toString());
        %>

       <%=fields%>
        <p></p>
        <%=vpcURL%> <p></p>
        <%=ba%><p></p>
        <%=buf.toString()%>

    </body>
</html>
