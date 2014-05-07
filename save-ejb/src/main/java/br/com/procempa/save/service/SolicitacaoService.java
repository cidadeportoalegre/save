package br.com.procempa.save.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.StringTokenizer;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.validator.InvalidValue;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.security.Identity;

import br.com.procempa.arquitetura.seguranca.entidade.Role;
import br.com.procempa.cdl.client.ClienteCDL;
import br.com.procempa.exceptions.ProcempaValidationException;
import br.com.procempa.falapoa.importacao.Importacao;
import br.com.procempa.save.entity.Endereco;
import br.com.procempa.save.entity.IntervaloImportacao;
import br.com.procempa.save.entity.LogSolicitacao;
import br.com.procempa.save.entity.MotivoSolicitacao;
import br.com.procempa.save.entity.OrigemSolicitacao;
import br.com.procempa.save.entity.Perfil;
import br.com.procempa.save.entity.SituacaoSolicitacao;
import br.com.procempa.save.entity.Solicitacao;
import br.com.procempa.save.entity.Usuario;
import br.com.procempa.save.enumerator.Uf;
import br.com.procempa.save.iservice.IMotivoSolicitacaoService;
import br.com.procempa.save.iservice.IPerfilService;
import br.com.procempa.save.iservice.ISecretariaService;
import br.com.procempa.save.iservice.ISolicitacaoService;
import br.com.procempa.save.iservice.ISolicitanteService;

