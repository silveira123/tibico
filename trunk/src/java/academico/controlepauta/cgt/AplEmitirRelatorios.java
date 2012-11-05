/*
 * AplEmitirRelatorios.java 
 * Versão: 0.1 
 * Data de Criação : 04/07/2012, 12:39:44
 * Copyright (c) 2012 Fabrica de Software IFES.
 * Incubadora de Empresas IFES, sala 11
 * Rodovia ES-010 - Km 6,5 - Manguinhos, Serra, ES, 29164-321, Brasil.
 * All rights reserved.
 *
 * This software is the confidential and proprietary 
 * information of Fabrica de Software IFES. ("Confidential Information"). You 
 * shall not disclose such Confidential Information and 
 * shall use it only in accordance with the terms of the 
 * license agreement you entered into with Fabrica de Software IFES.
 */

package academico.controlepauta.cgt;
// imports devem ficar aqui!

import academico.controleinterno.cdp.Calendario;
import academico.controleinterno.cdp.Curso;
import academico.controleinterno.cdp.Turma;
import academico.controleinterno.cgt.AplCadastrarCalendarioSala;
import academico.controleinterno.cgt.AplCadastrarCurso;
import academico.controleinterno.cgt.AplControlarTurma;
import academico.controlepauta.cdp.MatriculaTurma;
import academico.controlepauta.cih.ResultadosToPdf;
import academico.util.Exceptions.AcademicoException;
import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;


/**
 *  Esta classe controla os Relatórios. 
 * 
 * @author erigamonte
 * @version
 * @see
 */
public class AplEmitirRelatorios {
    private AplCadastrarCurso aplCadastroCurso = AplCadastrarCurso.getInstance();
    private AplCadastrarCalendarioSala aplCadastrarCalendario = AplCadastrarCalendarioSala.getInstance();
    private AplControlarTurma aplControlarTurma = AplControlarTurma.getInstance();
    
    
    private AplEmitirRelatorios() {
    }

    private static AplEmitirRelatorios instance = null;
    

    public static AplEmitirRelatorios getInstance() {
        if (instance == null) {
            instance = new AplEmitirRelatorios();
        }
        return instance;
    }

    /**
    * <<descrição do método>>
    *
    * @param <<nome do parâmetro>> <<descrição do parâmetro>>
    * @param ...
    * @return <<descrição do retorno>>
    * @throws <<Exception gerada e o motivo>>
    */

    //Métodos Estáticos:
    
    //Métodos de Instância:
    public List<Curso> obterCursos () throws AcademicoException{
        return aplCadastroCurso.obterCursos();
    }
    
    public List<Calendario> obterCalendarios (Curso curso) throws AcademicoException{
        return aplCadastrarCalendario.obterCalendarios(curso);
    }
    
    public List<Turma> obterTurmas (Calendario calendario){
        return aplControlarTurma.obterTurmas(calendario);
    }
    public List<MatriculaTurma> obter(Turma t) {
        return AplControlarMatricula.getInstance().obter(t);
    }
    public boolean gerarRelatorios(Turma obj, Double media) throws BadElementException, MalformedURLException, IOException, DocumentException {
        List<MatriculaTurma> listaMat = obter(obj);
        if(listaMat.size()>0){
            ResultadosToPdf.gerarPdf(listaMat, media);
            return true;
        }
        return false;
    }
    
    
 }
