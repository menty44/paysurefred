<%-- 
    Document   : success
    Created on : Aug 6, 2012, 5:06:38 PM
    Author     : paysure
--%>
<%@page import="java.math.BigInteger"%>
<%@page import="paygate.objects.Responsecode"%>
<%@page import="paygate.objects.Cardtype"%>
<%@page import="paygate.objects.Status"%>
<%@page import="paygate.objects.Purchase"%>
<%@page import="paygate.objects.Transaction"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@page import="javax.transaction.UserTransaction"%>
<%@page import="javax.naming.Context"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.annotation.Resource"%>
<%@page import="java.util.*"%>
<%@page import="javax.sql.*"%>
<%@page import="paygate.objects.Merchant"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<%@page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
        <link rel="stylesheet" type="text/css" href="./css/style.css"/>
        <script type="text/javascript" src="./js/jquery.js"></script>
        <title>Successful Transaction</title>
    </head>
    <head>      
    <body>

        <div id="img">
            <img alt="Paysure Logo" src="./images/logo.png"/>
        </div>
        <div id="top">
            <h1>Successful Visa Transaction</h1>        
        </div>
        <%
            /*
             * String pageHeading = "Successfull"; switch
             * (Integer.parseInt(request.getParameter("LITE_PAYMENT_CARD_STATUS")))
             * { case 0: break; case 1: case 2: case 5: case 9: pageHeading =
             * "Trylater"; break; case 255: pageHeading = "error"; break;
             * default: pageHeading = "Fail"; break; }
             */
            Transaction t = (Transaction) request.getAttribute("transaction");
            //BigInteger amt=t.getAmount().multiply(BigInteger.valueOf(100));            
            //String amount=amt.toString();
            BigInteger bd=t.getAmount();
            Double d= bd.doubleValue();
            String currency = t.getCurrency();
            
%>
        <!--<table align="center" class="clsQuery" cellspacing="0" border="1">
                <tr>
                        <td align="center" class="clsQueryHeading">Parameter</td>
                        <td align="center" class="clsQueryHeading">Value</td>
                </tr>
                <tr>
                        <td align="center" class="clsQueryHeading"><nobr>LITE_PAYMENT_CARD_STATUS (Error Code)</nobr></td>
                        <td align="center" class="clsQuery"><nobr><%=request.getParameter("LITE_PAYMENT_CARD_STATUS")%></nobr></td>
                </tr>
                <tr>
                        <td align="center" class="clsQueryHeading"><nobr>Description</nobr></td>
                        <td align="center" class="clsQuery"><nobr><%=request.getParameter("LITE_RESULT_DESCRIPTION")%></nobr></td>
                </tr>
                <tr>
                        <td align="center" class="clsQueryHeading"><nobr>Merchant Reference</nobr></td>
                        <td align="center" class="clsQuery"><nobr><%=request.getParameter("MERCHANTREFERENCE")%></nobr></td>
                </tr>
                <tr>
                        <td align="center" class="clsQueryHeading"><nobr>Lite Merchant ID</nobr></td>
                        <td align="center" class="clsQuery"><nobr><%=request.getParameter("LITE_MERCHANT_APPLICATIONID")%></nobr></td>
                </tr>
                <tr>
                        <td align="center" class="clsQueryHeading"><nobr>Amount</nobr></td>
                        <td align="center" class="clsQuery"><nobr><%=request.getParameter("LITE_ORDER_AMOUNT")%></nobr></td>
                </tr>        
        </table>  -->   


        <!--
        <table border="1" width="500" cellspacing="5" cellpadding="5">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Value</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>Merchant Name</td>
                    <td></td>
                </tr>
                <tr>
                    <td>Reference Number</td>
                    <td></td>
                </tr>
                <tr>
                    <td>Amount</td>
                    <td></td>
                </tr>
                <tr>
                    <td>Description</td>
                    <td></td>
                </tr>
                <tr>
                    <td>Date</td>
                    <td></td>
                </tr>
            </tbody>
        </table>-->

        <div id="main">
            <table border="0" width="500" cellspacing="5" cellpadding="5">                
                <tbody>
                    <tr>
                        <td style="float: right">Merchant Name</td>
                        <td><%=t.getMerchantname()%></td>
                    </tr>
                    <tr>
                        <td style="float: right">Reference Number</td>
                        <td><%=t.getRefno()%></td>
                    </tr>
                    <tr>
                        <td style="float: right">Amount</td>
                        <td><%=currency%>: <%=d%></td>
                    </tr>
                    <tr>
                        <td style="float: right">Description</td>
                        <td><%=t.getDescription()%></td>
                    </tr>
                    <tr>
                        <td style="float: right">Date</td>
                        <td><%=t.getModified()%></td>
                    </tr>
                </tbody>
            </table>  
        </div>
        <div id="four">
            <img src="./images/Visa-Mastercard logo.png"/><br>
            <p>Powered by...</p>            
        </div>
        <div id="five">
            <img src="https://epayments.paysure.co.ke/logo/poweredbypaysure.png" align="middle"/>            
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
