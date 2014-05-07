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
import br.com.procempa.save.entity.Idade;
import br.com.procempa.save.iservice.IIdadeService;
import br.com.procempa.save.service.IdadeService;

/**
 * @author bridi
 * 
 */
@Name("idadeAction")
@Scope(ScopeType.CONVERSATION)
@ActionInterceptor
public class IdadeAction extends AbstractSaveAction {
	private static final long serialVersionUID = 1L;
	   
	@Out(required = true)
	@In(required = false, create = true)
	private Idade idade;

	@In(value = IdadeService.SERVICE_NAME, create = true)
	private IIdadeService idadeService;
	
	@DataModel
	private List<Idade> idadeLista;

	@Factory("idadeLista")
	@Observer("idadeObserver")
	public void loadIdadeLista() {
		this.setIdadeLista(idadeService.findAll());			
	}
		
	public String filtrar(){
		Events.instance().raiseEvent("idadeObserver");
		return "listar";
	}
		
	
	public String editar(Idade idade) {
		setIdade(idade);
		return super.editar();
	}
	
	public String salvar() {
		Long id = idade.getId();
		if(BooleanUtils.isTrue(idadeService.salvar(idade))){
			idade = idadeService.refresh(idadeService.evict(idade));
			Events.instance().raiseEvent("idadeObserver");
			if(null == id){
				criar();
			}			
		}
		return "listar";
	}

	public String criar() {
		this.idade = new Idade();		
		return "criar";
	}
 
	public String excluir(Idade idade) {
		setIdade(idade);
		idadeService.excluir( getIdade() );
		Events.instance().raiseEvent("idadeObserver");
		return "listar";
	}

    public String cancelar() {  
        if (null != idade.getId()) {  
             idadeService.refresh(idade);    
        }
	   Events.instance().raiseEvent("idadeObserver");        
       return "listar";  
   }  
    
	public Idade getIdade() {
		return idade;
	}

	public void setIdade(Idade idade) {
		this.idade = idade;
	}

	public List<Idade> getIdadeLista() {
		return idadeLista;
	}

	public void setIdadeLista(List<Idade> idadeLista) {
		this.idadeLista = idadeLista;
	}
}

