package br.com.procempa.save.datamodel;

import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import br.com.procempa.arquitetura.datamodel.AbstractDataModel;
import br.com.procempa.save.entity.Usuario;
import br.com.procempa.save.iservice.IUsuarioService;
import br.com.procempa.save.service.UsuarioService;

/**
 * @author bridi
 * 
 */
@Name("usuarioDataModel")
@Scope(ScopeType.CONVERSATION)
public class UsuarioDataModel extends AbstractDataModel<Usuario, Long> {

	private static final long serialVersionUID = 1L;
	private String criterioNome;
	
	@In(value=UsuarioService.SERVICE_NAME, create=true)
	private IUsuarioService usuarioService;

	@Override
	public Long getCount() {
		return usuarioService.getCount( getCriterioNome());
	}

	@Override
	public Usuario findById(Long id) {
		return usuarioService.findById(id);
	}

	@Override
	public List<Usuario> getList(Integer firstRow, Integer maxResults) {
		return this.listRow = usuarioService.getList(firstRow, maxResults, getCriterioNome() );
	}

	public void setCriterioNome(String criterioNome) {
		this.criterioNome = criterioNome;
	}

	public String getCriterioNome() {
		return criterioNome;
	}
}
