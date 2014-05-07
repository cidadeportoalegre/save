package br.com.procempa.save.iservice;

import javax.ejb.Local;

import br.com.procempa.save.entity.TipoTramite;

@Local
public interface ITipoTramiteService extends IApoioAbstractService<TipoTramite> {

	public void importarTiposTramite();
}
