package br.com.procempa.save.entity;

import javax.persistence.Entity;

import org.jboss.seam.annotations.Name;

@Entity
@Name("diagnostico")
public class Diagnostico extends SaveAbstract {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String  codigo;
	private String descricao;
	private Boolean situacao;
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
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
