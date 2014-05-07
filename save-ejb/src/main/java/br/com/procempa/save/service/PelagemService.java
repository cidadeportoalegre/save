package br.com.procempa.save.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.hibernate.validator.InvalidValue;
import org.jboss.seam.annotations.Name;

import br.com.procempa.exceptions.ProcempaValidationException;
import br.com.procempa.save.entity.Pelagem;
import br.com.procempa.save.iservice.IPelagemService;

@Stateless
@Name(PelagemService.SERVICE_NAME)
public class PelagemService extends AbstractSaveService<Pelagem> implements IPelagemService {
	public static final String SERVICE_NAME = "pelagemService";

	@SuppressWarnings("unchecked")
	@Override
	public List<Pelagem> findAll() {
				
		return createNamedQuery(Pelagem.FIND_ALL).getResultList();

	}

	@Override
	public Boolean salvar(Pelagem pelagem){
		ArrayList<InvalidValue> erros = new ArrayList<InvalidValue>();
		Query query;		
		if(null == pelagem.getId()){
			query = createNamedQuery(Pelagem.FIND_BY_DESCRICAO_COUNT_NOVO);
			query.setParameter("descricao", pelagem.getDescricao().trim());			
		}
		else{
			query = createNamedQuery(Pelagem.FIND_BY_DESCRICAO_COUNT_EDITAR);
			query.setParameter("descricao", pelagem.getDescricao().trim());
			query.setParameter("id", pelagem.getId());
		}
		if((Long)query.getSingleResult()==0){
			return super.salvar(pelagem);
			}		
		else{
			erros.add(new InvalidValue("Já existe Pelagem com a descrição informada", 
					Pelagem.class, "", null, null));
			throw new ProcempaValidationException( erros.toArray(new InvalidValue[erros.size()]));
		}
	}	
}
