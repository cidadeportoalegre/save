package br.com.procempa.save.service;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.lang.BooleanUtils;
import org.jboss.seam.annotations.Name;

import br.com.procempa.exceptions.PersistenceException;
import br.com.procempa.exceptions.ProcempaValidationException;
import br.com.procempa.falapoa.client.FalaPoaClient;
import br.com.procempa.falapoa.client.Solicitacao;
import br.com.procempa.falapoa.importacao.Importacao;
import br.com.procempa.save.entity.DesfechoSolicitacao;
import br.com.procempa.save.entity.RespostaSolicitacao;
import br.com.procempa.save.entity.Tramite;
import br.com.procempa.save.iservice.ISolicitacaoService;
import br.com.procempa.save.iservice.ITramiteService;


@Stateless
@Name(TramiteService.SERVICE_NAME)
public class TramiteService extends AbstractSaveService<Tramite> implements ITramiteService {
public static final String SERVICE_NAME = "tramiteService";

	//public static final Integer CODIGO_SEDA = 999900185;
	public static final Integer CODIGO_RESPOSTA_GENERICA = 151;
	
	//@EJB
	//private IRespostaSolicitacaoService respostaSolicitacaoService;	
	
	@EJB
	private ISolicitacaoService solicitacaoService;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Tramite> findAll(Integer firstRow, Integer maxResults) {
		Query query = createNamedQuery( Tramite.TRAMITES_ALL);
		
		if(maxResults > 0) {
			query.setFirstResult( firstRow );
			query.setMaxResults( maxResults );
		}
		return query.getResultList();
	}
	
	@Override
	public Tramite findLastBySolicitacao(br.com.procempa.save.entity.Solicitacao solicitacao){
		Tramite tramite = null;
		try {
			Query query = createNamedQuery( Tramite.TRAMITES_LAST_BY_SOLICITACAO);
			query.setParameter("solicitacao", solicitacao).setMaxResults(1);
			tramite = (Tramite)query.getSingleResult();
		} catch (NoResultException e) {
			//DO NOTHING
		}
		return refresh(evict(tramite));
	}
	
	@Override
	public boolean tramitarFalaPoa(Tramite tramite, RespostaSolicitacao resposta, Boolean encerrarProcesso) {
		Solicitacao[] solicitacoes;
		FalaPoaClient falaPoaClient = new FalaPoaClient();
		try {
			solicitacoes = falaPoaClient.consultaSolicitacao(Importacao.getInstance().getChaveWs(), tramite.getSolicitacao().getProtocoloFalapoa());		
			if(solicitacoes.length==1){
				if(!encerrarProcesso){
					efetuarTramite(solicitacoes[0], tramite, resposta, falaPoaClient);
				} else {
					efetuarResposta(solicitacoes[0], tramite, resposta, 1, falaPoaClient);
				}
			} else {
				throw new RemoteException();
			}
		} catch (Exception e) {
			throw new PersistenceException(new ProcempaValidationException("Problemas de comunicação com o sistema FalaPoa. Tente novamente mais tarde."));
		}
		return true;
	}
	
	private void efetuarTramite(Solicitacao solicitacao, Tramite tramite, RespostaSolicitacao respostaSolicitacao, FalaPoaClient falaPoaClient) throws Exception {
		String resposta = "";
		resposta = falaPoaClient.insereProximoTramiteServicoForcado(Importacao.getInstance().getChaveWs(), solicitacao.getNrSolicitacao(), solicitacao.getDtSolicitacao(), tramite.getSolicitacao().getMotivoSolicitacao().getSecretaria().getCodigoFalaPoa(), tramite.getTipoTramite().getCodigoFalaPoa());	
		if(! resposta.contains("Sucesso")){
			efetuarResposta(solicitacao, tramite,respostaSolicitacao, 0, falaPoaClient);
		}
	}
	
