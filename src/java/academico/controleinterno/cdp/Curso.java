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
    private int duracao;
    private String descricao;
    private GrauInstrucao grauInstrucao;
    private GrandeAreaConhecimento grandeAreaConhecimento;
    private Regime regime;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    //TODO colocar GrandeAreaConhecimento como nullable = false
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = true)
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
}
