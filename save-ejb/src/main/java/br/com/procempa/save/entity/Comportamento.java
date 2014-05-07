package br.com.procempa.save.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.jboss.seam.annotations.Name;

/**
 * @author rosmaria / leandro.bogoni
 * 
 */

@Entity
@DiscriminatorValue(value=Comportamento.NAME)
@NamedQueries(value={
		@NamedQuery( name=Comportamento.FIND_ALL, 
				query="SELECT t FROM Comportamento t " +
				"ORDER BY t.descricao ASC"),
		@NamedQuery( name=Comportamento.FIND_BY_DESCRICAO, 
				query="SELECT t FROM Comportamento t " +
				"WHERE descricao = :descricao"),
		@NamedQuery( name=Comportamento.FIND_BY_DESCRICAO_COUNT_EDITAR, 
				query="SELECT count(t) FROM Comportamento t " +
				"WHERE descricao = :descricao "
				+ "and id <> :id"),
		@NamedQuery( name=Comportamento.FIND_BY_DESCRICAO_COUNT_NOVO, 
				query="SELECT count(t) FROM Comportamento t " +
				"WHERE descricao = :descricao ")
})
@Name(Comportamento.NAME)
public class Comportamento extends ApoioAbstract {
	private static final long serialVersionUID = 1L;
	public static final String NAME = "comportamento";
	public static final String FIND_ALL = "comportamento.findAll";
	public static final String FIND_BY_DESCRICAO = "comportamento.findByDescricao";
	public static final String FIND_BY_DESCRICAO_COUNT_EDITAR = "comportamento.findByDescricaoCountEditar";
	public static final String FIND_BY_DESCRICAO_COUNT_NOVO = "comportamento.findByDescricaoCountNovo";	

}
