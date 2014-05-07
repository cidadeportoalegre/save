package br.com.procempa.save.service;

import java.util.ArrayList;
import java.util.List;

import br.com.procempa.arquitetura.service.AbstractService;
import br.com.procempa.save.entity.SaveAbstract;

/**
 * @author bridi
 * 
 */
public abstract class AbstractSaveService<T extends SaveAbstract> extends AbstractService<T> {
	
	@Override
	public T findById(Long id) {
		return super.findById(id);
	}
	
	@Override
	public Boolean salvar(T pojo) {
		persist(pojo);
		return true;
	};
	
	@Override
	public void excluir(T pojo) {
		remove(pojo);
	};
	
	@Override
	public void excluir(T pojo, List<T> pojos) {
		for (T t : pojos) {
			remove(t);
		}
	};
	
	@Override
	public List<T> pesquisar(String... filtro) {
		return new ArrayList<T>();
	}
	
}
