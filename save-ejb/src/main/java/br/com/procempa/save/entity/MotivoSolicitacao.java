package br.com.procempa.save.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.jboss.seam.annotations.Name;

@Entity
@DiscriminatorValue(value=MotivoSolicitacao.NAME)
@NamedQueries(value={
		@NamedQuery( name=MotivoSolicitacao.FIND_ALL, 
				query="SELECT t FROM MotivoSolicitacao t " +
				"ORDER BY t.descricao ASC"),
		@NamedQuery( name=MotivoSolicitacao.FIND_BY_DESCRICAO, 
				query="SELECT t FROM MotivoSolicitacao t " +
				"WHERE descricao = :descricao"),
		@NamedQuery( name=MotivoSolicitacao.FIND_BY_CODIGO_FALA_POA, 
				query="SELECT t FROM MotivoSolicitacao t " +
				"WHERE t.codigoFalaPoa = :codigoFalaPoa"),				
		@NamedQuery( name=MotivoSolicitacao.FIND_BY_DESCRICAO_COUNT_EDITAR, 
				query="SELECT count(t) FROM MotivoSolicitacao t " +
				"WHERE descricao = :descricao "
				+ "and id <> :id"),
		@NamedQuery( name=MotivoSolicitacao.FIND_BY_DESCRICAO_COUNT_NOVO, 
				query="SELECT count(t) FROM MotivoSolicitacao t " +
				"WHERE descricao = :descricao ")
})
@Name(MotivoSolicitacao.NAME)
public class MotivoSolicitacao extends ApoioAbstract {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "motivoSolicitacao";
	public static final String FIND_ALL = "motivoSolicitacao.findAll";	
	public static final String FIND_BY_DESCRICAO = "motivoSolicitacao.findByDescricao";
	public static final String FIND_BY_DESCRICAO_COUNT_EDITAR = "motivoSolicitacao.findByDescricaoCountEditar";
	public static final String FIND_BY_DESCRICAO_COUNT_NOVO = "motivoSolicitacao.findByDescricaoCountNovo";
	public static final String FIND_BY_CODIGO_FALA_POA = "motivoSolicitacao.findByCodigoFalaPoa";	
	
	private Integer codigoFalaPoa;
	private Secretaria secretaria;

	public Integer getCodigoFalaPoa() {
		return codigoFalaPoa;
	}

	public void setCodigoFalaPoa(Integer codigoFalaPoa) {
		this.codigoFalaPoa = codigoFalaPoa;
	}

	@ManyToOne(fetch=FetchType.LAZY)	
	public Secretaria getSecretaria() {
		return secretaria;
	}

	public void setSecretaria(Secretaria secretaria) {
		this.secretaria = secretaria;
	}
}
