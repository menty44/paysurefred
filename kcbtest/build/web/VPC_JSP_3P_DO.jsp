<%@page import="java.util.TreeMap"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--

/* -----------------------------------------------------------------------------

 Version 2.0

------------------ Disclaimer --------------------------------------------------

Copyright 2004 Dialect Holdings.  All rights reserved.

This document is provided by Dialect Holdings on the basis that you will treat
it as confidential.

No part of this document may be reproduced or copied in any form by any means
without the written permission of Dialect Holdings.  Unless otherwise expressly
agreed in writing, the information contained in this document is subject to
change without notice and Dialect Holdings assumes no responsibility for any
alteration to, or any error or other deficiency, in this document.

All intellectual property rights in the Document and in all extracts and things
derived from any part of the Document are owned by Dialect and will be assigned
to Dialect on their creation. You will protect all the intellectual property
rights relating to the Document in a manner that is equal to the protection
you provide your own intellectual property.  You will notify Dialect
immediately, and in writing where you become aware of a breach of Dialect's
intellectual property rights in relation to the Document.

The names "Dialect", "QSI Payments" and all similar words are trademarks of
Dialect Holdings and you must not use that name or any similar name.

Dialect may at its sole discretion terminate the rights granted in this
document with immediate effect by notifying you in writing and you will
thereupon return (or destroy and certify that destruction to Dialect) all
copies and extracts of the Document in its possession or control.

Dialect does not warrant the accuracy or completeness of the Document or its
content or its usefulness to you or your merchant customers.   To the extent
permitted by law, all conditions and warranties implied by law (whether as to
fitness for any particular purpose or otherwise) are excluded.  Where the
exclusion is not effective, Dialect limits its liability to $100 or the
resupply of the Document (at Dialect's option).

Data used in examples and sample data files are intended to be fictional and
any resemblance to real persons or companies is entirely coincidental.

Dialect does not indemnify you or any third party in relation to the content or
any use of the content as contemplated in these terms and conditions.

Mention of any product not owned by Dialect does not constitute an endorsement
of that product.

This document is governed by the laws of New South Wales, Australia and is
intended to be legally binding.

-------------------------------------------------------------------------------

Following is a copy of the disclaimer / license agreement provided by RSA:

Copyright (C) 1991-2, RSA Data Security, Inc. Created 1991. All rights reserved.

License to copy and use this software is granted provided that it is identified
as the "RSA Data Security, Inc. MD5 Message-Digest Algorithm" in all material 
mentioning or referencing this software or this function.

License is also granted to make and use derivative works provided that such 
works are identified as "derived from the RSA Data Security, Inc. MD5 
Message-Digest Algorithm" in all material mentioning or referencing the 
derived work.

RSA Data Security, Inc. makes no representations concerning either the 
merchantability of this software or the suitability of this software for any 
particular purpose. It is provided "as is" without express or implied warranty 
of any kind.

These notices must be retained in any copies of any part of this documentation 
and/or software.

-------------------------------------------------------------------------------- 
 
This example assumes that a form has been sent to this example with the
required fields. The example then processes the command and displays the
receipt or error to a HTML page in the users web browser.

*****
NOTE:
*****
 
  For jdk1.2, 1.3
  * Must have jsse.jar, jcert.jar and jnet.jar in your classpath
  * Best approach is to make them installed extensions - 
    i.e. put them in the jre/lib/ext directory.

  For jdk1.4 (jsse is already part of default installation - should run fine)

--------------------------------------------------------------------------------

 @author Dialect Payment Solutions Pty Ltd Group 

------------------------------------------------------------------------------*/

--%>
<%@ page import="java.util.List,
         java.util.ArrayList,
         java.util.Collections,
         java.util.Iterator,
         java.util.Enumeration,
         java.security.MessageDigest,
         java.util.Map,
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
            byte[] c = new byte[a.length+b.length];
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



    <body>

        <!--<%= fields.get("vpc_AccessCode")%>
        <%= fields.get("vpc_Amount")%>
        <%= fields.get("vpc_Command")%>
        <%= fields.get("vpc_Locale")%>-->


        <%= fields%><br><br><br><br><br>
        <%

            Map<String, String> treeMap = new TreeMap<String, String>(fields);





        %>

        <%= treeMap%>


        <form action="https://migs-mtf.mastercard.com.au/vpcpay" method="post">
            <table >
                <tr >
                    <td>&nbsp;</td>
                    <td align="right"><b><i> VPC Version: </i></b></td>
                    <td><input type="text" name="vpc_Version" value="<%= fields.get("vpc_Version")%>" size="20" maxlength="8"></td><br>
                </tr>
                <tr bgcolor="#CED7EF">
                    <td>&nbsp;</td>
                    <td align="right"><b><i>Command Type: </i></b></td>
                    <td><input type="text" name="vpc_Command" value="<%= fields.get("vpc_Command")%>" size="20" maxlength="16"></td><br>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td align="right"><b><i>Merchant AccessCode: </i></b></td>
                    <td><input type="text" name="vpc_AccessCode" value="<%= fields.get("vpc_AccessCode")%>" size="20" maxlength="8"></td><br>
                </tr>
                <tr bgcolor="#CED7EF">
                    <td>&nbsp;</td>
                    <td align="right"><b><i>Merchant Transaction Reference: </i></b></td>
                    <td><input type="text" name="vpc_MerchTxnRef" value="<%= fields.get("vpc_MerchTxnRef")%>" size="20" maxlength="40"></td><br>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td align="right"><b><i>MerchantID: </i></b></td>
                    <td><input type="text" name="vpc_Merchant" value="<%= fields.get("vpc_Merchant")%>" size="20" maxlength="16"></td><br>
                </tr>
                <tr bgcolor="#CED7EF">
                    <td>&nbsp;</td>
                    <td align="right"><b><i>Transaction OrderInfo: </i></b></td>
                    <td><input type="text" name="vpc_OrderInfo" value="<%= fields.get("vpc_OrderInfo")%>" size="20" maxlength="34"></td><br>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td align="right"><b><i>Purchase Amount: </i></b></td>
                    <td><input type="text" name="vpc_Amount" value="100" size="20" maxlength="10"></td><br>
                </tr>                  
                <tr bgcolor="#CED7EF">
                    <td>&nbsp;</td>
                    <td align="right"><b><i>Payment Server Display Language Locale: </i></b></td>
                    <td><input type="text" name="vpc_Locale" value="en" size="20" maxlength="5"></td><br>
                </tr>
                <tr bgcolor="#CED7EF">
                    <td>&nbsp;</td>
                    <td align="right"><b><i>Return Auth Response Data: </i></b></td>
                    <td><input type="text" name="vpc_ReturnAuthResponseData" value="<%= fields.get("vpc_ReturnAuthResponseData")%>" size="20" maxlength="5"></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td align="right"><b><i>Receipt ReturnURL: </i></b></td>
                    <td><input type="text" name="vpc_ReturnURL" size="63" value="http://196.216.64.237:8080/kcbtest/Process" maxlength="250"></td><br>
                </tr>
                <tr>
                    <td>VPC SecureHashSecret:</td>
                    <td><input type="text" name="vpc_SecureHash" size="70" value="<%= fields.get("vpc_SecureHash")%>" readonly="readonly" /></td><br>
                </tr>

                <tr>    <td colspan="2">&nbsp;</td> 
                    <td><input type="submit" name="SubButL" value="Pay Now!"></td>
                </tr>
            </table>
        </form>
                
                    <%=buff%><br><br>
                    <%=buff2%><br><br>
                    <%=buff3%><br><br>
                    <%=buff4%>

    </body>
</html>