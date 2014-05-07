package br.com.procempa.save.entity;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.Length;
import org.jboss.seam.annotations.Name;

@Entity
@NamedQueries(value={
		@NamedQuery( name=RespostaSolicitacao.RESPOSTAS_ALL, 
				query="SELECT t FROM RespostaSolicitacao t " +
				"ORDER BY t.seqResp"),
			@NamedQuery( name=RespostaSolicitacao.RESPOSTAS_BY_SEQRESP, 
				query="SELECT t FROM RespostaSolicitacao t " +
				"WHERE t.tramite = :tramite " +
				"  AND t.seqResp = :seqResp " +						
				"ORDER BY t.seqResp DESC"),
				@NamedQuery( name=RespostaSolicitacao.RESPOSTAS_LAST_BY_TRAMITE, 
				query="SELECT t FROM RespostaSolicitacao t " +
				"WHERE t.tramite = :tramite " +
				"ORDER BY t.seqResp DESC")				
})
@Name(RespostaSolicitacao.NAME)
public class RespostaSolicitacao extends SaveAbstract {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "resposta";
	public static final String RESPOSTAS_ALL = "resposta.findAll";
	public static final String RESPOSTAS_BY_SEQRESP = "resposta.findBySeqFase";
	public static final String RESPOSTAS_LAST_BY_TRAMITE = "resposta.findLastByTramite";

	private Solicitacao solicitacao;
	private Tramite tramite;
	private Integer codigoResposta;
	private String descricaoResposta;
	private Calendar dataResposta;
	private Integer seqResp;
	private TipoResposta tipoResposta;
	
	@ManyToOne(fetch=FetchType.LAZY)
	public Tramite getTramite() {
		return tramite;
	}

	public void setTramite(Tramite tramite) {
		this.tramite = tramite;
	}

	public Integer getCodigoResposta() {
		return codigoResposta;
	}

	public void setCodigoResposta(Integer codigoResposta) {
		this.codigoResposta = codigoResposta;
	}
	
	@Length(max=8000)
	public String getDescricaoResposta() {
		return descricaoResposta;
	}

	public void setDescricaoResposta(String descricaoResposta) {
		this.descricaoResposta = descricaoResposta;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getDataResposta() {
		return dataResposta;
	}

	public void setDataResposta(Calendar dataResposta) {
		this.dataResposta = dataResposta;
	}

	public Integer getSeqResp() {
		return seqResp;
	}

	public void setSeqResp(Integer seqResp) {
		this.seqResp = seqResp;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	public TipoResposta getTipoResposta() {
		return tipoResposta;
	}

	public void setTipoResposta(TipoResposta tipoResposta) {
		this.tipoResposta = tipoResposta;
	}	
	
}
