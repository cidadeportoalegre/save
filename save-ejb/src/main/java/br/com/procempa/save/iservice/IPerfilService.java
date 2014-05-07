package br.com.procempa.save.iservice;

import java.util.List;

import javax.ejb.Local;

import br.com.procempa.arquitetura.business.interfaces.IBusiness;
import br.com.procempa.save.entity.Perfil;

@Local
public interface IPerfilService extends IBusiness<Perfil> {
 		
	/**
	 * @return lista completa de perfis
	 */
	public List<Perfil> findAll();
	
}
