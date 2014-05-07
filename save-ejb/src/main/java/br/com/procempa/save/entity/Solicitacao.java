package br.com.procempa.save.entity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Formula;
import org.hibernate.validator.Length;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.security.management.UserRoles;

@Entity
@NamedQueries(value={
		@NamedQuery( name=Solicitacao.SOLICITACOES_ALL, 
				query="SELECT distinct t FROM Solicitacao t " +
					"JOIN t.secretariaList ss " +
					"WHERE (null = :situacao OR t.situacaoSolicitacao = :situacao) " +
					"	AND (null = :protocolo OR t.protocoloFalapoa = :protocolo OR t.protocoloMp = :protocolo OR t.protocoloPa = :protocolo) " +
					"	AND (false = :informouDataDe OR t.dataSolicitacao >= :dataDe) " +
					"	AND (false = :informouDataA OR t.dataSolicitacao <= :dataA) " +
					"   AND (null = :intervalo OR t.intervaloImportacao = :intervalo) " +
					"   AND (:administrador = 1 OR ss.id in (:secretarias)) " +
					"   AND (null = :origemSolicitacao OR t.origemSolicitacao = :origemSolicitacao) " +
					"   AND (null = :situacaoSolicitacao OR t.situacaoSolicitacao = :situacaoSolicitacao) " +
					"   AND (null = :motivoSolicitacao OR t.motivoSolicitacao = :motivoSolicitacao) " +
					"   AND (:urgente = 0 OR t.urgente = true) " +
					"	AND ((:vinculadas = 0 AND t.solicitacaoOrigem is null) OR :vinculadas = 1)" + 
					"   AND (null = :cep OR t.enderecoOcorrencia.cep like :cep) " +					
					"ORDER BY t.dataSolicitacao DESC"),
					
		@NamedQuery( name=Solicitacao.SOLICITACOES_ALL_COUNT, 
				query="SELECT count(distinct t) FROM Solicitacao t " +
						"JOIN t.secretariaList ss " +			
						"WHERE (null = :situacao OR t.situacaoSolicitacao = :situacao) " +
						"	AND (null = :protocolo OR t.protocoloFalapoa = :protocolo OR t.protocoloMp = :protocolo OR t.protocoloPa = :protocolo ) "	+
						"	AND (false = :informouDataDe OR t.dataSolicitacao >= :dataDe) " +
						"	AND (false = :informouDataA OR t.dataSolicitacao <= :dataA) " +
						"   AND (null = :intervalo OR t.intervaloImportacao = :intervalo) " +
						"   AND (:administrador = 1 OR ss.id in (:secretarias)) " +								
						"   AND (null = :origemSolicitacao OR t.origemSolicitacao = :origemSolicitacao) " +
						"   AND (null = :situacaoSolicitacao OR t.situacaoSolicitacao = :situacaoSolicitacao) " +
						"   AND (null = :motivoSolicitacao OR t.motivoSolicitacao = :motivoSolicitacao) " +
						"   AND (:urgente = 0 OR t.urgente = true) " +
						"	AND ((:vinculadas = 0 AND t.solicitacaoOrigem is null) OR :vinculadas = 1)" + 
						"   AND (null = :cep OR t.enderecoOcorrencia.cep like :cep) "),
		@NamedQuery( name=Solicitacao.SOLICITACOES_BY_FALAPOA, 
				query="SELECT t FROM Solicitacao t " +
					"WHERE t.protocoloFalapoa = :protocolo"),
		
		@NamedQuery( name=Solicitacao.SOLICITACOES_BY_FALAPOA_COUNT, 
				query="SELECT count(t) FROM Solicitacao t " +
					"WHERE t.protocoloFalapoa = :protocolo ")			
})
@Name(Solicitacao.NAME)
public class Solicitacao extends SaveAbstract {
		private static final long serialVersionUID = 1L;
		
		public static final String NAME = "solicitacao";
		public static final String SOLICITACOES_ALL = "solicitacao.findAll";		
		public static final String SOLICITACOES_ALL_COUNT = "solicitacao.findAllCount";
		public static final String SOLICITACOES_BY_FALAPOA = "solicitacao.filtroByFalaPoa";
		public static final String SOLICITACOES_BY_FALAPOA_COUNT = "solicitacao.filtroByFalaPoaCount";
		public static final String SOLICITACOES_VINCULADA_PAI = "solicitacao.filtroByVinculadaPai";
		public static final String SOLICITACOES_VINCULADA_FILHOS = "solicitacao.filtroByVinculadaFilhos";		
		public static final String SOLICITACOES_LAST_IMPORTED = "solicitacao.findLastImported";		
		private static final int TAMANHO_RESUMO_RELATO = 200;
		
