package br.com.procempa.save.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.security.Identity;

import br.com.procempa.arquitetura.seguranca.entidade.User;
import br.com.procempa.arquitetura.seguranca.service.IUserService;
import br.com.procempa.exceptions.ProcempaValidationException;
import br.com.procempa.guardiao.GuardiaoFactory;
import br.com.procempa.guardiao.GuardiaoUser;
import br.com.procempa.guardiao.IGuardiaoService;
import br.com.procempa.save.entity.Usuario;
import br.com.procempa.save.iservice.IUsuarioService;

@Stateless
@Name(UsuarioService.SERVICE_NAME)
public class UsuarioService extends AbstractSaveService<Usuario> implements IUsuarioService {
	public static final String SERVICE_NAME = "usuarioService";	
	
	@EJB
	IUserService userService;
	
	@Override
	public Boolean salvar(Usuario pojo) {
		persist(pojo);
		return true;
	}

	@Override
	public List<Usuario> pesquisar(String... filtro) {
		return null;
	}
	
	@Override
	public void excluir(Usuario pojo, List<Usuario> pojos) {
		excluir(pojo);
		pojos.remove( pojo );		
	}
	
	@Override
	public void excluir(Usuario pojo) {
		remove( pojo );		
	}

	@Override
	public Usuario findByUser(User user) {
		@SuppressWarnings("unchecked")
		List<Usuario> usuarios = createNamedQuery(Usuario.FIND_BY_USER)
			.setParameter("user", user)
			.getResultList();
		
		return usuarios.size() > 0 ? usuarios.get(0) : null;
	}

	@Override
	public Usuario findByDomainUserName(String domain, String userName) {
		@SuppressWarnings("unchecked")
		List<Usuario> usuarios = createNamedQuery(Usuario.FIND_BY_DOMAIN_USER)
			.setParameter("userName", userName)
			.setParameter("domain", domain)
			.getResultList();
	
		return usuarios.size() > 0 ? usuarios.get(0) : null;
	}

	@Override
	public Usuario findByIdentity(Identity identity) {
		String domainUser = identity.getCredentials().getUsername();
		String[] parts = domainUser.split("\\\\");
		return findByDomainUserName(parts[0], parts[1]);
	}	
	
	/**
	 * Busca informações do usuário no LDAP e no Guardiao, combinando-as
	 */
	public Boolean loadUserFromLDAP(Usuario usuario){
		
		if (StringUtils.isBlank(usuario.getUser().getUserName()) || 
				StringUtils.isBlank(usuario.getUser().getDomain()))
			return false;

		//antes, verificar se já existe o usuário informado
		User userExistente = userService.findByDomainUserName(
				usuario.getUser().getDomain(), usuario.getUser().getUserName());
		if (null != userExistente) {
			Usuario usuarioExistente = findByDomainUserName(
					usuario.getUser().getDomain(), usuario.getUser().getUserName());
			if (null != usuarioExistente ){
				if( null == usuario.getId() || (! usuarioExistente.getUser().getName().equals( usuario.getUser().getName() ))) {
					ArrayList<InvalidValue> erros = new ArrayList<InvalidValue>();
					
					erros.add( new InvalidValue(String.format("Usuário %s " +
							"já cadastrado", usuarioExistente.getUser().getName() ), Usuario.class, "idUsuario", usuario.getUser().getUserName(), usuario ) );
					
					throw new ProcempaValidationException( (InvalidValue[]) erros.toArray(new InvalidValue[erros.size()]) );
				}
				else {
					usuario = usuarioExistente;
				}
			}
			else {
				usuario.setUser(userExistente);
			}
		}
		User userLDAP =  null;
		/*
		 * teste provisório até os usuários serem migrados para o AD
		 * 
		 */
		if( "pmpa".equals( usuario.getUser().getDomain().toLowerCase()) ){
			
			 userLDAP = userService.getUserFromLDAP(usuario.getUser().getUserName());
		}

		GuardiaoFactory guardiaoFactory;
		GuardiaoUser userGuardiao = null;
		try {
			guardiaoFactory = (GuardiaoFactory) GuardiaoFactory.getFactory();
			IGuardiaoService guardiaoService =  guardiaoFactory.getGuardiaoService();
			userGuardiao = guardiaoService.getGuardiaoUser(
					usuario.getUser().getUserName(), 
					usuario.getUser().getDomain());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (null != userGuardiao){
			if (null != userLDAP){
				if ("pmpa".equalsIgnoreCase(usuario.getUser().getDomain()) || 
					userLDAP.getName().equalsIgnoreCase(userGuardiao.getNomeCompleto())){
					if (StringUtils.isNotBlank(userLDAP.getEmail())){
						usuario.getUser().setEmail( userLDAP.getEmail() );
					}
					if (StringUtils.isNotBlank(userLDAP.getName())){
						usuario.getUser().setName( userLDAP.getName() );
					}
					if (StringUtils.isNotBlank(userGuardiao.getDominio())){ //usar domínio do guardiao
						usuario.getUser().setDomain( userGuardiao.getDominio() );
					}						
				} 			
			} else {
				if (StringUtils.isNotBlank(userGuardiao.getNomeCompleto())){
					usuario.getUser().setName(userGuardiao.getNomeCompleto());
				}
			}
		}
		else {
			ArrayList<InvalidValue> erros = new ArrayList<InvalidValue>();
 
			erros.add( new InvalidValue("Usuário não localizado", Usuario.class, "idUsuario", usuario.getUser().getUserName(), usuario ) );
 
			throw new ProcempaValidationException( (InvalidValue[]) erros.toArray(new InvalidValue[erros.size()]) );
		}

		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> getList(Integer firstRow, Integer maxResults,
			String nome) {
		Query query = createNamedQuery( Usuario.USUARIOS_FILTRO);
		query.setParameter("nome", "%" + (nome == null ? "" : nome.trim()) + "%"  );
 
		if(maxResults > 0) {
			query.setFirstResult( firstRow );
			query.setMaxResults( maxResults );
		}

		return query.getResultList();
	}

	@Override
	public Long getCount(String nome) {
		Query query = createNamedQuery( Usuario.USUARIOS_FILTRO_COUNT);
		query.setParameter("nome", "%" + (nome == null ? "" : nome.trim()) + "%"  );
 
		return (Long) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> findAll() {
		Query query = createNamedQuery( Usuario.USUARIOS_ALL);
		
		return query.getResultList();
	}
	
}
