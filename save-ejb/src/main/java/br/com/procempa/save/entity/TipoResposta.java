package br.com.procempa.save.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.hibernate.validator.Length;
import org.jboss.seam.annotations.Name;

@Entity
@DiscriminatorValue(value=TipoResposta.NAME)
@NamedQueries(value={
		@NamedQuery( name=TipoResposta.FIND_ALL, 
				query="SELECT t FROM TipoResposta t " +
				"ORDER BY t.descricao ASC"),
		@NamedQuery( name=TipoResposta.FIND_BY_DESCRICAO_COUNT_EDITAR, 
				query="SELECT count(t) FROM TipoResposta t " +
				"WHERE descricao = :descricao "
				+ "and id <> :id"),
		@NamedQuery( name=TipoResposta.FIND_BY_DESCRICAO_COUNT_NOVO, 
				query="SELECT count(t) FROM TipoResposta t " +
				"WHERE descricao = :descricao "),				
		@NamedQuery( name=TipoResposta.FIND_BY_DESCRICAO, 
				query="SELECT t FROM TipoResposta t " +
				"WHERE descricao = :descricao"),
		@NamedQuery( name=TipoResposta.FIND_BY_CODIGO_FALAPOA, 
				query="SELECT t FROM TipoResposta t " +
				"WHERE (t.codigoFalaPoa = :codigoFalaPoa) ") 	

})
@Name(TipoResposta.NAME)
public class TipoResposta extends ApoioAbstract {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "tipoResposta";
	public static final String FIND_ALL = "tipoResposta.findAll";	
	public static final String FIND_BY_DESCRICAO = "tipoResposta.findByDescricao";
	public static final String FIND_BY_CODIGO_FALAPOA = "tipoResposta.findByCodigoFalaPoa";	
	public static final String FIND_BY_DESCRICAO_COUNT_EDITAR = "tipoResposta.findByDescricaoCountEditar";
	public static final String FIND_BY_DESCRICAO_COUNT_NOVO = "tipoResposta.findByDescricaoCountNovo";
	
	private Integer codigoFalaPoa;
	private String assunto;

	@Length(max=8000)
	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public Integer getCodigoFalaPoa() {
		return codigoFalaPoa;
	}

	public void setCodigoFalaPoa(Integer codigoFalaPoa) {
		this.codigoFalaPoa = codigoFalaPoa;
	}

}
