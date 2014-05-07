package br.com.procempa.save.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.jboss.seam.annotations.Name;

import br.com.procempa.save.entity.Secretaria;
import br.com.procempa.save.iservice.ISecretariaService;

@Stateless
@Name(SecretariaService.SERVICE_NAME)
public class SecretariaService extends AbstractSaveService<Secretaria> implements ISecretariaService {
	public static final String SERVICE_NAME = "secretariaService";	
	
	@Override
	public Boolean salvar(Secretaria pojo) {
		persist(pojo);
		return true;
	}

	@Override
	public List<Secretaria> pesquisar(String... filtro) {
		return null;
	}
	
	@Override
	public void excluir(Secretaria pojo, List<Secretaria> pojos) {
		excluir(pojo);
		pojos.remove( pojo );		
	}
	
	@Override
	public void excluir(Secretaria pojo) {
		remove( pojo );		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Secretaria> findAll() {
		Query query = createNamedQuery( Secretaria.FIND_ALL);
		
		return query.getResultList();
	}
	
}
