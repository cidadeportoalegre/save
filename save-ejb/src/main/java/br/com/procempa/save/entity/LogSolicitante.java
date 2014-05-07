package br.com.procempa.save.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.jboss.seam.annotations.Name;

@Entity
@DiscriminatorValue(value=LogSolicitante.NAME)
@NamedQueries(value={
		@NamedQuery( name=LogSolicitante.LOGSOLICITANTE_ALL, 
				query="SELECT t FROM LogSolicitante t " +
				"ORDER BY t.ultimaRefAtualizacao DESC"),
		@NamedQuery( name=LogSolicitante.LOGSOLICITANTE_FIND_LAST, 
				query="SELECT t FROM LogSolicitante t " +
				"ORDER BY t.ultimaRefAtualizacao DESC")						
				
})
@Name(LogSolicitante.NAME)
public class LogSolicitante extends LogAbstract {
		private static final long serialVersionUID = 1L;
		public static final String NAME = "logSolicitante";
		public static final String LOGSOLICITANTE_ALL = "logSolicitante.findAll";
		public static final String LOGSOLICITANTE_FIND_LAST = "logSolicitante.findLast";		
		
}
