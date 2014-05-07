package br.com.procempa.save.entity;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.jboss.seam.annotations.Name;

@Entity
@Name("box")
public class Box extends SaveAbstract {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Setor setor;
	private SituacaoBox situacaobox;
	private String descricao;
	private Calendar dataSituacao;
	public Setor getSetor() {
		return setor;
	}
	public void setSetor(Setor setor) {
		this.setor = setor;
	}
	public SituacaoBox getSituacaobox() {
		return situacaobox;
	}
	public void setSituacaobox(SituacaoBox situacaobox) {
		this.situacaobox = situacaobox;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getDataSituacao() {
		return dataSituacao;
	}
	public void setDataSituacao(Calendar dataSituacao) {
		this.dataSituacao = dataSituacao;
	}
			
}
