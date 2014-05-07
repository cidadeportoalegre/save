package br.com.procempa.save.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.jboss.seam.annotations.Name;

@Entity
@DiscriminatorValue(value=SituacaoSolicitacao.NAME)
@NamedQueries(value={
		@NamedQuery( name=SituacaoSolicitacao.FIND_ALL, 
				query="SELECT t FROM SituacaoSolicitacao t " +
				"ORDER BY t.descricao ASC"),
		@NamedQuery( name=SituacaoSolicitacao.FIND_BY_DESCRICAO, 
				query="SELECT t FROM SituacaoSolicitacao t " +
				"WHERE descricao = :descricao"),
		@NamedQuery( name=SituacaoSolicitacao.FIND_BY_DESCRICAO_COUNT_EDITAR, 
				query="SELECT count(t) FROM SituacaoSolicitacao t " +
				"WHERE descricao = :descricao "
				+ "and id <> :id"),
		@NamedQuery( name=SituacaoSolicitacao.FIND_BY_DESCRICAO_COUNT_NOVO, 
				query="SELECT count(t) FROM SituacaoSolicitacao t " +
				"WHERE descricao = :descricao ")
})
@Name(SituacaoSolicitacao.NAME)
public class SituacaoSolicitacao extends ApoioAbstract {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "situacaoSolicitacao";
	public static final String FIND_ALL = "situacaoSolicitacao.findAll";	
	public static final String FIND_BY_DESCRICAO = "situacaoSolicitacao.findByDescricao";
	public static final String FIND_BY_DESCRICAO_COUNT_EDITAR = "situacaoSolicitacao.findByDescricaoCountEditar";
	public static final String FIND_BY_DESCRICAO_COUNT_NOVO = "situacaoSolicitacao.findByDescricaoCountNovo";	

}
