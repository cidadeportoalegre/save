package br.com.procempa.save.entity;

import javax.persistence.Entity;

import org.jboss.seam.annotations.Name;

@Entity
@Name("classificacaorisco")
public class ClassificacaoRisco extends SaveAbstract {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Solicitacao solicitacao;
	private ClassifRiscoResposta classifriscoresposta;
	public Solicitacao getSolicitacao() {
		return solicitacao;
	}
	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}
	public ClassifRiscoResposta getClassifriscoresposta() {
		return classifriscoresposta;
	}
	public void setClassifriscoresposta(ClassifRiscoResposta classifriscoresposta) {
		this.classifriscoresposta = classifriscoresposta;
	}
							
}
