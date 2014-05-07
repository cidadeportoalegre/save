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
import br.com.procempa.save.entity.ClassifRiscoGrupo;
import br.com.procempa.save.iservice.IClassifRiscoGrupoService;
import br.com.procempa.save.service.ClassifRiscoGrupoService;

/**
 * @author bridi
 * 
 */
@Name("classifRiscoGrupoAction")
@Scope(ScopeType.CONVERSATION)
@ActionInterceptor
public class ClassifRiscoGrupoAction extends AbstractSaveAction {
	private static final long serialVersionUID = 1L;
	   
	@Out(required = true)
	@In(required = false, create = true)
	private ClassifRiscoGrupo classifRiscoGrupo;

	@In(value = ClassifRiscoGrupoService.SERVICE_NAME, create = true)
	private IClassifRiscoGrupoService classifRiscoGrupoService;
	
	@DataModel
	private List<ClassifRiscoGrupo> classifRiscoGrupoLista;

	@Factory("classifRiscoGrupoLista")
	@Observer("classifRiscoGrupoObserver")
	public void loadClassifRiscoGrupoLista() {
		this.setClassifRiscoGrupoLista(classifRiscoGrupoService.findAll());			
	}
		
	public String filtrar(){
		Events.instance().raiseEvent("classifRiscoGrupoObserver");
		return "listar";
	}
		
	
	public String editar(ClassifRiscoGrupo classifRiscoGrupo) {
		setClassifRiscoGrupo(classifRiscoGrupo);
		return super.editar();
	}
	
	public String salvar() {
		Long id = classifRiscoGrupo.getId();
		if(BooleanUtils.isTrue(classifRiscoGrupoService.salvar(classifRiscoGrupo))){
			classifRiscoGrupo = classifRiscoGrupoService.refresh(classifRiscoGrupoService.evict(classifRiscoGrupo));
			Events.instance().raiseEvent("classifRiscoGrupoObserver");
			if(null == id){
				criar();
			}			
		}
		return "listar";
	}

	public String criar() {
		this.classifRiscoGrupo = new ClassifRiscoGrupo();		
		return "criar";
	}
 
	public String excluir(ClassifRiscoGrupo classifRiscoGrupo) {
		setClassifRiscoGrupo(classifRiscoGrupo);
		classifRiscoGrupoService.excluir( getClassifRiscoGrupo() );
		Events.instance().raiseEvent("classifRiscoGrupoObserver");
		return "listar";
	}
	
    public String cancelar() {  
        if (null != classifRiscoGrupo.getId()) {  
             classifRiscoGrupoService.refresh(classifRiscoGrupo);    
        }
	   Events.instance().raiseEvent("classifRiscoGrupoObserver");        
       return "listar";  
   }  

	public ClassifRiscoGrupo getClassifRiscoGrupo() {
		return classifRiscoGrupo;
	}

	public void setClassifRiscoGrupo(ClassifRiscoGrupo classifRiscoGrupo) {
		this.classifRiscoGrupo = classifRiscoGrupo;
	}

	public List<ClassifRiscoGrupo> getClassifRiscoGrupoLista() {
		return classifRiscoGrupoLista;
	}

	public void setClassifRiscoGrupoLista(List<ClassifRiscoGrupo> classifRiscoGrupoLista) {
		this.classifRiscoGrupoLista = classifRiscoGrupoLista;
	}
}

