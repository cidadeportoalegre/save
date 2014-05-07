package br.com.procempa.save.iservice;

import java.util.List;

import br.com.procempa.arquitetura.business.interfaces.IBusiness;
import br.com.procempa.save.entity.LogTramite;


public interface ILogTramiteService extends IBusiness<LogTramite> {

		public List<LogTramite> findAll();

 		
}
