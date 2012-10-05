/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package academico.util.email;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 * Esta classe pode ser usada para envio de email
 *
 * @author João Paulo Miranda
 * @version 0.1
 * @see
 */
public class Mail {

    private List<String> emailDestinatario = new ArrayList<String>();
    private List<String> nomeDestinatario = new ArrayList<String>();
    private String emailRemetente;
    private String nomeRemetente;
    private Integer portaSmtp;
    private String hostSmtp;
    private String assunto;
    private String mensagem;
    private String login;
    private String senha;

    

    /**
     * Retorna o nome de email do remtente.
     */
    public String getEmailRemetente() {
        return emailRemetente;
    }

    /**
     * Altera o nome de email do remtente.
     */
    public void setEmailRemetente(String emailRemetente) {
        this.emailRemetente = emailRemetente;
    }

    /**
     * Retorna o Host do servidor smtp.
     */
    public String getHostSmtp() {
        return hostSmtp;
    }

    /**
     * Altera o Host do servidor smtp.
     */
    public void setHostSmtp(String hostSmtp) {
        this.hostSmtp = hostSmtp;
    }

    /**
     * Retorna o Login do email.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Altera o Login do email.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Retorna o Assunto do email.
     */
    public String getAssunto() {
        return assunto;
    }

    /**
     * Altera o Assunto do email.
     */
    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    /**
     * Retorna a Mensagem do corpo do email.
     */
    public String getMensagem() {
        return mensagem;
    }

    /**
     * Altera a Mensagem do corpo do email.
     */
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    

    /**
     * Adiciona um Destinatario.
     */
    public void addRemetente(String nome, String email){
        this.emailDestinatario.add(email);
        this.nomeDestinatario.add(nome);
    }

    /**
     * Retorna o Nome ou apelido do Remetente.
     */
    public String getNomeRemetente() {
        return nomeRemetente;
    }

    /**
     * Altera o Nome ou apelido do Remetente.
     */
    public void setNomeRemetente(String nomeRemetente) {
        this.nomeRemetente = nomeRemetente;
    }

    /**
     * Retorna a Porta do servidor smtp.
     */
    public Integer getPortaSmtp() {
        return portaSmtp;
    }

    /**
     * Altera a Porta do servidor smtp.
     */
    public void setPortaSmtp(Integer portaSmtp) {
        this.portaSmtp = portaSmtp;
    }

    /**
     * Retorna a Senha do email.
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Altera a Senha do email.
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }
    /**
     * Realiza o envio do Email configurado
     * @return void
     */
    public void sendEmail() throws EmailException {

        SimpleEmail email = new SimpleEmail();

        email.setHostName(this.hostSmtp);
        email.setSmtpPort(this.portaSmtp);
        for (int i = 0; i < emailDestinatario.size(); i++) {
            email.addTo(this.emailDestinatario.get(i), this.nomeDestinatario.get(i));
        }
        email.setFrom(this.emailRemetente, this.nomeRemetente);
        email.setSubject(this.assunto);
        email.setMsg(this.mensagem);
        email.setSSL(true);
        email.setAuthentication(this.login, this.senha);
        email.send();
        System.out.println("Email enviado!");
    }
}
