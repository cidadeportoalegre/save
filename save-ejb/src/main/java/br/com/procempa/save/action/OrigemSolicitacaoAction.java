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
import br.com.procempa.save.entity.OrigemSolicitacao;
import br.com.procempa.save.iservice.IOrigemSolicitacaoService;
import br.com.procempa.save.service.OrigemSolicitacaoService;

/**
 * @author bridi
 * 
 */
@Name("origemSolicitacaoAction")
@Scope(ScopeType.CONVERSATION)
@ActionInterceptor
public class OrigemSolicitacaoAction extends AbstractSaveAction {
	private static final long serialVersionUID = 1L;
	   
	@Out(required = true)
	@In(required = false, create = true)
	private OrigemSolicitacao origemSolicitacao;

	@In(value = OrigemSolicitacaoService.SERVICE_NAME, create = true)
	private IOrigemSolicitacaoService origemSolicitacaoService;
	
	@DataModel
	private List<OrigemSolicitacao> origemSolicitacaoLista;

	@Factory("origemSolicitacaoLista")
	@Observer("origemSolicitacaoObserver")
	public void loadOrigemSolicitacaoLista() {
		this.setOrigemSolicitacaoLista(origemSolicitacaoService.findAll());			
	}
		
	public String filtrar(){
		Events.instance().raiseEvent("origemSolicitacaoObserver");
		return "listar";
	}
		
	
	public String editar(OrigemSolicitacao origemSolicitacao) {
		setOrigemSolicitacao(origemSolicitacao);
		return super.editar();
	}
	
	public String salvar() {
		Long id = origemSolicitacao.getId();
		if(BooleanUtils.isTrue(origemSolicitacaoService.salvar(origemSolicitacao))){
			origemSolicitacao = origemSolicitacaoService.refresh(origemSolicitacaoService.evict(origemSolicitacao));
			Events.instance().raiseEvent("origemSolicitacaoObserver");
			if(null == id){
				criar();
			}			
		}
		return "listar";
	}

	public String criar() {
		this.origemSolicitacao = new OrigemSolicitacao();		
		return "criar";
	}
 
	public String excluir(OrigemSolicitacao origemSolicitacao) {
		setOrigemSolicitacao(origemSolicitacao);
		origemSolicitacaoService.excluir( getOrigemSolicitacao() );
		Events.instance().raiseEvent("origemSolicitacaoObserver");
		return "listar";
	}

    public String cancelar() {  
        if (null != origemSolicitacao.getId()) {  
             origemSolicitacaoService.refresh(origemSolicitacao);    
        }
	   Events.instance().raiseEvent("origemSolicitacaoObserver");        
       return "listar";  
   }  
    
	public OrigemSolicitacao getOrigemSolicitacao() {
		return origemSolicitacao;
	}

	public void setOrigemSolicitacao(OrigemSolicitacao origemSolicitacao) {
		this.origemSolicitacao = origemSolicitacao;
	}

	public List<OrigemSolicitacao> getOrigemSolicitacaoLista() {
		return origemSolicitacaoLista;
	}

	public void setOrigemSolicitacaoLista(List<OrigemSolicitacao> origemSolicitacaoLista) {
		this.origemSolicitacaoLista = origemSolicitacaoLista;
	}
}

