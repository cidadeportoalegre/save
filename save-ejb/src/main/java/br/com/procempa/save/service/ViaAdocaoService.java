package br.com.procempa.save.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.hibernate.validator.InvalidValue;
import org.jboss.seam.annotations.Name;

import br.com.procempa.exceptions.ProcempaValidationException;
import br.com.procempa.save.entity.ViaAdocao;
import br.com.procempa.save.iservice.IViaAdocaoService;

@Stateless
@Name(ViaAdocaoService.SERVICE_NAME)
public class ViaAdocaoService extends AbstractSaveService<ViaAdocao> implements IViaAdocaoService {
	public static final String SERVICE_NAME = "viaAdocaoService";

	@SuppressWarnings("unchecked")
	@Override
	public List<ViaAdocao> findAll() {
				
		return createNamedQuery(ViaAdocao.FIND_ALL).getResultList();

	}

	@Override
	public Boolean salvar(ViaAdocao viaAdocao){
		ArrayList<InvalidValue> erros = new ArrayList<InvalidValue>();
		Query query;		
		if(null == viaAdocao.getId()){
			query = createNamedQuery(ViaAdocao.FIND_BY_DESCRICAO_COUNT_NOVO);
			query.setParameter("descricao", viaAdocao.getDescricao().trim());			
		}
		else{
			query = createNamedQuery(ViaAdocao.FIND_BY_DESCRICAO_COUNT_EDITAR);
			query.setParameter("descricao", viaAdocao.getDescricao().trim());
			query.setParameter("id", viaAdocao.getId());
		}
		if((Long)query.getSingleResult()==0){
			return super.salvar(viaAdocao);
			}		
		else{
			erros.add(new InvalidValue("Já existe Via de Adoção com a descrição informada", 
					ViaAdocao.class, "", null, null));
			throw new ProcempaValidationException( erros.toArray(new InvalidValue[erros.size()]));
		}
	}	
}
