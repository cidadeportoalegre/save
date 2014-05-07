package br.com.procempa.save.iservice;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import org.jboss.seam.security.Identity;

import br.com.procempa.arquitetura.business.interfaces.IBusiness;
import br.com.procempa.save.entity.IntervaloImportacao;
import br.com.procempa.save.entity.MotivoSolicitacao;
import br.com.procempa.save.entity.OrigemSolicitacao;
import br.com.procempa.save.entity.SituacaoSolicitacao;
import br.com.procempa.save.entity.Solicitacao;

@Local
public interface ISolicitacaoService extends IBusiness<Solicitacao> {
 		
	/**
	 * @param vinculadas 
	 * @return lista completa de solicitacoes
	 */
	public List<Solicitacao> findAll(Integer firstRow, Integer maxResults, Date data, IntervaloImportacao intervaloImportacao, Date dataDe, Date dataA, SituacaoSolicitacao situacaoSolicitacaoAtiva, Boolean vinculadas, String protocoloSolicitacao, Identity identity,
			OrigemSolicitacao origemSolicitacao, SituacaoSolicitacao situacaoSolicitacao, MotivoSolicitacao motivoSolicitacao, Boolean urgente, String cep);

	public Long getCountAll(Date data, IntervaloImportacao intervaloImportacao, Date dataDe, Date dataA, SituacaoSolicitacao situacaoSolicitacaoAtiva, Boolean vinculadas, String protocoloSolicitacao, Identity identity,
			OrigemSolicitacao origemSolicitacao, SituacaoSolicitacao situacaoSolicitacao, MotivoSolicitacao motivoSolicitacao, Boolean urgente, String cep);
	
	public void importarSolicitacoes(String interval);

	public Solicitacao carregaDadosCdl(Solicitacao solicitacao, Integer codigoCdl, String numero);
	
	public List<IntervaloImportacao> getIntervaloLista();
	
	public Boolean importarSolicitacao(String protocolo);

	public Solicitacao encerrarSolicitacao(Solicitacao solicitacao);

}
