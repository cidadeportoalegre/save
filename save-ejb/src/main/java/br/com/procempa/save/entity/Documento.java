package br.com.procempa.save.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.jboss.seam.annotations.Name;

@Entity
@DiscriminatorValue(value=Documento.NAME)
@NamedQueries(value={
		@NamedQuery( name=Documento.FIND_ALL, 
				query="SELECT t FROM Documento t " +
				"ORDER BY t.descricao ASC")
})
@Name(Documento.NAME)
public class Documento extends ApoioAbstract {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "documento";
	public static final String FIND_ALL = "documento.findAll";	

}
