<%-- 
    Document   : Login
    Created on : 31/07/2012, 15:50:41
    Author     : jmiranda
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <title></title>
        <link rel="stylesheet" href="https://ajax.aspnetcdn.com/ajax/jquery.mobile/1.1.0/jquery.mobile-1.1.0.min.css" />
        <link rel="stylesheet" href="my.css" />
        <style>
            /* App custom styles */
        </style>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js">
        </script>
        <script src="https://ajax.aspnetcdn.com/ajax/jquery.mobile/1.1.0/jquery.mobile-1.1.0.min.js">
        </script>
        <script src="my.js">
        </script>

        <title>JSP Page</title>
    </head>
    <body>
        <!-- Home -->
        <div data-role="page" id="page1">
            <div data-theme="b" data-role="header">
                <h3>
                </h3>
            </div>
            <form name="form1" action="AreaLogada" method="post">

                <div data-role="content" style="padding: 15px">
                    <div style=" text-align:center">
                        <img style="width: 288px; height: 100px" src="images/tibico6.png">
                    </div>
                    <div data-role="fieldcontain">
                        <fieldset data-role="controlgroup">
                            <label for="textinput1">
                                Login
                            </label>
                            <input name="usuario" id="textinput1" placeholder="" value="" type="text">
                        </fieldset>
                    </div>
                    <div data-role="fieldcontain">
                        <fieldset data-role="controlgroup">
                            <label for="textinput2">
                                Senha
                            </label>
                            <input name="senha" id="textinput2" placeholder="" value="" type="password" />
                        </fieldset>
                    </div>
                    <a href="JAVASCRIPT:form1.submit()" data-role="button" data-transition="fade" data-theme="b">
                        LOGIN
                    </a>
                </div>
            </form>
            <div data-theme="b" data-role="footer" data-position="fixed">
                <h3>
                    Fábrica de Software - IFES
                </h3>
            </div>
        </div> 
        <script>
            //App custom javascript
        </script>
    </body>
</html>
