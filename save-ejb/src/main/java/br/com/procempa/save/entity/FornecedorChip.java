package br.com.procempa.save.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.jboss.seam.annotations.Name;

@Entity
@DiscriminatorValue(value=FornecedorChip.NAME)
@NamedQueries(value={
		@NamedQuery( name=FornecedorChip.FIND_ALL, 
				query="SELECT t FROM FornecedorChip t " +
				"ORDER BY t.descricao ASC"),
		@NamedQuery( name=FornecedorChip.FIND_BY_DESCRICAO, 
				query="SELECT t FROM FornecedorChip t " +
				"WHERE descricao = :descricao"),
		@NamedQuery( name=FornecedorChip.FIND_BY_DESCRICAO_COUNT_EDITAR, 
				query="SELECT count(t) FROM FornecedorChip t " +
				"WHERE descricao = :descricao "
				+ "and id <> :id"),
		@NamedQuery( name=FornecedorChip.FIND_BY_DESCRICAO_COUNT_NOVO, 
				query="SELECT count(t) FROM FornecedorChip t " +
				"WHERE descricao = :descricao ")
})
@Name(FornecedorChip.NAME)
public class FornecedorChip extends ApoioAbstract {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "fornecedorChip";
	public static final String FIND_ALL = "fornecedorChip.findAll";		
	public static final String FIND_BY_DESCRICAO = "fornecedorChip.findByDescricao";
	public static final String FIND_BY_DESCRICAO_COUNT_EDITAR = "fornecedorChip.findByDescricaoCountEditar";
	public static final String FIND_BY_DESCRICAO_COUNT_NOVO = "fornecedorChip.findByDescricaoCountNovo";	

}
