package br.com.procempa.save.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.jboss.seam.annotations.Name;

@Entity
@DiscriminatorValue(value=TipoSolicitante.NAME)
@NamedQueries(value={
		@NamedQuery( name=TipoSolicitante.FIND_ALL, 
				query="SELECT t FROM TipoSolicitante t " +
				"ORDER BY t.descricao ASC"),
		@NamedQuery( name=TipoSolicitante.FIND_BY_DESCRICAO, 
				query="SELECT t FROM TipoSolicitante t " +
				"WHERE descricao = :descricao"),
		@NamedQuery( name=TipoSolicitante.FIND_BY_DESCRICAO_COUNT_EDITAR, 
				query="SELECT count(t) FROM TipoSolicitante t " +
				"WHERE descricao = :descricao "
				+ "and id <> :id"),
		@NamedQuery( name=TipoSolicitante.FIND_BY_DESCRICAO_COUNT_NOVO, 
				query="SELECT count(t) FROM TipoSolicitante t " +
				"WHERE descricao = :descricao ")
})
@Name(TipoSolicitante.NAME)
public class TipoSolicitante extends ApoioAbstract {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "tipoSolicitante";
	public static final String FIND_ALL = "tipoSolicitante.findAll";		
	public static final String FIND_BY_DESCRICAO = "tipoSolicitante.findByDescricao";
	public static final String FIND_BY_DESCRICAO_COUNT_EDITAR = "tipoSolicitante.findByDescricaoCountEditar";
	public static final String FIND_BY_DESCRICAO_COUNT_NOVO = "tipoSolicitante.findByDescricaoCountNovo";	
	
	private Integer cotaMes;

	public Integer getCotaMes() {
		return cotaMes;
	}

	public void setCotaMes(Integer cotaMes) {
		this.cotaMes = cotaMes;
	}
}
