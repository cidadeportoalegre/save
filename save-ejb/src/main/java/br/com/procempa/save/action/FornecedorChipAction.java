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
import br.com.procempa.save.entity.FornecedorChip;
import br.com.procempa.save.iservice.IFornecedorChipService;
import br.com.procempa.save.service.FornecedorChipService;

/**
 * @author bridi
 * 
 */
@Name("fornecedorChipAction")
@Scope(ScopeType.CONVERSATION)
@ActionInterceptor
public class FornecedorChipAction extends AbstractSaveAction {
	private static final long serialVersionUID = 1L;
	   
	@Out(required = true)
	@In(required = false, create = true)
	private FornecedorChip fornecedorChip;

	@In(value = FornecedorChipService.SERVICE_NAME, create = true)
	private IFornecedorChipService fornecedorChipService;
	
	@DataModel
	private List<FornecedorChip> fornecedorChipLista;

	@Factory("fornecedorChipLista")
	@Observer("fornecedorChipObserver")
	public void loadFornecedorChipLista() {
		this.setFornecedorChipLista(fornecedorChipService.findAll());			
	}
		
	public String filtrar(){
		Events.instance().raiseEvent("fornecedorChipObserver");
		return "listar";
	}
		
	
	public String editar(FornecedorChip fornecedorChip) {
		setFornecedorChip(fornecedorChip);
		return super.editar();
	}
	
	public String salvar() {
		Long id = fornecedorChip.getId();
		if(BooleanUtils.isTrue(fornecedorChipService.salvar(fornecedorChip))){
			fornecedorChip = fornecedorChipService.refresh(fornecedorChipService.evict(fornecedorChip));
			Events.instance().raiseEvent("fornecedorChipObserver");
			if(null == id){
				criar();
			}			
		}
		return "listar";
	}

	public String criar() {
		this.fornecedorChip = new FornecedorChip();		
		return "criar";
	}
 
	public String excluir(FornecedorChip fornecedorChip) {
		setFornecedorChip(fornecedorChip);
		fornecedorChipService.excluir( getFornecedorChip() );
		Events.instance().raiseEvent("fornecedorChipObserver");
		return "listar";
	}

    public String cancelar() {  
        if (null != fornecedorChip.getId()) {  
             fornecedorChipService.refresh(fornecedorChip);    
        }
	   Events.instance().raiseEvent("fornecedorChipObserver");        
       return "listar";  
   }  
    
	public FornecedorChip getFornecedorChip() {
		return fornecedorChip;
	}

	public void setFornecedorChip(FornecedorChip fornecedorChip) {
		this.fornecedorChip = fornecedorChip;
	}

	public List<FornecedorChip> getFornecedorChipLista() {
		return fornecedorChipLista;
	}

	public void setFornecedorChipLista(List<FornecedorChip> fornecedorChipLista) {
		this.fornecedorChipLista = fornecedorChipLista;
	}
}

