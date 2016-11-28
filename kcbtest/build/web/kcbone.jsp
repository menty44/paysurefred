<%-- 
    Document   : kcbone
    Created on : Jan 15, 2014, 10:02:32 AM
    Author     : Joseph
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head><title>Virtual Payment Client Example</title>
        <meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'>
        <style type='text/css'>
            <!--
            h1       { font-family:Arial,sans-serif; font-size:24pt; color:#08185A; font-weight:100}
            h2.co    { font-family:Arial,sans-serif; font-size:24pt; color:#08185A; margin-top:0.1em; margin-bottom:0.1em; font-weight:100}
            h3.co    { font-family:Arial,sans-serif; font-size:16pt; color:#000000; margin-top:0.1em; margin-bottom:0.1em; font-weight:100}
            body     { font-family:Verdana,Arial,sans-serif; font-size:10pt; color:#08185A background-color:#FFFFFF }
            p        { font-family:Verdana,Arial,sans-serif; font-size:10pt; color:#FFFFFF }
            p.bl     { font-family:Verdana,Arial,sans-serif; font-size:8pt; color:#08185A }
            a:link   { font-family:Verdana,Arial,sans-serif; font-size:8pt; color:#08185A }
            a:visited{ font-family:Verdana,Arial,sans-serif; font-size:8pt; color:#08185A }
            a:hover  { font-family:Verdana,Arial,sans-serif; font-size:8pt; color:#FF0000 }
            a:active { font-family:Verdana,Arial,sans-serif; font-size:8pt; color:#FF0000 }
            td       { font-family:Verdana,Arial,sans-serif; font-size:8pt; color:#08185A }
            th       { font-family:Verdana,Arial,sans-serif; font-size:10pt; color:#08185A; font-weight:bold; background-color:#CED7EF; padding-top:0.5em; padding-bottom:0.5em}
            input    { font-family:Verdana,Arial,sans-serif; font-size:8pt; color:#08185A; background-color:#CED7EF; font-weight:bold }
            select   { font-family:Verdana,Arial,sans-serif; font-size:8pt; color:#08185A; background-color:#CED7EF; font-weight:bold }
            textarea { font-family:Verdana,Arial,sans-serif; font-size:8pt; color:#08185A; background-color:#CED7EF; font-weight:normal; scrollbar-arrow-color:#08185A; scrollbar-base-color:#CED7EF }
            -->
        </style></head>
    <body>

        <!-- branding table -->
        <table width='100%' border='2' cellpadding='2' bgcolor='#0074C4'><tr><td bgcolor='#CED7EF' width='90%'><h2 class='co'>&nbsp;Virtual Payment Client - Version 1</h2></td><td bgcolor='#0074C4' align='center'><h3 class='co'>MIGS</h3></td></tr></table>

    <center><h1>JSP 3-Party Basic Example - Request Details</H1></center>

    <!-- The "Pay Now!" button submits the form, transferring control -->
    <form action="VPC_JSP_3P_DO.jsp" method="post">

        <!-- get user input -->
        <table width="80%" align="center" border="0" cellpadding='0' cellspacing='0'>

            <input type="hidden" name="Title" value="JSP VPC 3-Party">

            <tr bgcolor="#CED7EF">
                <td width="1%">&nbsp;</td>
                <td width="40%" align="right"><b><i>Virtual Payment Client URL:&nbsp;</i></b></td>
                <td width="59%"><input type="text" name="virtualPaymentClientURL" size="63" value="https://migs-mtf.mastercard.com.au/vpcpay" maxlength="250"></td>
            </tr>
            <tr>
                <td colspan="3">&nbsp;<hr width="75%">&nbsp;</td>
            </tr>
            <tr bgcolor="#0074C4">
                <td colspan="3" height="25"><p><b>&nbsp;Basic 3-Party Transaction Fields</b></p></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td align="right"><b><i> VPC Version: </i></b></td>
                <td><input type="text" name="vpc_Version" value="1" size="20" maxlength="8"></td>
            </tr>
            <tr bgcolor="#CED7EF">
                <td>&nbsp;</td>
                <td align="right"><b><i>Command Type: </i></b></td>
                <td><input type="text" name="vpc_Command" value="pay" size="20" maxlength="16"></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td align="right"><b><i>Merchant AccessCode: </i></b></td>
                <td><input type="text" name="vpc_AccessCode" value="" size="20" maxlength="8"></td>
            </tr>
            <tr bgcolor="#CED7EF">
                <td>&nbsp;</td>
                <td align="right"><b><i>Merchant Transaction Reference: </i></b></td>
                <td><input type="text" name="vpc_MerchTxnRef" value="" size="20" maxlength="40"></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td align="right"><b><i>MerchantID: </i></b></td>
                <td><input type="text" name="vpc_Merchant" value="" size="20" maxlength="16"></td>
            </tr>
            <tr bgcolor="#CED7EF">
                <td>&nbsp;</td>
                <td align="right"><b><i>Transaction OrderInfo: </i></b></td>
                <td><input type="text" name="vpc_OrderInfo" value="VPC Example" size="20" maxlength="34"></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td align="right"><b><i>Purchase Amount: </i></b></td>
                <td><input type="text" name="vpc_Amount" value="100" size="20" maxlength="10"></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td align="right"><b><i>Currency: </i></b></td>
                <td><input type="text" name="vpc_Currency" value="KES" size="20" maxlength="10" readonly="readonly"></td>
            </tr>    
            <tr bgcolor="#CED7EF">
                <td>&nbsp;</td>
                <td align="right"><b><i>Payment Server Display Language Locale: </i></b></td>
                <td><input type="text" name="vpc_Locale" value="en" size="20" maxlength="5"></td>
            </tr>
            <tr bgcolor="#CED7EF">
                <td>&nbsp;</td>
                <td align="right"><b><i>Return Auth Response Data: </i></b></td>
                <td><input type="text" name="vpc_ReturnAuthResponseData" value="Y" size="20" maxlength="5"></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td align="right"><b><i>Receipt ReturnURL: </i></b></td>
                <td><input type="text" name="vpc_ReturnURL" size="63" value="http://196.216.64.237:8080/kcbtest/Process" maxlength="250"></td>
            </tr>
            <tr>    <td colspan="3">&nbsp;</td></tr>

            <tr>    <td colspan="2">&nbsp;</td> 
                <td><input type="submit" name="SubButL" value="Pay Now!"></td></tr>

            <tr><td colspan="3">&nbsp;<hr width="75%">&nbsp;</td></tr>
            <tr bgcolor="#0074C4">
                <td colspan="3" height="25"><p><b>&nbsp;Optional Ticket Number Field</b></p></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td align="right"><b><i>TicketNo: </i></b></td>
                <td><input type="text" name="vpc_TicketNo" maxlength="15"></td>
            </tr>


            <tr><td colspan="3">&nbsp;<hr width="75%">&nbsp;</td></tr>
        </table><br/>
    </form>
</body>
</html>
