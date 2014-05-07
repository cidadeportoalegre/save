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
import br.com.procempa.save.entity.ViaAdocao;
import br.com.procempa.save.iservice.IViaAdocaoService;
import br.com.procempa.save.service.ViaAdocaoService;

/**
 * @author bridi
 * 
 */
@Name("viaAdocaoAction")
@Scope(ScopeType.CONVERSATION)
@ActionInterceptor
public class ViaAdocaoAction extends AbstractSaveAction {
	private static final long serialVersionUID = 1L;
	   
	@Out(required = true)
	@In(required = false, create = true)
	private ViaAdocao viaAdocao;

	@In(value = ViaAdocaoService.SERVICE_NAME, create = true)
	private IViaAdocaoService viaAdocaoService;
	
	@DataModel
	private List<ViaAdocao> viaAdocaoLista;

	@Factory("viaAdocaoLista")
	@Observer("viaAdocaoObserver")
	public void loadViaAdocaoLista() {
		this.setViaAdocaoLista(viaAdocaoService.findAll());			
	}
		
	public String filtrar(){
		Events.instance().raiseEvent("viaAdocaoObserver");
		return "listar";
	}
		
	
	public String editar(ViaAdocao viaAdocao) {
		setViaAdocao(viaAdocao);
		return super.editar();
	}
	
	public String salvar() {
		Long id = viaAdocao.getId();
		if(BooleanUtils.isTrue(viaAdocaoService.salvar(viaAdocao))){
			viaAdocao = viaAdocaoService.refresh(viaAdocaoService.evict(viaAdocao));
			Events.instance().raiseEvent("viaAdocaoObserver");
			if(null == id){
				criar();
			}			
		}
		return "listar";
	}

	public String criar() {
		this.viaAdocao = new ViaAdocao();		
		return "criar";
	}
 
	public String excluir(ViaAdocao viaAdocao) {
		setViaAdocao(viaAdocao);
		viaAdocaoService.excluir( getViaAdocao() );
		Events.instance().raiseEvent("viaAdocaoObserver");
		return "listar";
	}

    public String cancelar() {  
        if (null != viaAdocao.getId()) {  
             viaAdocaoService.refresh(viaAdocao);    
        }
	   Events.instance().raiseEvent("viaAdocaoObserver");        
       return "listar";  
   }  
    
	public ViaAdocao getViaAdocao() {
		return viaAdocao;
	}

	public void setViaAdocao(ViaAdocao viaAdocao) {
		this.viaAdocao = viaAdocao;
	}

	public List<ViaAdocao> getViaAdocaoLista() {
		return viaAdocaoLista;
	}

	public void setViaAdocaoLista(List<ViaAdocao> viaAdocaoLista) {
		this.viaAdocaoLista = viaAdocaoLista;
	}
}

