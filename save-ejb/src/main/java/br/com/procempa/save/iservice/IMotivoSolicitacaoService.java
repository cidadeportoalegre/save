package br.com.procempa.save.iservice;

import javax.ejb.Local;

import br.com.procempa.save.entity.MotivoSolicitacao;

@Local
public interface IMotivoSolicitacaoService extends IApoioAbstractService<MotivoSolicitacao> {

	public void importarMotivosSolicitacao();
	
 		
}
