package br.com.procempa.save.iservice;

import java.util.List;

import br.com.procempa.arquitetura.business.interfaces.IBusiness;
import br.com.procempa.save.entity.LogSolicitacao;


public interface ILogSolicitacaoService extends IBusiness<LogSolicitacao> {

		public List<LogSolicitacao> findAll();

 		
}
