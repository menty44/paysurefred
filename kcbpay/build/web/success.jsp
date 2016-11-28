<%-- 
    Document   : success
    Created on : Feb 11, 2014, 4:53:13 PM
    Author     : Joseph
--%>

<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css" rel="stylesheet">
        <script src="//netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>
        <title>JSP Page</title>
    </head>
    <body>
        <img title="Paysure Logo" src="./images/logo.png" class="img-responsive" />
        
        <div style="width: 80%; margin: 0 auto;">
            <!--<h2 style="margin: 0 auto;">Success KCB Payment</h2>-->
            <div style="margin: 0 auto; width: 60%;" >                
                <table border="0" class="table-responsive table-striped">
                    <thead>
                        <tr>                            
                            <th colspan="2">Successful KCB Payment</th>
                        </tr>
                    </thead>
                    <tbody style="border: none;">
                        <tr>
                            <td style="float: right;">Merchant Name : </td>
                            <td>Paysure Ltd</td>
                        </tr>
                        <tr>
                            <td style="float: right;">Reference Number: </td>
                            <td>1R0000000001</td>
                        </tr>
                        <tr>
                            <td style="float: right;">Amount : </td>
                            <td>500</td>
                        </tr>
                        <tr>
                            <td style="float: right;">Description : </td>
                            <td>Test Payment For Paysure Ltd</td>
                        </tr>
                        <tr>
                            <td style="float: right;">Date : </td>
                            <td><%= new Date()%></td>
                        </tr>
                    </tbody>
                </table>                
            </div>                     
            <div style="width: 80%; margin: 0 auto;">
                <img src="https://epayments.paysure.co.ke/logo/poweredbypaysure.png" align="middle" class="img-responsive"/>                  
            </div>
        </div>
    </body>
</html>
