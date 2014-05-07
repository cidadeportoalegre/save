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
import br.com.procempa.save.entity.Raca;
import br.com.procempa.save.iservice.IEspecieService;
import br.com.procempa.save.iservice.IRacaService;
import br.com.procempa.save.service.EspecieService;
import br.com.procempa.save.service.RacaService;

/**
 * @author bridi
 * 
 */
@Name("racaAction")
@Scope(ScopeType.CONVERSATION)
@ActionInterceptor
public class RacaAction extends AbstractSaveAction {
	private static final long serialVersionUID = 1L;
	   
	@Out(required = true)
	@In(required = false, create = true)
	private Raca raca;

	@In(value = RacaService.SERVICE_NAME, create = true)
	private IRacaService racaService;
	
	@DataModel
	private List<Raca> racaLista;

	@In(value = EspecieService.SERVICE_NAME, create = true)
	private IEspecieService especieService;
	
	private List<Especie> especieList = null;
	
	@Factory("racaLista")
	@Observer("racaObserver")
	public void loadRacaLista() {
		this.setRacaLista(racaService.findAll());			
	}
		
	public String filtrar(){
		Events.instance().raiseEvent("racaObserver");
		return "listar";
	}
		
	
	public String editar(Raca raca) {
		setRaca(raca);
		return super.editar();
	}
	
	public String salvar() {
		Long id = raca.getId();
		if(BooleanUtils.isTrue(racaService.salvar(raca))){
			raca = racaService.refresh(racaService.evict(raca));
			Events.instance().raiseEvent("racaObserver");
			if(null == id){
				criar();
			}			
		}
		return "listar";
	}
	public String criar() {
		this.raca = new Raca();		
		return "criar";
	}
 
	public String excluir(Raca raca) {
		setRaca(raca);
		racaService.excluir( getRaca() );
		Events.instance().raiseEvent("racaObserver");
		return "listar";
	}

    public String cancelar() {  
        if (null != raca.getId()) {  
             racaService.refresh(raca);    
        }
	   Events.instance().raiseEvent("racaObserver");        
       return "listar";  
   }  
    
	public Raca getRaca() {
		return raca;
	}

	public void setRaca(Raca raca) {
		this.raca = raca;
	}

	public List<Raca> getRacaLista() {
		return racaLista;
	}

	public void setRacaLista(List<Raca> racaLista) {
		this.racaLista = racaLista;
	}

	public List<Especie> getEspecieList() {
		especieList = especieService.findAtivos();
		return especieList;
	}

	public void setEspecieList(List<Especie> especieList) {
		this.especieList = especieList;
	}
}

