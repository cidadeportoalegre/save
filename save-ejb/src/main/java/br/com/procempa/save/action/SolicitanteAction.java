package br.com.procempa.save.action;

import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.core.Events;

import br.com.procempa.interceptors.ActionInterceptor;
import br.com.procempa.save.datamodel.SolicitanteDataModel;
import br.com.procempa.save.entity.Endereco;
import br.com.procempa.save.entity.Solicitacao;
import br.com.procempa.save.entity.Solicitante;
import br.com.procempa.save.enumerator.Uf;
import br.com.procempa.save.iservice.ISolicitacaoService;
import br.com.procempa.save.iservice.ISolicitanteService;
import br.com.procempa.save.service.SolicitacaoService;
import br.com.procempa.save.service.SolicitanteService;

/**
 * @author bridi
 * 
 */
@Name("solicitanteAction")
@Scope(ScopeType.CONVERSATION)
@ActionInterceptor
public class SolicitanteAction extends AbstractSaveAction {
	private static final long serialVersionUID = 1L;
	   
	@Out(required = true)
	@In(required = false, create = true)
	private Solicitante solicitante;

	@In(value = SolicitanteService.SERVICE_NAME, create = true)
	private ISolicitanteService solicitanteService;
	
	@DataModel
	private List<Solicitacao> solicitacaoLista;

	@In(value = SolicitacaoService.SERVICE_NAME, create = true)
	private ISolicitacaoService solicitacaoService;
	
	@In(value = "solicitanteDataModel", create = true)
	private  SolicitanteDataModel solicitanteDataModel;
	
	@In(required=false, create = false)
	@Out(required = false)
	Solicitacao solicitacao;
	
	@Factory("ufLista")
	public Uf[] loadUfLista(){
		return Uf.getValuesUF();
	}
		
	public String filtrar(){
		Events.instance().raiseEvent("solicitanteObserver");
		return "listar";
	}
		
	
	public String selecionar() {
		return "selecionar";
	}

	public String salvarEdicao() {
		if(BooleanUtils.isTrue(solicitanteService.salvar(solicitante))){
			solicitante = solicitanteService.refresh(solicitanteService.evict(solicitante));			
		}
		return null;
	}

	public String cancelarEdicao() {
		solicitante = solicitanteService.refresh(solicitanteService.evict(solicitante));
		return null;
	}
	
	public String salvarNovo(Solicitacao solicitacao){
			solicitante.getSolicitacaoList().add(solicitacao);
			solicitacao.setSolicitante(solicitante);
			if(BooleanUtils.isTrue(solicitanteService.salvar(solicitante))){
				solicitante = solicitanteService.refresh(solicitanteService.evict(solicitante));
				solicitacao = solicitacaoService.refresh(solicitacaoService.evict(solicitacao));
				return "listarSolicitacoes";				
			}

			return "";
	}
	public String criar(Solicitacao solicitacao) {
		solicitante = new Solicitante();
//		solicitante.setNomeSolicitante(solicitacao.getNomeSolicitante());
		
		//solicitante.setTipoSolicitante();
		//solicitante.setBolsaFamilia();
		
		//solicitante.setcpf(solicitacao.getCpf());
		//solicitante.setrg(solicitacao.getRg());
		//solicitante.setcnpj(solicitacao.getCnpj());

		//solicitante.setnis();

		//solicitante.getEndereco().setCodigoCdl(solicitacao.getSoliclogradourocodigo());
		//solicitante.getEndereco().setNumero(solicitacao.getSoliclogradouronumero().toString());
		
		//solicitante = solicitanteService.carregaDadosCdl(solicitante);		
		//solicitante.getEndereco().setComplemento(solicitacao.getSoliclogradourocompl());
		//solicitante.setTelefoneCel(solicitacao.getTelefoneCel());
		//solicitante.setTelefoneCom(solicitacao.getTelefoneCom());
		//solicitante.setTelefoneRes(solicitacao.getTelefoneRes());
		//solicitante.setEmail(solicitacao.getEmail());		
		return "criar";
	}
 
	public String excluir(Solicitante solicitante) {
		setSolicitante(solicitante);
		solicitanteService.excluir( getSolicitante() );
		Events.instance().raiseEvent("solicitanteObserver");
		return "listar";
	}

	public Solicitante getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(Solicitante solicitante) {
		this.solicitante = solicitante;
	}

	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}

	public boolean isEnderecoPoa() {
		return solicitante.getEndereco().isPortoAlegre();
	}
	
	/**
	 * chamado pelo xhtml para setar o endereço fora de porto alegre
	 */
	public void defineEnderecoFora(){
		solicitante.getEndereco().setCodigoCdl(Endereco.ENDERECO_FORA);
	}	
	
	/**
	 * chamado pelo xhtml para setar o endereço de porto alegre
	 */
	public void defineEnderecoPoa(){ 
		solicitante = solicitanteService.carregaDadosCdl(solicitante, solicitante.getEndereco().getCodigoCdl(), solicitante.getEndereco().getNumero());
	}

	@Factory("solicitacaoLista")	
	@Observer("solicitacaoObserver")	
	public List<Solicitacao> getSolicitacaoLista() {		
		return solicitacaoLista = solicitante.getSolicitacaoList();
	}

	public void setSolicitacaoLista(List<Solicitacao> solicitacaoLista) {
		this.solicitacaoLista = solicitacaoLista;
	}

	public void lista() {
	}

	public SolicitanteDataModel getSolicitanteDataModel() {
		return solicitanteDataModel;
	}

	public void setSolicitanteDataModel(SolicitanteDataModel solicitanteDataModel) {
		this.solicitanteDataModel = solicitanteDataModel;
	}
	public String cancelarNovo(){
		return "listar";
	}
	public String cancelarLista(){
		return "editarSolicitacao";
	}

	//metodo usado somente para testes. Em producao, a importacao será chamada automaticamente em horarios pre-definidos
	public void importarAgora() {
		solicitanteService.importarSolicitantes("0 30 8 * * ?");
	}
	
}

