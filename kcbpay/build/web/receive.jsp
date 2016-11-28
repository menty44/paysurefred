<%-- 
    Document   : receive
    Created on : Feb 12, 2014, 2:47:13 PM
    Author     : Joseph
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css" rel="stylesheet">
        <script src="//netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js" type="text/javascript"></script>
        <script type="text/javascript">
            $(document).ready(function(){
                $('#form').hide();   
                $('img').css('margin-left','350px');
            });                      
        </script>
        <title>Receiving Page</title>
    </head>
    <body>
        <h2>Wait To Be Redirected to Visa/Master-Card...</h2> 
        <img src="images/visamastercard.png" />        
        <div id="form">            
            <form METHOD="POST" ACTION="vpcpay.jsp" id="aForm" name="aForm">                               
                <table >                    
                    <tr >
                        <td>&nbsp;</td>
                        <td align="right"><b><i> Virtual Payment Client URL: </i></b></td>
                        <td><input type="text" name="virtualPaymentClientURL" size="63" value="https://migs-mtf.mastercard.com.au/vpcpay" maxlength="250"></td>
                    </tr>
                    <tr >
                        <td>&nbsp;</td>
                        <td align="right"><b><i> VPC Version: </i></b></td>
                        <td><input type="text" name="vpc_Version" value="<%= request.getParameter("vpc_Version")%>" size="20" maxlength="8"></td><br>
                    </tr>
                    <tr bgcolor="#CED7EF">
                        <td>&nbsp;</td>
                        <td align="right"><b><i>Command Type: </i></b></td>
                        <td><input type="text" name="vpc_Command" value="<%= request.getParameter("vpc_Command")%>" size="20" maxlength="16"></td><br>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td align="right"><b><i>Merchant AccessCode: </i></b></td>
                        <td><input type="text" name="vpc_AccessCode" value="<%= request.getParameter("vpc_AccessCode")%>" size="20" maxlength="8"></td><br>
                    </tr>
                    <tr bgcolor="#CED7EF">
                        <td>&nbsp;</td>
                        <td align="right"><b><i>Merchant Transaction Reference: </i></b></td>
                        <td><input type="text" name="vpc_MerchTxnRef" value="<%= request.getParameter("vpc_MerchTxnRef")%>" size="20" maxlength="40"></td><br>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td align="right"><b><i>MerchantID: </i></b></td>
                        <td><input type="text" name="vpc_Merchant" value="<%= request.getParameter("vpc_Merchant")%>" size="20" maxlength="16"></td><br>
                    </tr>
                    <tr bgcolor="#CED7EF">
                        <td>&nbsp;</td>
                        <td align="right"><b><i>Transaction OrderInfo: </i></b></td>
                        <td><input type="text" name="vpc_OrderInfo" value="<%= request.getParameter("vpc_OrderInfo")%>" size="20" maxlength="34"></td><br>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td align="right"><b><i>Purchase Amount: </i></b></td>
                        <td><input type="text" name="vpc_Amount" value="<%= request.getParameter("vpc_Amount")%>" size="20" maxlength="10"></td><br>
                    </tr>                  
                    <tr bgcolor="#CED7EF">
                        <td>&nbsp;</td>
                        <td align="right"><b><i>Payment Server Display Language Locale: </i></b></td>
                        <td><input type="text" name="vpc_Locale" value="en" size="20" maxlength="5"></td><br>
                    </tr>
                    <tr bgcolor="#CED7EF">
                        <td>&nbsp;</td>
                        <td align="right"><b><i>Return Auth Response Data: </i></b></td>
                        <td><input type="text" name="vpc_ReturnAuthResponseData" value="<%= request.getParameter("vpc_ReturnAuthResponseData")%>" size="20" maxlength="5"></td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td align="right"><b><i>Receipt ReturnURL: </i></b></td>
                        <td><input type="text" name="vpc_ReturnURL" size="63" value="http://196.216.64.237:8080/kcbtest/Process" maxlength="250"></td><br>
                    </tr>

                    <tr>    <td colspan="2">&nbsp;</td> 
                        <td><input type="submit" name="SubButL" value="Pay Now!"></td>
                    </tr>
                </table>     
            </form>
        </div>
        <script type="text/javascript">
            window.onload=document.aForm.submit();
        </script> 
    </body>
</html>
