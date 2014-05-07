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
import br.com.procempa.save.entity.CausaAgenda;
import br.com.procempa.save.iservice.ICausaAgendaService;
import br.com.procempa.save.service.CausaAgendaService;

/**
 * @author bridi
 * 
 */
@Name("causaAgendaAction")
@Scope(ScopeType.CONVERSATION)
@ActionInterceptor
public class CausaAgendaAction extends AbstractSaveAction {
	private static final long serialVersionUID = 1L;
	   
	@Out(required = true)
	@In(required = false, create = true)
	private CausaAgenda causaAgenda;

	@In(value = CausaAgendaService.SERVICE_NAME, create = true)
	private ICausaAgendaService causaAgendaService;
	
	@DataModel
	private List<CausaAgenda> causaAgendaLista;

	@Factory("causaAgendaLista")
	@Observer("causaAgendaObserver")
	public void loadCausaAgendaLista() {
		this.setCausaAgendaLista(causaAgendaService.findAll());			
	}
		
	public String filtrar(){
		Events.instance().raiseEvent("causaAgendaObserver");
		return "listar";
	}
		
	
	public String editar(CausaAgenda causaAgenda) {
		setCausaAgenda(causaAgenda);
		return super.editar();
	}
	
	public String salvar() {
		Long id = causaAgenda.getId();
		if(BooleanUtils.isTrue(causaAgendaService.salvar(causaAgenda))){
			causaAgenda = causaAgendaService.refresh(causaAgendaService.evict(causaAgenda));
			Events.instance().raiseEvent("causaAgendaObserver");
			if(null == id){
				criar();
			}			
		}
		return "listar";
	}

	public String criar() {
		this.causaAgenda = new CausaAgenda();		
		return "criar";
	}
 
	public String excluir(CausaAgenda causaAgenda) {
		setCausaAgenda(causaAgenda);
		causaAgendaService.excluir( getCausaAgenda() );
		Events.instance().raiseEvent("causaAgendaObserver");
		return "listar";
	}
	
    public String cancelar() {  
        if (null != causaAgenda.getId()) {  
             causaAgendaService.refresh(causaAgenda);    
        }
	   Events.instance().raiseEvent("causaAgendaObserver");        
       return "listar";  
   }  

	public CausaAgenda getCausaAgenda() {
		return causaAgenda;
	}

	public void setCausaAgenda(CausaAgenda causaAgenda) {
		this.causaAgenda = causaAgenda;
	}

	public List<CausaAgenda> getCausaAgendaLista() {
		return causaAgendaLista;
	}

	public void setCausaAgendaLista(List<CausaAgenda> causaAgendaLista) {
		this.causaAgendaLista = causaAgendaLista;
	}
}

