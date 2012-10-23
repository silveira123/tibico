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
import academico.controlepauta.cdp.Usuario;
import academico.util.Exceptions.AcademicoException;
import academico.util.academico.cdp.AreaConhecimento;
import academico.util.academico.cdp.GrauInstrucao;
import academico.util.pessoa.cdp.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.xml.bind.JAXBException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;
import org.zkoss.zul.ext.Selectable;

/**
 * Esta classe, através de alguns importes utiliza atributos do zkoss para leitura e interpretação de dados; A classe contém os dados formulário, abrangendo a leitura e interpretação para a tela
 * PagFormularioProfessor.zul
 * <p/>
 * @author Gabriel Quézid
 * @author Rodrigo Maia
 */
public class PagFormularioProfessor extends GenericForwardComposer {

    // Atributos da interface
    private Button salvarProfessor;
    private Button voltar;
    private Button foto;
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
    private Textbox senha;
    private Textbox confSenha;
    private CtrlPessoa ctrlPessoa = CtrlPessoa.getInstance();
    private Window winFormularioProfessor;
    private Professor obj;
    private byte[] bytes = null;
    private int MODO;
    private Image pics;
    private Separator separator;
    private Grid gridUsuario;
    private Usuario usuarioObjetoProf;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        List<Pais> paises = ctrlPessoa.obterPaises();
        ListModelList<Pais> list = new ListModelList<Pais>(paises, true);
        pais.setModel(list);

        //colocando Brasil como pais padrão
        list.addToSelection(paises.get(32));
        onSelect$pais(null);

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

