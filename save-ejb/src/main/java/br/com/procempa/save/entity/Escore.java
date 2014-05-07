package br.com.procempa.save.entity;

import javax.persistence.Entity;

import org.jboss.seam.annotations.Name;

@Entity
@Name("escore")
public class Escore extends SaveAbstract {

	private static final long serialVersionUID = 1L;
	private Integer escoreInicial;
	private Integer escoreFinal;
	private String descricao;
	private Boolean situacao;
	public Integer getEscoreInicial() {
		return escoreInicial;
	}
	public void setEscoreInicial(Integer escoreInicial) {
		this.escoreInicial = escoreInicial;
	}
	public Integer getEscoreFinal() {
		return escoreFinal;
	}
	public void setEscoreFinal(Integer escoreFinal) {
		this.escoreFinal = escoreFinal;
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
	
}
