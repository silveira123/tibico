<%@page import="academico.controlepauta.cgt.AplControlarAula"%>
<%@page import="academico.controlepauta.cdp.Resultado"%>
<%@page import="academico.controlepauta.cci.CtrlAula"%>
<%@page import="academico.controlepauta.cdp.Avaliacao"%>
<%@page import="academico.controlepauta.cdp.SituacaoAlunoTurma"%>
<%@page import="academico.controleinterno.cdp.Aluno"%>
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
                    </label>
                </h3>
            </div>
            <div data-role="content" style="padding: 15px">
                <div style=" text-align:center">
                    <img style="width: 288px; height: 100px" src="images/tibico6.png">
                </div>
                <%
                    Usuario u = (Usuario) request.getAttribute("usuario");
                %>
                <div data-role="content" style="padding: 15px">
                    <div align="left">
                        <h4>
                            Aluno: <%=  u.getPessoa().toString()%>
                            <br>
                            Curso: <%=  ((Aluno) u.getPessoa()).getCurso().getSigla()%>
                        </h4>
                    </div>
                    <%
                        List<MatriculaTurma> matTurma = (List<MatriculaTurma>) request.getAttribute("matriculas");
                        for (int i = 0; matTurma != null && i < matTurma.size(); i++) {
                    %>
                    <div data-role="collapsible-set" data-theme="b" data-content-theme="b">
                        <div data-role="collapsible" data-collapsed="true">
                            <h3>
                                <%=  matTurma.get(i).getTurma().getDisciplina().toString()%>
                                <br> 
                                Freq: <%=  matTurma.get(i).toDecimalFormat()%>% /
                                Nota: <%=  matTurma.get(i).getResultadoFinal().toString()%>
                            </h3>
                            <b>
                                <div class="ui-grid-a">
                                    <div class="ui-block-a">
                                        Professor
                                    </div>
                                    <div class="ui-block-b">
                                        <%  if (matTurma.get(i).getTurma().getProfessor() != null) {
                                        %>
                                        <%=  matTurma.get(i).getTurma().getProfessor()%>
                                        <% }
                                        else {
                                        %>
                                        --
                                        <% }
                                        %>
                                    </div>
                                    <div class="ui-block-a">
                                        CH:
                                    </div>
                                    <div class="ui-block-b">
                                        <%=  matTurma.get(i).getTurma().getDisciplina().getCargaHoraria()%>
                                    </div>
                                    <div class="ui-block-a">
                                        Situação Aluno:
                                    </div>
                                    <%
                                        if (matTurma.get(i).getSituacaoAluno().equals(SituacaoAlunoTurma.REPROVADOFALTA) || matTurma.get(i).getSituacaoAluno().equals(SituacaoAlunoTurma.REPROVADONOTA)) {
                                    %>
                                    <div class="ui-block-b" style="color:red;">
                                        <%=  matTurma.get(i).getSituacaoAluno()%>
                                    </div> 
                                    <%}
                                    else if (matTurma.get(i).getSituacaoAluno().equals(SituacaoAlunoTurma.APROVADO)) {%>
                                    <div class="ui-block-b" style="color:green;">
                                        <%=  matTurma.get(i).getSituacaoAluno()%>
                                    </div> 
                                    <%}
                                    else {%>
                                    <div class="ui-block-b">
                                        <%=  matTurma.get(i).getSituacaoAluno()%>
                                    </div> 
                                    <%}%>

                                    <div class="ui-block-a">
                                        Status Turma:
                                    </div>
                                    <div class="ui-block-b">
                                        <%=  matTurma.get(i).getTurma().getEstadoTurma().toString()%>
                                    </div>
                                    <%
                                    //Isto é muito feio não faça
                                    List<Avaliacao> listAvaliacao = ((AplControlarAula) request.getAttribute("apl")).obterAvaliacoes(matTurma.get(i).getTurma());
                                    if(!listAvaliacao.isEmpty()) 
                                    {%>
                                        <div class="ui-block-a">
                                            <br>
                                        </div>
                                        <div class="ui-block-b">
                                            <br>
                                        </div>
                                        <div class="ui-block-a">
                                            AVALIAÇÃO
                                        </div>
                                        <div class="ui-block-b">
                                            NOTAS
                                        </div>
                                    <%
                                        
                                        for(int j=0; j<listAvaliacao.size();j++){
                                            //Isso foi feito por Gabriel Miranda, isto é muito feio não faça
                                            Resultado r = ((AplControlarAula) request.getAttribute("apl")).obtemResultado(listAvaliacao.get(j), matTurma.get(i));
                                            %>
                                                <div class="ui-block-a">
                                                    <%=  listAvaliacao.get(j).toString()%>
                                                </div>
                                                <div class="ui-block-b">
                                                    <% if(r.getPontuacao()!=null) {%>
                                                            <%=r.getPontuacao()%>
                                                        <%}
                                                        else {%>
                                                            --
                                                        <%}
                                                    %>
                                                </div>
                                        <%}
                                    }%>
                                </div>
                            </b>
                        </div>
                    </div>
                    <%
                        }
                    %>
                </div>
                <input data-theme="b" data-icon="back" data-inline="true" data-iconpos="left" value="Sair" type="submit" onClick='javascript:location.href="indexMobile.jsp"'/>
 
            </div>


        </div>
        <script>
            //App custom javascript
        </script>
    </body>
</html>