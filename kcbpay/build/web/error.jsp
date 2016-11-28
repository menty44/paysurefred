<%-- 
    Document   : error
    Created on : Feb 11, 2014, 4:53:25 PM
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
        <h1>Error KCB Payment</h1>
        <div>
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
                            <td></td>
                        </tr>
                        <tr>
                            <td style="float: right;">Reference Number: </td>
                            <td></td>
                        </tr>
                        <tr>
                            <td style="float: right;">Amount : </td>
                            <td></td>
                        </tr>
                        <tr>
                            <td style="float: right;">Description : </td>
                            <td></td>
                        </tr>
                        <tr>
                            <td style="float: right;">Date : </td>
                            <td><%= new Date()%></td>
                        </tr>
                    </tbody>
                </table>                
            </div>                                   
            <div style="width: 80%; margin: 0 auto;">
                <img src="https://epayments.paysure.co.ke/logo/poweredbypaysure.png" class="img-responsive" align="middle"/>                  
            </div>
        </div>
    </body>
</html>