		private Solicitante solicitante;
		private MotivoSolicitacao motivoSolicitacao; 
		private OrigemSolicitacao origemSolicitacao;
		private DesfechoSolicitacao desfechoSolicitacao;
		private SituacaoSolicitacao situacaoSolicitacao;
		private Integer escoreClassifRisco;
		private Calendar dataEscore;
		private String operadorEscore; 
		private String protocoloFalapoa;
		private String protocoloMp;
		private String protocoloPa;
		private Endereco enderecoOcorrencia;			
		private String relatoSolicitante; 
		private Calendar dataSolicitacao;
		private Calendar dataAbertura;
		private Calendar dataEncerramento;
		
		private String relatoSolicitanteResumo;
		private TipoSolicitante tipoSolicitante;
		private Integer nrSolicitacaoFalaPoa;
		private Integer dtSolicitacaoFalaPoa;

		private String protocoloOrigem;
		private Solicitacao solicitacaoOrigem;
		
		private IntervaloImportacao intervaloImportacao;
		
		private BigInteger refAtualizacao;
		
		private Boolean urgente;
		
		private Boolean sigiloso;
		
		private Boolean possuiTramites = Boolean.FALSE;
		 
		private String protocolo;
		
		private List<Tramite> tramiteList = new ArrayList<Tramite>();	

		private List<Secretaria>  secretariaList = new ArrayList<Secretaria>();
		
		private List<Solicitacao> solicitacaoVinculadaList = new ArrayList<Solicitacao>();
		
		@ManyToOne(fetch=FetchType.LAZY)
		public Solicitante getSolicitante() {
			return solicitante;
		}
		public void setSolicitante(Solicitante solicitante) {
			this.solicitante = solicitante;
		}
		
		@ManyToOne(fetch=FetchType.LAZY)
		public MotivoSolicitacao getMotivoSolicitacao() {
			return motivoSolicitacao;
		}
		public void setMotivoSolicitacao(MotivoSolicitacao motivoSolicitacao) {
			this.motivoSolicitacao = motivoSolicitacao;
		}
		
		@ManyToOne(fetch=FetchType.LAZY)
		public OrigemSolicitacao getOrigemSolicitacao() {
			return origemSolicitacao;
		}
		public void setOrigemSolicitacao(OrigemSolicitacao origemSolicitacao) {
			this.origemSolicitacao = origemSolicitacao;
		}
		
		@ManyToOne(fetch=FetchType.LAZY)
		public DesfechoSolicitacao getDesfechoSolicitacao() {
			return desfechoSolicitacao;
		}
		public void setDesfechoSolicitacao(DesfechoSolicitacao desfechoSolicitacao) {
			this.desfechoSolicitacao = desfechoSolicitacao;
		}
		
		@ManyToOne(fetch=FetchType.LAZY)
		public SituacaoSolicitacao getSituacaoSolicitacao() {
			return situacaoSolicitacao;
		}
		public void setSituacaoSolicitacao(SituacaoSolicitacao situacaoSolicitacao) {
			this.situacaoSolicitacao = situacaoSolicitacao;
		}
		
		public Integer getEscoreClassifRisco() {
			return escoreClassifRisco;
		}
		public void setEscoreClassifRisco(Integer escoreClassifRisco) {
			this.escoreClassifRisco = escoreClassifRisco;
		}
		
		@Temporal(TemporalType.TIMESTAMP)
		public Calendar getDataEscore() {
			return dataEscore;
		}
		public void setDataEscore(Calendar dataEscore) {
			this.dataEscore = dataEscore;
		}
		
		public String getOperadorEscore() {
			return operadorEscore;
		}
		public void setOperadorEscore(String operadorEscore) {
			this.operadorEscore = operadorEscore;
		}
		
		public String getProtocoloFalapoa() {
			return protocoloFalapoa;
		}
		public void setProtocoloFalapoa(String protocoloFalapoa) {
			this.protocoloFalapoa = protocoloFalapoa;
		}
		
		public String getProtocoloMp() {
			return protocoloMp;
		}
		public void setProtocoloMp(String protocoloMp) {
			this.protocoloMp = protocoloMp;
		}
		
		public String getProtocoloPa() {
			return protocoloPa;
		}
		public void setProtocoloPa(String protocoloPa) {
			this.protocoloPa = protocoloPa;
		}
		
		@Length(max=8000)
		public String getRelatoSolicitante() {
			return relatoSolicitante;
		}
		public void setRelatoSolicitante(String relatoSolicitante) {
			this.relatoSolicitante = relatoSolicitante;
		}

		@Embedded
		public Endereco getEnderecoOcorrencia() {
			if (null == this.enderecoOcorrencia) {
				this.enderecoOcorrencia = new Endereco();
			}
			return this.enderecoOcorrencia;

		}
		public void setEnderecoOcorrencia(Endereco enderecoOcorrencia) {
			this.enderecoOcorrencia = enderecoOcorrencia;
		}

		@Formula("SUBSTRING(relatoSolicitante,0,"+TAMANHO_RESUMO_RELATO+")")
		public String getRelatoSolicitanteResumo() {
			return relatoSolicitanteResumo;
		}

