package br.com.procempa.save.service;

import java.util.List;

import javax.ejb.Stateless;

import org.jboss.seam.annotations.Name;

import br.com.procempa.falapoa.importacao.Importacao;
import br.com.procempa.save.entity.TipoTramite;
import br.com.procempa.save.iservice.ITipoTramiteService;

@Stateless
@Name(TipoTramiteService.SERVICE_NAME)
public class TipoTramiteService extends AbstractSaveService<TipoTramite> implements ITipoTramiteService {
	public static final String SERVICE_NAME = "tipoTramiteService";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TipoTramite> findAll() {
				
		return createNamedQuery(TipoTramite.FIND_ALL).getResultList();

	}

	@Override
	public void importarTiposTramite() {
		Importacao.getInstance().importarTiposTramite();		
	}

}
