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
import br.com.procempa.save.entity.Porte;
import br.com.procempa.save.iservice.IPorteService;
import br.com.procempa.save.service.PorteService;

/**
 * @author bridi
 * 
 */
@Name("porteAction")
@Scope(ScopeType.CONVERSATION)
@ActionInterceptor
public class PorteAction extends AbstractSaveAction {
	private static final long serialVersionUID = 1L;
	   
	@Out(required = true)
	@In(required = false, create = true)
	private Porte porte;

	@In(value = PorteService.SERVICE_NAME, create = true)
	private IPorteService porteService;
	
	@DataModel
	private List<Porte> porteLista;

	@Factory("porteLista")
	@Observer("porteObserver")
	public void loadPorteLista() {
		this.setPorteLista(porteService.findAll());			
	}
		
	public String filtrar(){
		Events.instance().raiseEvent("porteObserver");
		return "listar";
	}
		
	
	public String editar(Porte porte) {
		setPorte(porte);
		return super.editar();
	}
	
	public String salvar() {
		Long id = porte.getId();
		if(BooleanUtils.isTrue(porteService.salvar(porte))){
			porte = porteService.refresh(porteService.evict(porte));
			Events.instance().raiseEvent("porteObserver");
			if(null == id){
				criar();
			}			
		}
		return "listar";
	}

	public String criar() {
		this.porte = new Porte();		
		return "criar";
	}
 
	public String excluir(Porte porte) {
		setPorte(porte);
		porteService.excluir( getPorte() );
		Events.instance().raiseEvent("porteObserver");
		return "listar";
	}
	
    public String cancelar() {  
        if (null != porte.getId()) {  
             porteService.refresh(porte);    
        }
	   Events.instance().raiseEvent("porteObserver");        
       return "listar";  
   }  

	public Porte getPorte() {
		return porte;
	}

	public void setPorte(Porte porte) {
		this.porte = porte;
	}

	public List<Porte> getPorteLista() {
		return porteLista;
	}

	public void setPorteLista(List<Porte> porteLista) {
		this.porteLista = porteLista;
	}
}

