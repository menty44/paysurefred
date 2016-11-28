<%-- 
    Document   : errors
    Created on : Jan 24, 2013, 12:15:15 AM
    Author     : Joseph
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">        
        <link rel="stylesheet" type="text/css" href="./css/style.css"/>
        <script type="text/javascript" src="./js/jquery.js"></script>
        <title>Back-Office Error Page</title> 
    </head>
    <body class="clsMain">
        <!--<h1>Error</h1>-->
        <%
            String pageHeading = "Back Office Encountered an Error!";
            switch (Integer.parseInt(request.getParameter("LITE_PAYMENT_CARD_STATUS"))) {
                case 0:
                    break;
                case 1:
                case 2:
                case 400:
                    pageHeading = "Bad Request";
                    break;
                case 401:
                    pageHeading = "Unauthorized";
                    break;
                case 402:
                    pageHeading = "Payment Required";
                    break;
                case 403:
                    pageHeading = "Forbidden";
                    break;
                case 404:
                    pageHeading = "Not Found";
                    break;
                case 405:
                    pageHeading = "Method Not Allowed";
                    break;
                case 406:
                    pageHeading = "Not Acceptible";
                    break;
                case 408:
                    pageHeading = "Request Timed Out";
                    break;
                case 500:
                    pageHeading = "Internal Server Error";
                    break;
                case 501:
                    pageHeading = "Not Implemented";
                    break;
                case 503:
                    pageHeading = "Service Unavailable";
                    break;
                case 504:
                    pageHeading = "Gateway Timedout";
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
                default:
                    pageHeading = "Fail";
                    break;
            }
        %>
        <div id="img">
            <img alt="Paysure Logo" src="./images/logo.png"/>
        </div>   
        <div id="top">
            <h1>Error Occurred</h1>        
        </div>
        <div id="main">
            <table border="1" width="500" cellspacing="5" cellpadding="5">
                <thead>
                    <tr>
                        <th>Parameter</th>
                        <th>Value</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><nobr>LITE_PAYMENT_CARD_STATUS (Error Code)</nobr></td>
                <td><nobr><%=request.getParameter("LITE_PAYMENT_CARD_STATUS")%></nobr></td>
                </tr>
                <tr>
                    <td><nobr>Description</nobr></td>
                <td><nobr><%=request.getParameter("LITE_RESULT_DESCRIPTION")%></nobr></td>
                </tr>                    
                </tbody>
            </table>  
        </div>       
        <div id="four">
            <img src="./images/Visa-Mastercard logo.png"/><br>
            <p>Powered by...</p>            
        </div>
        <div id="five">
            <img src="./images/Paysure Logo.png" align="middle          "/>            
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