	private void efetuarResposta(Solicitacao solicitacao, Tramite tramite, RespostaSolicitacao respostaSolicitacao, int encerrar, FalaPoaClient falaPoaClient) throws Exception{
		String mensagemEnviada = respostaSolicitacao.getDescricaoResposta();
		
		if(encerrar==1 && null != tramite.getDesfechoSolicitacao()){
			mensagemEnviada = mensagemEnviada + "  :  " + tramite.getDesfechoSolicitacao().getDescricao();
		}
		String resposta = "";
		
		Integer tipoResposta = respostaSolicitacao.getTipoResposta().getCodigoFalaPoa();
		if (null == tipoResposta){
			tipoResposta = CODIGO_RESPOSTA_GENERICA;
		}
		//Recebe tramite
		resposta = falaPoaClient.recebeTramiteServico(Importacao.getInstance().getChaveWs(), solicitacao.getNrSolicitacao(), solicitacao.getDtSolicitacao(), new Timestamp(Calendar.getInstance().getTimeInMillis()).toString());
		//Responde tramite
		resposta = falaPoaClient.insereRespostaServicoForcado(Importacao.getInstance().getChaveWs(), solicitacao.getNrSolicitacao(), solicitacao.getDtSolicitacao(), tipoResposta, mensagemEnviada, encerrar, new Timestamp(Calendar.getInstance().getTimeInMillis()).toString());
		//System.out.println(resposta);
		if(resposta.contains("Solicitação finalizada")){
			//System.out.println(resposta);
		}
		else if(resposta.contains("Tramite já respondido")){
			throw new RemoteException(resposta);
		}
		else if(resposta.contains("Sucesso")){
			if(!tramite.getEncerrarProcesso()){
				efetuarTramite(solicitacao, tramite, respostaSolicitacao, falaPoaClient);
			}
		}
		else{
			throw new RemoteException(resposta);
		}
	}
	
	
	@Override
	public Boolean salvar(Tramite tramite) {
		
		br.com.procempa.save.entity.Solicitacao solicitacao = tramite.getSolicitacao();

		RespostaSolicitacao respostaSolicitacao = tramite.getRespostaList().iterator().next();
		
		tramite.getRespostaList().clear();
		
		if (!tramite.getEncerrarProcesso()) {
			Tramite tramiteAnterior = findLastBySolicitacao(solicitacao);
			tramite.setSeqFase(tramiteAnterior.getSeqFase() + 1);
			tramite.setDataTramite(GregorianCalendar.getInstance());
			if (BooleanUtils.isTrue(tramitarFalaPoa(tramite, respostaSolicitacao, false))) {
				respostaSolicitacao.setTramite(tramiteAnterior);
				tramiteAnterior.getRespostaList().add(respostaSolicitacao);
				solicitacao.setPossuiTramites( Boolean.TRUE );
				tramite = persist(tramite);
			}
		} else {
			List<Tramite> tramitesFalaPoa = new LinkedList<Tramite>();
			
			//encerra solicitações vinculadas a solicitação atual
			for (br.com.procempa.save.entity.Solicitacao solicitacaoVinculada : solicitacao.getSolicitacaoVinculadaList()) {
				encerraSolicitacao(tramite.getDesfechoSolicitacao(), respostaSolicitacao, solicitacaoVinculada, tramitesFalaPoa);
			}
			
			//encerra solicitação pai
			encerraSolicitacao(tramite.getDesfechoSolicitacao(), respostaSolicitacao, solicitacao, tramitesFalaPoa);
			
			//atualiza FalaPOA para as solicitações encerradas
			for (Tramite tramiteFPA : tramitesFalaPoa) {
				boolean tramitarFalaPoa = tramitarFalaPoa(tramiteFPA, respostaSolicitacao, true);
				if (! tramitarFalaPoa) {
					throw new PersistenceException("Erro na atualização do FalaPoa", null);
				}
			}
		}
		return true;
	}
	
	private void encerraSolicitacao(DesfechoSolicitacao desfechoSolicitacao, RespostaSolicitacao respostaSolicitacao, br.com.procempa.save.entity.Solicitacao solicitacao, List<Tramite> tramitesFalaPoa) {
		
		RespostaSolicitacao respSol = new RespostaSolicitacao();
		respSol.setDescricaoResposta(respostaSolicitacao.getDescricaoResposta());
		respSol.setSeqResp(respostaSolicitacao.getSeqResp());
		respSol.setTipoResposta(respostaSolicitacao.getTipoResposta());
		
		Tramite tramiteAnterior = findLastBySolicitacao(solicitacao);
		tramiteAnterior.setEncerrarProcesso(true);
		tramiteAnterior.setDesfechoSolicitacao(desfechoSolicitacao);
		
		tramitesFalaPoa.add(tramiteAnterior);
		
		respSol.setTramite(tramiteAnterior);
		tramiteAnterior.getRespostaList().add(respSol);
		
		solicitacao.setPossuiTramites( Boolean.TRUE );
		solicitacaoService.encerrarSolicitacao(solicitacao);
	}
}
