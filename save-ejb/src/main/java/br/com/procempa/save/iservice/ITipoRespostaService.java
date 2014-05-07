package br.com.procempa.save.iservice;

import javax.ejb.Local;

import br.com.procempa.save.entity.TipoResposta;

@Local
public interface ITipoRespostaService extends IApoioAbstractService<TipoResposta> {

	public void importarTiposResposta();
}
