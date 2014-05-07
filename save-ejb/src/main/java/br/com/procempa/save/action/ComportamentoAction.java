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
import br.com.procempa.save.entity.Comportamento;
import br.com.procempa.save.iservice.IComportamentoService;
import br.com.procempa.save.service.ComportamentoService;

/**
 * @author bridi / leandro.bogoni
 * 
 */
@Name("comportamentoAction")
@Scope(ScopeType.CONVERSATION)
@ActionInterceptor
public class ComportamentoAction extends AbstractSaveAction {
	private static final long serialVersionUID = 1L;
	   
	@Out(required = true)
	@In(required = false, create = true)
	private Comportamento comportamento;

	@In(value = ComportamentoService.SERVICE_NAME, create = true)
	private IComportamentoService comportamentoService;
	
	@DataModel
	private List<Comportamento> comportamentoLista;

	@Factory("comportamentoLista")
	@Observer("comportamentoObserver")
	public void loadComportamentoLista() {
		this.setComportamentoLista(comportamentoService.findAll());			
	}
		
	public String filtrar(){
		Events.instance().raiseEvent("comportamentoObserver");
		return "listar";
	}
		
	public String editar(Comportamento comportamento) {
		setComportamento(comportamento);
		return super.editar();
	}
	
	public String salvar() {
		Long id = comportamento.getId();
		if(BooleanUtils.isTrue(comportamentoService.salvar(comportamento))){
			comportamento = comportamentoService.refresh(comportamentoService.evict(comportamento));
			Events.instance().raiseEvent("comportamentoObserver");
			if(null == id){
				criar();
			}			
		}
		return "listar";
	}

	public String criar() {
		this.comportamento = new Comportamento();		
		return "criar";
	}
 
	public String excluir(Comportamento comportamento) {
		setComportamento(comportamento);
		comportamentoService.excluir( getComportamento() );
		Events.instance().raiseEvent("comportamentoObserver");
		return "listar";
	}
	
    public String cancelar() {  
        if (null != comportamento.getId()) {  
             comportamentoService.refresh(comportamento);    
        }
	   Events.instance().raiseEvent("comportamentoObserver");        
       return "listar";  
   }  

	public Comportamento getComportamento() {
		return comportamento;
	}

	public void setComportamento(Comportamento comportamento) {
		this.comportamento = comportamento;
	}

	public List<Comportamento> getComportamentoLista() {
		return comportamentoLista;
	}

	public void setComportamentoLista(List<Comportamento> comportamentoLista) {
		this.comportamentoLista = comportamentoLista;
	}
}

