package br.com.procempa.save.datamodel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.security.Identity;

import br.com.procempa.arquitetura.datamodel.AbstractDataModel;
import br.com.procempa.save.entity.IntervaloImportacao;
import br.com.procempa.save.entity.MotivoSolicitacao;
import br.com.procempa.save.entity.OrigemSolicitacao;
import br.com.procempa.save.entity.SituacaoSolicitacao;
import br.com.procempa.save.entity.Solicitacao;
import br.com.procempa.save.iservice.ISituacaoSolicitacaoService;
import br.com.procempa.save.iservice.ISolicitacaoService;
import br.com.procempa.save.service.SituacaoSolicitacaoService;
import br.com.procempa.save.service.SolicitacaoService;

/**
 * @author bridi
 * 
 */
@Name("solicitacaoDataModel")
@Scope(ScopeType.CONVERSATION)
public class SolicitacaoDataModel extends AbstractDataModel<Solicitacao, Long> {

	private static final long serialVersionUID = 1L;

	private String protocoloSolicitacao;
	private Date data = null;
	private Date dataDe = null;
	private Date dataA = null;
	private IntervaloImportacao intervalo;
	private SituacaoSolicitacao situacaoSolicitacaoAtiva;
	private Boolean ativas = true;
	private Boolean vinculadas = false;

	private OrigemSolicitacao origemSolicitacao = null;
	private SituacaoSolicitacao situacaoSolicitacao = null;
	private MotivoSolicitacao motivoSolicitacao = null;
	private Boolean urgente = false;
	private String cep = null;

	private Identity identity = Identity.instance();

	@In(value = SolicitacaoService.SERVICE_NAME, create = true)
	private ISolicitacaoService solicitacaoService;

	@In(value = SituacaoSolicitacaoService.SERVICE_NAME, create = true)
	private ISituacaoSolicitacaoService situacaoSolicitacaoService;

	@Override
	public Long getCount() {
		acertaDatas();
		return this.rowCount = solicitacaoService.getCountAll(getData(),
				getIntervalo(), getDataDe(), getDataA(),
				getSituacaoSolicitacaoAtiva(), getVinculadas(), getProtocoloSolicitacao(),
				identity, getOrigemSolicitacao(), getSituacaoSolicitacao(),
				getMotivoSolicitacao(), getUrgente(), getCep());
	}

	@Override
	public Solicitacao findById(Long id) {
		return solicitacaoService.findById(id);
	}

	@Override
	public List<Solicitacao> getList(Integer firstRow, Integer maxResults) {
		acertaDatas();
		return this.listRow = solicitacaoService.findAll(firstRow, maxResults,
				getData(), getIntervalo(), getDataDe(), getDataA(),
				getSituacaoSolicitacaoAtiva(), getVinculadas(), getProtocoloSolicitacao(),
				identity, getOrigemSolicitacao(), getSituacaoSolicitacao(),
				getMotivoSolicitacao(), getUrgente(), getCep());
	}

	private void acertaDatas() {
		if (this.getData() != null) {
			this.setDataDe(null);
			this.setDataA(null);
		}
	}

	public String getProtocoloSolicitacao() {
		return protocoloSolicitacao;
	}

	public void setProtocoloSolicitacao(String protocoloSolicitacao) {
		this.protocoloSolicitacao = protocoloSolicitacao;
	}

	public Date getDataDe() {
		return dataDe;
	}

	public void setDataDe(Date dataDe) {
		this.dataDe = dataDe;
	}

	public Date getDataA() {
		return dataA;
	}

	public void setDataA(Date dataA) {
		this.dataA = dataA;
	}

	public IntervaloImportacao getIntervalo() {
		return intervalo;
	}

	public void setIntervalo(IntervaloImportacao intervalo) {
		this.intervalo = intervalo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public SituacaoSolicitacao getSituacaoSolicitacaoAtiva() {
		situacaoSolicitacaoAtiva = ativas ? situacaoSolicitacaoService
				.findByDescricao(SolicitacaoService.ATIVA_DESC) : null;
		return situacaoSolicitacaoAtiva;
	}

	public void setSituacaoSolicitacaoAtiva(
			SituacaoSolicitacao situacaoSolicitacao) {
		this.situacaoSolicitacaoAtiva = situacaoSolicitacao;
	}

	public Boolean getAtivas() {
		return ativas;
	}

	public void setAtivas(Boolean ativas) {
		this.ativas = ativas;
	}

	public Boolean getVinculadas() {
		return vinculadas;
	}

	public void setVinculadas(Boolean vinculadas) {
		this.vinculadas = vinculadas;
	}

	public OrigemSolicitacao getOrigemSolicitacao() {
		return origemSolicitacao;
	}

	public void setOrigemSolicitacao(OrigemSolicitacao origemSolicitacao) {
		this.origemSolicitacao = origemSolicitacao;
	}

	public SituacaoSolicitacao getSituacaoSolicitacao() {
		return situacaoSolicitacao;
	}

	public void setSituacaoSolicitacao(SituacaoSolicitacao situacaoSolicitacao) {
		this.situacaoSolicitacao = situacaoSolicitacao;
	}

	public MotivoSolicitacao getMotivoSolicitacao() {
		return motivoSolicitacao;
	}

	public void setMotivoSolicitacao(MotivoSolicitacao motivoSolicitacao) {
		this.motivoSolicitacao = motivoSolicitacao;
	}

	public Boolean getUrgente() {
		return urgente;
	}

	public void setUrgente(Boolean urgente) {
		this.urgente = urgente;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getFiltrosDescricao() {
		StringBuffer buff = new StringBuffer("");
		String separador = "     ";
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		if (this.getData()!=null) {
			buff.append("Data Importação:"+df.format(this.getData())+separador);
		} else {
			if (this.getDataDe() != null || this.getDataA() != null) {
				buff.append("Data Importação:");
				if (this.getDataDe() != null) {
					buff.append(df.format(this.dataDe));
				}
				if (this.getDataA() != null) {
					buff.append(" até " + df.format(this.dataA));
				} else {
					buff.append(" até " + df.format(Calendar.getInstance().getTime()));
				}
				buff.append(separador);
			}
		}

		if (this.protocoloSolicitacao != null) {
			buff.append("Protocolo:" + this.protocoloSolicitacao + separador);
		}

		buff.append("Ativas:" + (this.ativas ? "Sim" : "Não") + separador);

		buff.append("Vinculadas:" + (this.vinculadas ? "Sim" : "Não") + separador);

		buff.append("Urgentes:" + (this.urgente ? "Sim" : "Não") + separador);

		if (this.origemSolicitacao != null) {
			buff.append("Origem:" + this.origemSolicitacao.getDescricao()
					+ separador);
		}

		if (this.situacaoSolicitacao != null) {
			buff.append("Situação:" + this.situacaoSolicitacao.getDescricao()
					+ separador);
		}

		if (this.motivoSolicitacao != null) {
			buff.append("Motivo:" + this.motivoSolicitacao.getDescricao()
					+ separador);
		}

		if (this.cep != null) {
			buff.append("CEP:" + this.cep + separador);
		}

		return buff.toString();
	}
}
