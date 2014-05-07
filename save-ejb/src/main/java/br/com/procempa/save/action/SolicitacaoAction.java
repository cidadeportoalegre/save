package br.com.procempa.save.action;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.core.Events;

import br.com.procempa.interceptors.ActionInterceptor;
import br.com.procempa.save.datamodel.SolicitacaoDataModel;
import br.com.procempa.save.entity.DesfechoSolicitacao;
import br.com.procempa.save.entity.Endereco;
import br.com.procempa.save.entity.IntervaloImportacao;
import br.com.procempa.save.entity.MotivoSolicitacao;
import br.com.procempa.save.entity.OrigemSolicitacao;
import br.com.procempa.save.entity.RespostaSolicitacao;
import br.com.procempa.save.entity.Secretaria;
import br.com.procempa.save.entity.SituacaoSolicitacao;
import br.com.procempa.save.entity.Solicitacao;
import br.com.procempa.save.entity.Solicitante;
import br.com.procempa.save.entity.TipoResposta;
import br.com.procempa.save.entity.TipoSolicitante;
import br.com.procempa.save.entity.TipoTramite;
import br.com.procempa.save.entity.Tramite;
import br.com.procempa.save.iservice.IDesfechoSolicitacaoService;
import br.com.procempa.save.iservice.ILogRespostaService;
import br.com.procempa.save.iservice.ILogSolicitacaoService;
import br.com.procempa.save.iservice.ILogTramiteService;
import br.com.procempa.save.iservice.IMotivoSolicitacaoService;
import br.com.procempa.save.iservice.IOrigemSolicitacaoService;
import br.com.procempa.save.iservice.IRespostaSolicitacaoService;
import br.com.procempa.save.iservice.ISecretariaService;
import br.com.procempa.save.iservice.ISituacaoSolicitacaoService;
import br.com.procempa.save.iservice.ISolicitacaoService;
import br.com.procempa.save.iservice.ISolicitanteService;
import br.com.procempa.save.iservice.ITipoRespostaService;
import br.com.procempa.save.iservice.ITipoSolicitanteService;
import br.com.procempa.save.iservice.ITipoTramiteService;
import br.com.procempa.save.iservice.ITramiteService;
import br.com.procempa.save.service.DesfechoSolicitacaoService;
import br.com.procempa.save.service.LogRespostaService;
import br.com.procempa.save.service.LogSolicitacaoService;
import br.com.procempa.save.service.LogTramiteService;
import br.com.procempa.save.service.MotivoSolicitacaoService;
import br.com.procempa.save.service.OrigemSolicitacaoService;
import br.com.procempa.save.service.RespostaSolicitacaoService;
import br.com.procempa.save.service.SecretariaService;
import br.com.procempa.save.service.SituacaoSolicitacaoService;
import br.com.procempa.save.service.SolicitacaoService;
import br.com.procempa.save.service.SolicitanteService;
import br.com.procempa.save.service.TipoRespostaService;
import br.com.procempa.save.service.TipoSolicitanteService;
import br.com.procempa.save.service.TipoTramiteService;
import br.com.procempa.save.service.TramiteService;

/**
 * @author bridi / bogoni
 * 
 */
@Name("solicitacaoAction")
@Scope(ScopeType.CONVERSATION)
@ActionInterceptor
public class SolicitacaoAction extends AbstractSaveAction {
	private static final long serialVersionUID = 1L;

	private static final String SITUACAO_DESTACADA = "Ativa";
	private static final String ORIGEM_MANUAL = "Inserida manualmente";

	// usado para importar uma solicitacao específica
	private String protocoloFalaPoa;
	
	@Out(required = true)
	@In(required = false, create = true)
	private Tramite tramite;

	@Out(required = true)
	@In(required = false, create = true)
	private Solicitacao solicitacao;
	
	private RespostaSolicitacao respostaSolicitacao;
	
	@In(value = SolicitacaoService.SERVICE_NAME, create = true)
	private ISolicitacaoService solicitacaoService;
	
	@In(value = "solicitacaoDataModel", create = true)
	private SolicitacaoDataModel solicitacaoDataModel;
	
