package br.com.procempa.save.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.jboss.seam.annotations.Name;

import br.com.procempa.arquitetura.seguranca.service.IRoleService;
import br.com.procempa.save.entity.Perfil;
import br.com.procempa.save.iservice.IPerfilService;

@Stateless
@Name(PerfilService.SERVICE_NAME)
public class PerfilService extends AbstractSaveService<Perfil> implements IPerfilService {
	public static final String SERVICE_NAME = "perfilService";	
	
	@EJB
	IRoleService userService;
	
	@Override
	public Boolean salvar(Perfil pojo) {
		persist(pojo);
		return true;
	}

	@Override
	public List<Perfil> pesquisar(String... filtro) {
		return null;
	}
	
	@Override
	public void excluir(Perfil pojo, List<Perfil> pojos) {
		excluir(pojo);
		pojos.remove( pojo );		
	}
	
	@Override
	public void excluir(Perfil pojo) {
		remove( pojo );		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Perfil> findAll() {
		Query query = createNamedQuery( Perfil.PERFIS_ALL);
		
		return query.getResultList();
	}
	
}
