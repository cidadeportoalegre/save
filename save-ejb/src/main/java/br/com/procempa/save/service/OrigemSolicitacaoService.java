package br.com.procempa.save.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.hibernate.validator.InvalidValue;
import org.jboss.seam.annotations.Name;

import br.com.procempa.exceptions.ProcempaValidationException;
import br.com.procempa.save.entity.OrigemSolicitacao;
import br.com.procempa.save.iservice.IOrigemSolicitacaoService;

@Stateless
@Name(OrigemSolicitacaoService.SERVICE_NAME)
public class OrigemSolicitacaoService extends AbstractSaveService<OrigemSolicitacao> implements IOrigemSolicitacaoService {
	public static final String SERVICE_NAME = "origemSolicitacaoService";

	@SuppressWarnings("unchecked")
	@Override
	public List<OrigemSolicitacao> findAll() {
				
		return createNamedQuery(OrigemSolicitacao.FIND_ALL).getResultList();

	}
	@Override
	public Boolean salvar(OrigemSolicitacao origemSolicitacao){
		ArrayList<InvalidValue> erros = new ArrayList<InvalidValue>();
		Query query;		
		if(null == origemSolicitacao.getId()){
			query = createNamedQuery(OrigemSolicitacao.FIND_BY_DESCRICAO_COUNT_NOVO);
			query.setParameter("descricao", origemSolicitacao.getDescricao().trim());			
		}
		else{
			query = createNamedQuery(OrigemSolicitacao.FIND_BY_DESCRICAO_COUNT_EDITAR);
			query.setParameter("descricao", origemSolicitacao.getDescricao().trim());
			query.setParameter("id", origemSolicitacao.getId());
		}
		if((Long)query.getSingleResult()==0){
			return super.salvar(origemSolicitacao);
			}		
		else{
			erros.add(new InvalidValue("Já existe Origem de Solicitação com a descrição informada", 
					OrigemSolicitacao.class, "", null, null));
			throw new ProcempaValidationException( erros.toArray(new InvalidValue[erros.size()]));
		}
	}
	@Override
	public OrigemSolicitacao findByDescricao(String origemManual) {		
		Query query = createNamedQuery(OrigemSolicitacao.FIND_BY_DESCRICAO).setMaxResults(1);
		query.setParameter("descricao", origemManual);
		return (OrigemSolicitacao)query.getSingleResult();
		
	}	

}
