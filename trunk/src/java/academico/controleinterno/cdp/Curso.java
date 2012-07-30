package academico.controleinterno.cdp;

import academico.util.academico.cdp.GrandeAreaConhecimento;
import academico.util.academico.cdp.GrauInstrucao;
import academico.util.academico.cdp.Regime;
import academico.util.persistencia.ObjetoPersistente;
import javax.persistence.*;

/**
 * Representa os cursos da instituição.
 * <p/>
 * @author FS
 */
@Entity
public class Curso extends ObjetoPersistente {

    private String nome;
    private String descricao;
    private String sigla;
    private Integer duracao;
    private GrandeAreaConhecimento grandeAreaConhecimento;
    private GrauInstrucao grauInstrucao;
    private Regime regime;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Column(length = 100000)
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
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
}