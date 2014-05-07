package br.com.procempa.save.entity;

import javax.persistence.Entity;

import org.jboss.seam.annotations.Name;

@Entity
@Name("classifriscopergunta")
public class ClassifRiscoPergunta extends SaveAbstract {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ClassifRiscoGrupo classifriscogrupo;
	private String descricao;
	private Boolean situacao;
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
	public ClassifRiscoGrupo getClassifriscogrupo() {
		return classifriscogrupo;
	}
	public void setClassifriscogrupo(ClassifRiscoGrupo classifriscogrupo) {
		this.classifriscogrupo = classifriscogrupo;
	}
	
}
