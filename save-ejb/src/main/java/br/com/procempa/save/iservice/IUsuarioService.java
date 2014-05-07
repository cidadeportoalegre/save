package br.com.procempa.save.iservice;

import java.util.List;

import javax.ejb.Local;

import org.jboss.seam.security.Identity;

import br.com.procempa.arquitetura.business.interfaces.IBusiness;
import br.com.procempa.arquitetura.seguranca.entidade.User;
import br.com.procempa.save.entity.Usuario;

@Local
public interface IUsuarioService extends IBusiness<Usuario> {
 		
	/**
	 * Busca um usuário do portal com o user informado
	 * @param user
	 * @return instância de UsuarioPortal ou null caso não encontre
	 */
	public Usuario findByUser(User user);

	/**
	 * Busca um usuário do portal com o dominio e username informado
	 * @param domain
	 * @param userName
	 * @return instância de UsuarioPortal ou null caso não encontre
	 */
	public Usuario findByDomainUserName(String domain, String userName);
	
	/**
	 * Busca um usuário do portal com o identity informado
	 * @param identity
	 * @return
	 */
	public Usuario findByIdentity(Identity identity);
	
	public List<Usuario> getList(Integer firstRow, Integer maxResults, String nome);

	public Long getCount(String nome);

	public Boolean loadUserFromLDAP(Usuario usuario);

	/**
	 * @return lista completa de usuários
	 */
	public List<Usuario> findAll();
	
}
