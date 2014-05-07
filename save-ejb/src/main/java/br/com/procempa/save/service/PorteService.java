package br.com.procempa.save.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.hibernate.validator.InvalidValue;
import org.jboss.seam.annotations.Name;

import br.com.procempa.exceptions.ProcempaValidationException;
import br.com.procempa.save.entity.Porte;
import br.com.procempa.save.iservice.IPorteService;

@Stateless
@Name(PorteService.SERVICE_NAME)
public class PorteService extends AbstractSaveService<Porte> implements IPorteService {
	public static final String SERVICE_NAME = "porteService";

	@SuppressWarnings("unchecked")
	@Override
	public List<Porte> findAll() {
				
		return createNamedQuery(Porte.FIND_ALL).getResultList();

	}

	@Override
	public Boolean salvar(Porte porte){
		ArrayList<InvalidValue> erros = new ArrayList<InvalidValue>();
		Query query;		
		if(null == porte.getId()){
			query = createNamedQuery(Porte.FIND_BY_DESCRICAO_COUNT_NOVO);
			query.setParameter("descricao", porte.getDescricao().trim());			
		}
		else{
			query = createNamedQuery(Porte.FIND_BY_DESCRICAO_COUNT_EDITAR);
			query.setParameter("descricao", porte.getDescricao().trim());
			query.setParameter("id", porte.getId());
		}
		if((Long)query.getSingleResult()==0){
			return super.salvar(porte);
			}		
		else{
			erros.add(new InvalidValue("Já existe Porte com a descrição informada", 
					Porte.class, "", null, null));
			throw new ProcempaValidationException( erros.toArray(new InvalidValue[erros.size()]));
		}
	}	

}
