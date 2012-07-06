/*
 * PagFormularioProfessor.java 
 * Versão: 0.1 
 * Data de Criação : 22/06/2012, 10:33:15
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
package academico.controleinterno.cih;

import academico.controleinterno.cci.CtrlPessoa;
import academico.controleinterno.cdp.Professor;
import academico.util.Exceptions.AcademicoException;
import academico.util.academico.cdp.AreaConhecimento;
import academico.util.academico.cdp.GrauInstrucao;
import academico.util.pessoa.cdp.*;
import java.util.Calendar;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;
import org.zkoss.zul.ext.Selectable;

/**
 * <<descrição da Classe>>
 * <p/>
 * @author Gabriel Quézid
 * @version
 * @see
 */
public class PagFormularioProfessor extends GenericForwardComposer {

    // Atributos da interface
    private Button salvarProfessor;
    private Button cancelarProfessor;
    private Textbox nome;
    private Radiogroup sexo;
    private Datebox dataNasc;
    private Textbox telefone;
    private Textbox celular;
    private Textbox email;
    private Textbox cpf;
    private Textbox rg;
    private Combobox pais;
    private Textbox cep;
    private Combobox estado;
    private Combobox cidade;
    private Combobox bairro;
    private Textbox logradouro;
    private Intbox numero;
    private Textbox complemento;
    private Combobox grauInstrucao;
    private Listbox listAreaConhecimento;
    private CtrlPessoa ctrlPessoa = CtrlPessoa.getInstance();
    private Window winFormularioProfessor;
    private Professor obj;
    private int MODO;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        List<Pais> paises = ctrlPessoa.obterPaises();

        pais.setModel(new ListModelList(paises, true));
        
        pais.setReadonly(true);
        estado.setReadonly(true);
        cidade.setReadonly(true);
        bairro.setReadonly(true);

        GrauInstrucao vetGrauInstrucao[] = GrauInstrucao.values();
        List<GrauInstrucao> data = new ArrayList<GrauInstrucao>();

        if (vetGrauInstrucao.length > 0) {
            for (int i = 0; i < vetGrauInstrucao.length; i++) {
                data.add(vetGrauInstrucao[i]);
            }
            grauInstrucao.setModel(new ListModelList(data, true));
            grauInstrucao.setReadonly(true);
        }


