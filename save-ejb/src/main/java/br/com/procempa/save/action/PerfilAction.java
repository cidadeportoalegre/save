package br.com.procempa.save.action;

import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.core.Events;

import br.com.procempa.arquitetura.action.AbstractAction;
import br.com.procempa.save.entity.Perfil;
import br.com.procempa.save.iservice.IPerfilService;
import br.com.procempa.save.service.PerfilService;

@Name("perfilAction")
@Scope(ScopeType.CONVERSATION)
public class PerfilAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	@Out(required = false )
	@In(required = false )
	@DataModelSelection	
	private Perfil perfil;

	@In(value = PerfilService.SERVICE_NAME, create = true)
	private IPerfilService perfilService;

	@DataModel
	private List<Perfil> perfilLista;

	public String criar() {
		this.perfil = new Perfil();
		return "criar";
	}
	
	public String salvar() {
		perfilService.salvar(perfil);
		Events.instance().raiseEvent("perfilObserver");
		return "listar";
	}

	@Factory("perfilLista")
	@Observer("perfilObserver")
	public void loadPerfilLista() {
		this.setPerfilLista(perfilService.pesquisar());			
	}
	
	public String filtrar(){
		Events.instance().raiseEvent("perfilObserver");
		return "listar";
	}
	
	public String excluir() {
		perfilService.excluir(perfil);
		Events.instance().raiseEvent("perfilObserver");
		return "listar";
	}

	public void setPerfilLista(List<Perfil> perfilLista) {
		this.perfilLista = perfilLista;
	}

	public List<Perfil> getPerfilLista() {
		return perfilLista;
	}	
}
