package br.com.procempa.save.entity;

import javax.persistence.Entity;

import org.jboss.seam.annotations.Name;

@Entity
@Name("classifriscoresposta")
public class ClassifRiscoResposta extends SaveAbstract {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ClassifRiscoPergunta classifriscopergunta;
	private String descricao;
	private Boolean situacao;
	private Integer pontos;
	public ClassifRiscoPergunta getClassifriscopergunta() {
		return classifriscopergunta;
	}
	public void setClassifriscopergunta(ClassifRiscoPergunta classifriscopergunta) {
		this.classifriscopergunta = classifriscopergunta;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Boolean getSituacao() {
		return situacao;
	}
	public void setSituacao(Boolean situacao) {
		this.situacao = situacao;
	}
	public Integer getPontos() {
		return pontos;
	}
	public void setPontos(Integer pontos) {
		this.pontos = pontos;
	} 
		
}
