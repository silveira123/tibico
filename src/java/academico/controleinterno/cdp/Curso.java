/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package academico.controleinterno.cdp;

/**
 *
 * @author Administrador
 */
import academico.util.academico.cdp.GrandeAreaConhecimento;
import academico.util.academico.cdp.GrauInstrucao;
import academico.util.academico.cdp.Regime;
import academico.util.persistencia.ObjetoPersistente;
import javax.persistence.*;

@Entity
public class Curso extends ObjetoPersistente {

    private String nome;
    private Integer duracao;
    private String descricao;
    private GrauInstrucao grauInstrucao;
    private GrandeAreaConhecimento grandeAreaConhecimento;
    private Regime regime;
    private String sigla;
    
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false)
    public GrandeAreaConhecimento getGrandeAreaConhecimento() {
        return grandeAreaConhecimento;
    }

    public void setGrandeAreaConhecimento(GrandeAreaConhecimento grandeAreaConhecimento) {
        this.grandeAreaConhecimento = grandeAreaConhecimento;
    }

    @Enumerated(EnumType.STRING)
    public GrauInstrucao getGrauInstrucao() {
        return grauInstrucao;
    }

    public void setGrauInstrucao(GrauInstrucao grauInstrucao) {
        this.grauInstrucao = grauInstrucao;
    }

    @Enumerated(EnumType.STRING)
    public Regime getRegime() {
        return regime;
    }

    public void setRegime(Regime regime) {
        this.regime = regime;
    }

    @Override
    public String toString() {
        return this.nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
    
    
}
