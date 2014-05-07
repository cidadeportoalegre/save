package br.com.procempa.save.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.hibernate.validator.InvalidValue;
import org.jboss.seam.annotations.Name;

import br.com.procempa.exceptions.ProcempaValidationException;
import br.com.procempa.save.entity.Idade;
import br.com.procempa.save.iservice.IIdadeService;

@Stateless
@Name(IdadeService.SERVICE_NAME)
public class IdadeService extends AbstractSaveService<Idade> implements IIdadeService {
	public static final String SERVICE_NAME = "idadeService";

	@SuppressWarnings("unchecked")
	@Override
	public List<Idade> findAll() {
				
		return createNamedQuery(Idade.FIND_ALL).getResultList();

	}
	@Override
	public Boolean salvar(Idade idade){
		ArrayList<InvalidValue> erros = new ArrayList<InvalidValue>();
		Query query;		
		if(null == idade.getId()){
			query = createNamedQuery(Idade.FIND_BY_DESCRICAO_COUNT_NOVO);
			query.setParameter("descricao", idade.getDescricao().trim());			
		}
		else{
			query = createNamedQuery(Idade.FIND_BY_DESCRICAO_COUNT_EDITAR);
			query.setParameter("descricao", idade.getDescricao().trim());
			query.setParameter("id", idade.getId());
		}
		if((Long)query.getSingleResult()==0){
			return super.salvar(idade);
			}		
		else{
			erros.add(new InvalidValue("Já existe Idade com a descrição informada", 
					Idade.class, "", null, null));
			throw new ProcempaValidationException( erros.toArray(new InvalidValue[erros.size()]));
		}
	}	

}
