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
    
    private CtrlPessoa ctrlPessoa = new CtrlPessoa();
    private Window winFormularioProfessor;
    private Professor obj;
    private int MODO;
    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        
        List<Pais> paises = ctrlPessoa.obterPaises();
        
        pais.setModel(new ListModelList(paises, true));        
        pais.setReadonly(true);
        
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
        
        for (int i = 0; i < listItems.size(); i++) {
            if (listItems.get(i).getValue().equals(obj)) {
                listItems.remove(i);
            }
        }

        //seleciona os que devem ser marcados em prerequisito
        if (obj.getAreaConhecimento().size() > 0) {
            
            List selects = obj.getAreaConhecimento();
            
            for (int i = 0; i < listItems.size(); i++) {
                if (selects.contains(listItems.get(i).getValue())) {
                    listAreaConhecimento.addItemToSelection(listItems.get(i));
                }
            }
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
            
            pais.setModel(new ListModelList(paises, true));            
            pais.setReadonly(true);
            
            List<Comboitem> a = pais.getItems(); // retornado a lista de paises
            for (int i = 0; i < a.size(); i++) {
                // verificando qual pais esta cadastrado
                if (a.get(i).getValue() == p) {
                    pais.setSelectedItem(a.get(i));
                }
            }
            
            estado.setModel(new ListModelList(estados, true));            
            estado.setReadonly(true);
            
            a = estado.getItems(); // retornado a lista de estados
            for (int i = 0; i < a.size(); i++) {
                // verificando qual estado esta cadastrado
                if (a.get(i).getValue() == e) {
                    estado.setSelectedItem(a.get(i));
                }
            }
            
            cidade.setModel(new ListModelList(municipios, true));            
            cidade.setReadonly(true);
            
            a = cidade.getItems(); // retornado a lista de municipio
            for (int i = 0; i < a.size(); i++) {
                // verificando qual municipio esta cadastrado
                if (a.get(i).getValue() == m) {
                    cidade.setSelectedItem(a.get(i));
                }
            }            
            
            bairro.setModel(new ListModelList(bairros, true));            
            bairro.setReadonly(true);
            
            a = bairro.getItems(); // retornado a lista de bairro
            for (int i = 0; i < a.size(); i++) {
                // verificando qual bairro esta cadastrado
                if (a.get(i).getValue() == b) {
                    bairro.setSelectedItem(a.get(i));
                }
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
                
                Set auxAC = ((Selectable) listAreaConhecimento.getModel()).getSelection();
                
                obj.setAreaConhecimento(new ArrayList<AreaConhecimento>(auxAC));
                
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
                
                Set auxAC = ((Selectable) listAreaConhecimento.getModel()).getSelection();
                
                list.add(new ArrayList<AreaConhecimento>(auxAC));                
                
                p = ctrlPessoa.incluirProfessor(list);
                
                Messagebox.show("Cadastro feito!");
                
                limparCampos();
            }
        }
        catch (Exception e) {
            Messagebox.show("Falha no cadastro feito!");
        }
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
        if(bairro.getSelectedItem() != null)
            e.setBairro((Bairro) bairro.getSelectedItem().getValue());
        else e.setBairro(null);
        
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
    
    public void onSelect$pais() {
        List<Estado> listEstados = ctrlPessoa.obterEstados((Pais) pais.getSelectedItem().getValue());
        estado.setModel(new ListModelList(listEstados, true));        
        estado.setReadonly(true);
    }
    
    public void onSelect$estado() {
        List<Municipio> listMunicipio = ctrlPessoa.obterMunicipio((Estado) estado.getSelectedItem().getValue());
        cidade.setModel(new ListModelList(listMunicipio, true));        
        cidade.setReadonly(true);
    }
    
    public void onSelect$cidade() {
        
        List<Bairro> listBairro = ctrlPessoa.obterBairro((Municipio) cidade.getSelectedItem().getValue());
        bairro.setModel(new ListModelList(listBairro, true));        
        bairro.setReadonly(true);
    }
    
    public void onClick$cancelarProfessor(Event event) {
        winFormularioProfessor.onClose();
        ctrlPessoa.redirectPag("/pagEventosProfessor.zul");
    }
    
    public void onClose$winFormularioProfessor(Event event) {
        ctrlPessoa.redirectPag("/pagEventosProfessor.zul");
    }

    
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
}
