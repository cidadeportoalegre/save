package br.com.procempa.save.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.hibernate.validator.InvalidValue;
import org.jboss.seam.annotations.Name;

import br.com.procempa.exceptions.ProcempaValidationException;
import br.com.procempa.save.entity.ClassifRiscoGrupo;
import br.com.procempa.save.iservice.IClassifRiscoGrupoService;

@Stateless
@Name(ClassifRiscoGrupoService.SERVICE_NAME)
public class ClassifRiscoGrupoService extends AbstractSaveService<ClassifRiscoGrupo> implements IClassifRiscoGrupoService {
	public static final String SERVICE_NAME = "classifRiscoGrupoService";

	@SuppressWarnings("unchecked")
	@Override
	public List<ClassifRiscoGrupo> findAll() {
				
		return createNamedQuery(ClassifRiscoGrupo.FIND_ALL).getResultList();

	}

	@Override
	public Boolean salvar(ClassifRiscoGrupo classifRiscoGrupo){
		ArrayList<InvalidValue> erros = new ArrayList<InvalidValue>();
		Query query;		
		if(null == classifRiscoGrupo.getId()){
			query = createNamedQuery(ClassifRiscoGrupo.FIND_BY_DESCRICAO_COUNT_NOVO);
			query.setParameter("descricao", classifRiscoGrupo.getDescricao().trim());			
		}
		else{
			query = createNamedQuery(ClassifRiscoGrupo.FIND_BY_DESCRICAO_COUNT_EDITAR);
			query.setParameter("descricao", classifRiscoGrupo.getDescricao().trim());
			query.setParameter("id", classifRiscoGrupo.getId());
		}
		if((Long)query.getSingleResult()==0){
			return super.salvar(classifRiscoGrupo);
			}		
		else{
			erros.add(new InvalidValue("Já existe Grupo de Classificação de Risco com a descrição informada", 
					ClassifRiscoGrupo.class, "", null, null));
			throw new ProcempaValidationException( erros.toArray(new InvalidValue[erros.size()]));
		}
	}	
}
