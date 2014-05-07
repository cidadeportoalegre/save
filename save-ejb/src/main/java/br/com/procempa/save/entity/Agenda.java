package br.com.procempa.save.entity;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.jboss.seam.annotations.Name;

@Entity
@Name("agenda")
public class Agenda extends SaveAbstract {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Setor setor;
	private CausaAgenda causaagenda;
	private Solicitacao solicitacao;
	private SituacaoAgenda situacaoagenda;
	private Especie  especie;
	private Calendar dataAgenda;
	private Integer quantAgendada;
	private Integer quantRealizada;
	private String  genero;
	private String  porteTexto;
	public Setor getSetor() {
		return setor;
	}
	public void setSetor(Setor setor) {
		this.setor = setor;
	}
	public CausaAgenda getCausaagenda() {
		return causaagenda;
	}
	public void setCausaagenda(CausaAgenda causaagenda) {
		this.causaagenda = causaagenda;
	}
	public Solicitacao getSolicitacao() {
		return solicitacao;
	}
	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}
	public SituacaoAgenda getSituacaoagenda() {
		return situacaoagenda;
	}
	public void setSituacaoagenda(SituacaoAgenda situacaoagenda) {
		this.situacaoagenda = situacaoagenda;
	}
	public Especie getEspecie() {
		return especie;
	}
	public void setEspecie(Especie especie) {
		this.especie = especie;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getDataAgenda() {
		return dataAgenda;
	}
	public void setDataAgenda(Calendar dataAgenda) {
		this.dataAgenda = dataAgenda;
	}
	
	public Integer getQuantAgendada() {
		return quantAgendada;
	}
	public void setQuantAgendada(Integer quantAgendada) {
		this.quantAgendada = quantAgendada;
	}
	public Integer getQuantRealizada() {
		return quantRealizada;
	}
	public void setQuantRealizada(Integer quantRealizada) {
		this.quantRealizada = quantRealizada;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public String getPorteTexto() {
		return porteTexto;
	}
	public void setPorteTexto(String porteTexto) {
		this.porteTexto = porteTexto;
	}

	
}
