package br.com.procempa.save.action;

import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.core.Events;

import br.com.procempa.interceptors.ActionInterceptor;
import br.com.procempa.save.entity.LogSolicitacao;
import br.com.procempa.save.iservice.ILogSolicitacaoService;
import br.com.procempa.save.service.LogSolicitacaoService;

/**
 * @author bridi
 * 
 */
@Name("logAction")
@Scope(ScopeType.CONVERSATION)
@ActionInterceptor
public class LogAction extends AbstractSaveAction {
	private static final long serialVersionUID = 1L;
	   

	@In(value = LogSolicitacaoService.SERVICE_NAME, create = true)
	private ILogSolicitacaoService logSolicitacaoService;
	
	@DataModel
	private List<LogSolicitacao> logSolicitacaoLista;

	@Factory("logSolicitacaoLista")
	@Observer("logSolicitacaoObserver")
	public void loadLogSolicitacaoLista() {
		this.setLogSolicitacaoLista(logSolicitacaoService.findAll());			
	}
		
	public String filtrar(){
		Events.instance().raiseEvent("logSolicitacaoObserver");
		return "listar";
	}
		

	public List<LogSolicitacao> getLogSolicitacaoLista() {
		return logSolicitacaoLista;
	}

	public void setLogSolicitacaoLista(List<LogSolicitacao> logLista) {
		this.logSolicitacaoLista = logLista;
	}
}

