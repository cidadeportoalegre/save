package br.com.procempa.save.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.jboss.seam.annotations.Name;

import br.com.procempa.save.entity.RespostaSolicitacao;
import br.com.procempa.save.entity.Tramite;
import br.com.procempa.save.iservice.IRespostaSolicitacaoService;


@Stateless
@Name(RespostaSolicitacaoService.SERVICE_NAME)
public class RespostaSolicitacaoService extends AbstractSaveService<RespostaSolicitacao> implements IRespostaSolicitacaoService {
public static final String SERVICE_NAME = "respostaSolicitacaoService";

	@SuppressWarnings("unchecked")
	@Override
	public List<RespostaSolicitacao> findAll(Integer firstRow, Integer maxResults) {
		Query query = createNamedQuery( RespostaSolicitacao.RESPOSTAS_ALL);

		if(maxResults > 0) {
			query.setFirstResult( firstRow );
			query.setMaxResults( maxResults );
		}
		return query.getResultList();

	}
	
	@Override
	public RespostaSolicitacao findLast(Tramite tramite){
		Query query = createNamedQuery( RespostaSolicitacao.RESPOSTAS_LAST_BY_TRAMITE);
		query.setParameter("tramite", tramite);
		query.setMaxResults(1);
		return (RespostaSolicitacao)query.getSingleResult();
	}
}