	@In(value = SituacaoSolicitacaoService.SERVICE_NAME, create = true)
	private ISituacaoSolicitacaoService situacaoSolicitacaoService;
	
	@In(value = MotivoSolicitacaoService.SERVICE_NAME, create = true)
	private IMotivoSolicitacaoService motivoSolicitacaoService;
	
	@In(value = TramiteService.SERVICE_NAME, create = true)
	private ITramiteService tramiteService;
	
	@In(value = OrigemSolicitacaoService.SERVICE_NAME, create = true)
	private IOrigemSolicitacaoService origemSolicitacaoService;
	
	@In(value = SolicitanteService.SERVICE_NAME, create = true)
	private ISolicitanteService solicitanteService;
	
	@In(value = LogSolicitacaoService.SERVICE_NAME, create = true)
	private ILogSolicitacaoService logSolicitacaoService;
	
	@In(value = LogTramiteService.SERVICE_NAME, create = true)
	private ILogTramiteService logTramiteService;
	
	@In(value = LogRespostaService.SERVICE_NAME, create = true)
	private ILogRespostaService logRespostaService;
	
	@In(value = TipoTramiteService.SERVICE_NAME, create = true)
	private ITipoTramiteService tipoTramiteService;
	
	@In(value = TipoRespostaService.SERVICE_NAME, create = true)
	private ITipoRespostaService tipoRespostaService;
	
	@In(value = DesfechoSolicitacaoService.SERVICE_NAME, create = true)
	private IDesfechoSolicitacaoService desfechoSolicitacaoService;
	
	@In(value = TipoSolicitanteService.SERVICE_NAME, create = true)
	private ITipoSolicitanteService tipoSolicitanteService;
	
	@In(value = SecretariaService.SERVICE_NAME, create = true)
	private ISecretariaService secretariaService;
	
	@In(value = RespostaSolicitacaoService.SERVICE_NAME, create = true)
	private IRespostaSolicitacaoService respostaSolicitacaoService;
	
	@DataModel
	private List<Tramite> tramiteLista;
	
	private List<Solicitacao> solicitacaoVinculadaList;
	
	private List<IntervaloImportacao> intervaloLista = null;
	
	private List<SituacaoSolicitacao> situacaoSolicitacaoList = null;
	private List<MotivoSolicitacao> motivoSolicitacaoList = null;
	
	private List<OrigemSolicitacao> origemSolicitacaoList = null;
	
	private List<Solicitante> solicitanteList = null;
	
	private List<TipoTramite> tipoTramiteList = null;
	private List<TipoResposta> tipoRespostaList = null;
	
	private List<DesfechoSolicitacao> desfechoSolicitacaoList = null;
	
	private List<TipoSolicitante> tipoSolicitanteList = null;
	
	private List<Secretaria> secretariaList = null;
	
	private  List<Solicitacao> reportSolicitacaoData = null;
	
	private String origemNavegacao;
	
	@RequestParameter
	public void setOrigem(String origem) {
		if (origem!=null){
			this.origemNavegacao = origem;
		}
	}
	
	public String salvar() {
		if (BooleanUtils.isTrue(solicitacaoService.salvar(solicitacao))) {
			solicitacao = solicitacaoService.refresh(solicitacaoService
					.evict(solicitacao));
			return "listar";
		}
		return "";
	}
	
	public String salvarNova() {
		if (BooleanUtils.isTrue(solicitacaoService.salvar(solicitacao))) {
			solicitacao = solicitacaoService.refresh(solicitacaoService
					.evict(solicitacao));
			return "listar";
		}
		return "";
	}
	
	public void lista() {
	}
	
	// Pressionado o botão de busca da busca avançada. Usar o intervalo de datas definidas no modal
	public void busca() {
		if (solicitacaoDataModel.getDataDe()!= null || solicitacaoDataModel.getDataA()!=null) {
			solicitacaoDataModel.setData(null);
		}
	}
	
