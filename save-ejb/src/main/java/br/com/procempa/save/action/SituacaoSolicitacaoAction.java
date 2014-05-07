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
import br.com.procempa.save.entity.SituacaoSolicitacao;
import br.com.procempa.save.iservice.ISituacaoSolicitacaoService;
import br.com.procempa.save.service.SituacaoSolicitacaoService;

/**
 * @author bridi
 * 
 */
@Name("situacaoSolicitacaoAction")
@Scope(ScopeType.CONVERSATION)
@ActionInterceptor
public class SituacaoSolicitacaoAction extends AbstractSaveAction {
	private static final long serialVersionUID = 1L;
	   
	@Out(required = true)
	@In(required = false, create = true)
	private SituacaoSolicitacao situacaoSolicitacao;

	@In(value = SituacaoSolicitacaoService.SERVICE_NAME, create = true)
	private ISituacaoSolicitacaoService situacaoSolicitacaoService;
	
	@DataModel
	private List<SituacaoSolicitacao> situacaoSolicitacaoLista;

	@Factory("situacaoSolicitacaoLista")
	@Observer("situacaoSolicitacaoObserver")
	public void loadSituacaoSolicitacaoLista() {
		this.setSituacaoSolicitacaoLista(situacaoSolicitacaoService.findAll());			
	}
		
	public String filtrar(){
		Events.instance().raiseEvent("situacaoSolicitacaoObserver");
		return "listar";
	}
		
	
	public String editar(SituacaoSolicitacao situacaoSolicitacao) {
		setSituacaoSolicitacao(situacaoSolicitacao);
		return super.editar();
	}
	
	public String salvar() {
		Long id = situacaoSolicitacao.getId();
		if(BooleanUtils.isTrue(situacaoSolicitacaoService.salvar(situacaoSolicitacao))){
			situacaoSolicitacao = situacaoSolicitacaoService.refresh(situacaoSolicitacaoService.evict(situacaoSolicitacao));
			Events.instance().raiseEvent("situacaoSolicitacaoObserver");
			if(null == id){
				criar();
			}			
		}
		return "listar";
	}

	public String criar() {
		this.situacaoSolicitacao = new SituacaoSolicitacao();		
		return "criar";
	}
 
	public String excluir(SituacaoSolicitacao situacaoSolicitacao) {
		setSituacaoSolicitacao(situacaoSolicitacao);
		situacaoSolicitacaoService.excluir( getSituacaoSolicitacao() );
		Events.instance().raiseEvent("situacaoSolicitacaoObserver");
		return "listar";
	}
	
    public String cancelar() {  
        if (null != situacaoSolicitacao.getId()) {  
             situacaoSolicitacaoService.refresh(situacaoSolicitacao);    
        }
	   Events.instance().raiseEvent("situacaoSolicitacaoObserver");        
       return "listar";  
   }  

	public SituacaoSolicitacao getSituacaoSolicitacao() {
		return situacaoSolicitacao;
	}

	public void setSituacaoSolicitacao(SituacaoSolicitacao situacaoSolicitacao) {
		this.situacaoSolicitacao = situacaoSolicitacao;
	}

	public List<SituacaoSolicitacao> getSituacaoSolicitacaoLista() {
		return situacaoSolicitacaoLista;
	}

	public void setSituacaoSolicitacaoLista(List<SituacaoSolicitacao> situacaoSolicitacaoLista) {
		this.situacaoSolicitacaoLista = situacaoSolicitacaoLista;
	}
}

