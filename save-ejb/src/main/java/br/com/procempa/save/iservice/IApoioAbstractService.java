package br.com.procempa.save.iservice;

import java.util.List;

import br.com.procempa.arquitetura.business.interfaces.IBusiness;
import br.com.procempa.save.entity.ApoioAbstract;


public interface IApoioAbstractService<T extends ApoioAbstract> extends IBusiness<T> {

		public List<T> findAll();

 		
}
