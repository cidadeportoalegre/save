package br.com.procempa.save.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.hibernate.validator.InvalidValue;
import org.jboss.seam.annotations.Name;

import br.com.procempa.exceptions.ProcempaValidationException;
import br.com.procempa.save.entity.TipoSolicitante;
import br.com.procempa.save.iservice.ITipoSolicitanteService;

@Stateless
@Name(TipoSolicitanteService.SERVICE_NAME)
public class TipoSolicitanteService extends AbstractSaveService<TipoSolicitante> implements ITipoSolicitanteService {
	public static final String SERVICE_NAME = "tipoSolicitanteService";

	@SuppressWarnings("unchecked")
	@Override
	public List<TipoSolicitante> findAll() {
				
		return createNamedQuery(TipoSolicitante.FIND_ALL).getResultList();

	}

	@Override
	public Boolean salvar(TipoSolicitante tipoSolicitante){
		ArrayList<InvalidValue> erros = new ArrayList<InvalidValue>();
		Query query;		
		if(null == tipoSolicitante.getId()){
			query = createNamedQuery(TipoSolicitante.FIND_BY_DESCRICAO_COUNT_NOVO);
			query.setParameter("descricao", tipoSolicitante.getDescricao().trim());			
		}
		else{
			query = createNamedQuery(TipoSolicitante.FIND_BY_DESCRICAO_COUNT_EDITAR);
			query.setParameter("descricao", tipoSolicitante.getDescricao().trim());
			query.setParameter("id", tipoSolicitante.getId());
		}
		if((Long)query.getSingleResult()==0){
			return super.salvar(tipoSolicitante);
			}		
		else{
			erros.add(new InvalidValue("Já existe Tipo de Solicitante com a descrição informada", 
					TipoSolicitante.class, "", null, null));
			throw new ProcempaValidationException( erros.toArray(new InvalidValue[erros.size()]));
		}
	}	
}
