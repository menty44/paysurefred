<%-- 
    Document   : visa
    Created on : Feb 7, 2013, 11:44:24 AM
    Author     : Joseph
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Visa Mobile Form</title>
        <!--<link rel="stylesheet" href="http://code.jquery.com/mobile/1.2.0/jquery.mobile-1.2.0.min.css" />-->
        <link rel="stylesheet" href="WEB-INF/css/jquery.mobile.structure-1.2.0.min.css"/>
        <script src="http://code.jquery.com/jquery-1.8.2.min.js"></script>
        <script src="http://code.jquery.com/mobile/1.2.0/jquery.mobile-1.2.0.min.js"></script>        
        <style type="text/css">            
            .ui-header{
                padding-left: 20px;
            }
            #hdr{
                background-color: white;
            }
        </style>         
    </head>
    <body>
        <hr><br>
        <div data-role="page">            
            <%String nm = request.getParameter("mname");
                String mn = "http://196.216.64.237/logo/" + nm.toLowerCase() + ".png";%>
            <div data-role="header" id="hdr">                
                <img src="<%=mn%>" style="margin-left: 50px">                                
            </div>
            <div data-role="content">                
                <table>
                    <tr>
                        <td>Merchant Name:</td>
                        <td><%=request.getParameter("mname")%></td>
                    </tr>
                    <tr>
                        <td>Amount:</td>
                        <td></td>
                    </tr>                    
                </table> 
                <hr>
                <form action="Visa" method="get">
                    <input type="hidden" name="mname" value="<%=request.getParameter("mname")%>"/>
                    <input type="hidden" name="appid" value="<%=request.getParameter("appid")%>"/>
                    <input type="hidden" name="tamt" value="<%=request.getParameter("tamt")%>"/>
                    <input type="hidden" name="reference" value="<%=request.getParameter("reference")%>"/>
                    <input type="hidden" name="email" value="<%=request.getParameter("email")%>"/>
                    <input type="hidden" name="desc" value="<%=request.getParameter("desc")%>"/>                                       
                    <table>                        
                        <tr>
                            <td><label for="cardno">Card No:</label></td>
                            <td><input type="number" name="cardno" id="cardno" data-mini="true" value="" /></td>
                        </tr>
                        <tr>
                            <td><label for="date">Expiry Date:</label></td>
                            <td><input type="month" name="date" id="date" data-mini="true" value="" />
                            </td>
                        </tr>                        
                        <tr>
                            <td><label for="basic"></label></td>
                            <td><input type="submit" value="Pay" data-in-line="true"></td>
                        </tr>  
                    </table>
                </form>                                     
            </div>
            <hr><br>
            <div data-role="footer">
                <img src="visa-mastercard.png">                           
            </div>            
        </div>        
    </body>
</html>