    public void onBlur$cep() {
        Webservicecep web = null;
        try {
            web = academico.util.pessoa.cgt.BuscaCep.getEndereco(cep.getValue());

        }
        catch (JAXBException ex) {
            Logger.getLogger(PagFormularioAluno.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (MalformedURLException ex) {
            Logger.getLogger(PagFormularioAluno.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (web != null) {
            logradouro.setValue(web.getTipo_logradouro() + " " + web.getLogradouro());
            for (int i = 0; i < estado.getModel().getSize(); i++) {
                Estado e = (Estado) estado.getModel().getElementAt(i);
                if (e.getSigla().equals(web.getUf())) {
                    ((ListModelList) estado.getModel()).addToSelection(e);
                    onSelect$estado(null);
                }
                //estado.setValue(web.getUf());

            }
            if (cidade.getModel() != null) {
                for (int i = 0; i < cidade.getModel().getSize(); i++) {
                    Municipio e = (Municipio) cidade.getModel().getElementAt(i);
                    if (e.getNome().equals(web.getCidade())) {
                        ((ListModelList) cidade.getModel()).addToSelection(e);
                        onSelect$cidade(null);
                    }
                    //cidade.setValue(web.getCidade());

                }

                for (int i = 0; i < bairro.getModel().getSize(); i++) {
                    Bairro e = (Bairro) bairro.getModel().getElementAt(i);
                    if (e.getNome().equals(web.getBairro())) {
                        ((ListModelList) bairro.getModel()).addToSelection(e);
                    }
                    //bairro.setValue(web.getBairro());
                }
            }
        }

        //bairro.setValue(web.getBairro());
        System.out.println(web.getResultado());
        System.out.println(web.getResultado_txt());
        System.out.println(web.getLogradouro());
        System.out.println(web.getTipo_logradouro());
        System.out.println(web.getUf());
        System.out.println(web.getCidade());
        System.out.println(web.getBairro());
    }

    public void onCreate$winFormularioProfessor() {

        //if feito para verificar se existe algum usuario logado, se nao existir eh redirecionado para o login
        if (Executions.getCurrent().getSession().getAttribute("usuario") == null) {
            Executions.sendRedirect("/");
            winFormularioProfessor.detach();
        }
        else {
            MODO = (Integer) arg.get("tipo");

            if (MODO != CtrlPessoa.SALVAR) {
                obj = (Professor) arg.get("obj");
                usuarioObjetoProf = ctrlPessoa.obterUsuario(obj);
                preencherTela();
                if (MODO == CtrlPessoa.CONSULTAR) {
                    this.salvarProfessor.setVisible(false);
                    separator.setVisible(false);
                    gridUsuario.setVisible(false);
                    bloquearTela();
                }
            }
            else {
                separator.setVisible(false);
                gridUsuario.setVisible(false);
            }
        }
    }

    private void preencherTela() {
        Calendar c = Calendar.getInstance();

        nome.setText(obj.getNome());
        sexo.setSelectedIndex(marcarSexo(obj.getSexo()));

        c = obj.getDataNascimento();
        dataNasc.setValue(c.getTime());

        //Preenchendo o campo telefone
        if (obj.getTelefone().get(0).getDdd() != null) {
            telefone.setText(preencherTelefone(obj.getTelefone().get(0)));
        }
        //Preenchendo o campo celular
        if (obj.getTelefone().get(1).getDdd() != null) {
            celular.setText(preencherTelefone(obj.getTelefone().get(1)));
        }

        email.setText(obj.getEmail());
        cpf.setText(preencherCpf(obj.getCpf()));
        rg.setText(obj.getIdentidade());

        preencherEndereco(obj.getEndereco());

        cep.setText(obj.getEndereco().getCep().toString());
        logradouro.setText(obj.getEndereco().getLogradouro());
        complemento.setText(obj.getEndereco().getComplemento());
        numero.setValue(obj.getEndereco().getNumero());

        this.bytes = obj.getFoto();
        construirImagem(this.bytes);

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

        Usuario u = (Usuario) Executions.getCurrent().getSession().getAttribute("usuario");
        if (u != null && u.getPrivilegio() == 3) {
            nome.setDisabled(true);
            sexo.getItemAtIndex(0).setDisabled(true);
            sexo.getItemAtIndex(1).setDisabled(true);
            dataNasc.setDisabled(true);
            cpf.setDisabled(true);
            rg.setDisabled(true);
        }
    }

    /**
     * A partir de um byte array, constroi uma imagem
     * <p/>
     * @param img contém um byte array da imagem
     */
    public void construirImagem(byte[] img) {
        if (img != null) {
            try {
                ByteArrayInputStream bais = new ByteArrayInputStream(img);
                BufferedImage bufferedImg = ImageIO.read(bais);

                pics.setContent(bufferedImg);
            }
            catch (IOException ex) {
                Logger.getLogger(PagFormularioProfessor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * A partir de um tipo enumerado Sexo, identifica a seleção adquada
     * <p/>
     * @param sexo
     * @return
     */
    public int marcarSexo(Sexo sexo) {
        if (sexo == Sexo.FEMININO) {
            return 1;
        }
        else {
            return 0;
        }
    }

    /**
     * A partir de um objeto Telefone, cria uma string de acordo com o padrão da interface
     * <p/>
     * @return String formatada no seguinte padrão (xx)xxxx-xxxx
     */
    public String preencherTelefone(Telefone t) {
        String telefone = "(" + t.getDdd() + ")";
        String parte1 = t.getNumero().substring(0, 4);
        String parte2 = t.getNumero().substring(4, 8);

        telefone += parte1 + "-" + parte2;

        return telefone;
    }

    public String preencherCpf(String scpf) {
        String cpf;

        cpf = scpf.substring(0, 3) + "." + scpf.substring(3, 6) + "." + scpf.substring(6, 9) + "-" + scpf.substring(9, 11);

        return cpf;
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
        List<Listitem> listAreaConhecimentos = listAreaConhecimento.getItems();
        listAreaConhecimento.setCheckmark(false);
        for (int i = 0; i < listAreaConhecimentos.size(); i++) {
            listAreaConhecimentos.get(i).setDisabled(true);
        }
        grauInstrucao.setDisabled(true);
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
        foto.setDisabled(true);
        senha.setDisabled(true);
        confSenha.setDisabled(true);

    }

    public void onClick$salvarProfessor(Event event) {

        Professor p = null;
        Calendar c = Calendar.getInstance();
        ArrayList<Object> listaTelefone = new ArrayList<Object>();
        ArrayList<Object> listaEndereco;
        String msg = valido();
        boolean valido = false;
        if (msg.trim().equals("")) {
            if (MODO == ctrlPessoa.EDITAR) {
                obj.setNome(nome.getText());
                obj.setSexo(obterSexo(sexo.getSelectedIndex()));
                c.setTime(dataNasc.getValue());
                obj.setDataNascimento(c);

                obterTelefone(telefone.getText(), TipoTel.FIXO, listaTelefone);

                obterTelefone(celular.getText(), TipoTel.CELULAR, listaTelefone);

                // Se o telefone foi preenchido ele é setado na lista de telefones do objeto (na posição correta ("0"))
                if (listaTelefone.get(0) != null) {
                    obj.getTelefone().get(0).setDdd((String) listaTelefone.get(0));
                    obj.getTelefone().get(0).setNumero((String) listaTelefone.get(1));
                    obj.getTelefone().get(0).setTipo((TipoTel) listaTelefone.get(2));
                }
                else {
                    obj.getTelefone().get(0).setDdd(null);
                    obj.getTelefone().get(0).setNumero(null);
                    obj.getTelefone().get(0).setTipo((TipoTel) listaTelefone.get(2));
                }
                // Se o celular foi preenchido ele é setado na lista de telefones do objeto (na posição correta ("1"))
                if (listaTelefone.get(3) != null) {
                    obj.getTelefone().get(1).setDdd((String) listaTelefone.get(3));
                    obj.getTelefone().get(1).setNumero((String) listaTelefone.get(4));
                    obj.getTelefone().get(1).setTipo((TipoTel) listaTelefone.get(5));
                }
                else {
                    obj.getTelefone().get(1).setDdd(null);
                    obj.getTelefone().get(1).setNumero(null);
                    obj.getTelefone().get(1).setTipo((TipoTel) listaTelefone.get(5));
                }

                obj.setEmail(email.getText());
                obj.setCpf(obterCPF(cpf.getText()));
                obj.setIdentidade(rg.getText());

                obj.getEndereco().setLogradouro(logradouro.getText());
                obj.getEndereco().setCep(obterCEP(cep.getText()));
                obj.getEndereco().setNumero(Integer.parseInt(numero.getText()));
                obj.getEndereco().setComplemento(complemento.getText());
                obj.getEndereco().setBairro((Bairro) bairro.getSelectedItem().getValue());

                obj.setGrauInstrucao(GrauInstrucao.valueOf(grauInstrucao.getText())); // grau de intrução
                obj.setAreaConhecimento(getSelecionadosList(listAreaConhecimento));
                obj.setFoto(bytes);

                //se tiver colocado uma nova senha
                if (!senha.getText().trim().equals("")) {
                    usuarioObjetoProf.setSenha(senha.getText());
                    usuarioObjetoProf = ctrlPessoa.alterarUsuario(usuarioObjetoProf);
                }
                valido = ctrlPessoa.alterarProfessor(obj);
            }
            else {

                ArrayList<Object> list = new ArrayList<Object>();
                listaEndereco = new ArrayList<Object>();

                list.add(nome.getText());

                list.add(obterSexo(sexo.getSelectedIndex()));

                c.setTime(dataNasc.getValue());
                list.add(c);

                obterTelefone(telefone.getText(), TipoTel.FIXO, listaTelefone);

                obterTelefone(celular.getText(), TipoTel.CELULAR, listaTelefone);

                list.add(listaTelefone);
                list.add(email.getText());
                list.add(obterCPF(cpf.getText()));
                list.add(rg.getText());
                obterEndereco(listaEndereco);
                list.add(listaEndereco);
                list.add(GrauInstrucao.valueOf(grauInstrucao.getText())); // grau de intrução
                list.add(getSelecionadosList(listAreaConhecimento));
                list.add(this.bytes);

                valido = ctrlPessoa.incluirProfessor(list);
            }
            if (valido) {
                winFormularioProfessor.onClose();
            }
            else {
                Messagebox.show("CPF já Cadastrado!", "ERRO", 0, Messagebox.EXCLAMATION);
            }
        }
        else {
            Messagebox.show(msg, "Informe:", 0, Messagebox.EXCLAMATION);
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

    public void obterTelefone(String telefone, TipoTel tipo, List<Object> listTelefone) {
        String ddd, tel;

        int indice;

        //Obtendo o índice da lista onde o telefone (de acordo com o tipo) será inserido
        if (listTelefone.contains(tipo)) {
            indice = listTelefone.indexOf(tipo) - 2;
        }
        else {
            indice = listTelefone.size();
        }

        //Se a String vinda do formulário for diferente de "vazio" ela é tratada e inserida na lista
        if (!telefone.equals("")) {
            ddd = telefone.replace("(", " ");

            ddd = ddd.replace(")", " ");

            tel = ddd.replace("-", " ");

            StringTokenizer parser = new StringTokenizer(tel);

            listTelefone.add(indice, parser.nextToken());

            listTelefone.add(indice + 1, parser.nextToken() + parser.nextToken());

            listTelefone.add(indice + 2, tipo);
        } //Caso contrário, se mantêm o tipo, mas os valores ficam como null
        else {
            listTelefone.add(indice, null);
            listTelefone.add(indice + 1, null);
            listTelefone.add(indice + 2, tipo);
        }
    }

    public String obterCPF(String scpf) {
        String cpf = null;

        if (scpf != null) {
            cpf = scpf.replace(".", "");

            cpf = cpf.replace("-", "");
        }

        return cpf;
    }

    public void obterEndereco(ArrayList<Object> listaEndereco) {
        listaEndereco.add(logradouro.getText());
        listaEndereco.add(obterCEP(cep.getText()));
        listaEndereco.add(Integer.parseInt(numero.getText()));
        listaEndereco.add(complemento.getText());
        listaEndereco.add((Bairro) bairro.getSelectedItem().getValue());
    }

    public Long obterCEP(String scep) {
        Long cep = new Long(scep);

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
        senha.setText(null);
        confSenha.setText(null);
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

    public void onClick$voltar(Event event) {
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
        if (MODO != CtrlPessoa.SALVAR && senha.getText().trim().equals("") && !confSenha.getText().trim().equals("")) {
            msg += "- Senha\n";
        }
        if (MODO != CtrlPessoa.SALVAR && confSenha.getText().trim().equals("") && !senha.getText().trim().equals("")) {
            msg += "- Confirmação de Senha\n";
        }
        if (MODO != CtrlPessoa.SALVAR && !senha.getText().equals(confSenha.getText())) {
            msg += "- Senha e Confirmação não são iguais\n";
        }

        return msg;
    }

        public void onUpload$foto(UploadEvent event) {
        //Media media = event.getMedia();
        org.zkoss.util.media.Media media = event.getMedia();

        if (media != null && media.isBinary()) {
            if (media.getByteData().length > 1000 * 1024) {
                Messagebox.show("O arquivo selecionado é muito grande (máx 1024kb)!", "Alerta!", 0, Messagebox.EXCLAMATION);
            }
            else if ("jpg".equals(media.getFormat()) || "jpeg".equals(media.getFormat()) || "png".equals(media.getFormat())) {
                this.bytes = media.getByteData();
                construirImagem(bytes);
            }
            else {
                Messagebox.show("O arquivo selecionado não é valido! Por favor, selecione um arquivo do tipo jpg, jpeg ou png.", "Alerta!", 0, Messagebox.EXCLAMATION);
            }
        }
        else if (media != null && media.isBinary() == false) {
            if (media.getStringData().getBytes().length > 1000 * 1024) {
                Messagebox.show("O arquivo selecionado é muito grande (máx 1024kb)!", "Alerta!", 0, Messagebox.EXCLAMATION);
            }
            else if ("jpg".equals(media.getFormat()) || "jpeg".equals(media.getFormat()) || "png".equals(media.getFormat())) {
                this.bytes = media.getStringData().getBytes();
                construirImagem(bytes);
            }
            else {
                Messagebox.show("O arquivo selecionado não é valido! Por favor, selecione um arquivo do tipo jpg, jpeg ou png.", "Alerta!", 0, Messagebox.EXCLAMATION);
            }
        }
    }
}
