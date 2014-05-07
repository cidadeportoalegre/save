package br.com.procempa.save.service;

import java.util.List;

import javax.ejb.Stateless;

import org.jboss.seam.annotations.Name;

import br.com.procempa.save.entity.LogSolicitacao;
import br.com.procempa.save.iservice.ILogSolicitacaoService;

@Stateless
@Name(LogSolicitacaoService.SERVICE_NAME)
public class LogSolicitacaoService extends AbstractSaveService<LogSolicitacao> implements ILogSolicitacaoService {
	public static final String SERVICE_NAME = "logSolicitacaoService";

	@SuppressWarnings("unchecked")
	@Override
	public List<LogSolicitacao> findAll() {
				
		return createNamedQuery(LogSolicitacao.LOGSOLICITACAO_ALL).getResultList();

	}


}
