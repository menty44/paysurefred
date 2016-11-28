<%-- 
    Document   : error
    Created on : Aug 18, 2012, 10:05:43 AM
    Author     : paysure
--%>
<%@page import="java.math.BigInteger"%>
<%@page import="paygate.objects.Transaction"%>
<%@ page isErrorPage="true" import="java.io.*"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">        
        <link rel="stylesheet" type="text/css" href="./css/style.css"/>
        <script type="text/javascript" src="./js/jquery.js"></script>
        <title>Visa Error Page</title>        
    </head>
    <body class="clsMain">
        <!--<h1>Error</h1>-->
        <%
            String code = (String) request.getAttribute("code");

            String pageHeading = "Error";
            //switch (Integer.parseInt(request.getParameter("LITE_PAYMENT_CARD_STATUS"))) {
            switch (Integer.parseInt(code)) {
                case 0:
                    break;
                case 1:
                case 2:
                case 4:
                    pageHeading = "Denied";
                    break;
                case 5:
                    pageHeading = "Suspicious Transaction";
                    break;
                case 6:
                    pageHeading = "Card Address Failure";
                    break;
                case 7:
                    pageHeading = "Card Security Code Failure";
                    break;
                case 8:
                    pageHeading = "Card Type Not Supported";
                    break;
                case 9:
                    pageHeading = "Trylater-Unable To Process The Transaction";
                    break;
                case 10:
                    pageHeading = "Card Blocked";
                    break;
                case 11:
                    pageHeading = "Invalid Amount";
                    break;
                case 12:
                    pageHeading = "Invalid Budget Period";
                    break;
                case 13:
                    pageHeading = "Void (Unsuccessful)";
                    break;
                case 14:
                    pageHeading = "Invalid Card Number";
                    break;
                case 15:
                    pageHeading = "Invalid Track2";
                    break;
                case 16:
                    pageHeading = "Invalid Card Date";
                    break;
                case 18:
                    pageHeading = "Invalid Authorization Code";
                    break;
                case 19:
                    pageHeading = "Incorrect Pin";
                    break;
                case 20:
                    pageHeading = "Device PIN Key Has Expired";
                    break;
                case 22:
                    pageHeading = "EMV Not Supported";
                    break;
                case 23:
                    pageHeading = "Card Information Not Present";
                    break;
                case 205:
                    pageHeading = "General Error (Default Description)";
                    break;
                case 255:
                    pageHeading = "Error";
                    break;
                case 400:
                    pageHeading = "System Unavailable";
                    break;
                case 500:
                    pageHeading = "Please Contact Your Bank";
                    break;
                default:
                    pageHeading = "Fail";
                    break;
            }

            Transaction t = (Transaction) request.getAttribute("transaction");
            BigInteger bd = t.getAmount();
            Double d = bd.doubleValue();



        %>
        <div id="img">
            <img alt="Paysure Logo" src="./images/logo.png"/>
        </div>   
        <div id="top">
            <h1>Failed Visa Transaction</h1>        
        </div>
        <div id="main">
            <table border="0" width="500" cellspacing="5" cellpadding="5">                
                <tbody>                     
                <tr>
                    <td><nobr>Error Message</nobr></td>
                <td><nobr><%=request.getParameter("LITE_RESULT_DESCRIPTION")%></nobr></td>
                </tr>
                <tr>
                    <td><nobr>Merchant Name</nobr></td>
                <td><nobr><%= t.getMerchantname()%></nobr></td>
                </tr> 
                <tr>
                    <td><nobr>Reference Number</nobr></td>
                <td><nobr><%=t.getRefno()%></nobr></td>
                </tr> 
                <tr>
                    <td><nobr>Amount</nobr></td>
                <td><nobr><%=d%></nobr></td>
                </tr> 
                <tr>
                    <td><nobr>Date</nobr></td>
                <td><nobr><%=t.getModified()%></nobr></td>
                </tr> 
                </tbody>
            </table>  
        </div>       
        <div id="four">
            <img src="./images/Visa-Mastercard logo.png"/><br>
            <p>Powered by...</p>            
        </div>
        <div id="five">
            <img src="https://epayments.paysure.co.ke/logo/poweredbypaysure.png" align="middle          "/>            
        </div>
        <script type="text/javascript">
            $(function(){
                $("h1").addClass('tags'); 
                $("p").addClass('powered');
                $("table").addClass('position');
                //$("#top").addClass('position');
                $("#four").addClass('four');
                $("#five").addClass('five');                
            });           
        </script>
    </body>
</html>
