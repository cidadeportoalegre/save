package br.com.procempa.save.entity;

import javax.persistence.Entity;

import org.jboss.seam.annotations.Name;

@Entity
@Name("setor")
public class Setor extends SaveAbstract {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TipoSetor tiposetor;
	private String descricao;
	private String sigla;
	private Integer quantBox;
	private Boolean temAgenda;
	private Boolean situacao;
	public TipoSetor getTiposetor() {
		return tiposetor;
	}
	public void setTiposetor(TipoSetor tiposetor) {
		this.tiposetor = tiposetor;
	}
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
	public Integer getQuantBox() {
		return quantBox;
	}
	public void setQuantBox(Integer quantBox) {
		this.quantBox = quantBox;
	}
	public Boolean getTemAgenda() {
		return temAgenda;
	}
	public void setTemAgenda(Boolean temAgenda) {
		this.temAgenda = temAgenda;
	}
	public Boolean getSituacao() {
		return situacao;
	}
	public void setSituacao(Boolean situacao) {
		this.situacao = situacao;
	}
	
	
}
