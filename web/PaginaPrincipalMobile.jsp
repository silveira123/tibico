<%@page import="academico.controleinterno.cdp.Professor"%>
<%@page import="academico.controlepauta.cdp.Usuario"%>
<%@page import="academico.controlepauta.cdp.MatriculaTurma"%>
<%@page import="java.util.List"%>
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
                    <label id="header">
                        
                        Seja Bem Vindo(a)
                        <%
                            Usuario u = (Usuario) request.getAttribute("usuario");
                        %>
                        <%=  u.getPessoa().toString()%>
                    </label>
                </h3>
            </div>
            <form action="ctrlConsulta" method="POST">
                <div data-role="content" style="padding: 15px">
                    <div style=" text-align:center">
                        <img style="width: 288px; height: 100px" src="images/tibico6.png">
                    </div>

                    <div data-role="content" style="padding: 15px">
                        <%
                            List<MatriculaTurma> matTurma = (List<MatriculaTurma>) request.getAttribute("matriculas");
                            for (int i = 0; i < matTurma.size(); i++) {
                        %>
                        <div data-role="collapsible-set" data-theme="b" data-content-theme="b">
                            <div data-role="collapsible" data-collapsed="true">
                                <h3>
                                    <%=  matTurma.get(i).getTurma().getDisciplina().toString()%>
                                </h3>
                                <div class="ui-grid-a">
                                    <div class="ui-block-a">
                                        <h2>
                                            Frequência
                                        </h2>
                                    </div>
                                    <div class="ui-block-b">
                                        <h2>
                                            <%=  matTurma.get(i).toDecimalFormat()%>
                                        </h2>
                                    </div>
                                    <div class="ui-block-a">
                                        <h2>
                                            Nota
                                        </h2>
                                    </div>
                                    <div class="ui-block-b">
                                        <h2>
                                            <%=  matTurma.get(i).getResultadoFinal().toString()%>
                                        </h2>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <%
                            }
                        %>
                    </div>
                    <input data-theme="b" data-icon="back" data-inline="true" data-iconpos="left" value="Voltar" type="submit" onClick='javascript:location.href="indexMobile.jsp"'/>
                </div>

            </form>
            <div data-theme="b" data-role="footer" data-position="fixed">
                <h3>
                    Fabrica de Software - IFES
                </h3>
            </div>
        </div>
        <script>
            //App custom javascript
        </script>
    </body>
</html>