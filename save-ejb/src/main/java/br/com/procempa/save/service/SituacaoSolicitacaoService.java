package br.com.procempa.save.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.hibernate.validator.InvalidValue;
import org.jboss.seam.annotations.Name;

import br.com.procempa.exceptions.ProcempaValidationException;
import br.com.procempa.save.entity.SituacaoSolicitacao;
import br.com.procempa.save.iservice.ISituacaoSolicitacaoService;

@Stateless
@Name(SituacaoSolicitacaoService.SERVICE_NAME)
public class SituacaoSolicitacaoService extends AbstractSaveService<SituacaoSolicitacao> implements ISituacaoSolicitacaoService {
	public static final String SERVICE_NAME = "situacaoSolicitacaoService";

	@SuppressWarnings("unchecked")
	@Override
	public List<SituacaoSolicitacao> findAll() {
				
		return createNamedQuery(SituacaoSolicitacao.FIND_ALL).getResultList();

	}
	
	@Override
	public Boolean salvar(SituacaoSolicitacao situacaoSolicitacao){
		ArrayList<InvalidValue> erros = new ArrayList<InvalidValue>();
		Query query;		
		if(null == situacaoSolicitacao.getId()){
			query = createNamedQuery(SituacaoSolicitacao.FIND_BY_DESCRICAO_COUNT_NOVO);
			query.setParameter("descricao", situacaoSolicitacao.getDescricao().trim());			
		}
		else{
			query = createNamedQuery(SituacaoSolicitacao.FIND_BY_DESCRICAO_COUNT_EDITAR);
			query.setParameter("descricao", situacaoSolicitacao.getDescricao().trim());
			query.setParameter("id", situacaoSolicitacao.getId());
		}
		if((Long)query.getSingleResult()==0){
			return super.salvar(situacaoSolicitacao);
			}		
		else{
			erros.add(new InvalidValue("Já existe Situacao de Solicitação com a descrição informada", 
					SituacaoSolicitacao.class, "", null, null));
			throw new ProcempaValidationException( erros.toArray(new InvalidValue[erros.size()]));
		}
	}
	
	@Override
	public SituacaoSolicitacao findByDescricao(String descricao) {
		Query query = createNamedQuery(SituacaoSolicitacao.FIND_BY_DESCRICAO);
		query.setParameter("descricao", descricao);
		return (SituacaoSolicitacao) query.getSingleResult();

	}

}
