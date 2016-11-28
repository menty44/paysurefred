<%-- 
    Document   : index
    Created on : Jan 6, 2014, 3:30:33 PM
    Author     : Joseph
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>KCB Test Payment Form</title>
    </head>
    <body>
        <h1>Test Payment Visa-KCB</h1>        
        <!--<form action="https://migs-mtf.mastercard.com.au/vpcpay" method="POST">--> 
        <form action="processtwo.jsp" method="POST">            
            <table border="1">                
                <tbody>
                    <tr>
                        <td>VPC Virtual Payment Client URL:</td>
                        <td><input type="text" name="virtualPaymentClientURL" value="https://migs-mtf.mastercard.com.au/vpcpay" readonly="readonly" /></td>
                    </tr>
                    <tr>
                        <td>VPC Version:</td>
                        <td><input type="text" name="vpc_Version" value="1" readonly="readonly" /></td>
                    </tr>
                    <tr>
                        <td>VPC Command:</td>
                        <td><input type="text" name="vpc_Command" value="pay" readonly="readonly" /></td>
                    </tr>
                    <tr>
                        <td>VPC AccessCode:</td>
                        <td><input type="text" name="vpc_AccessCode" value="B8DB6EC6" readonly="readonly" /></td>
                    </tr>
                    <tr>
                        <td>VPC MerchantTxnRef:</td>
                        <td><input type="text" name="vpc_MerchTxnRef" value="REF" /></td>
                    </tr>
                    <tr>
                        <td>VPC MerchantId:</td>
                        <td><input type="text" name="vpc_Merchant" value="00000061" readonly="readonly" /></td>
                    </tr>
                    <tr>
                        <td>VPC OrderInfo:</td>
                        <td><input type="text" name="vpc_OrderInfo" value="ORDER" /></td>
                    </tr>
                    <tr>
                        <td>VPC Amount:</td>
                        <td><input type="text" name="vpc_Amount" value="" required="true" /></td>
                    </tr>
                    <tr>
                        <td>VPC CardNum:</td>
                        <td><input type="text" name="vpc_CardNum" value="" required="true" /></td>
                    </tr>
                    <tr>
                        <td>VPC CardExp:</td>
                        <td><input type="text" name="vpc_CardExp" value="" required="true" /></td>
                    </tr>
                    <!--<tr>
                        <td>VPC SecureHashSecret:</td>
                        <td><input type="text" name="vpc_SecureHash" value="B4C68D86E0A8C6F0BEDC65946BFCBC65" readonly="readonly" /></td>
                    </tr>-->
                    <tr>
                        <td>VPC Locale:</td>
                        <td><input type="text" name="vpc_Locale" value="en" readonly="readonly" /></td>
                    </tr>
                    <tr>
                        <td>VPC ReturnUrl:</td>
                        <td><input type="text" name="vpc_ReturnURL" value="http://196.216.64.237:8080/kcbtest/Process" readonly="readonly" /></td>
                    </tr>
                </tbody>
            </table>
            <br>
            <input type="submit" name="sbtn" value="Submit Payment" />
        </form>                
        
    </body>
</html>
