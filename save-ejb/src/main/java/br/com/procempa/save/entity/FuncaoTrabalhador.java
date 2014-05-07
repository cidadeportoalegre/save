package br.com.procempa.save.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.jboss.seam.annotations.Name;

@Entity
@DiscriminatorValue(value=FuncaoTrabalhador.NAME)
@NamedQueries(value={
		@NamedQuery( name=FuncaoTrabalhador.FIND_ALL, 
				query="SELECT t FROM FuncaoTrabalhador t " +
				"ORDER BY t.descricao ASC")
})
@Name(FuncaoTrabalhador.NAME)
public class FuncaoTrabalhador extends ApoioAbstract {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "funcaoTrabalhador";
	public static final String FIND_ALL = "funcaoTrabalhador.findAll";	

}
