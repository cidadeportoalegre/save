package br.com.procempa.save.iservice;

import java.util.List;

import javax.ejb.Local;

import br.com.procempa.arquitetura.business.interfaces.IBusiness;
import br.com.procempa.save.entity.RespostaSolicitacao;
import br.com.procempa.save.entity.Solicitacao;
import br.com.procempa.save.entity.Tramite;

@Local
public interface ITramiteService extends IBusiness<Tramite> {
	public List<Tramite> findAll(Integer firstRow, Integer maxResults);

	public Tramite findLastBySolicitacao(Solicitacao solicitacao);
	public boolean tramitarFalaPoa(Tramite tramite, RespostaSolicitacao resposta, Boolean encerrarProcesso);
}
