package br.com.procempa.save.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.jboss.seam.annotations.Name;

@Entity
@DiscriminatorValue(value=TipoSetor.NAME)
@NamedQueries(value={
		@NamedQuery( name=TipoSetor.FIND_ALL, 
				query="SELECT t FROM TipoSetor t " +
				"ORDER BY t.descricao ASC")
})
@Name(TipoSetor.NAME)
public class TipoSetor extends ApoioAbstract {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "tipoSetor";
	public static final String FIND_ALL = "tipoSetor.findAll";	

}
