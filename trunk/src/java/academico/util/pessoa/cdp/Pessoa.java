/*
 * Pessoa.java 
 * Versão: 0.1 
 * Data de Criação : 29/05/2012
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

package academico.util.pessoa.cdp;

import academico.util.persistencia.ObjetoPersistente;
import java.util.Calendar;
import java.util.List;
import javax.persistence.*;

/**
 * Esta classe contempla informações relativas a uma pessoa, seus respectivos telefones e o endereço
 * 
 * @author Gabriel Quézid
 * @version 0.1
 * @see
 */
@Entity
public class Pessoa extends ObjetoPersistente{
    private String nome;
    private Calendar dataNascimento;
    private long cpf;
    private String identidade;
    private String email;
    private Sexo sexo;
    private Endereco endereco;
    private List<Telefone> telefone;

    /**
     * Obtém o cpf de Pessoa
     * @return 
     */
    public long getCpf() {
        return cpf;
    }

    /**
     * Altera o valor de cpf em Pessoa
     * @param cpf 
     */
    public void setCpf(long cpf) {
        this.cpf = cpf;
    }
    
    /**
     * Obtém dataNascimento de Pessoa
     * @return 
     */
    @Temporal(javax.persistence.TemporalType.DATE)
    public Calendar getDataNascimento() {
        return dataNascimento;
    }

    /**
     * Altera o valor de dataNascimento em Pessoa
     * @param dataNascimento 
     */
    public void setDataNascimento(Calendar dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    /**
     * Obtém o email de Pessoa
     * @return 
     */
    public String getEmail() {
        return email;
    }

    /**
     * Altera o valor de email de Pessoa
     * @param email 
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * Obtém o endereco de Pessoa
     * @return 
     */
    @OneToOne(cascade= CascadeType.PERSIST)
    @JoinColumn(nullable= false)
    public Endereco getEndereco() {
        return endereco;
    }

    /**
     * Altera o valor de endereco de Pessoa
     * @param endereco 
     */
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    /**
     * Obtém identidade de Pessoa
     * @return 
     */
    public String getIdentidade() {
        return identidade;
    }

    /**
     * Altera o valor de identidade em Pessoa
     * @param identidade 
     */
    public void setIdentidade(String identidade) {
        this.identidade = identidade;
    }

    /**
     * Obtém nome em Pessoa
     * @return 
     */
    public String getNome() {
        return nome;
    }

    /**
     * Altera o valor de nome em Pessoa
     * @param nome 
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    /**
     * Obtém o sexo de Pessoa
     * @return 
     */
    @Enumerated(EnumType.STRING)
    public Sexo getSexo() {
        return sexo;
    }

    /**
     * Altera o sexo em Pessoa
     * @param sexo 
     */
    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }
    
    /**
     * Obtém o telefone de Pessoa
     * @return 
     */
    @OneToMany(cascade= CascadeType.PERSIST)
    @JoinColumn(nullable= true)
    public List<Telefone> getTelefone() {
        return telefone;
    }

    /**
     * Altera o valor de telefone em Pessoa
     * @param telefone 
     */
    public void setTelefone(List<Telefone> telefone) {
        this.telefone = telefone;
    }
    
}
