package br.com.procempa.save.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.jboss.seam.annotations.Name;

@Entity
@DiscriminatorValue(value=SituacaoBox.NAME)
@NamedQueries(value={
		@NamedQuery( name=SituacaoBox.FIND_ALL, 
				query="SELECT t FROM SituacaoBox t " +
				"ORDER BY t.descricao ASC")
})
@Name(SituacaoBox.NAME)
public class SituacaoBox extends ApoioAbstract {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "situacaoBox";
	public static final String FIND_ALL = "situacaoBox.findAll";	

}