        List<AreaConhecimento> areaConhecimentos = ctrlPessoa.obterAreaConhecimento();
        if (areaConhecimentos != null) {
            listAreaConhecimento.setModel(new ListModelList(areaConhecimentos, true));
        }
        ((ListModelList) listAreaConhecimento.getModel()).setMultiple(true);
    }

    public void onCreate$winFormularioProfessor() {
        MODO = (Integer) arg.get("tipo");

        if (MODO != ctrlPessoa.SALVAR) {
            obj = (Professor) arg.get("obj");
            preencherTela();
            if (MODO == ctrlPessoa.CONSULTAR) {
                this.salvarProfessor.setVisible(false);
                bloquearTela();
            }
        }
    }

    private void preencherTela() {
        Calendar c = Calendar.getInstance();

        nome.setText(obj.getNome());

        sexo.setSelectedIndex(marcarSexo(obj.getSexo()));

        c = obj.getDataNascimento();
        dataNasc.setValue(c.getTime());

        telefone.setText(preencherTelefone(obj.getTelefone().get(0)));

        celular.setText(preencherCelular(obj.getTelefone().get(1)));

        email.setText(obj.getEmail());

        cpf.setText(obj.getCpf().toString());

        rg.setText(obj.getIdentidade());

        preencherEndereco(obj.getEndereco());

        cep.setText(obj.getEndereco().getCep().toString());

        logradouro.setText(obj.getEndereco().getLogradouro());

        complemento.setText(obj.getEndereco().getComplemento());

        numero.setValue(obj.getEndereco().getNumero());

        List<Comboitem> a = grauInstrucao.getItems(); // retornado a lista de instruções
        for (int i = 0; i < a.size(); i++) {
            // verificando qual o grau de instrução esta cadastrado
            if (a.get(i).getValue() == obj.getGrauInstrucao()) {
                grauInstrucao.setSelectedItem(a.get(i));
            }
        }

        List<Listitem> listItems = listAreaConhecimento.getItems();

        //seleciona os que devem ser marcados em prerequisito
        if (obj.getAreaConhecimento().size() > 0) {
            setSelecionadosList(listAreaConhecimento, obj.getAreaConhecimento());
        }

    }

    public int marcarSexo(Sexo sexo) {
        if (sexo == Sexo.FEMININO) {
            return 1;
        }
        else {
            return 0;
        }
    }

    public String preencherTelefone(Telefone t) {
        String telefone = "(" + t.getDdd() + ")";
        String parte1 = t.getNumero().toString().substring(0, 4);
        String parte2 = t.getNumero().toString().substring(4, 8);

        telefone += parte1 + "-" + parte2;

        return telefone;
    }

    public String preencherCelular(Telefone t) {
        String telefone = "(" + t.getDdd() + ")";
        String parte1 = t.getNumero().toString().substring(0, 4);
        String parte2 = t.getNumero().toString().substring(4, 8);

        telefone += parte1 + "-" + parte2;

        return telefone;
    }

    public void preencherEndereco(Endereco endereco) {
        try {
            Pais p = endereco.getBairro().getMunicipio().getEstado().getPais();
            Estado e = endereco.getBairro().getMunicipio().getEstado();
            Municipio m = endereco.getBairro().getMunicipio();
            Bairro b = endereco.getBairro();

            List<Pais> paises = ctrlPessoa.obterPaises();
            List<Estado> estados = ctrlPessoa.obterEstados(p);
            List<Municipio> municipios = ctrlPessoa.obterMunicipio(e);
            List<Bairro> bairros = ctrlPessoa.obterBairro(m);

            if (p != null) {
                ((ListModelList) pais.getModel()).addToSelection(p);
                onSelect$pais(null);
            }
            
            if (e != null) {
                ((ListModelList) estado.getModel()).addToSelection(e);
                onSelect$estado(null);
            }
            
            if (m != null) {
                ((ListModelList) cidade.getModel()).addToSelection(m);
                onSelect$cidade(null);
            }
            
            if (b != null) {
                ((ListModelList) bairro.getModel()).addToSelection(b);
            }

        }
        catch (AcademicoException ex) {
            Logger.getLogger(PagFormularioProfessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void bloquearTela() {
        grauInstrucao.setDisabled(true);
        listAreaConhecimento.setDisabled(true);
        nome.setDisabled(true);
        sexo.getItemAtIndex(0).setDisabled(true);
        sexo.getItemAtIndex(1).setDisabled(true);
        dataNasc.setDisabled(true);
        telefone.setDisabled(true);
        celular.setDisabled(true);
        email.setDisabled(true);
        cpf.setDisabled(true);
        rg.setDisabled(true);
        pais.setDisabled(true);
        cep.setDisabled(true);
        estado.setDisabled(true);
        cidade.setDisabled(true);
        bairro.setDisabled(true);
        logradouro.setDisabled(true);
        numero.setDisabled(true);
        complemento.setDisabled(true);
    }

    public void onClick$salvarProfessor(Event event) {

        Professor p = null;
        Calendar c = Calendar.getInstance();
        Telefone t;
        ArrayList<Telefone> listaTelefone = new ArrayList<Telefone>();
        try {
            String msg = valido();
            if(msg.trim().equals("")){
                if (MODO == ctrlPessoa.EDITAR) {
                    obj.setNome(nome.getText());
                    obj.setSexo(obterSexo(sexo.getSelectedIndex()));
                    c.setTime(dataNasc.getValue());
                    obj.setDataNascimento(c);

                    if (telefone.getText() != null) {
                        t = obterTelefone(telefone.getText());
                        listaTelefone.add(t);
                    }
                    if (celular.getText() != null) {
                        t = obterTelefone(telefone.getText());
                        listaTelefone.add(t);
                    }

                    obj.setTelefone(listaTelefone);
                    obj.setEmail(email.getText());
                    obj.setCpf((Long.parseLong(cpf.getText())));
                    obj.setIdentidade(rg.getText());
                    obj.setEndereco(obterEndereco());
                    obj.setGrauInstrucao(GrauInstrucao.valueOf(grauInstrucao.getText())); // grau de intrução
                    obj.setAreaConhecimento(getSelecionadosList(listAreaConhecimento));
                    p = ctrlPessoa.alterarProfessor(obj);
                    Messagebox.show("Cadastro editado!");
                }
                else {

                    ArrayList<Object> list = new ArrayList<Object>();
                    list.add(nome.getText());

                    list.add(obterSexo(sexo.getSelectedIndex()));

                    c.setTime(dataNasc.getValue());
                    list.add(c);

                    if (!"".equals(telefone.getText())) {
                        t = obterTelefone(telefone.getText());
                        listaTelefone.add(t);
                    }
                    if (!"".equals(celular.getText())) {
                        t = obterTelefone(celular.getText());
                        listaTelefone.add(t);
                    }

                    list.add(listaTelefone);
                    list.add(email.getText());
                    list.add(obterCPF(cpf.getText()));
                    list.add(rg.getText());
                    list.add(obterEndereco());
                    list.add(GrauInstrucao.valueOf(grauInstrucao.getText())); // grau de intrução
                    list.add(getSelecionadosList(listAreaConhecimento));
                    p = ctrlPessoa.incluirProfessor(list);
                    Messagebox.show("Cadastro feito!");
                    limparCampos();
                }
                winFormularioProfessor.onClose();
            }
            else Messagebox.show(msg, "", 0, Messagebox.EXCLAMATION);
        }
        catch (Exception e) {
            Messagebox.show("Falha no cadastro feito!");
            System.err.println(e);
        }
    }
    
    public void setSelecionadosList(Listbox listbox, List selects) {
        ListModel model = listbox.getModel();
        ((Selectable) model).setSelection(selects);
    }

    public ArrayList getSelecionadosList(Listbox listbox) {
        ListModel model = listbox.getModel();
        return new ArrayList(((Selectable) model).getSelection());
    }

    public Sexo obterSexo(int i) {
        if (i == 0) {
            return Sexo.MASCULINO;
        }
        else {
            return Sexo.FEMININO;
        }
    }

    public Telefone obterTelefone(String telefone) {
        Telefone t = null;
        String ddd, tel;

        //TODO : arrumar uma expressão regular     
        if (telefone != null) {
            t = new Telefone();

            ddd = telefone.replace("(", " ");

            ddd = ddd.replace(")", " ");

            tel = ddd.replace("-", " ");

            StringTokenizer parser = new StringTokenizer(tel);

            t.setDdd(Integer.parseInt(parser.nextToken()));

            t.setNumero(Integer.parseInt(parser.nextToken() + parser.nextToken()));
        }

        return t;
    }

    public Long obterCPF(String scpf) {
        Long cpf = null;
        String aux1;

        if (scpf != null) {
            aux1 = scpf.replace(".", "");

            aux1 = aux1.replace("-", "");

            cpf = new Long(Long.parseLong(aux1));
        }

        return cpf;
    }

    public Endereco obterEndereco() {
        Endereco e = new Endereco();

        e.setLogradouro(logradouro.getText());
        e.setCep(obterCEP(cep.getText()));
        e.setNumero(Integer.parseInt(numero.getText()));
        e.setComplemento(complemento.getText());
        if (bairro.getSelectedItem() != null) {
            e.setBairro((Bairro) bairro.getSelectedItem().getValue());
        }
        else {
            e.setBairro(null);
        }

        return e;
    }

    public Long obterCEP(String scep) {
        Long cep = null;

        if (scep != null) {
            cep = Long.parseLong(scep.replace("-", ""));
        }

        return cep;
    }

    public void limparCampos() {
        nome.setText(null);
        sexo.getSelectedItem().setSelected(false);
        dataNasc.setText(null);
        telefone.setText(null);
        celular.setText(null);
        email.setText("exemplo@gmail.com");
        cpf.setText(null);
        rg.setText(null);
        logradouro.setText(null);
        numero.setText(null);
        complemento.setText(null);
        cep.setText(null);
        bairro.setSelectedItem(null);
        cidade.setSelectedItem(null);
        estado.setSelectedItem(null);
        pais.setSelectedItem(null);
        grauInstrucao.setSelectedItem(null);
        listAreaConhecimento.clearSelection();

    }

    public void onSelect$pais(Event event) {
        estado.setSelectedItem(null);
        cidade.setSelectedItem(null);
        bairro.setSelectedItem(null);
        
        Object[] array = ((ListModelList) pais.getModel()).getSelection().toArray();
        Pais p;
        if (array.length > 0) {
            p = (Pais) array[0];
            List<Estado> listEstados = ctrlPessoa.obterEstados(p);
            estado.setModel(new ListModelList(listEstados, true));
        }
    }

    public void onSelect$estado(Event event) {
        cidade.setSelectedItem(null);
        bairro.setSelectedItem(null);
        
        Object[] array = ((ListModelList) estado.getModel()).getSelection().toArray();
        Estado e;
        if (array.length > 0) {
            e = (Estado) array[0];
            List<Municipio> listCidade = ctrlPessoa.obterMunicipio(e);
            cidade.setModel(new ListModelList(listCidade, true));
        }
    }

    public void onSelect$cidade(Event event) {
        bairro.setSelectedItem(null);
        
        Object[] array = ((ListModelList) cidade.getModel()).getSelection().toArray();
        Municipio m;
        if (array.length > 0) {
            m = (Municipio) array[0];
            List<Bairro> listBairro = ctrlPessoa.obterBairro(m);
            bairro.setModel(new ListModelList(listBairro, true));
        }
    }

    public void onClick$cancelarProfessor(Event event) {
        winFormularioProfessor.onClose();
    }

    private String valido() {
        String msg = "";

        if (nome.getText().trim().equals("")) {
            msg += "- Nome\n";
        }
        if (sexo.getSelectedItem() == null) {
            msg += "- Sexo\n";
        }
        if (dataNasc.getValue() == null) {
            msg += "- Data de Nascimento\n";
        }
        if (telefone.getText().trim().equals("") && celular.getText().trim().equals("")) {
            msg += "- Telefone e/ou celular\n";
        }
//        if (email.getText().trim().equals("")) {
//            msg += "- Email\n";
//        }
        if (cpf.getText().trim().equals("")) {
            msg += "- CPF\n";
        }
        if (rg.getText().trim().equals("")) {
            msg += "- RG\n";
        }
        if (grauInstrucao.getSelectedItem() == null) {
            msg += "- Grau de Instrução\n";
        }
        if (getSelecionadosList(listAreaConhecimento).isEmpty()) {
            msg += "- Área de Conhecimento\n";
        }
        if (pais.getSelectedItem() == null) {
            msg += "- País\n";
        }
        if (estado.getSelectedItem() == null) {
            msg += "- Estado\n";
        }
        if (cidade.getSelectedItem() == null) {
            msg += "- Cidade\n";
        }
        if (bairro.getSelectedItem() == null) {
            msg += "- Bairro\n";
        }
        if (cep.getText().trim().equals("")) {
            msg += "- CEP\n";
        }
        if (logradouro.getText().trim().equals("")) {
            msg += "- Logradouro\n";
        }
        if (numero.getText().trim().equals("")) {
            msg += "- Numero\n";
        }
        
        if (!msg.trim().equals("")) {
            msg = "Informe:\n" + msg;
        }
        return msg;
    }
}
