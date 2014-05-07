package br.com.procempa.save.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.jboss.seam.annotations.Name;

@Entity
@DiscriminatorValue(value=CondicaoAlta.NAME)
@NamedQueries(value={
		@NamedQuery( name=CondicaoAlta.FIND_ALL, 
				query="SELECT t FROM CondicaoAlta t " +
				"ORDER BY t.descricao ASC")
})
@Name(CondicaoAlta.NAME)
public class CondicaoAlta extends ApoioAbstract {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "condicaoAlta";
	public static final String FIND_ALL = "condicaoAlta.findAll";	

}
