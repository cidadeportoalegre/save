package br.com.procempa.save.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.jboss.seam.annotations.Name;

@Entity
@DiscriminatorValue(value=SituacaoAgenda.NAME)
@NamedQueries(value={
		@NamedQuery( name=SituacaoAgenda.FIND_ALL, 
				query="SELECT t FROM SituacaoAgenda t " +
				"ORDER BY t.descricao ASC")
})
@Name(SituacaoAgenda.NAME)
public class SituacaoAgenda extends ApoioAbstract {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "situacaoAgenda";
	public static final String FIND_ALL = "situacaoAgenda.findAll";	

}
