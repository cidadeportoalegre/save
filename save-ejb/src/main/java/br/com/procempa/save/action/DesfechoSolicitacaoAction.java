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
import br.com.procempa.save.entity.DesfechoSolicitacao;
import br.com.procempa.save.iservice.IDesfechoSolicitacaoService;
import br.com.procempa.save.service.DesfechoSolicitacaoService;

/**
 * @author bridi
 * 
 */
@Name("desfechoSolicitacaoAction")
@Scope(ScopeType.CONVERSATION)
@ActionInterceptor
public class DesfechoSolicitacaoAction extends AbstractSaveAction {
	private static final long serialVersionUID = 1L;
	   
	@Out(required = true)
	@In(required = false, create = true)
	private DesfechoSolicitacao desfechoSolicitacao;

	@In(value = DesfechoSolicitacaoService.SERVICE_NAME, create = true)
	private IDesfechoSolicitacaoService desfechoSolicitacaoService;
	
	@DataModel
	private List<DesfechoSolicitacao> desfechoSolicitacaoLista;

	@Factory("desfechoSolicitacaoLista")
	@Observer("desfechoSolicitacaoObserver")
	public void loadDesfechoSolicitacaoLista() {
		this.setDesfechoSolicitacaoLista(desfechoSolicitacaoService.findAll());			
	}
		
	public String filtrar(){
		Events.instance().raiseEvent("desfechoSolicitacaoObserver");
		return "listar";
	}
		
	
	public String editar(DesfechoSolicitacao desfechoSolicitacao) {
		setDesfechoSolicitacao(desfechoSolicitacao);
		return super.editar();
	}
	
	public String salvar() {
		Long id = desfechoSolicitacao.getId();
		if(BooleanUtils.isTrue(desfechoSolicitacaoService.salvar(desfechoSolicitacao))){
			desfechoSolicitacao = desfechoSolicitacaoService.refresh(desfechoSolicitacaoService.evict(desfechoSolicitacao));
			Events.instance().raiseEvent("desfechoSolicitacaoObserver");
			if(null == id){
				criar();
			}			
		}
		return "listar";
	}

	public String criar() {
		this.desfechoSolicitacao = new DesfechoSolicitacao();		
		return "criar";
	}
 
	public String excluir(DesfechoSolicitacao desfechoSolicitacao) {
		setDesfechoSolicitacao(desfechoSolicitacao);
		desfechoSolicitacaoService.excluir( getDesfechoSolicitacao() );
		Events.instance().raiseEvent("desfechoSolicitacaoObserver");
		return "listar";
	}
	
    public String cancelar() {  
        if (null != desfechoSolicitacao.getId()) {  
             desfechoSolicitacaoService.refresh(desfechoSolicitacao);    
        }
	   Events.instance().raiseEvent("desfechoSolicitacaoObserver");        
       return "listar";  
   }
    
	public DesfechoSolicitacao getDesfechoSolicitacao() {
		return desfechoSolicitacao;
	}

	public void setDesfechoSolicitacao(DesfechoSolicitacao desfechoSolicitacao) {
		this.desfechoSolicitacao = desfechoSolicitacao;
	}

	public List<DesfechoSolicitacao> getDesfechoSolicitacaoLista() {
		return desfechoSolicitacaoLista;
	}

	public void setDesfechoSolicitacaoLista(List<DesfechoSolicitacao> desfechoSolicitacaoLista) {
		this.desfechoSolicitacaoLista = desfechoSolicitacaoLista;
	}
}

