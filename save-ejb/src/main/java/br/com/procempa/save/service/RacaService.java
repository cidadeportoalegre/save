package br.com.procempa.save.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.hibernate.validator.InvalidValue;
import org.jboss.seam.annotations.Name;

import br.com.procempa.exceptions.ProcempaValidationException;
import br.com.procempa.save.entity.Raca;
import br.com.procempa.save.iservice.IRacaService;

@Stateless
@Name(RacaService.SERVICE_NAME)
public class RacaService extends AbstractSaveService<Raca> implements IRacaService {
	public static final String SERVICE_NAME = "racaService";

	@SuppressWarnings("unchecked")
	@Override
	public List<Raca> findAll() {
				
		return createNamedQuery(Raca.FIND_ALL).getResultList();

	}

	@Override
	public Boolean salvar(Raca raca){
		ArrayList<InvalidValue> erros = new ArrayList<InvalidValue>();
		Query query;		
		if(null == raca.getId()){
			query = createNamedQuery(Raca.FIND_BY_DESCRICAO_COUNT_NOVO);
			query.setParameter("descricao", raca.getDescricao().trim());	
			query.setParameter("especie", raca.getEspecie());			
		}
		else{
			query = createNamedQuery(Raca.FIND_BY_DESCRICAO_COUNT_EDITAR);
			query.setParameter("descricao", raca.getDescricao().trim());
			query.setParameter("especie", raca.getEspecie());					
			query.setParameter("id", raca.getId());
		}
		if((Long)query.getSingleResult()==0){
			return super.salvar(raca);
			}		
		else{
			erros.add(new InvalidValue("Já existe Raça com a descrição informada", 
					Raca.class, "", null, null));
			throw new ProcempaValidationException( erros.toArray(new InvalidValue[erros.size()]));
		}
	}	
}
