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
import br.com.procempa.save.entity.Especie;
import br.com.procempa.save.iservice.IEspecieService;
import br.com.procempa.save.service.EspecieService;

/**
 * @author bridi
 * 
 */
@Name("especieAction")
@Scope(ScopeType.CONVERSATION)
@ActionInterceptor
public class EspecieAction extends AbstractSaveAction {
	private static final long serialVersionUID = 1L;
	   
	@Out(required = true)
	@In(required = false, create = true)
	private Especie especie;

	@In(value = EspecieService.SERVICE_NAME, create = true)
	private IEspecieService especieService;
	
	@DataModel
	private List<Especie> especieLista;

	@Factory("especieLista")
	@Observer("especieObserver")
	public void loadEspecieLista() {
		this.setEspecieLista(especieService.findAll());			
	}
		
	public String filtrar(){
		Events.instance().raiseEvent("especieObserver");
		return "listar";
	}
		
	
	public String editar(Especie especie) {
		setEspecie(especie);
		return super.editar();
	}
	
	public String salvar() {
		Long id = especie.getId();
		if(BooleanUtils.isTrue(especieService.salvar(especie))){
			especie = especieService.refresh(especieService.evict(especie));
			Events.instance().raiseEvent("especieObserver");
			if(null == id){
				criar();
			}			
		}
		return "listar";
	}

	public String criar() {
		this.especie = new Especie();		
		return "criar";
	}
 
	public String excluir(Especie especie) {
		setEspecie(especie);
		especieService.excluir( getEspecie() );
		Events.instance().raiseEvent("especieObserver");
		return "listar";
	}

    public String cancelar() {  
        if (null != especie.getId()) {  
             especieService.refresh(especie);    
        }
	   Events.instance().raiseEvent("especieObserver");        
       return "listar";  
   }  
	public Especie getEspecie() {
		return especie;
	}

	public void setEspecie(Especie especie) {
		this.especie = especie;
	}

	public List<Especie> getEspecieLista() {
		return especieLista;
	}

	public void setEspecieLista(List<Especie> especieLista) {
		this.especieLista = especieLista;
	}
}

