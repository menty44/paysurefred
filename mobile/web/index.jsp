<%-- 
    Document   : index
    Created on : Feb 4, 2013, 4:26:28 AM
    Author     : Joseph
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Mobile Payment Form</title>
        <link rel="stylesheet" href="http://code.jquery.com/mobile/1.2.0/jquery.mobile-1.2.0.min.css" />
        <link href="css/bootstrap.min.css" rel="stylesheet" media="screen"></link>
        <link href="css/bootstrap-responsive.min.css" rel="stylesheet" media="screen"></link>
        <script src="http://code.jquery.com/jquery-1.8.2.min.js"></script>
        <script src="http://code.jquery.com/mobile/1.2.0/jquery.mobile-1.2.0.min.js"></script>
        <link href="css/style.css" rel="stylesheet" media="screen"></link>
    </head>
    <body>
        <div data-role="page">
            <div data-role="header" style="background-color: black;">
                <img src="http://196.216.64.237/logo/mobile/paysure.png">                
            </div>
            <%                                String nm = request.getParameter("mname");
                String mn = "http://196.216.64.237/logo/mobile/paysure.png";
            %>           
            <div data-role="content">      
                <!--<img src="<%=mn%>" style="margin-left: 50px">-->    
                <form action="Process" method="post" style="border: 1px solid red; margin-left: 0px; padding-left: 0px;">
                    <input type="hidden" name="mname" value="<%=request.getParameter("mname")%>"/>
                    <input type="hidden" name="payoption" value="3"/>
                    <table style="border: 1px solid blue; width: 235px;" >                        
                        <tr>
                            <td><label for="name" style="float: right;">Name:</label></td>
                            <td><input type="text" name="name" id="name" data-mini="true" value="" /></td>
                        </tr>
                        <tr>
                            <td><label for="email" style="float: right;">Email:</label></td>
                            <td><input type="text" name="email" id="email" data-mini="true" value="" /></td>
                        </tr>  
                        <tr>
                            <td><label for="amount" style="float: right;">Amount:</label></td>
                            <td><input type="text" name="amount" id="amount" data-mini="true" value="" /></td>
                        </tr>
                        <tr>
                            <td><label for="description" style="float: right;">Description:</label></td>
                            <td><input type="text" name="description" id="description" data-mini="true" value="" /></td>
                        </tr>
                        <tr>
                            <td><label></label></td>
                            <td><input type="submit" value="Make Payment" class="btn-success" ></td>
                        </tr>  
                    </table>
                    
                    
                </form>                
            </div>
                    <div data-role="footer" style="background-color: black;">
                Powered by Paysure Ltd
            </div>
        </div>
        <script type="text/javascript" src="/js/bootstrap.min.js"></script>
    </body>
</html>
