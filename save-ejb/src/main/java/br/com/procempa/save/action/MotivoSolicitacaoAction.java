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
import br.com.procempa.save.entity.MotivoSolicitacao;
import br.com.procempa.save.iservice.IMotivoSolicitacaoService;
import br.com.procempa.save.service.MotivoSolicitacaoService;

/**
 * @author bridi
 * 
 */
@Name("motivoSolicitacaoAction")
@Scope(ScopeType.CONVERSATION)
@ActionInterceptor
public class MotivoSolicitacaoAction extends AbstractSaveAction {
	private static final long serialVersionUID = 1L;
	   
	@Out(required = true)
	@In(required = false, create = true)
	private MotivoSolicitacao motivoSolicitacao;

	@In(value = MotivoSolicitacaoService.SERVICE_NAME, create = true)
	private IMotivoSolicitacaoService motivoSolicitacaoService;
	
	@DataModel
	private List<MotivoSolicitacao> motivoSolicitacaoLista;

	@Factory("motivoSolicitacaoLista")
	@Observer("motivoSolicitacaoObserver")
	public void loadMotivoSolicitacaoLista() {
		this.setMotivoSolicitacaoLista(motivoSolicitacaoService.findAll());			
	}
		
	public String filtrar(){
		Events.instance().raiseEvent("motivoSolicitacaoObserver");
		return "listar";
	}
		
	
	public String editar(MotivoSolicitacao motivoSolicitacao) {
		setMotivoSolicitacao(motivoSolicitacao);
		return super.editar();
	}
	
	public String salvar() {
		Long id = motivoSolicitacao.getId();
		if(BooleanUtils.isTrue(motivoSolicitacaoService.salvar(motivoSolicitacao))){
			motivoSolicitacao = motivoSolicitacaoService.refresh(motivoSolicitacaoService.evict(motivoSolicitacao));
			Events.instance().raiseEvent("motivoSolicitacaoObserver");
			if(null == id){
				criar();
			}			
		}
		return "listar";
	}

	public String criar() {
		this.motivoSolicitacao = new MotivoSolicitacao();		
		return "criar";
	}
 
	public String excluir(MotivoSolicitacao motivoSolicitacao) {
		setMotivoSolicitacao(motivoSolicitacao);
		motivoSolicitacaoService.excluir( getMotivoSolicitacao() );
		Events.instance().raiseEvent("motivoSolicitacaoObserver");
		return "listar";
	}

    public String cancelar() {  
        if (null != motivoSolicitacao.getId()) {  
             motivoSolicitacaoService.refresh(motivoSolicitacao);    
        }
	   Events.instance().raiseEvent("motivoSolicitacaoObserver");        
       return "listar";  
   }  
    
	public MotivoSolicitacao getMotivoSolicitacao() {
		return motivoSolicitacao;
	}

	public void setMotivoSolicitacao(MotivoSolicitacao motivoSolicitacao) {
		this.motivoSolicitacao = motivoSolicitacao;
	}

	public List<MotivoSolicitacao> getMotivoSolicitacaoLista() {
		return motivoSolicitacaoLista;
	}

	public void setMotivoSolicitacaoLista(List<MotivoSolicitacao> motivoSolicitacaoLista) {
		this.motivoSolicitacaoLista = motivoSolicitacaoLista;
	}
}

