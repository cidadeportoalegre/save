package br.com.procempa.save.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.jboss.seam.annotations.Name;

@Entity
@DiscriminatorValue(value=TipoTramite.NAME)
@NamedQueries(value={
		@NamedQuery( name=TipoTramite.FIND_ALL, 
				query="SELECT t FROM TipoTramite t " +
				"ORDER BY t.descricao ASC"),
		@NamedQuery( name=TipoTramite.FIND_BY_DESCRICAO, 
				query="SELECT t FROM TipoTramite t " +
				"WHERE descricao = :descricao"),
		@NamedQuery( name=TipoTramite.FIND_BY_DESCRICAO_COUNT_EDITAR, 
				query="SELECT count(t) FROM TipoTramite t " +
				"WHERE descricao = :descricao "
				+ "and id <> :id"),
		@NamedQuery( name=TipoTramite.FIND_BY_DESCRICAO_COUNT_NOVO, 
				query="SELECT count(t) FROM TipoTramite t " +
				"WHERE descricao = :descricao "),
		@NamedQuery( name=TipoTramite.FIND_BY_CODIGO_FALAPOA, 
				query="SELECT t FROM TipoTramite t " +
				"WHERE (t.codigoFalaPoa = :codigoFalaPoa) ") 	

})
@Name(TipoTramite.NAME)
public class TipoTramite extends ApoioAbstract {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "tipoTramite";
	public static final String FIND_ALL = "tipoTramite.findAll";	
	public static final String FIND_BY_DESCRICAO = "tipoTramite.findByDescricao";
	public static final String FIND_BY_DESCRICAO_COUNT_EDITAR = "tipoTramite.findByDescricaoCountEditar";
	public static final String FIND_BY_DESCRICAO_COUNT_NOVO = "tipoTramite.findByDescricaoCountNovo";
	public static final String FIND_BY_CODIGO_FALAPOA = "tipoTramite.findByCodigoFalaPoa";	
	
	private Integer codigoFalaPoa;

	public Integer getCodigoFalaPoa() {
		return codigoFalaPoa;
	}

	public void setCodigoFalaPoa(Integer codigoFalaPoa) {
		this.codigoFalaPoa = codigoFalaPoa;
	}

}
