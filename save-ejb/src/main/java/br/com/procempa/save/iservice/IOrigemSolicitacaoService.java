package br.com.procempa.save.iservice;

import javax.ejb.Local;

import br.com.procempa.save.entity.OrigemSolicitacao;

@Local
public interface IOrigemSolicitacaoService extends IApoioAbstractService<OrigemSolicitacao> {

	public OrigemSolicitacao findByDescricao(String origemManual);
	
 		
}
