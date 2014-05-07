package br.com.procempa.save.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.hibernate.validator.InvalidValue;
import org.jboss.seam.annotations.Name;

import br.com.procempa.exceptions.ProcempaValidationException;
import br.com.procempa.save.entity.Comportamento;
import br.com.procempa.save.iservice.IComportamentoService;

/**
 * @author bridi / leandro.bogoni
 * 
 */

@Stateless
@Name(ComportamentoService.SERVICE_NAME)
public class ComportamentoService extends AbstractSaveService<Comportamento> implements IComportamentoService {
	public static final String SERVICE_NAME = "comportamentoService";

	@SuppressWarnings("unchecked")
	@Override
	public List<Comportamento> findAll() {
				
		return createNamedQuery(Comportamento.FIND_ALL).getResultList();

	}
	
	@Override
	public Boolean salvar(Comportamento comportamento){
		ArrayList<InvalidValue> erros = new ArrayList<InvalidValue>();
		Query query;		
		if(null == comportamento.getId()){
			query = createNamedQuery(Comportamento.FIND_BY_DESCRICAO_COUNT_NOVO);
			query.setParameter("descricao", comportamento.getDescricao().trim());			
		}
		else{
			query = createNamedQuery(Comportamento.FIND_BY_DESCRICAO_COUNT_EDITAR);
			query.setParameter("descricao", comportamento.getDescricao().trim());
			query.setParameter("id", comportamento.getId());
		}
		if((Long)query.getSingleResult()==0){
			return super.salvar(comportamento);
			}		
		else{
			erros.add(new InvalidValue("Já existe Comportamento com a descrição informada", 
					Comportamento.class, "", null, null));
			throw new ProcempaValidationException( erros.toArray(new InvalidValue[erros.size()]));
		}
	}	
}
