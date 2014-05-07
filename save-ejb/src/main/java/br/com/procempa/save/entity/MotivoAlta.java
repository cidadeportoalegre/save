package br.com.procempa.save.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.jboss.seam.annotations.Name;

@Entity
@DiscriminatorValue(value=MotivoAlta.NAME)
@NamedQueries(value={
		@NamedQuery( name=MotivoAlta.FIND_ALL, 
				query="SELECT t FROM MotivoAlta t " +
				"ORDER BY t.descricao ASC")
})
@Name(MotivoAlta.NAME)
public class MotivoAlta extends ApoioAbstract {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "motivoAlta";
	public static final String FIND_ALL = "motivoAlta.findAll";	

}
