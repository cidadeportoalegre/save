package br.com.procempa.save.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.jboss.seam.annotations.Name;

@Entity
@DiscriminatorValue(value=ClassifRiscoGrupo.NAME)
@NamedQueries(value={
		@NamedQuery( name=ClassifRiscoGrupo.FIND_ALL, 
				query="SELECT t FROM ClassifRiscoGrupo t " +
				"ORDER BY t.descricao ASC"),
		@NamedQuery( name=ClassifRiscoGrupo.FIND_BY_DESCRICAO, 
				query="SELECT t FROM ClassifRiscoGrupo t " +
				"WHERE descricao = :descricao"),
		@NamedQuery( name=ClassifRiscoGrupo.FIND_BY_DESCRICAO_COUNT_EDITAR, 
				query="SELECT count(t) FROM ClassifRiscoGrupo t " +
				"WHERE descricao = :descricao "
				+ "and id <> :id"),
		@NamedQuery( name=ClassifRiscoGrupo.FIND_BY_DESCRICAO_COUNT_NOVO, 
				query="SELECT count(t) FROM ClassifRiscoGrupo t " +
				"WHERE descricao = :descricao ")
})
@Name(ClassifRiscoGrupo.NAME)
public class ClassifRiscoGrupo extends ApoioAbstract {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "classifRiscoGrupo";
	public static final String FIND_ALL = "classifRiscoGrupo.findAll";	
	public static final String FIND_BY_DESCRICAO = "classifRiscoGrupo.findByDescricao";
	public static final String FIND_BY_DESCRICAO_COUNT_EDITAR = "classifRiscoGrupo.findByDescricaoCountEditar";
	public static final String FIND_BY_DESCRICAO_COUNT_NOVO = "classifRiscoGrupo.findByDescricaoCountNovo";		

}
