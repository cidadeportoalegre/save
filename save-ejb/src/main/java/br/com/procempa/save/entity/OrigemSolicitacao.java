package br.com.procempa.save.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.jboss.seam.annotations.Name;

@Entity
@DiscriminatorValue(value=OrigemSolicitacao.NAME)
@NamedQueries(value={
		@NamedQuery( name=OrigemSolicitacao.FIND_ALL, 
				query="SELECT t FROM OrigemSolicitacao t " +
				"ORDER BY t.descricao ASC"),
		@NamedQuery( name=OrigemSolicitacao.FIND_BY_DESCRICAO, 
				query="SELECT t FROM OrigemSolicitacao t " +
				"WHERE descricao = :descricao"),
		@NamedQuery( name=OrigemSolicitacao.FIND_BY_DESCRICAO_COUNT_EDITAR, 
				query="SELECT count(t) FROM OrigemSolicitacao t " +
				"WHERE descricao = :descricao "
				+ "and id <> :id"),
		@NamedQuery( name=OrigemSolicitacao.FIND_BY_DESCRICAO_COUNT_NOVO, 
				query="SELECT count(t) FROM OrigemSolicitacao t " +
				"WHERE descricao = :descricao ")
})
@Name(OrigemSolicitacao.NAME)
public class OrigemSolicitacao extends ApoioAbstract {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "origemSolicitacao";
	public static final String FIND_ALL = "origemSolicitacao.findAll";	
	public static final String FIND_BY_DESCRICAO = "origemSolicitacao.findByDescricao";
	public static final String FIND_BY_DESCRICAO_COUNT_EDITAR = "origemSolicitacao.findByDescricaoCountEditar";
	public static final String FIND_BY_DESCRICAO_COUNT_NOVO = "origemSolicitacao.findByDescricaoCountNovo";		

}
