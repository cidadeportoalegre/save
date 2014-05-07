package br.com.procempa.save.iservice;

import java.util.List;

import javax.ejb.Local;

import br.com.procempa.arquitetura.business.interfaces.IBusiness;
import br.com.procempa.save.entity.Secretaria;

@Local
public interface ISecretariaService extends IBusiness<Secretaria> {
 		
	/**
	 * @return lista completa de perfis
	 */
	public List<Secretaria> findAll();
	
}