	// Pressionado o botão da busca padrão. Neste caso usa a data da toolbar se informada
	public void buscaPadrao() {
		if (StringUtils.isNotBlank(solicitacaoDataModel.getProtocoloSolicitacao())) {
			
			//Se informado critério de número de protocolo este vale mais que os demais critérios
			solicitacaoDataModel.setAtivas(false);
			solicitacaoDataModel.setUrgente(false);
			solicitacaoDataModel.setVinculadas(true);
			//
			solicitacaoDataModel.setData(null);
			solicitacaoDataModel.setDataDe(null);
			solicitacaoDataModel.setDataA(null);
			solicitacaoDataModel.setOrigemSolicitacao(null);
			solicitacaoDataModel.setSituacaoSolicitacao(null);
			solicitacaoDataModel.setMotivoSolicitacao(null);
			solicitacaoDataModel.setCep(null);
			//
		} else {
			if (solicitacaoDataModel.getData()!= null) {
				solicitacaoDataModel.setDataDe(solicitacaoDataModel.getData());
				solicitacaoDataModel.setDataA(solicitacaoDataModel.getData());
			}
		}
	}
	
	public String criar() { 
		if (null == this.solicitacao
				|| null == this.solicitacao.getSituacaoSolicitacao()) {
			this.solicitacao = new Solicitacao();
			this.solicitacao.setSituacaoSolicitacao(situacaoSolicitacaoService
					.findByDescricao(SITUACAO_DESTACADA));
			this.solicitacao.setOrigemSolicitacao(origemSolicitacaoService
					.findByDescricao(ORIGEM_MANUAL));
			this.solicitacao.setDataSolicitacao(Calendar.getInstance());
		}
		return "criar";
	}
	
	public String excluir() {
		solicitacaoService.excluir(solicitacao);
		return "listar";
	}
	
