package br.com.procempa.save.action;

import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;

import br.com.procempa.arquitetura.seguranca.entidade.Role;
import br.com.procempa.arquitetura.seguranca.entidade.User;
import br.com.procempa.arquitetura.seguranca.service.IRoleService;
import br.com.procempa.arquitetura.seguranca.service.RoleService;
import br.com.procempa.interceptors.ActionInterceptor;
import br.com.procempa.save.entity.Usuario;
import br.com.procempa.save.iservice.IUsuarioService;
import br.com.procempa.save.service.UsuarioService;

/**
 * @author bridi
 * 
 */
@Name("usuarioAction")
@Scope(ScopeType.CONVERSATION)
@ActionInterceptor
public class UsuarioAction extends AbstractSaveAction {
	private static final long serialVersionUID = 1L;
	private List<Role> fullRoleList = null;
	   
	@Out(required = true)
	@In(required = false, create = true)
	private Usuario usuario;

	@In(value = UsuarioService.SERVICE_NAME, create = true)
	private IUsuarioService usuarioService;
	
	@In(value = RoleService.SERVICE_NAME, create = true)
	private  IRoleService roleService;
	
	/*
	 * Habilita botão Salvar, na inclusão,
	 * após consultar LDAP e Guardiao.
	 * Modificado no método verificarUsuário e
	 * editar
	 * 
	 */
	private boolean verificou = false;
		
	private User usuarioOld = new User();
	
	/**
	 * Busca informações do usuário no LDAP e no Guardiao, combinando-as
	 */
	public String verificarUsuario(){		
		 Boolean loadUserFromLDAP = usuarioService.loadUserFromLDAP(usuario);
		 setVerificou(  loadUserFromLDAP == null ? false : loadUserFromLDAP );
		 
		 return "";
	}
 
	@Override
	public String editar() {
		setVerificou( true );
		getUsuarioOld().setDomain( usuario.getUser().getDomain() );
		getUsuarioOld().setUserName( usuario.getUser().getUserName() );
		return super.editar();
	}
	
	public String salvar() {
		if (null != usuario.getId()) {
			if( getUsuarioOld().getDomain().equals( usuario.getUser().getDomain() ) &&
					getUsuarioOld().getUserName().equals( usuario.getUser().getUserName() ) ){
				usuarioService.salvar(usuario);
				return "listar";
			}
			else {
				Boolean loadUserFromLDAP = usuarioService.loadUserFromLDAP(usuario);
				if( loadUserFromLDAP != null && loadUserFromLDAP.booleanValue() ) {
					usuarioService.salvar(usuario);
					return "listar";
				}
			}
		} else {
			usuarioService.salvar(usuario);			
			return "listar";
		}
		
		return "";
	}

	public void lista() {
	}

	public String criar() {
		this.usuario = new Usuario();		
		return "criar";
	}
 
	public String excluir() {
		usuarioService.excluir( usuario );
		return "listar";
	}

	public List<Role> getFullRoleList() {
		fullRoleList = roleService.getAllRoles(); 
		fullRoleList.removeAll( getUsuario().getUser().getRoleList() );
		return fullRoleList;
	}
	
	public void setFullRoleList( List<Role> fullRoleList ) {
		this.fullRoleList = fullRoleList;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setVerificou(boolean verificou) {
		this.verificou = verificou;
	}

	public boolean isVerificou() {
		return verificou;
	}

	public void setUsuarioOld(User usuarioOld) {
		this.usuarioOld = usuarioOld;
	}

	public User getUsuarioOld() {
		return usuarioOld;
	}

}

