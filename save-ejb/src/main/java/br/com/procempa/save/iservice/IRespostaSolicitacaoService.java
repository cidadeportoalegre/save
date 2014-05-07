package br.com.procempa.save.iservice;

import java.util.List;

import javax.ejb.Local;

import br.com.procempa.arquitetura.business.interfaces.IBusiness;
import br.com.procempa.save.entity.RespostaSolicitacao;
import br.com.procempa.save.entity.Tramite;

@Local
public interface IRespostaSolicitacaoService extends IBusiness<RespostaSolicitacao> {
	public List<RespostaSolicitacao> findAll(Integer firstRow, Integer maxResults);

	public RespostaSolicitacao findLast(Tramite tramite);
}
