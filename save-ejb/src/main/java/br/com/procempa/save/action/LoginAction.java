package br.com.procempa.save.action;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.international.StatusMessage.Severity;

import br.com.procempa.arquitetura.seguranca.entidade.User;
import br.com.procempa.arquitetura.seguranca.identity.ProcempaIdentity;
import br.com.procempa.arquitetura.seguranca.service.IUserService;
import br.com.procempa.interceptors.ActionInterceptor;
 
/**
 * @author bridi
 */
@Name("loginAction")
@ActionInterceptor
@Scope(ScopeType.CONVERSATION)
public class LoginAction extends AbstractSaveAction {

	private static final long serialVersionUID = 1L;
 
	@In
	private ProcempaIdentity identity;
	
	@In(value = "userService", create = true)
	private IUserService userService;
	
	@In(value = "userService", create = true)
	private IUserService UserService;
	
	@RequestParameter("messLogin")
	private String mess;
	
	public String login() {
		// efetiva o login do usuário
		String logou = identity.login();
		
		if (identity.isLoggedIn()) {
			// busca o usuário portal associado e coloca na sessão
			// se não encontrar, faz logout
			String domainUser = identity.getCredentials().getUsername();
			String[] parts = domainUser.split("\\\\");
			
			User user = null;
			try {
				user = userService.validateUserByDomainUserName(parts[0], parts[1]);
			} catch (Exception e) {				
				fm.add(Severity.FATAL, e.getMessage());
				identity.logout();
				logou = "";
			}
			
			if (null == user) {
				identity.logout();
				logou = "semUsuario";
			}
		}
		
		return logou;
	}
	
	
	public void addMessage(){
		if( getMess() != null ) {
			if ("semUsuario".equals(mess)){	
				fm.add(Severity.ERROR, "Você não é um usuário do save, entre em contato com o administrador do sistema");
			}
		}
	}

	public String getMess() {
		return mess;
	}

	public void setMess(String mess) {
		this.mess = mess;
	}	
}
