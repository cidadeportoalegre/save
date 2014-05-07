package br.com.procempa.save.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.hibernate.validator.InvalidValue;
import org.jboss.seam.annotations.Name;

import br.com.procempa.exceptions.ProcempaValidationException;
import br.com.procempa.save.entity.FornecedorChip;
import br.com.procempa.save.iservice.IFornecedorChipService;

@Stateless
@Name(FornecedorChipService.SERVICE_NAME)
public class FornecedorChipService extends AbstractSaveService<FornecedorChip> implements IFornecedorChipService {
	public static final String SERVICE_NAME = "fornecedorChipService";

	@SuppressWarnings("unchecked")
	@Override
	public List<FornecedorChip> findAll() {
				
		return createNamedQuery(FornecedorChip.FIND_ALL).getResultList();

	}

	@Override
	public Boolean salvar(FornecedorChip fornecedorChip){
		ArrayList<InvalidValue> erros = new ArrayList<InvalidValue>();
		Query query;		
		if(null == fornecedorChip.getId()){
			query = createNamedQuery(FornecedorChip.FIND_BY_DESCRICAO_COUNT_NOVO);
			query.setParameter("descricao", fornecedorChip.getDescricao().trim());			
		}
		else{
			query = createNamedQuery(FornecedorChip.FIND_BY_DESCRICAO_COUNT_EDITAR);
			query.setParameter("descricao", fornecedorChip.getDescricao().trim());
			query.setParameter("id", fornecedorChip.getId());
		}
		if((Long)query.getSingleResult()==0){
			return super.salvar(fornecedorChip);
			}		
		else{
			erros.add(new InvalidValue("Já existe Fornecedor de Chip com a descrição informada", 
					FornecedorChip.class, "", null, null));
			throw new ProcempaValidationException( erros.toArray(new InvalidValue[erros.size()]));
		}
	}	
}
