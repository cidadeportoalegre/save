package br.com.procempa.save.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.jboss.seam.annotations.Name;

@Entity
@DiscriminatorValue(value=CausaAgenda.NAME)
@NamedQueries(value={
		@NamedQuery( name=CausaAgenda.FIND_ALL, 
				query="SELECT t FROM CausaAgenda t " +
				"ORDER BY t.descricao ASC"),
		@NamedQuery( name=CausaAgenda.FIND_BY_DESCRICAO, 
				query="SELECT t FROM CausaAgenda t " +
				"WHERE descricao = :descricao"),
		@NamedQuery( name=CausaAgenda.FIND_BY_DESCRICAO_COUNT_EDITAR, 
				query="SELECT count(t) FROM CausaAgenda t " +
				"WHERE descricao = :descricao "
				+ "and id <> :id"),
		@NamedQuery( name=CausaAgenda.FIND_BY_DESCRICAO_COUNT_NOVO, 
				query="SELECT count(t) FROM CausaAgenda t " +
				"WHERE descricao = :descricao ")
})
@Name(CausaAgenda.NAME)
public class CausaAgenda extends ApoioAbstract {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "causaAgenda";
	public static final String FIND_ALL = "causaAgenda.findAll";	
	public static final String FIND_BY_DESCRICAO = "causaAgenda.findByDescricao";
	public static final String FIND_BY_DESCRICAO_COUNT_EDITAR = "causaAgenda.findByDescricaoCountEditar";
	public static final String FIND_BY_DESCRICAO_COUNT_NOVO = "causaAgenda.findByDescricaoCountNovo";	

}
