package br.com.procempa.save.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.jboss.seam.annotations.Name;

@Entity
@DiscriminatorValue(value=Especie.NAME)
@NamedQueries(value={
		@NamedQuery( name=Especie.FIND_ALL, 
				query="SELECT t FROM Especie t " +
				"ORDER BY t.descricao ASC"),		
		@NamedQuery( name=Especie.FIND_ATIVOS, 
				query="SELECT t FROM Especie t " +
				"WHERE t.ativo = 1" +
				"ORDER BY t.descricao ASC"),
		@NamedQuery( name=Especie.FIND_BY_DESCRICAO, 
				query="SELECT t FROM Especie t " +
				"WHERE descricao = :descricao"),
		@NamedQuery( name=Especie.FIND_BY_DESCRICAO_COUNT_EDITAR, 
				query="SELECT count(t) FROM Especie t " +
				"WHERE descricao = :descricao "
				+ "and id <> :id"),
		@NamedQuery( name=Especie.FIND_BY_DESCRICAO_COUNT_NOVO, 
				query="SELECT count(t) FROM Especie t " +
				"WHERE descricao = :descricao ")
})
@Name(Especie.NAME)
public class Especie extends ApoioAbstract {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "especie";
	public static final String FIND_ALL = "especie.findAll";
	public static final String FIND_ATIVOS = "especie.findAtivos";	
	public static final String FIND_BY_DESCRICAO = "especie.findByDescricao";
	public static final String FIND_BY_DESCRICAO_COUNT_EDITAR = "especie.findByDescricaoCountEditar";
	public static final String FIND_BY_DESCRICAO_COUNT_NOVO = "especie.findByDescricaoCountNovo";		
	
}
