package br.com.procempa.save.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.jboss.seam.annotations.Name;

@Entity
@DiscriminatorValue(value=DesfechoSolicitacao.NAME)
@NamedQueries(value={
		@NamedQuery( name=DesfechoSolicitacao.FIND_ALL, 
				query="SELECT t FROM DesfechoSolicitacao t " +
				"ORDER BY t.descricao ASC"),
		@NamedQuery( name=DesfechoSolicitacao.FIND_BY_DESCRICAO, 
				query="SELECT t FROM DesfechoSolicitacao t " +
				"WHERE descricao = :descricao"),
		@NamedQuery( name=DesfechoSolicitacao.FIND_BY_DESCRICAO_COUNT_EDITAR, 
				query="SELECT count(t) FROM DesfechoSolicitacao t " +
				"WHERE descricao = :descricao "
				+ "and id <> :id"),
		@NamedQuery( name=DesfechoSolicitacao.FIND_BY_DESCRICAO_COUNT_NOVO, 
				query="SELECT count(t) FROM DesfechoSolicitacao t " +
				"WHERE descricao = :descricao ")
})
@Name(DesfechoSolicitacao.NAME)
public class DesfechoSolicitacao extends ApoioAbstract {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "desfechoSolicitacao";
	public static final String FIND_ALL = "desfechoSolicitacao.findAll";	
	public static final String FIND_BY_DESCRICAO = "desfechoSolicitacao.findByDescricao";
	public static final String FIND_BY_DESCRICAO_COUNT_EDITAR = "desfechoSolicitacao.findByDescricaoCountEditar";
	public static final String FIND_BY_DESCRICAO_COUNT_NOVO = "desfechoSolicitacao.findByDescricaoCountNovo";	
	
	private Boolean encerraAutomatico156;
	
	public Boolean getEncerraAutomatico156() {
		return encerraAutomatico156;
	}
	
	public void setEncerraAutomatico156(Boolean encerraautomatico156) {
		this.encerraAutomatico156 = encerraautomatico156;
	}
}
