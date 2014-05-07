package br.com.procempa.save.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.jboss.seam.annotations.Name;

@Entity
@DiscriminatorValue(value=Pelagem.NAME)
@NamedQueries(value={
		@NamedQuery( name=Pelagem.FIND_ALL, 
				query="SELECT t FROM Pelagem t " +
				"ORDER BY t.descricao ASC"),
		@NamedQuery( name=Pelagem.FIND_BY_DESCRICAO, 
				query="SELECT t FROM Pelagem t " +
				"WHERE descricao = :descricao"),
		@NamedQuery( name=Pelagem.FIND_BY_DESCRICAO_COUNT_EDITAR, 
				query="SELECT count(t) FROM Pelagem t " +
				"WHERE descricao = :descricao "
				+ "and id <> :id"),
		@NamedQuery( name=Pelagem.FIND_BY_DESCRICAO_COUNT_NOVO, 
				query="SELECT count(t) FROM Pelagem t " +
				"WHERE descricao = :descricao ")
})
@Name(Pelagem.NAME)
public class Pelagem extends ApoioAbstract {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "pelagem";
	public static final String FIND_ALL = "pelagem.findAll";
	public static final String FIND_BY_DESCRICAO = "pelagem.findByDescricao";
	public static final String FIND_BY_DESCRICAO_COUNT_EDITAR = "pelagem.findByDescricaoCountEditar";
	public static final String FIND_BY_DESCRICAO_COUNT_NOVO = "pelagem.findByDescricaoCountNovo";		
}
