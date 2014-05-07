package br.com.procempa.save.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.hibernate.validator.InvalidValue;
import org.jboss.seam.annotations.Name;

import br.com.procempa.exceptions.ProcempaValidationException;
import br.com.procempa.save.entity.Especie;
import br.com.procempa.save.iservice.IEspecieService;

@Stateless
@Name(EspecieService.SERVICE_NAME)
public class EspecieService extends AbstractSaveService<Especie> implements IEspecieService {
	public static final String SERVICE_NAME = "especieService";

	@SuppressWarnings("unchecked")
	@Override
	public List<Especie> findAll() {
				
		return createNamedQuery(Especie.FIND_ALL).getResultList();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Especie> findAtivos() {
		return createNamedQuery(Especie.FIND_ATIVOS).getResultList();
	}

	@Override
	public Boolean salvar(Especie especie){
		ArrayList<InvalidValue> erros = new ArrayList<InvalidValue>();
		Query query;		
		if(null == especie.getId()){
			query = createNamedQuery(Especie.FIND_BY_DESCRICAO_COUNT_NOVO);
			query.setParameter("descricao", especie.getDescricao().trim());			
		}
		else{
			query = createNamedQuery(Especie.FIND_BY_DESCRICAO_COUNT_EDITAR);
			query.setParameter("descricao", especie.getDescricao().trim());
			query.setParameter("id", especie.getId());
		}
		if((Long)query.getSingleResult()==0){
			return super.salvar(especie);
			}		
		else{
			erros.add(new InvalidValue("Já existe Espécie com a descrição informada", 
					Especie.class, "", null, null));
			throw new ProcempaValidationException( erros.toArray(new InvalidValue[erros.size()]));
		}
	}	
}
