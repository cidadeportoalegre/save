package br.com.procempa.save.service;

import java.util.List;

import javax.ejb.Stateless;

import org.jboss.seam.annotations.Name;

import br.com.procempa.falapoa.importacao.Importacao;
import br.com.procempa.save.entity.TipoResposta;
import br.com.procempa.save.iservice.ITipoRespostaService;

@Stateless
@Name(TipoRespostaService.SERVICE_NAME)
public class TipoRespostaService extends AbstractSaveService<TipoResposta> implements ITipoRespostaService {
	public static final String SERVICE_NAME = "tipoRespostaService";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TipoResposta> findAll() {
				
		return createNamedQuery(TipoResposta.FIND_ALL).getResultList();

	}

	@Override
	public void importarTiposResposta() {
		Importacao.getInstance().importarTiposResposta();
	}
}
