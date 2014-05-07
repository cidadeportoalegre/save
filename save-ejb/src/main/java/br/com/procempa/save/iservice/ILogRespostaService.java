package br.com.procempa.save.iservice;

import java.util.List;

import br.com.procempa.arquitetura.business.interfaces.IBusiness;
import br.com.procempa.save.entity.LogResposta;


public interface ILogRespostaService extends IBusiness<LogResposta> {

		public List<LogResposta> findAll();

 		
}
