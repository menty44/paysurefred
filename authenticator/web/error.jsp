<%-- 
    Document   : error
    Created on : Feb 6, 2014, 10:46:25 AM
    Author     : Joseph
--%>

<%@page import="java.util.Date"%>
<%@page import="objects.Purchase"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css" rel="stylesheet">
        <script src="//netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>
        <title>Kenswitch Error Page</title>
    </head>
    <body>
        <div style="width: 100%;">

            <%

                Purchase purchase = (Purchase) request.getAttribute("purchase");
                String code = (String) request.getAttribute("code");
                String pageHeading = "Error";
                switch (Integer.parseInt(code)) {
                    case 01:
                        pageHeading = "Refer to Card Issuer";
                        break;
                    case 02:
                        pageHeading = "Refer to Card Issuer, Special Condition";
                        break;
                    case 03:
                        pageHeading = "Invalid Merchant";
                        break;
                    case 04:
                        pageHeading = "Pick-up Card";
                        break;
                    case 05:
                        pageHeading = "Do not Honor";
                        break;
                    case 06:
                        pageHeading = "Error";
                        break;
                    case 07:
                        pageHeading = "Pick-up Card, Special Condition";
                        break;
                    case 8:
                        pageHeading = "Honor with Identification";
                        break;
                    case 9:
                        pageHeading = "Request in Progress";
                        break;
                    case 10:
                        pageHeading = "Approved, Partial";
                        break;
                    case 11:
                        pageHeading = "Approved, VIP";
                        break;
                    case 12:
                        pageHeading = "Invalid Transaction";
                        break;
                    case 13:
                        pageHeading = "Invalid Amount";
                        break;
                    case 14:
                        pageHeading = "Invalid Card Number";
                        break;
                    case 15:
                        pageHeading = "No Such Issuer";
                        break;
                    case 16:
                        pageHeading = "Approved, Update Track 3";
                        break;
                    case 17:
                        pageHeading = "Customer Cancellation";
                        break;
                    case 18:
                        pageHeading = "Customer Dispute";
                        break;
                    case 19:
                        pageHeading = "Re-enter transaction";
                        break;
                    case 20:
                        pageHeading = "Invalid response";
                        break;
                    case 21:
                        pageHeading = "No action taken";
                        break;
                    case 22:
                        pageHeading = "Suspected malfunction";
                        break;
                    case 23:
                        pageHeading = "Unacceptable transaction fee";
                        break;
                    case 24:
                        pageHeading = "File update not supported";
                        break;
                    case 25:
                        pageHeading = "Unable to locate record";
                        break;
                    case 26:
                        pageHeading = "Duplicate record";
                        break;
                    case 27:
                        pageHeading = "File update edit error";
                        break;
                    case 28:
                        pageHeading = "File update file locked";
                        break;
                    case 29:
                        pageHeading = "File update failed";
                        break;
                    case 30:
                        pageHeading = "Format error";
                        break;
                    case 31:
                        pageHeading = "Bank not supported";
                        break;
                    case 32:
                        pageHeading = "Completed partially";
                        break;
                    case 33:
                        pageHeading = "Expired card, pick-up";
                        break;
                    case 34:
                        pageHeading = "Suspected fraud, pick-up";
                        break;
                    case 35:
                        pageHeading = "Contact acquirer, pick-up";
                        break;
                    case 36:
                        pageHeading = "Restricted card, pick-up";
                        break;
                    case 37:
                        pageHeading = "Call acquirer security, pick-up";
                        break;
                    case 38:
                        pageHeading = "PIN tries exceeded, pick-up";
                        break;
                    case 39:
                        pageHeading = "No credit account";
                        break;
                    case 40:
                        pageHeading = "Function not supported";
                        break;
                    case 41:
                        pageHeading = "Lost card";
                        break;
                    case 42:
                        pageHeading = "No universal account";
                        break;
                    case 43:
                        pageHeading = "Stolen card";
                        break;
                    case 44:
                        pageHeading = "No investment account";
                        break;                    
                    case 51:
                        pageHeading = "Not sufficient funds";
                        break;
                    case 52:
                        pageHeading = "No check account";
                        break;
                    case 53:
                        pageHeading = "No savings account";
                        break;
                    case 54:
                        pageHeading = "Expired card";
                        break;
                    case 55:
                        pageHeading = "Incorrect PIN";
                        break;
                    case 56:
                        pageHeading = "No card record";
                        break;
                    case 57:
                        pageHeading = "Transaction not permitted to cardholder";
                        break;
                    case 58:
                        pageHeading = "Transaction not permitted on terminal";
                        break;
                    case 59:
                        pageHeading = "Suspected fraud";
                        break;
                    case 60:
                        pageHeading = "Contact acquirer";
                        break;
                    case 61:
                        pageHeading = "Exceeds withdrawal limit";
                        break;   
                    case 62:
                        pageHeading = "Restricted card";
                        break;
                    case 63:
                        pageHeading = "Security violation";
                        break;
                    case 64:
                        pageHeading = "Original amount incorrect";
                        break;
                    case 65:
                        pageHeading = "Exceeds withdrawal frequency";
                        break;  
                    case 66:
                        pageHeading = "Call acquirer security";
                        break;
                    case 67:
                        pageHeading = "Hard capture";
                        break;
                    case 68:
                        pageHeading = "Response received too late";
                        break;
                    case 94:
                        pageHeading = "Duplicate transaction";
                        break;      
                    default:
                    pageHeading = "Failed..Error!!";
                    break;
                }



            %>

            <img alt="Paysure Logo" src="./img/logo.png"/>                
            <table class="table-striped" style="width: 60%; margin: 0 auto;">
                <thead>
                    <tr>
                        <th colspan="2" style="text-align: center;"><h3 class="btn-danger">Failed Kenswitch Transaction</h3> </th>                    
                </tr>
                </thead>
                <tbody style="border: none;" class="text-danger">
                    <tr class="text-danger">
                        <td style="float: right">Error Message : </td>
                        <td><%=pageHeading%></td>
                    </tr>
                    <tr class="text-danger">
                        <td style="float: right">Error Code : </td>
                        <td><%=code%></td>
                    </tr>
                    <tr style="width: 50%;">
                        <td style="float: right;" class="text-danger">Merchant Name : </td>
                        <td><%= purchase.getMerchantid().getName()%></td>
                    </tr>
                    <tr>
                        <td style="float: right">Reference Number : </td>
                        <td><%=purchase.getReferenceno()%></td>
                    </tr>
                    <tr>
                        <td style="float: right">Amount : </td>
                        <td><%=purchase.getAmount()/100%></td>
                    </tr>
                    <tr>
                        <td style="float: right">Description : </td>
                        <td><%=purchase.getDescription()%></td>
                    </tr>
                    <tr class="text-info">
                        <td style="float: right">Date : </td>
                        <td><%= new Date()%></td>
                    </tr>                    
                </tbody>
            </table>
            <br>                        
            <div style="width: 13%; margin: 0 auto;">
                <img src="./img/ken.png" align="middle"/><br>                
            </div>
            <div style="width: 80%; margin: 0 auto;">
                <img src="https://epayments.paysure.co.ke/logo/poweredbypaysure.png" class="img-responsive" align="middle"/>                  
            </div>            
        </div>
    </body>
</html>
