package br.com.procempa.save.service;

import java.util.List;

import javax.ejb.Stateless;

import org.jboss.seam.annotations.Name;

import br.com.procempa.save.entity.LogResposta;
import br.com.procempa.save.iservice.ILogRespostaService;

@Stateless
@Name(LogRespostaService.SERVICE_NAME)
public class LogRespostaService extends AbstractSaveService<LogResposta> implements ILogRespostaService {
	public static final String SERVICE_NAME = "logRespostaService";

	@SuppressWarnings("unchecked")
	@Override
	public List<LogResposta> findAll() {
				
		return createNamedQuery(LogResposta.LOGRESPOSTA_ALL).getResultList();

	}


}
