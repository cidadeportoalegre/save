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
import br.com.procempa.save.entity.Pelagem;
import br.com.procempa.save.iservice.IPelagemService;
import br.com.procempa.save.service.PelagemService;

/**
 * @author bridi
 * 
 */
@Name("pelagemAction")
@Scope(ScopeType.CONVERSATION)
@ActionInterceptor
public class PelagemAction extends AbstractSaveAction {
	private static final long serialVersionUID = 1L;
	   
	@Out(required = true)
	@In(required = false, create = true)
	private Pelagem pelagem;

	@In(value = PelagemService.SERVICE_NAME, create = true)
	private IPelagemService pelagemService;
	
	@DataModel
	private List<Pelagem> pelagemLista;

	@Factory("pelagemLista")
	@Observer("pelagemObserver")
	public void loadPelagemLista() {
		this.setPelagemLista(pelagemService.findAll());			
	}
		
	public String filtrar(){
		Events.instance().raiseEvent("pelagemObserver");
		return "listar";
	}
		
	
	public String editar(Pelagem pelagem) {
		setPelagem(pelagem);
		return super.editar();
	}
	
	public String salvar() {
		Long id = pelagem.getId();
		if(BooleanUtils.isTrue(pelagemService.salvar(pelagem))){
			pelagem = pelagemService.refresh(pelagemService.evict(pelagem));
			Events.instance().raiseEvent("pelagemObserver");
			if(null == id){
				criar();
			}			
		}
		return "listar";
	}

	public String criar() {
		this.pelagem = new Pelagem();		
		return "criar";
	}
 
	public String excluir(Pelagem pelagem) {
		setPelagem(pelagem);
		pelagemService.excluir( getPelagem() );
		Events.instance().raiseEvent("pelagemObserver");
		return "listar";
	}
	
    public String cancelar() {  
        if (null != pelagem.getId()) {  
             pelagemService.refresh(pelagem);    
        }
	   Events.instance().raiseEvent("pelagemObserver");        
       return "listar";  
   }  

	public Pelagem getPelagem() {
		return pelagem;
	}

	public void setPelagem(Pelagem pelagem) {
		this.pelagem = pelagem;
	}

	public List<Pelagem> getPelagemLista() {
		return pelagemLista;
	}

	public void setPelagemLista(List<Pelagem> pelagemLista) {
		this.pelagemLista = pelagemLista;
	}
}