	public Solicitacao getSolicitacao() {
		return solicitacao;
	}
	
	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}
	
	public List<SituacaoSolicitacao> getSituacaoSolicitacaoList() {
		situacaoSolicitacaoList = situacaoSolicitacaoService.findAll();
		return situacaoSolicitacaoList;
	}
	
	public void setSituacaoSolicitacaoList(
			List<SituacaoSolicitacao> situacaoSolicitacaoList) {
		this.situacaoSolicitacaoList = situacaoSolicitacaoList;
	}
	
	public List<Solicitante> getSolicitanteList() {
		solicitanteList = solicitanteService.findAll();
		return solicitanteList;
	}
	
	public void setSolicitanteList(List<Solicitante> solicitanteList) {
		this.solicitanteList = solicitanteList;
	}
	
	public String selecionarSolicitante() {
		return "selecionarSolicitante";
	}
	
	public String salvarSolicitacaoSelecionarSolicitante() {
		if (salvarNova() == "listar")
			return "selecionarSolicitante";
		else
			return "";
	}
	
	public SolicitacaoDataModel getSolicitacaoDataModel() {
		return solicitacaoDataModel;
	}
	
	public void setSolicitacaoDataModel(
			SolicitacaoDataModel solicitacaoDataModel) {
		this.solicitacaoDataModel = solicitacaoDataModel;
	}
	
	public void importarAgora() {
		solicitacaoService.importarSolicitacoes("0 30 8 * * ?");
	}
	
	public void importarTiposTramite() {
		tipoTramiteService.importarTiposTramite();
	}
	
	public void importarTiposResposta() {
		tipoRespostaService.importarTiposResposta();
	}

	public void importarMotivosSolicitacao() {
		motivoSolicitacaoService.importarMotivosSolicitacao();
	}

	public String getSituacaoDestacada() {
		return SITUACAO_DESTACADA;
	}
	
	public String getOrigemManual() {
		return ORIGEM_MANUAL;
	}
	
	public List<MotivoSolicitacao> getMotivoSolicitacaoList() {
		motivoSolicitacaoList = motivoSolicitacaoService.findAll();
		return motivoSolicitacaoList;
	}
	
	public void setMotivoSolicitacaoList(
			List<MotivoSolicitacao> motivoSolicitacaoList) {
		this.motivoSolicitacaoList = motivoSolicitacaoList;
	}
	
	public List<OrigemSolicitacao> getOrigemSolicitacaoList() {
		origemSolicitacaoList = origemSolicitacaoService.findAll();
		return origemSolicitacaoList;
	}
	
	public void setOrigemSolicitacaoList(
			List<OrigemSolicitacao> origemSolicitacaoList) {
		this.origemSolicitacaoList = origemSolicitacaoList;
	}
	
	public String editar(Solicitacao solicitacao) {
		solicitacao = solicitacaoService.refresh(solicitacaoService
				.evict(solicitacao));
		this.solicitacao = solicitacao;
		return super.editar();
	}
	
	@Factory("tramiteLista")
	@Observer("tramiteObserver")
	public List<Tramite> getTramiteLista() {
		return tramiteLista = solicitacao.getTramiteList();
	}
	
	public void setTramiteLista(List<Tramite> tramiteLista) {
		this.tramiteLista = tramiteLista;
	}
	
	public List<IntervaloImportacao> getIntervaloLista() {
		intervaloLista = solicitacaoService.getIntervaloLista();
		return intervaloLista;
	}
	
	public void setIntervaloLista(List<IntervaloImportacao> intervaloLista) {
		this.intervaloLista = intervaloLista;
	}
	
	public String criarTramite() {
		this.tramite = new Tramite();
		this.tramite.setEncerrarProcesso(Boolean.FALSE);
		this.tramite.setSolicitacao(solicitacao);
		this.respostaSolicitacao = new RespostaSolicitacao();
		this.respostaSolicitacao.setSeqResp(0);
		
		this.respostaSolicitacao.setTramite(tramite);
		this.tramite.getRespostaList().add(respostaSolicitacao);
		
		return "criar";
	}
	
	public String salvarTramite() {
		
		tramiteService.salvar(tramite);
		solicitacaoService.evict(solicitacao);
		solicitacao = solicitacaoService.refresh(solicitacao);
		Events.instance().raiseEvent("tramiteObserver");
		return "";
	}
	
	public String cancelarTramite() {
		this.solicitacao = solicitacaoService.refresh(solicitacaoService.evict(this.solicitacao));
		Events.instance().raiseEvent("tramiteObserver");
		return null;
	}
	
	public List<TipoTramite> getTipoTramiteList() {
		tipoTramiteList = tipoTramiteService.findAll();
		return tipoTramiteList;
	}
	
	public void setTipoTramiteList(List<TipoTramite> tipoTramiteList) {
		this.tipoTramiteList = tipoTramiteList;
	}
	
	public List<TipoResposta> getTipoRespostaList() {
		tipoRespostaList = tipoRespostaService.findAll();
		return tipoRespostaList;
	}
	
	public void setTipoRespostaList(List<TipoResposta> tipoRespostaList) {
		this.tipoRespostaList = tipoRespostaList;
	}
	
	public List<DesfechoSolicitacao> getDesfechoSolicitacaoList() {
		desfechoSolicitacaoList = desfechoSolicitacaoService.findAll();
		return desfechoSolicitacaoList;
	}
	
	public void setDesfechoSolicitacaoList(
			List<DesfechoSolicitacao> desfechoSolicitacaoList) {
		this.desfechoSolicitacaoList = desfechoSolicitacaoList;
	}
	
	public List<TipoSolicitante> getTipoSolicitanteList() {
		tipoSolicitanteList = tipoSolicitanteService.findAll();
		return tipoSolicitanteList;
	}
	
	public void setTipoSolicitanteList(List<TipoSolicitante> tipoSolicitanteList) {
		this.tipoSolicitanteList = tipoSolicitanteList;
	}
	
	public String visualizarTramite(Tramite tramite) {
		this.tramite = tramite;
		return "";
	}
	
	public String escolherResposta() {
		this.respostaSolicitacao.setDescricaoResposta(this.respostaSolicitacao
				.getTipoResposta().getAssunto());
		return "";
	}
	
	public String importarSolicitacao() {
		if (BooleanUtils.isTrue(solicitacaoService
				.importarSolicitacao(getProtocoloFalaPoa())))
			return "listar";
		else
			return "";
	}
	
	public String getProtocoloFalaPoa() {
		return protocoloFalaPoa;
	}
	
	public void setProtocoloFalaPoa(String protocoloFalaPoa) {
		this.protocoloFalaPoa = protocoloFalaPoa;
	}
	
	public RespostaSolicitacao getRespostaSolicitacao() {
		return respostaSolicitacao;
	}
	
	public void setRespostaSolicitacao(RespostaSolicitacao respostaSolicitacao) {
		this.respostaSolicitacao = respostaSolicitacao;
	}
	
	public List<Secretaria> getSecretariaList() {
		secretariaList = secretariaService.findAll();
		secretariaList.removeAll(this.solicitacao.getSecretariaList());
		return secretariaList;
	}
	
	public void setSecretariaList(List<Secretaria> secretariaList) {
		this.secretariaList = secretariaList;
	}
	
	public boolean isEnderecoPoa() {
		return solicitacao.getEnderecoOcorrencia().isPortoAlegre();
	}
	
	/**
	 * chamado pelo xhtml para setar o endereço fora de porto alegre
	 */
	public void defineEnderecoFora() {
		solicitacao.getEnderecoOcorrencia()
				.setCodigoCdl(Endereco.ENDERECO_FORA);
	}
	
	/**
	 * chamado pelo xhtml para setar o endereço de porto alegre
	 */
	public void defineEnderecoPoa() {
		solicitacao = solicitacaoService.carregaDadosCdl(solicitacao,
				solicitacao.getEnderecoOcorrencia().getCodigoCdl(), solicitacao
						.getEnderecoOcorrencia().getNumero());
	}
	
	public String selecionarSolicitante(Solicitante solicitante) {
		solicitante = solicitanteService.refresh(solicitanteService
				.evict(solicitante));
		solicitacao.setSolicitante(solicitante);
		solicitacaoService.salvar(solicitacao);
		solicitacao = solicitacaoService.refresh(solicitacaoService
				.evict(solicitacao));
		return "editarSolicitacao";
	}
	
	@Factory("solicitacaoVinculadaList")
	@Observer("solicitacaoVinculadaObserver")
	public List<Solicitacao> getSolicitacaoVinculadaList() {
		this.solicitacaoVinculadaList = solicitacao
				.getSolicitacaoVinculadaList();
		if (null != solicitacao.getSolicitacaoOrigem()
				&& !this.solicitacaoVinculadaList.contains(solicitacao
						.getSolicitacaoOrigem()))
			this.solicitacaoVinculadaList.add(solicitacao
					.getSolicitacaoOrigem());
		return this.solicitacaoVinculadaList;
	}
	
	public String getReportURL() {
		return "reports/solicitacaoRelatorio.seam";
	}
	
	public List<Solicitacao> getReportSolicitacaoData() {
		return reportSolicitacaoData;
	}
	
	public void reportSolicitacao() {
		this.reportSolicitacaoData = this.solicitacaoDataModel.getList(0, 0);
	}
	
	@Override
	public String cancelar() {
		if (this.origemNavegacao != null) {
			String temp = origemNavegacao;
			origemNavegacao = "";
			return temp;
		}
		return super.cancelar();
	}
	
	public String goSolicitacaoOrigem() {
		if (this.solicitacao.getSolicitacaoOrigem()!=null){
			this.solicitacao = solicitacaoService.refresh(solicitacaoService.evict(this.solicitacao.getSolicitacaoOrigem()));
			Events.instance().raiseEvent("tramiteObserver");
			Events.instance().raiseEvent("solicitacaoVinculadaObserver");
			
		}
		return null;
	}

	public String goSolicitacaoVinculada(Solicitacao solicitacaoVinculada) {
		this.solicitacao = solicitacaoService.refresh(solicitacaoService.evict(solicitacaoVinculada));
		Events.instance().raiseEvent("tramiteObserver");
		Events.instance().raiseEvent("solicitacaoVinculadaObserver");
		return null;
	}
}