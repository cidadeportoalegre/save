package br.com.procempa.save.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.jboss.seam.annotations.Name;

@Entity
@DiscriminatorValue(value=Porte.NAME)
@NamedQueries(value={
		@NamedQuery( name=Porte.FIND_ALL, 
				query="SELECT t FROM Porte t " +
				"ORDER BY t.descricao ASC"),
		@NamedQuery( name=Porte.FIND_BY_DESCRICAO, 
				query="SELECT t FROM Porte t " +
				"WHERE descricao = :descricao"),
		@NamedQuery( name=Porte.FIND_BY_DESCRICAO_COUNT_EDITAR, 
				query="SELECT count(t) FROM Porte t " +
				"WHERE descricao = :descricao "
				+ "and id <> :id"),
		@NamedQuery( name=Porte.FIND_BY_DESCRICAO_COUNT_NOVO, 
				query="SELECT count(t) FROM Porte t " +
				"WHERE descricao = :descricao ")
})
@Name(Porte.NAME)
public class Porte extends ApoioAbstract {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "porte";
	public static final String FIND_ALL = "porte.findAll";	
	public static final String FIND_BY_DESCRICAO = "porte.findByDescricao";
	public static final String FIND_BY_DESCRICAO_COUNT_EDITAR = "porte.findByDescricaoCountEditar";
	public static final String FIND_BY_DESCRICAO_COUNT_NOVO = "porte.findByDescricaoCountNovo";	
	

}
