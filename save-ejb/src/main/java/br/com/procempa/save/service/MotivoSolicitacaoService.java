package br.com.procempa.save.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.hibernate.validator.InvalidValue;
import org.jboss.seam.annotations.Name;

import br.com.procempa.exceptions.ProcempaValidationException;
import br.com.procempa.falapoa.importacao.Importacao;
import br.com.procempa.save.entity.MotivoSolicitacao;
import br.com.procempa.save.iservice.IMotivoSolicitacaoService;

@Stateless
@Name(MotivoSolicitacaoService.SERVICE_NAME)
public class MotivoSolicitacaoService extends AbstractSaveService<MotivoSolicitacao> implements IMotivoSolicitacaoService {
	public static final String SERVICE_NAME = "motivoSolicitacaoService";

	@SuppressWarnings("unchecked")
	@Override
	public List<MotivoSolicitacao> findAll() {
				
		return createNamedQuery(MotivoSolicitacao.FIND_ALL).getResultList();

	}

	@Override
	public Boolean salvar(MotivoSolicitacao motivoSolicitacao){
		ArrayList<InvalidValue> erros = new ArrayList<InvalidValue>();
		Query query;		
		if(null == motivoSolicitacao.getId()){
			query = createNamedQuery(MotivoSolicitacao.FIND_BY_DESCRICAO_COUNT_NOVO);
			query.setParameter("descricao", motivoSolicitacao.getDescricao().trim());			
		}
		else{
			query = createNamedQuery(MotivoSolicitacao.FIND_BY_DESCRICAO_COUNT_EDITAR);
			query.setParameter("descricao", motivoSolicitacao.getDescricao().trim());
			query.setParameter("id", motivoSolicitacao.getId());
		}
		if((Long)query.getSingleResult()==0){
			return super.salvar(motivoSolicitacao);
			}		
		else{
			erros.add(new InvalidValue("Já existe Motivo de Solicitação com a descrição informada", 
					MotivoSolicitacao.class, "", null, null));
			throw new ProcempaValidationException( erros.toArray(new InvalidValue[erros.size()]));
		}
	}

	@Override
	public void importarMotivosSolicitacao() {
		Importacao.getInstance().importarMotivosSolicitao();
	}	
}
