package br.com.procempa.save.iservice;

import javax.ejb.Local;

import br.com.procempa.save.entity.SituacaoSolicitacao;

@Local
public interface ISituacaoSolicitacaoService extends IApoioAbstractService<SituacaoSolicitacao> {

	public SituacaoSolicitacao findByDescricao(String descricao);
	
 		
}
