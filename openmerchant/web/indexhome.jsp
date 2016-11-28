<%-- 
    Document   : index
    Created on : Nov 15, 2012, 4:55:13 PM
    Author     : paysure
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Open Merchant Payment Form</title>
        <link rel="stylesheet" href="css/style.css"/>
        <script src="js/jquery.js"></script>
        <script src="js/custom.js"></script>

    </head>
    <body>
        <div id="logo">
            <img src="https://epayments.paysure.co.ke/logo/openmerchant/logo.png"> 
        </div>
                               
        <%                                String nm = request.getParameter("mname");
        String mn="https://epayments.paysure.co.ke/logo/"+nm.toLowerCase()+".png";
        
        
        
%>                                                                       
                            
        <table align="center" id="tblone">
            <tr>
                <td>
                    <img src="<%=mn%>">
                </td>
            </tr>                                            
        </table><p>
        <form action="Process" name="formone" id="formone" method="POST">
            <table align="center" id="frmtbl">
                <tr>
                    <td>
                        Name:<p>
                    </td>
                    <td>
                        <input type="hidden" name="mname" value="<%=request.getParameter("mname")%>"/>
                        <input type="text" name="name" value="" required="true"/><p>
                    </td>
                </tr>   
                <tr>
                    <td>
                        Email:<p>
                    </td>
                    <td>
                        <input type="text" name="email" value="" required="true"/><p>
                    </td>
                </tr>        
                <tr>
                    <td>
                        Amount:<p>
                    </td>
                    <td>
                        <input type="number" name="amount" value="" required="true" title="Amount Should Include Cents! e.g. 100.00 or 100.85"/><p>
                    </td>
                </tr>  
                <tr>
                    <td>
                        Description:<p>
                    </td>
                    <td>
                        <input type="text" name="description" value="" required="true"/><p>
                    </td>
                </tr>                                 
                <tr>
                    <td><input type="radio" name="payoption" value="1">Kenswitch<br><img src="https://epayments.paysure.co.ke/logo/openmerchant/kenswitch.png"></td>
                    <td><input type="radio" name="payoption" value="2" style="margin-left: 12px">VISA/MasterCard<br><br><img src="https://epayments.paysure.co.ke/logo/openmerchant/visa-mastercard.png" style="padding-left: 12px"></td><p>
                </tr>
                <tr>
                    <td colspan="2"><p><input type="checkbox" id="chkAgree" onclick="$('#btnSubmit').attr('disabled', !$(this).is(':checked'));"><a href="terms.jsp">I Agree with the terms and conditions</a><br></td><p>

                </tr>
                <tr>
                    <td></td>
                    <td><p><input type="submit" id="btnSubmit" value="Make Payment" name="submit" /></td>
                </tr>             
            </table>
        </form>    
    </body>
</html>