@Stateless
@Name(SolicitacaoService.SERVICE_NAME)
public class SolicitacaoService extends AbstractSaveService<Solicitacao>
		implements ISolicitacaoService {
	public static final String SERVICE_NAME = "solicitacaoService";
	
	public static final int ATIVA = 1;
	public static final int ENCERRADA = 2;
	public static final int CANCELADA = 3;
	
	public static final String ATIVA_DESC = "Ativa";
	public static final String ENCERRADA_DESC = "Encerrada";
	public static final String CANCELADA_DESC = "Cancelada";
	
	public static String chaveSeguranca;
	
	@EJB
	private ISolicitanteService solicitanteService;
	
	@EJB
	private IMotivoSolicitacaoService motivoSolicitacaoService;
	
	@EJB
	private IPerfilService perfilService;
	
	@EJB
	private ISecretariaService secretariaService;

	@Override
	public Boolean salvar(Solicitacao pojo) {
		ArrayList<InvalidValue> erros = new ArrayList<InvalidValue>();
		if (null == pojo.getProtocoloMp() && null == pojo.getProtocoloPa()
				&& null == pojo.getProtocoloFalapoa()) {
			erros.add(new InvalidValue(
					"Preencher pelo menos 1 Nº de protocolo",
					Solicitacao.class, "", null, null));
		}
		if (pojo.getSecretariaList()==null || pojo.getSecretariaList().size() == 0) {
			erros.add(new InvalidValue("Selecionar pelo menos 1 secretaria",
					Solicitacao.class, "", null, null));
		}
		if (erros.size() > 0) {
			throw new ProcempaValidationException(
					erros.toArray(new InvalidValue[erros.size()]));
		}
		persist(pojo);
		return true;
	}

	@Override
	public List<Solicitacao> pesquisar(String... filtro) {
		return null;
	}

	@Override
	public void excluir(Solicitacao pojo, List<Solicitacao> pojos) {
		excluir(pojo);
		pojos.remove(pojo);
	}

	@Override
	public void excluir(Solicitacao pojo) {
		remove(pojo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Solicitacao> findAll(Integer firstRow, Integer maxResults,
			Date data, IntervaloImportacao intervaloImportacao, Date dataDe,
			Date dataA, SituacaoSolicitacao situacaoSolicitacaoAtiva, Boolean vinculadas,
			String protocoloSolicitacao, Identity identity,
			OrigemSolicitacao origemSolicitacao,
			SituacaoSolicitacao situacaoSolicitacao,
			MotivoSolicitacao motivoSolicitacao, Boolean urgente, String cep) {
		Query query = createNamedQuery(Solicitacao.SOLICITACOES_ALL);
		Calendar ldataDe = null;
		Calendar ldataA = null;

		if (dataDe!= null || dataA!=null) {
			if (dataDe!=null && dataA==null) {
				dataA = Calendar.getInstance().getTime();
			}
		} else if (data!=null) {
			dataDe = data;
			dataA = data;
		}
		
		if (null != dataDe) {

			ldataDe = Calendar.getInstance();
			ldataDe.setTime((Date) dataDe.clone());
			ldataDe.set(Calendar.HOUR_OF_DAY, 0);
			ldataDe.set(Calendar.MINUTE, 0);
			ldataDe.set(Calendar.SECOND, 0);
		}
		
		if (null != dataA) {
			
			ldataA = Calendar.getInstance();
			ldataA.setTime((Date) dataA.clone());
			ldataA.set(Calendar.HOUR_OF_DAY, 23);
			ldataA.set(Calendar.MINUTE, 59);
			ldataA.set(Calendar.SECOND, 59);
		}
		
		query.setParameter("informouDataDe", ldataDe!=null);
		query.setParameter("dataDe", ldataDe);
		query.setParameter("informouDataA", ldataA!=null);
		query.setParameter("dataA", ldataA);
		
		query.setParameter("intervalo", intervaloImportacao);
		query.setParameter("situacao", situacaoSolicitacaoAtiva);
		query.setParameter("protocolo", (protocoloSolicitacao == null ? null
				: protocoloSolicitacao.trim()));
		query.setParameter("secretarias", getSecretarias(identity
				.getCredentials().getUsername()));
		query.setParameter("administrador",
				identity.hasRole("Administrador") ? 1 : 0);
		query.setParameter("origemSolicitacao", origemSolicitacao);
		query.setParameter("situacaoSolicitacao", situacaoSolicitacao);
		query.setParameter("motivoSolicitacao", motivoSolicitacao);
		query.setParameter("origemSolicitacao", origemSolicitacao);
		query.setParameter("urgente", urgente ? 1 : 0);
		query.setParameter("vinculadas", vinculadas ? 1 : 0);
		query.setParameter("cep", null == cep ? null : "%" + cep + "%");

		if (maxResults > 0) {
			query.setFirstResult(firstRow);
			query.setMaxResults(maxResults);
		}

		return query.getResultList();
	}

	private List<Long> getSecretarias(String username) {
		List<Long> secretarias = new ArrayList<Long>();
		StringTokenizer st = new StringTokenizer(username, "\\\\");
		Query query = createNamedQuery(Usuario.FIND_BY_DOMAIN_USER);
		query.setParameter("domain", st.nextToken());
		query.setParameter("userName", st.nextToken());
		Usuario usuario = (Usuario) query.getSingleResult();
		ListIterator<Role> iterator = usuario.getUser().getRoleList()
				.listIterator();
		while (iterator.hasNext()) {
			query = createNamedQuery(Perfil.PERFIL_BY_ROLE);
			query.setParameter("role", iterator.next());
			Perfil perfil = (Perfil) query.getSingleResult();
			if (null != perfil.getSecretaria())
				secretarias.add(perfil.getSecretaria().getId());
		}
		return secretarias;
	}

	@Override
	public Long getCountAll(Date data, IntervaloImportacao intervaloImportacao,
			Date dataDe, Date dataA,
			SituacaoSolicitacao situacaoSolicitacaoAtiva, Boolean vinculadas, 
			String protocoloSolicitacao, Identity identity,
			OrigemSolicitacao origemSolicitacao,
			SituacaoSolicitacao situacaoSolicitacao,
			MotivoSolicitacao motivoSolicitacao, Boolean urgente, String cep) {
		Query query = createNamedQuery(Solicitacao.SOLICITACOES_ALL_COUNT);
		
		if (dataDe!= null || dataA!=null) {
			if (dataDe!=null && dataA==null) {
				dataA = Calendar.getInstance().getTime();
			}
		} else if (data!=null) {
			dataDe = data;
			dataA = data;
		}
		
		Calendar ldataDe = null;
		Calendar ldataA = null;
		
		if (null != dataDe) {

			ldataDe = Calendar.getInstance();
			ldataDe.setTime((Date) dataDe.clone());
			ldataDe.set(Calendar.HOUR_OF_DAY, 0);
			ldataDe.set(Calendar.MINUTE, 0);
			ldataDe.set(Calendar.SECOND, 0);
		}
		
		if (null != dataA) {
			
			ldataA = Calendar.getInstance();
			ldataA.setTime((Date) dataA.clone());
			ldataA.set(Calendar.HOUR_OF_DAY, 23);
			ldataA.set(Calendar.MINUTE, 59);
			ldataA.set(Calendar.SECOND, 59);
		}
		
		query.setParameter("informouDataDe", ldataDe!=null);
		query.setParameter("dataDe", ldataDe);
		query.setParameter("informouDataA", ldataA!=null);
		query.setParameter("dataA", ldataA);
		
		query.setParameter("intervalo", intervaloImportacao);
		query.setParameter("situacao", situacaoSolicitacaoAtiva);
		query.setParameter("protocolo", (protocoloSolicitacao == null ? null
				: protocoloSolicitacao.trim()));
		query.setParameter("secretarias", getSecretarias(identity
				.getCredentials().getUsername()));
		query.setParameter("administrador",
				identity.hasRole("Administrador") ? 1 : 0);
		query.setParameter("origemSolicitacao", origemSolicitacao);
		query.setParameter("situacaoSolicitacao", situacaoSolicitacao);
		query.setParameter("motivoSolicitacao", motivoSolicitacao);
		query.setParameter("origemSolicitacao", origemSolicitacao);
		query.setParameter("urgente", urgente ? 1 : 0);
		query.setParameter("vinculadas", vinculadas ? 1 : 0);
		query.setParameter("cep", null == cep ? null : "%" + cep + "%");
		return (Long) query.getSingleResult();
	}

	@Override
	public void importarSolicitacoes(String interval) {
		BigInteger ultimaRefAtualizacao;
		Query query;
		try {
			query = createNamedQuery(LogSolicitacao.LOGSOLICITACAO_FIND_LAST)
					.setMaxResults(1);
			ultimaRefAtualizacao = ((LogSolicitacao) query.getSingleResult())
					.getUltimaRefAtualizacao();
		} catch (NoResultException e) {
			ultimaRefAtualizacao = new BigInteger("0");
		}
		if (null == ultimaRefAtualizacao)
			ultimaRefAtualizacao = new BigInteger("0");

		Importacao.getInstance().importarSolicitacoes(
				ultimaRefAtualizacao.toString(), interval);

	}

	@Override
	public Solicitacao carregaDadosCdl(Solicitacao solicitacao,
			Integer codigoCdl, String numero) {
		try {
			ClienteCDL cdl = ClienteCDL.load(codigoCdl.toString(), numero);
			if (null == solicitacao.getEnderecoOcorrencia()) {
				solicitacao.setEnderecoOcorrencia(new Endereco());
				solicitacao.getEnderecoOcorrencia().setCodigoCdl(codigoCdl);
				solicitacao.getEnderecoOcorrencia().setNumero(numero);
			}
			solicitacao.getEnderecoOcorrencia().setBairro(cdl.getNomeBairro());
			solicitacao.getEnderecoOcorrencia().setCepFormatado(cdl.getCep());
			solicitacao.getEnderecoOcorrencia()
					.setLogradouro(cdl.getNomeLogr());
			solicitacao.getEnderecoOcorrencia().setMunicipio("Porto Alegre");
			solicitacao.getEnderecoOcorrencia().setUf(Uf.RS);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return solicitacao;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<IntervaloImportacao> getIntervaloLista() {
		Query query = createNamedQuery(IntervaloImportacao.INTERVALOIMPORTACOES_ALL);
		return query.getResultList();
	}
	
	@Override
	public Boolean importarSolicitacao(String protocoloFalaPoa) {
		ArrayList<InvalidValue> erros = new ArrayList<InvalidValue>();
		if (null == protocoloFalaPoa || protocoloFalaPoa.equals("")) {
			erros.add(new InvalidValue("Protocolo não pode ser vazio",
					Solicitacao.class, "", null, null));
			throw new ProcempaValidationException(
					erros.toArray(new InvalidValue[erros.size()]));
		}
		String interval = "0 30 8 * * ?";
		try {
			if (Importacao.getInstance()
					.importarSolicitacao(protocoloFalaPoa.trim(), interval)
					.equals("0")) {
				erros.add(new InvalidValue(
						"Nenhuma solicitação encontrada com o número de protocolo fornecido",
						Solicitacao.class, "", null, null));
			}
		} catch (Exception e) {
			erros.add(new InvalidValue("Ocorreu um erro durante a importação",
					Solicitacao.class, "", null, null));
			throw new ProcempaValidationException(
					erros.toArray(new InvalidValue[erros.size()]));
		}
		if (erros.size() > 0)
			throw new ProcempaValidationException(
					erros.toArray(new InvalidValue[erros.size()]));
		return true;
	}
	
	@Override
	public Solicitacao encerrarSolicitacao(Solicitacao solicitacao) {
		solicitacao.setDataEncerramento(Calendar.getInstance());
		try {
			Query query = createNamedQuery(SituacaoSolicitacao.FIND_BY_DESCRICAO);
			query.setParameter("descricao", ENCERRADA_DESC);
			solicitacao.setSituacaoSolicitacao((SituacaoSolicitacao) query.getSingleResult());
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		
		salvar(solicitacao);
		
		return solicitacao;
	}

}