package br.com.procempa.save.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.hibernate.validator.InvalidValue;
import org.jboss.seam.annotations.Name;

import br.com.procempa.exceptions.ProcempaValidationException;
import br.com.procempa.save.entity.DesfechoSolicitacao;
import br.com.procempa.save.iservice.IDesfechoSolicitacaoService;

@Stateless
@Name(DesfechoSolicitacaoService.SERVICE_NAME)
public class DesfechoSolicitacaoService extends AbstractSaveService<DesfechoSolicitacao> implements IDesfechoSolicitacaoService {
	public static final String SERVICE_NAME = "desfechoSolicitacaoService";

	@SuppressWarnings("unchecked")
	@Override
	public List<DesfechoSolicitacao> findAll() {
				
		return createNamedQuery(DesfechoSolicitacao.FIND_ALL).getResultList();

	}

	@Override
	public Boolean salvar(DesfechoSolicitacao desfechoSolicitacao){
		ArrayList<InvalidValue> erros = new ArrayList<InvalidValue>();
		Query query;		
		if(null == desfechoSolicitacao.getId()){
			query = createNamedQuery(DesfechoSolicitacao.FIND_BY_DESCRICAO_COUNT_NOVO);
			query.setParameter("descricao", desfechoSolicitacao.getDescricao().trim());			
		}
		else{
			query = createNamedQuery(DesfechoSolicitacao.FIND_BY_DESCRICAO_COUNT_EDITAR);
			query.setParameter("descricao", desfechoSolicitacao.getDescricao().trim());
			query.setParameter("id", desfechoSolicitacao.getId());
		}
		if((Long)query.getSingleResult()==0){
			return super.salvar(desfechoSolicitacao);
			}		
		else{
			erros.add(new InvalidValue("Já existe Desfecho de Solicitação com a descrição informada", 
					DesfechoSolicitacao.class, "", null, null));
			throw new ProcempaValidationException( erros.toArray(new InvalidValue[erros.size()]));
		}
	}	
	
}
