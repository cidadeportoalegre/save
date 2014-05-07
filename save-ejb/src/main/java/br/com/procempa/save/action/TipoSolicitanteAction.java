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
import br.com.procempa.save.entity.TipoSolicitante;
import br.com.procempa.save.iservice.ITipoSolicitanteService;
import br.com.procempa.save.service.TipoSolicitanteService;

/**
 * @author bridi
 * 
 */
@Name("tipoSolicitanteAction")
@Scope(ScopeType.CONVERSATION)
@ActionInterceptor
public class TipoSolicitanteAction extends AbstractSaveAction {
	private static final long serialVersionUID = 1L;
	   
	@Out(required = true)
	@In(required = false, create = true)
	private TipoSolicitante tipoSolicitante;

	@In(value = TipoSolicitanteService.SERVICE_NAME, create = true)
	private ITipoSolicitanteService tipoSolicitanteService;
	
	@DataModel
	private List<TipoSolicitante> tipoSolicitanteLista;

	@Factory("tipoSolicitanteLista")
	@Observer("tipoSolicitanteObserver")
	public void loadTipoSolicitanteLista() {
		this.setTipoSolicitanteLista(tipoSolicitanteService.findAll());			
	}
		
	public String filtrar(){
		Events.instance().raiseEvent("tipoSolicitanteObserver");
		return "listar";
	}
		
	
	public String editar(TipoSolicitante tipoSolicitante) {
		setTipoSolicitante(tipoSolicitante);
		return super.editar();
	}
	
	public String salvar() {
		Long id = tipoSolicitante.getId();
		if(BooleanUtils.isTrue(tipoSolicitanteService.salvar(tipoSolicitante))){
			tipoSolicitante = tipoSolicitanteService.refresh(tipoSolicitanteService.evict(tipoSolicitante));
			Events.instance().raiseEvent("tipoSolicitanteObserver");
			if(null == id){
				criar();
			}			
		}
		return "listar";
	}

	public String criar() {
		this.tipoSolicitante = new TipoSolicitante();		
		return "criar";
	}
 
	public String excluir(TipoSolicitante tipoSolicitante) {
		setTipoSolicitante(tipoSolicitante);
		tipoSolicitanteService.excluir( getTipoSolicitante() );
		Events.instance().raiseEvent("tipoSolicitanteObserver");
		return "listar";
	}

    public String cancelar() {  
        if (null != tipoSolicitante.getId()) {  
             tipoSolicitanteService.refresh(tipoSolicitante);    
        }
	   Events.instance().raiseEvent("tipoSolicitanteObserver");        
       return "listar";  
   }  
    
	public TipoSolicitante getTipoSolicitante() {
		return tipoSolicitante;
	}

	public void setTipoSolicitante(TipoSolicitante tipoSolicitante) {
		this.tipoSolicitante = tipoSolicitante;
	}

	public List<TipoSolicitante> getTipoSolicitanteLista() {
		return tipoSolicitanteLista;
	}

	public void setTipoSolicitanteLista(List<TipoSolicitante> tipoSolicitanteLista) {
		this.tipoSolicitanteLista = tipoSolicitanteLista;
	}
}

