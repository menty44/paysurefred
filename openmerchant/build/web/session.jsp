<%-- 
    Document   : session
    Created on : Aug 26, 2013, 10:02:07 AM
    Author     : Joseph
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Session Expired</title>
        <link rel="stylesheet" href="css/style.css"/>
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>        
    </head>
    <body>
        <div id="img">
            <img alt="Paysure Logo" src="./images/logo.png"/>
        </div>
        <div id="msg">
            <h3>Dear User,<br><br>
                Your session has expired - you will be returned to start page. <br><br> <a href="/openmerchant/faces/home.xhtml">Please Click here to continue</a>
            </h3></div>     
        
        <div id="ftr">
            <img src="https://epayments.paysure.co.ke/logo/poweredbypaysure.png" align="middle"/>                        
        </div>
        <script type="text/javascript">
            
            $(function(){              
                $("#msg").css({"margin-left":"550px","margin-top":"10px"});
                $("#ftr").css({"margin-left":"250px","margin-top":"80px"});                
            });            
        </script>       
    </body>
</html>
