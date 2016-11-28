<%-- 
    Document   : confirm
    Created on : Jul 1, 2013, 5:05:39 PM
    Author     : Joseph
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>M-pesa Confirmation Page</title>
        <link rel="stylesheet" href="style.css" type="text/css"/>
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>        
    </head>
    <body>        
        <div id="logo">
            <img src="images/mpesa.png">                        
        </div>        
        <%
            String refno = request.getParameter("refno");
            String amt = request.getParameter("tamt");
            int amount = Integer.parseInt(amt)/100;
            String merchant = request.getParameter("mname");
        %>
        <div style="margin-left: 120px"> 
            <h5 id="hd">Please pay for your order</h5>
            Send M-PESA Ksh. <%=amount%> to Pay Bill Business number 913100. Submit the Confirmation Code below
        </div>                                       
        <div class="transbox">            
            <p>
                <ol>
                <li>Go to M-PESA on your phone</li>
                <li>Select Payment Services option</li>
                <li>Select Pay Bill option to pay merchant <%=merchant%></li>
                <li>Enter Business no. 913100</li>
                <li>Enter Account no. 310815</li>
                <li>Enter the Amount Ksh. <%=amount%> </li>
                <li>Enter your M-PESA PIN and Send</li>
                <li>You will receive a confirmation SMS from M-PESA with a Confirmation Code</li>
                <li>After you receive the confirmation SMS, enter your phone number and the Confirmation Code on your right</li>
                <li>Click on Complete</li>                               
            </ol>
            </p>            
        </div>
                <div id="confirm">                    
                    Enter Payment Details                    
                    <form action="Process" method="POST" style="border: 2px solid darkgrey; padding-top: 10px;">                       
                        <table border="0" cellspacing="2" cellpadding="3">                            
                            <tbody>                                
                                <tr>
                                    <td>M-Pesa Confirmation Code:</td>
                                    <td><input type="text" name="confirmcode" value="" /></td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td><input type="submit" value="Complete" name="submit" /></td>
                                </tr>
                            </tbody>
                        </table>
                    </form>
                </div>              
        <script>                        
            $(document).ready(function(){                           
                //alert("working");
                //$("body").css("background-image","url('../images/Portal-Landing-Bg.jpg')");
                //$("#logo").css({"background-color":"blue","margin-left":"250px"});                         
            });                 
        </script>
    </body>
</html>
