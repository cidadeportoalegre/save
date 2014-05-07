package br.com.procempa.save.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.hibernate.validator.InvalidValue;
import org.jboss.seam.annotations.Name;

import br.com.procempa.exceptions.ProcempaValidationException;
import br.com.procempa.save.entity.CausaAgenda;
import br.com.procempa.save.iservice.ICausaAgendaService;

@Stateless
@Name(CausaAgendaService.SERVICE_NAME)
public class CausaAgendaService extends AbstractSaveService<CausaAgenda> implements ICausaAgendaService {
	public static final String SERVICE_NAME = "causaAgendaService";

	@SuppressWarnings("unchecked")
	@Override
	public List<CausaAgenda> findAll() {
				
		return createNamedQuery(CausaAgenda.FIND_ALL).getResultList();

	}

	@Override
	public Boolean salvar(CausaAgenda causaAgenda){
		ArrayList<InvalidValue> erros = new ArrayList<InvalidValue>();
		Query query;		
		if(null == causaAgenda.getId()){
			query = createNamedQuery(CausaAgenda.FIND_BY_DESCRICAO_COUNT_NOVO);
			query.setParameter("descricao", causaAgenda.getDescricao().trim());			
		}
		else{
			query = createNamedQuery(CausaAgenda.FIND_BY_DESCRICAO_COUNT_EDITAR);
			query.setParameter("descricao", causaAgenda.getDescricao().trim());
			query.setParameter("id", causaAgenda.getId());
		}
		if((Long)query.getSingleResult()==0){
			return super.salvar(causaAgenda);
			}		
		else{
			erros.add(new InvalidValue("Já existe Causa Agenda com a descrição informada", 
					CausaAgenda.class, "", null, null));
			throw new ProcempaValidationException( erros.toArray(new InvalidValue[erros.size()]));
		}
	}	
}
