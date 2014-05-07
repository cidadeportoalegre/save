package br.com.procempa.save.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.jboss.seam.annotations.Name;

@Entity
@DiscriminatorValue(value=LogResposta.NAME)
@NamedQueries(value={
		@NamedQuery( name=LogResposta.LOGRESPOSTA_ALL, 
				query="SELECT t FROM LogResposta t " +
				"ORDER BY t.ultimaRefAtualizacao DESC"),
		@NamedQuery( name=LogResposta.LOGRESPOSTA_FIND_LAST, 
				query="SELECT t FROM LogResposta t " +
				"ORDER BY t.ultimaRefAtualizacao DESC")						
				
})
@Name(LogResposta.NAME)
public class LogResposta extends LogAbstract {
		private static final long serialVersionUID = 1L;
		public static final String NAME = "logResposta";
		public static final String LOGRESPOSTA_ALL = "logResposta.findAll";
		public static final String LOGRESPOSTA_FIND_LAST = "logResposta.findLast";		
		
}
