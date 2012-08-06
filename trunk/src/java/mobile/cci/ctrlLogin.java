/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mobile.cci;

import academico.controleinterno.cdp.Aluno;
import academico.controleinterno.cdp.Calendario;
import academico.controleinterno.cgt.AplControlarTurma;
import academico.controleinterno.cgt.AplPrincipal;
import academico.controlepauta.cdp.MatriculaTurma;
import academico.controlepauta.cdp.Usuario;
import academico.controlepauta.cgt.AplControlarMatricula;
import academico.util.Exceptions.AcademicoException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jmiranda
 */
@WebServlet(name = "ctrl", urlPatterns = {"/ctrl"})
public class ctrlLogin extends HttpServlet {

    public Usuario obterLogin(HttpServletRequest request) {
        String login = request.getParameter("usuario");
        String senha = request.getParameter("senha");

        Usuario u = null;
        try {
            u = AplPrincipal.getInstance().login(login, senha);
        } catch (AcademicoException ex) {
            Logger.getLogger(ctrlLogin.class.getName()).log(Level.SEVERE, null, ex);
        }

        return u;
    }

    public List<MatriculaTurma> obterTurmas(Usuario usuario) {
        Aluno aluno = (Aluno) usuario.getPessoa();
        List<Calendario> list = AplControlarMatricula.getInstance().buscaCalendarios(aluno);
        Calendario cal = null;
        List<MatriculaTurma> matTurma = null;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).ehAtual()) {
                cal = list.get(i);
                break;
            }
        }

        try {
            matTurma = AplControlarMatricula.getInstance().emitirBoletim(aluno, cal);
        } catch (AcademicoException ex) {
            Logger.getLogger(ctrlLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ctrlLogin.class.getName()).log(Level.SEVERE, null, ex);
        }

        return matTurma;
    }

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /*
             * TODO output your page here. You may use following sample code.
             */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ctrl</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ctrl at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("indexMobile.jsp");
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Usuario usuario = obterLogin(request);
        if (usuario != null && usuario.getPrivilegio() == 4) {
            request.setAttribute("matriculas", obterTurmas(usuario));
            request.setAttribute("usuario", usuario);
            RequestDispatcher rd = request.getRequestDispatcher("PaginaPrincipalMobile.jsp");
            rd.forward(request, response);
        } else if (usuario != null) {
            //se algum usuario que não seja aluno tentar logar
            response.sendRedirect("ErroSemAutoricaoMobile.jsp");
        } else {
            //usuario digitar login ou senha errada
            response.sendRedirect("ErroLoginMobile.jsp");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
