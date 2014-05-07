package br.com.procempa.save.service;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.jboss.seam.annotations.Name;

import br.com.procempa.cdl.client.ClienteCDL;
import br.com.procempa.falapoa.importacao.Importacao;
import br.com.procempa.save.entity.Endereco;
import br.com.procempa.save.entity.LogSolicitante;
import br.com.procempa.save.entity.Solicitante;
import br.com.procempa.save.enumerator.Uf;
import br.com.procempa.save.iservice.ISolicitanteService;


@Stateless
@Name(SolicitanteService.SERVICE_NAME)
public class SolicitanteService extends AbstractSaveService<Solicitante> implements ISolicitanteService {
	public static final String SERVICE_NAME = "solicitanteService";

	@Override
	public List<Solicitante> pesquisar(String... filtro) {
		return null;
	}
	
	@Override
	public void excluir(Solicitante pojo, List<Solicitante> pojos) {
		excluir(pojo);
		pojos.remove( pojo );		
	}
	
	@Override
	public void excluir(Solicitante pojo) {
		remove( pojo );		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Solicitante> findAll() {		 	
		return createNamedQuery(Solicitante.FIND_ALL).getResultList();

	}
	
	@Override
	public Solicitante carregaDadosCdl(Solicitante solicitante, Integer codigoCdl, String numero) {
		try {
			ClienteCDL cdl = ClienteCDL.load(
					codigoCdl.toString(), numero);
			if(null == solicitante.getEndereco()){
				solicitante.setEndereco(new Endereco());
				solicitante.getEndereco().setCodigoCdl(codigoCdl);
				solicitante.getEndereco().setNumero(numero);
			}
			solicitante.getEndereco().setBairro(cdl.getNomeBairro());
			solicitante.getEndereco().setCepFormatado(cdl.getCep());
			solicitante.getEndereco().setLogradouro(cdl.getNomeLogr());
			solicitante.getEndereco().setMunicipio("Porto Alegre");
			solicitante.getEndereco().setUf(Uf.RS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return solicitante;
	}

	@Override
	public Long getCountFiltroNome(String criterioNome, String criterioDocumento) {
		Query query = createNamedQuery( Solicitante.SOLICITANTES_FILTRO_COUNT);
		query.setParameter("nome", "%" + (criterioNome == null ? "" : criterioNome.trim()) + "%"  );
		query.setParameter("documento", criterioDocumento);
		return (Long) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Solicitante> getListFiltroNome(Integer firstRow,
			Integer maxResults, String criterioNome, String criterioDocumento) {
		Query query = createNamedQuery( Solicitante.SOLICITANTES_FILTRO);
		query.setParameter("nome", "%" + (criterioNome == null ? "" : criterioNome.trim()) + "%"  );
		query.setParameter("documento", criterioDocumento);
		
		if(maxResults > 0) {
			query.setFirstResult( firstRow );
			query.setMaxResults( maxResults );
		}
		return query.getResultList();
	}

	
	@Override
	public Boolean salvar(Solicitante pojo) {
		// TODO Auto-generated method stub
		return super.salvar(pojo);
	}

	@Override
	public void importarSolicitantes(String interval) {
		BigInteger ultimaRefAtualizacao;
		Query query;
		try {
			query = createNamedQuery(LogSolicitante.LOGSOLICITANTE_FIND_LAST)
					.setMaxResults(1);
			ultimaRefAtualizacao = ((LogSolicitante) query.getSingleResult())
					.getUltimaRefAtualizacao();
		} catch (NoResultException e) {
			ultimaRefAtualizacao = new BigInteger("0");
		}
		if (null == ultimaRefAtualizacao)
			ultimaRefAtualizacao = new BigInteger("0");

		Importacao.getInstance().importarSolicitantes(
				ultimaRefAtualizacao.toString(), interval);

	}

}
