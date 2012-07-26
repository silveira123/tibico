/*
 * Usuario.java 
 * Versão: 0.1 
 * Data de Criação : 15/06/2012
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
package academico.controlepauta.cdp;

import academico.util.persistencia.ObjetoPersistente;
import academico.util.pessoa.cdp.Pessoa;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * Esta classe representa os dados do usuário, contém os aspéctos necessários para o controle de acesso
 * <p/>
 * @author Pietro Crhist
 * @author Geann Valfré
 */
@Entity
public class Usuario extends ObjetoPersistente {

    private String nome;
    private String senha;
    private Integer privilegio;
    private Pessoa pessoa;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenhaCriptografar(String senha) {
        this.senha = gerarHashCode(senha);
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getPrivilegio() {
        return privilegio;
    }

    public void setPrivilegio(Integer privilegio) {
        this.privilegio = privilegio;
    }

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(nullable = true)
    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public static String gerarHashCode(String s) {
        //difine o tipo de hash a ser gerado
        MessageDigest hash=null;
        try {
            hash = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        //passa os bytes da senha
        hash.update(s.getBytes());
        //transforma o hash em bytes gerado para hexadecimal
        String hashCode = byte2StringHexa(hash.digest());
        hash.reset();

        return hashCode;
    }

    private static String byte2StringHexa(byte[] bytes) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int parteAlta = ((bytes[i] >> 4) & 0xf) << 4;
            int parteBaixa = bytes[i] & 0xf;
            if (parteAlta == 0) {
                s.append('0');
            }
            s.append(Integer.toHexString(parteAlta | parteBaixa));
        }
        return s.toString();
    }
}
