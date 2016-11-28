<%-- 
    Document   : vpcpay
    Created on : Feb 3, 2014, 4:06:43 PM
    Author     : Joseph
--%>

<%@page import="java.util.TreeMap"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List,
         java.util.ArrayList,
         java.util.Collections,
         java.util.Iterator,
         java.util.Enumeration,
         java.security.MessageDigest,
         java.util.Map,
         java.math.*,
         java.security.*,
         java.net.URLEncoder,
         java.util.HashMap"%>

<%! // Define Constants
    // ****************
    // This is secret for encoding the MD5 hash
    // This secret will vary from merchant to merchant
    // static final String SECURE_SECRET = "your-secure-hash-secret";
    //static final String SECURE_SECRET = "2B18335BE717C987B5B42C9E821F1EE5";
    static final String SECURE_SECRET = "B4C68D86E0A8C6F0BEDC65946BFCBC65";
    //static final String SECURE_SECRET = "28AAE9CAF6A3DBBFC42EEFE99B10BC56";    
    StringBuffer buff = new StringBuffer();
    StringBuffer buff2 = new StringBuffer();
    StringBuffer buff3 = new StringBuffer();
    String buff4 = new String();
    // This is an array for creating hex chars
    static final char[] HEX_TABLE = new char[]{
        '0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

//  ----------------------------------------------------------------------------
    /**
     * This method is for sorting the fields and creating an MD5 secure hash.
     *
     * @param fields is a map of all the incoming hey-value pairs from the VPC
     * @param buf is the hash being returned for comparison to the incoming hash
     */
    String hashAllFields(Map fields) {

        // create a list and sort it
        /*
         * List fieldNames = new ArrayList(fields.keySet());
         * Collections.sort(fieldNames);
         */
        Map<String, String> fieldNames = new TreeMap<String, String>(fields);


        // create a buffer for the md5 input and add the secure secret first
        StringBuffer buf = new StringBuffer();
        buf.append(SECURE_SECRET);

        // iterate through the list and add the remaining field values
        /*
         * Iterator itr = fieldNames.iterator();
         *
         * while (itr.hasNext()) { String fieldName = (String) itr.next();
         * String fieldValue = (String) fields.get(fieldName); if ((fieldValue
         * != null) && (fieldValue.length() > 0)) { buf.append(fieldValue); } }
         */

        for (Map.Entry<String, String> entry : fieldNames.entrySet()) {
            String fieldName = entry.getKey();
            String fieldValue = entry.getValue();
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                buf.append(fieldValue);
            }
            buff = buf;
        }

        MessageDigest md5 = null;
        byte[] ba = null;

        // create the md5 hash and UTF-8 encode it
        try {
            md5 = MessageDigest.getInstance("MD5");
            buff3 = buf;
            ba = md5.digest(buf.toString().getBytes("UTF-8"));

            byte[] a = SECURE_SECRET.getBytes("UTF-8");
            byte[] b = md5.digest(buf.toString().getBytes("UTF-8"));
            byte[] c = new byte[a.length + b.length];
            //ba = new byte[a.length+b.length];
            buff4 = ba.toString();

        } catch (Exception e) {
        } // wont happen

        //return buf.toString();
        return hex(ba);

    } // end hashAllFields()

//  ----------------------------------------------------------------------------
    /**
     * Returns Hex output of byte array
     */
    static String hex(byte[] input) {
        // create a StringBuffer 2x the size of the hash array
        StringBuffer sb = new StringBuffer(input.length * 2);

        // retrieve the byte array data, convert it to hex
        // and add it to the StringBuffer
        for (int i = 0; i < input.length; i++) {
            sb.append(HEX_TABLE[(input[i] >> 4) & 0xf]);
            sb.append(HEX_TABLE[input[i] & 0xf]);
        }
        return sb.toString();
    }

//  ----------------------------------------------------------------------------
    /**
     * This method is for creating a URL query string.
     *
     * @param buf is the inital URL for appending the encoded fields to
     * @param fields is the input parameters from the order page
     */
    // Method for creating a URL query string
    void appendQueryFields(StringBuffer buf, Map fields) {

        // create a list
        List fieldNames = new ArrayList(fields.keySet());
        Iterator itr = fieldNames.iterator();

        // move through the list and create a series of URL key/value pairs
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) fields.get(fieldName);

            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                // append the URL parameters
                buf.append(URLEncoder.encode(fieldName));
                buf.append('=');
                buf.append(URLEncoder.encode(fieldValue));
            }

            // add a '&' to the end if we have more fields coming.
            if (itr.hasNext()) {
                buf.append('&');
            }
        }
        buff2 = buf;

    } // appendQueryFields()

//  ----------------------------------------------------------------------------

%><%// *******************************************
    // START OF MAIN PROGRAM
    // *******************************************

    // The Page does a redirect to the Virtual Payment Client

    // retrieve all the parameters into a hash map
    Map fields = new HashMap();
    for (Enumeration enumm = request.getParameterNames(); enumm.hasMoreElements();) {
        String fieldName = (String) enumm.nextElement();
        String fieldValue = request.getParameter(fieldName);
        if ((fieldValue != null) && (fieldValue.length() > 0)) {
            fields.put(fieldName, fieldValue);
        }
    }

    // no need to send the vpc url, EnableAVSdata and submit button to the vpc
    String vpcURL = (String) fields.remove("virtualPaymentClientURL");
    fields.remove("vpc_ReturnURL");
    fields.remove("SubButL");
    fields.remove("Title");

    // Retrieve the order page URL from the incoming order page and add it to 
    // the hash map. This is only here to give the user the easy ability to go 
    // back to the Order page. This would not be required in a production system
    // NB. Other merchant application fields can be added in the same manner
    //String againLink = request.getHeader("Referer");
    //fields.put("AgainLink", againLink);

    // Create MD5 secure hash and insert it into the hash map if it was created
    // created. Remember if SECURE_SECRET = "" it will not be created
    if (SECURE_SECRET != null && SECURE_SECRET.length() > 0) {
        String secureHash = hashAllFields(fields);
        fields.put("vpc_SecureHash", secureHash);
    }

    // Create a redirection URL
    StringBuffer buf = new StringBuffer();
    buf.append(vpcURL).append('?');
    appendQueryFields(buf, fields);

    // Redirect to Virtual PaymentClient
    response.sendRedirect(buf.toString());
%>

<html>
    <body></body>
</html>
