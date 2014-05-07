package br.com.procempa.save.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.jboss.seam.annotations.Name;

@Entity
@DiscriminatorValue(value=Idade.NAME)
@NamedQueries(value={
		@NamedQuery( name=Idade.FIND_ALL, 
				query="SELECT t FROM Idade t " +
				"ORDER BY t.descricao ASC"),
		@NamedQuery( name=Idade.FIND_BY_DESCRICAO, 
				query="SELECT t FROM Idade t " +
				"WHERE descricao = :descricao"),
		@NamedQuery( name=Idade.FIND_BY_DESCRICAO_COUNT_EDITAR, 
				query="SELECT count(t) FROM Idade t " +
				"WHERE descricao = :descricao "
				+ "and id <> :id"),
		@NamedQuery( name=Idade.FIND_BY_DESCRICAO_COUNT_NOVO, 
				query="SELECT count(t) FROM Idade t " +
				"WHERE descricao = :descricao ")
})
@Name(Idade.NAME)
public class Idade extends ApoioAbstract {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "idade";
	public static final String FIND_ALL = "idade.findAll";
	public static final String FIND_BY_DESCRICAO = "idade.findByDescricao";
	public static final String FIND_BY_DESCRICAO_COUNT_EDITAR = "idade.findByDescricaoCountEditar";
	public static final String FIND_BY_DESCRICAO_COUNT_NOVO = "idade.findByDescricaoCountNovo";	
	
}
