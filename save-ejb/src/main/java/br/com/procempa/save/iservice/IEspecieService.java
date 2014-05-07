package br.com.procempa.save.iservice;

import java.util.List;

import javax.ejb.Local;

import br.com.procempa.save.entity.Especie;

@Local
public interface IEspecieService extends IApoioAbstractService<Especie> {

	List<Especie> findAtivos();
	
 		
}
