package br.com.procempa.save.entity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.NotNull;
import org.jboss.seam.annotations.Name;

@Entity
@NamedQueries(value={
		@NamedQuery( name=Tramite.TRAMITES_ALL, 
				query="SELECT t FROM Tramite t " +
				"ORDER BY t.seqFase"),
		@NamedQuery( name=Tramite.TRAMITES_LAST_BY_SOLICITACAO, 
				query="SELECT t FROM Tramite t " +
				"WHERE t.solicitacao = :solicitacao " +
				"ORDER BY t.seqFase DESC"),
			@NamedQuery( name=Tramite.TRAMITES_BY_SEQFASE, 
				query="SELECT t FROM Tramite t " +
				"WHERE t.solicitacao = :solicitacao " +
				"  AND t.seqFase = :seqFase " +						
				"ORDER BY t.seqFase DESC")
				
				
})
@Name(Tramite.NAME)
public class Tramite extends SaveAbstract {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "tramite";
	public static final String TRAMITES_ALL = "tramite.findAll";
	public static final String TRAMITES_LAST_BY_SOLICITACAO = "tramite.findLastBySolicitacao";	
	public static final String TRAMITES_BY_SEQFASE = "tramite.findBySeqFase";

	private Solicitacao solicitacao;
	private Calendar dataTramite;
	private Boolean encerrarProcesso = Boolean.FALSE;
	private String observacao;
	
	private TipoTramite tipoTramite;
	private DesfechoSolicitacao desfechoSolicitacao;
	
	private BigInteger refAtualizacao;
	private Integer seqFase;
	
	private List<RespostaSolicitacao> respostaList = new ArrayList<RespostaSolicitacao>();
	
	@ManyToOne(fetch=FetchType.LAZY)
	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getDataTramite() {
		return dataTramite;
	}

	public void setDataTramite(Calendar dataTramite) {
		this.dataTramite = dataTramite;
	}

	@NotNull
	public Boolean getEncerrarProcesso() {
		return encerrarProcesso;
	}

	public void setEncerrarProcesso(Boolean encerrarProcesso) {
		this.encerrarProcesso = encerrarProcesso;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	public TipoTramite getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(TipoTramite tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	public DesfechoSolicitacao getDesfechoSolicitacao() {
		return desfechoSolicitacao;
	}

	public void setDesfechoSolicitacao(DesfechoSolicitacao desfechoSolicitacao) {
		this.desfechoSolicitacao = desfechoSolicitacao;
	}

	public BigInteger getRefAtualizacao() {
		return refAtualizacao;
	}

	public void setRefAtualizacao(BigInteger refAtualizacao) {
		this.refAtualizacao = refAtualizacao;
	}

	
	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Integer getSeqFase() {
		return seqFase;
	}

	public void setSeqFase(Integer seqFase) {
		this.seqFase = seqFase;
	}

	@OneToMany(mappedBy="tramite", fetch=FetchType.LAZY)
	@Cascade({CascadeType.ALL})
	@OrderBy(value="seqResp" )	
	public List<RespostaSolicitacao> getRespostaList() {
		return respostaList;
	}

	public void setRespostaList(List<RespostaSolicitacao> respostaList) {
		this.respostaList = respostaList;
	}
}
