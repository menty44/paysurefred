<%-- 
    Document   : success
    Created on : Feb 6, 2014, 10:46:54 AM
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
        <title>Kenswitch Success Page</title>
    </head>
    <body>

        <div style="width: 100%;">
            <img alt="Paysure Logo" src="./img/logo.png"/>
            <br>        


            <%

                Purchase purchase = (Purchase) request.getAttribute("purchase");
                String code = (String) request.getAttribute("code");
                String pageHeading = "Success";






            %>       


            <table class="table-striped" style="width: 60%; margin: 0 auto; border: 2px;">
                <thead>
                    <tr>
                        <th colspan="2" style="text-align: center;"><h3 class="btn-success">Successful Kenswitch Transaction</h3> </th>                    
                </tr>
                </thead>
                <tbody>
                    <tr style="width: 50%;">
                        <td style="float: right;" class="emphasis">Merchant Name : </td>
                        <td><%=purchase.getMerchantid().getName()%></td>
                    </tr>
                    <tr>
                        <td style="float: right">Reference Number : </td>
                        <td><%=purchase.getReferenceno()%></td>
                    </tr>
                    <tr>
                        <td style="float: right">Amount : </td>
                        <td><%=purchase.getAmount()%></td>
                    </tr>
                    <tr>
                        <td style="float: right">Description : </td>
                        <td><%=purchase.getDescription()%></td>
                    </tr>
                    <tr>
                        <td style="float: right">Date : </td>
                        <td><%=new Date()%></td>
                    </tr>                    
                </tbody>
            </table>
            <div style="width: 13%; margin: 0 auto;">
                <img src="./img/ken.png" align="middle"/><br>                
            </div>
            <div style="width: 80%; margin: 0 auto;">
                <img src="https://epayments.paysure.co.ke/logo/poweredbypaysure.png" align="middle"/>                  
            </div>
        </div>
    </body>
</html>
