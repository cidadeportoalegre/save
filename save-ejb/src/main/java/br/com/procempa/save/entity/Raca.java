package br.com.procempa.save.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.jboss.seam.annotations.Name;

@Entity
@DiscriminatorValue(value=Raca.NAME)
@NamedQueries(value={
		@NamedQuery( name=Raca.FIND_ALL, 
				query="SELECT t FROM Raca t " +
				"ORDER BY t.descricao ASC"),
		@NamedQuery( name=Raca.FIND_BY_DESCRICAO, 
				query="SELECT t FROM Raca t " +
				"WHERE descricao = :descricao and especie = :especie"),
		@NamedQuery( name=Raca.FIND_BY_DESCRICAO_COUNT_EDITAR, 
				query="SELECT count(t) FROM Raca t " +
				"WHERE descricao = :descricao and especie = :especie "
				+ "and id <> :id"),
		@NamedQuery( name=Raca.FIND_BY_DESCRICAO_COUNT_NOVO, 
				query="SELECT count(t) FROM Raca t " +
				"WHERE descricao = :descricao and especie = :especie ")
})
@Name(Raca.NAME)
public class Raca extends ApoioAbstract {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "raca";
	public static final String FIND_ALL = "raca.findAll";
	public static final String FIND_BY_DESCRICAO = "raca.findByDescricao";
	public static final String FIND_BY_DESCRICAO_COUNT_EDITAR = "raca.findByDescricaoCountEditar";
	public static final String FIND_BY_DESCRICAO_COUNT_NOVO = "raca.findByDescricaoCountNovo";	
	
	private Especie especie;
	
	@ManyToOne(fetch=FetchType.LAZY)
	public Especie getEspecie() {
		return especie;
	}

	public void setEspecie(Especie especie) {
		this.especie = especie;
	}
}
