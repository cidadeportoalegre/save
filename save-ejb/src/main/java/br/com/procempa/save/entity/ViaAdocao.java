package br.com.procempa.save.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.jboss.seam.annotations.Name;

@Entity
@DiscriminatorValue(value=ViaAdocao.NAME)
@NamedQueries(value={
		@NamedQuery( name=ViaAdocao.FIND_ALL, 
				query="SELECT t FROM ViaAdocao t " +
				"ORDER BY t.descricao ASC"),
		@NamedQuery( name=ViaAdocao.FIND_BY_DESCRICAO, 
				query="SELECT t FROM ViaAdocao t " +
				"WHERE descricao = :descricao"),
		@NamedQuery( name=ViaAdocao.FIND_BY_DESCRICAO_COUNT_EDITAR, 
				query="SELECT count(t) FROM ViaAdocao t " +
				"WHERE descricao = :descricao "
				+ "and id <> :id"),
		@NamedQuery( name=ViaAdocao.FIND_BY_DESCRICAO_COUNT_NOVO, 
				query="SELECT count(t) FROM ViaAdocao t " +
				"WHERE descricao = :descricao ")
})
@Name(ViaAdocao.NAME)
public class ViaAdocao extends ApoioAbstract {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "viaAdocao";
	public static final String FIND_ALL = "viaAdocao.findAll";
	public static final String FIND_BY_DESCRICAO = "viaAdocao.findByDescricao";
	public static final String FIND_BY_DESCRICAO_COUNT_EDITAR = "viaAdocao.findByDescricaoCountEditar";
	public static final String FIND_BY_DESCRICAO_COUNT_NOVO = "viaAdocao.findByDescricaoCountNovo";		
}