		public void setRelatoSolicitanteResumo(String relatoSolicitanteResumo) {
			this.relatoSolicitanteResumo = relatoSolicitanteResumo;
		}

		@OneToMany(mappedBy="solicitacao", fetch=FetchType.LAZY)
		@OrderBy(value="seqFase" )
		public List<Tramite> getTramiteList() {
			return tramiteList;
		}

		public void setTramiteList(List<Tramite> tramiteList) {
			this.tramiteList = tramiteList;
		}

		public Integer getNrSolicitacaoFalaPoa() {
			return nrSolicitacaoFalaPoa;
		}

		public void setNrSolicitacaoFalaPoa(Integer nrSolicitacaoFalaPoa) {
			this.nrSolicitacaoFalaPoa = nrSolicitacaoFalaPoa;
		}

		public Integer getDtSolicitacaoFalaPoa() {
			return dtSolicitacaoFalaPoa;
		}

		public void setDtSolicitacaoFalaPoa(Integer dtSolicitacaoFalaPoa) {
			this.dtSolicitacaoFalaPoa = dtSolicitacaoFalaPoa;
		}

		@ManyToOne(fetch=FetchType.LAZY)
		public IntervaloImportacao getIntervaloImportacao() {
			return intervaloImportacao;
		}

		public void setIntervaloImportacao(IntervaloImportacao intervaloImportacao) {
			this.intervaloImportacao = intervaloImportacao;
		}
		
		public BigInteger getRefAtualizacao() {
			return refAtualizacao;
		}

		public void setRefAtualizacao(BigInteger refAtualizacao) {
			this.refAtualizacao = refAtualizacao;
		}

		@Temporal(TemporalType.TIMESTAMP)		
		public Calendar getDataSolicitacao() {
			return dataSolicitacao;
		}

		public void setDataSolicitacao(Calendar dataSolicitacao) {
			this.dataSolicitacao = dataSolicitacao;
		}

		@Temporal(TemporalType.TIMESTAMP)		
		public Calendar getDataAbertura() {
			return dataAbertura;
		}

		public void setDataAbertura(Calendar dataAbertura) {
			this.dataAbertura = dataAbertura;
		}

		@Temporal(TemporalType.TIMESTAMP)		
		public Calendar getDataEncerramento() {
			return dataEncerramento;
		}

		public void setDataEncerramento(Calendar dataEncerramento) {
			this.dataEncerramento = dataEncerramento;
		}

		@ManyToOne(fetch=FetchType.LAZY)
		public TipoSolicitante getTipoSolicitante() {
			return tipoSolicitante;
		}

		public void setTipoSolicitante(TipoSolicitante tipo) {
			this.tipoSolicitante = tipo;
		}
		
		@ManyToOne(fetch=FetchType.LAZY)		
		public Solicitacao getSolicitacaoOrigem() {
			return solicitacaoOrigem;
		}

		public void setSolicitacaoOrigem(Solicitacao solicitacaoOrigem) {
			this.solicitacaoOrigem = solicitacaoOrigem;
		}

		public String getProtocoloOrigem() {
			return protocoloOrigem;
		}

		public void setProtocoloOrigem(String protocoloOrigem) {
			this.protocoloOrigem = protocoloOrigem;
		}

		@Transient
		public String getProtocolo() {
			protocolo = getProtocoloFalapoa();
			if(null == protocolo || "" == protocolo){
				protocolo = getProtocoloMp();
			}
			if(null == protocolo || "" == protocolo){
				protocolo = getProtocoloPa();
			}			
			return protocolo;
		}

		public void setProtocolo(String protocolo) {
			this.protocolo = protocolo;
		}

		@UserRoles
		@ManyToMany(targetEntity=Secretaria.class )
		@Cascade({CascadeType.MERGE})
		@OrderBy(" nome asc ")		
		public List<Secretaria> getSecretariaList() {
			return secretariaList;
		}

		public void setSecretariaList(List<Secretaria> secretariaList) {
			this.secretariaList = secretariaList;
		}
		public Boolean getUrgente() {
			return urgente;
		}
		public void setUrgente(Boolean urgente) {
			this.urgente = urgente;
		}
		
		public Boolean getPossuiTramites() {
			return possuiTramites;
		}
		
		public void setPossuiTramites(Boolean possuiTramites) {
			this.possuiTramites = possuiTramites;
		}
		
		@OneToMany(mappedBy="solicitacaoOrigem", fetch=FetchType.LAZY)
		@OrderBy(value="dataSolicitacao" )
		public List<Solicitacao> getSolicitacaoVinculadaList() {
			return solicitacaoVinculadaList;
		}
		public void setSolicitacaoVinculadaList(
				List<Solicitacao> solicitacaoVinculadaList) {
			this.solicitacaoVinculadaList = solicitacaoVinculadaList;
		}
		public Boolean getSigiloso() {
			return sigiloso;
		}
		public void setSigiloso(Boolean sigiloso) {
			this.sigiloso = sigiloso;
		}
}
