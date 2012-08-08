<%-- 
    Document   : Login
    Created on : 31/07/2012, 15:50:41
    Author     : jmiranda
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <title>
        </title>
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
    </head>
    <body>
        <!-- Home -->
        <div data-role="page" id="page1">
            <div data-theme="b" data-role="header">
                <h3>
                </h3>
            </div>
            <div data-role="content" style="padding: 15px">
                <div style=" text-align:center">
                    <img style="width: 288px; height: 100px" src="images/tibico6.png">
                </div>
                <h3>
                    Acesso apenas para alunos!
                </h3>
                <input data-theme="b" data-icon="back" data-iconpos="left" value="Voltar" type="submit" onClick='javascript:location.href="indexMobile.jsp"'/>
            </div>
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
