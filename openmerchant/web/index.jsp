<%-- 
    Document   : home
    Created on : Mar 19, 2013, 2:50:16 PM
    Author     : Joseph
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <%                                String nm = request.getParameter("mname");
        String mn = "https://epayments.paysure.co.ke/logo/" + nm.toLowerCase().replaceAll("\\s", "") + ".png";

    %> 
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bill Payments</title>
        <link rel="stylesheet" href="css/style.css"/>
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>
        <link rel="image_src" href="<%=mn%>" />
        <script type="text/javascript">
            //<![CDATA[ 
            var cot_loc0=(window.location.protocol == "https:")? "https://secure.comodo.net/trustlogo/javascript/cot.js" :
                "http://www.trustlogo.com/trustlogo/javascript/cot.js";
            document.writeln('<scr' + 'ipt language="JavaScript" src="'+cot_loc0+'" type="text\/javascript">' + '<\/scr' + 'ipt>');
            //]]>
        </script>
    </head>
    <body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

        <!-- Save for Web Slices (portal concept-01.jpg) -->
        <table id="bgport" width="350" border="0" align="center">
            <tr>
                <td><table id="Table_01" width="851" height="602" border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td colspan="10">
                            </td>
                            <td>
                                <img src="images/spacer.gif" width="1" height="1" alt=""></td>
                        </tr>
                        <tr>
                            <td colspan="2" rowspan="2">Bill Payments
                            </td>
                            <td colspan="2">
                                <img src="images/Portal_03.jpg" width="200" height="84" alt=""></td>
                            <td colspan="6" rowspan="2">
                            </td>
                            <td>
                                <img src="images/spacer.gif" width="1" height="84" alt=""></td>
                        </tr>
                        <tr>
                            <td colspan="2">
                            </td>
                            <td>
                                <img src="images/spacer.gif" width="1" height="4" alt=""></td>
                        </tr>
                        <tr>
                            <td rowspan="8">
                            </td>
                            <td colspan="8">
                                <img src="images/Portal_07.jpg" width="713" height="14" alt=""></td>
                            <td rowspan="8">
                            </td>
                            <td>
                                <img src="images/spacer.gif" width="1" height="14" alt=""></td>
                        </tr>
                        <tr>
                            <td colspan="8">
                            </td>
                            <td>
                                <img src="images/spacer.gif" width="1" height="9" alt=""></td>
                        </tr>
                        <tr>
                            <td colspan="4">
                            </td>
                            <td colspan="3" rowspan="3">
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
                                                <input type="number" name="amount" value="" title="Follow Format Such as 1,000.55" required="true" pattern="^(\d|,)*\.?\d*$"/><p>
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
                                        <f:view>                                            
                                            <h:panelGroup rendered="#{bean.curr}">
                                                <tr>
                                                    <td id="mulla">Currency:</td>
                                                    <td id="first"><input type="radio" name="currency" value="KES" required="true">KES
                                                        <input id="innerusd" type="radio" name="currency" value="USD" required="true" checked="true">USD</td>                                            
                                                </tr>                                                
                                            </h:panelGroup>
                                        </f:view>
                                        <tr><br/>
                                        <td><br/><input type="radio" name="payoption" value="1">Kenswitch<br><img src="https://epayments.paysure.co.ke/logo/openmerchant/kenswitch.png"></td>
                                        <td><br/><input type="radio" name="payoption" value="2" style="margin-left: 12px">VISA/MasterCard<br><br><img src="https://epayments.paysure.co.ke/logo/openmerchant/visa-mastercard.png" style="padding-left: 12px"></td><p>
                                            </tr>
                                        <tr>

                                        <div id="try">

                                            <td colspan="100%"><p><input type="checkbox" id="chkAgree" onclick="$('#btnSubmit').attr('disabled', !$(this).is(':checked'));"><a href="terms.jsp">I Agree with the terms and conditions</a><br></td><p>
                                        </div>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td><p><input type="submit" id="btnSubmit" name="submit" value="" style="background: url(images/Portal_18.jpg); width:155px; height:33px;"/></td>
                                        </tr>             
                                    </table>
                                </form>    </td>
                            <td rowspan="6">
                            </td>
                            <td>
                                <img src="images/spacer.gif" width="1" height="74" alt=""></td>
                        </tr>
                        <tr>
                            <td colspan="2" rowspan="5">
                            </td>
                            <td colspan="2">
                                <%                                String nmm = request.getParameter("mname");
                                    String mnn = "https://epayments.paysure.co.ke/logo/" + nmm.toLowerCase().replaceAll("\\s", "") + ".png";



                                %>   <img src="<%=mnn%>"></td>
                            <td>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2" rowspan="4">
                            </td>
                            <td>
                                <img src="images/spacer.gif" width="1" height="136" alt=""></td>
                        </tr>
                        <tr>
                            <td colspan="3">
                            </td>
                            <td>
                                <img src="images/spacer.gif" width="1" height="8" alt=""></td>
                        </tr>
                        <tr>
                            <td rowspan="2">
                            </td>
                            <td>
                            </td>
                            <td rowspan="2">
                            </td>
                            <td>
                                <img src="images/spacer.gif" width="1" height="33" alt=""></td>
                        </tr>
                        <tr>
                            <td>
                            </td>
                            <td>
                                <img src="images/spacer.gif" width="1" height="48" alt=""></td>
                        </tr>
                        <tr>
                            <td>
                                <img src="images/spacer.gif" width="54" height="1" alt=""></td>
                            <td>
                                <img src="images/spacer.gif" width="16" height="1" alt=""></td>
                            <td>
                                <img src="images/spacer.gif" width="8" height="1" alt=""></td>
                            <td>
                                <img src="images/spacer.gif" width="192" height="1" alt=""></td>
                            <td>
                                <img src="images/spacer.gif" width="152" height="1" alt=""></td>
                            <td>
                                <img src="images/spacer.gif" width="113" height="1" alt=""></td>
                            <td>
                                <img src="images/spacer.gif" width="155" height="1" alt=""></td>
                            <td>
                                <img src="images/spacer.gif" width="62" height="1" alt=""></td>
                            <td>
                                <img src="images/spacer.gif" width="15" height="1" alt=""></td>
                            <td>
                                <img src="images/spacer.gif" width="83" height="1" alt=""></td>
                            <td></td>
                        </tr>
                    </table></td>
            </tr>
        </table>

        <script type="text/javascript">
            $(function(){                
                //alert("Page Ready");
                //$('input:radio[name=currency]')[0].checked = true;                
            });
            $(function(){
                
                $("#mulla").css("background","white");
                $("#first").css({"background":"white","padding-left":"9px"});
                $("#innerusd").css("margin-left","30px");
                
                
                
            });
                                
                                
                                
                                
                                
                                
        </script>


        <!-- End Save for Web Slices -->      
        <a href="http://www.instantssl.com" id="comodoTL">Premium SSL Certificate</a>
        <script language="JavaScript" type="text/javascript">
            COT("https://epayments.paysure.co.ke/logo/cot.gif", "SC2", "none");
        </script>

    </body>
</html>
