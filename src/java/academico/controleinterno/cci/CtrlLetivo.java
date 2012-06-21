/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package academico.controleinterno.cci;

import academico.controleinterno.cdp.Calendario;
import academico.controleinterno.cgt.AplCadastrarCalendario;
import academico.util.Exceptions.AcademicoException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zkoss.zk.ui.Executions;

/**
 *
 * @author Administrador
 */
public class CtrlLetivo {

    public static final int SALVAR = 0;
    public static final int EDITAR = 1;
    public static final int CONSULTAR = 2;
    private AplCadastrarCalendario apl = AplCadastrarCalendario.getInstance();
    private static CtrlLetivo instance = null;

    public static CtrlLetivo getInstance() {
        if (instance == null) {
            instance = new CtrlLetivo();
        }
        return instance;
    }

    private CtrlLetivo() {
    }

   public Calendario incluirCalendario(ArrayList<Object> args) throws AcademicoException {

        return apl.incluirCalendario(args);
    }

    public Calendario alterarCalendario(Calendario calendario) throws Exception {
        return apl.alterarCalendario(calendario);
    }

    public void apagarCalendario(Calendario calendario) throws Exception {
        apl.apagarCalendario(calendario);
    }

    public List<Calendario> obterCalendario() throws AcademicoException {
        return apl.obterCalendarios();
    }

    public void abrirIncluirCalendario() {
        Map map = new HashMap();
        map.put("tipo", CtrlLetivo.SALVAR);
        Executions.createComponents("/PagFormularioCalendario.zul", null, map);
    }

    public void abrirEditarCalendario(Calendario calendario) {
        Map map = new HashMap();
        map.put("tipo", CtrlLetivo.EDITAR);
        map.put("obj", calendario);
        Executions.createComponents("/PagFormularioCalendario.zul", null, map);
    }

    public void abrirConsultarCalendario(Calendario calendario) {
        Map map = new HashMap();
        Object put = map.put("tipo", CtrlLetivo.CONSULTAR);
        map.put("obj", calendario);
        Executions.createComponents("/PagFormularioCalendario.zul", null, map);
    }
    
    public void redirectPag(String url) {
        Executions.sendRedirect(url);
    }
}