package br.com.procempa.save.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.jboss.seam.annotations.Name;

@Entity
@DiscriminatorValue(value=LogSolicitacao.NAME)
@NamedQueries(value={
		@NamedQuery( name=LogSolicitacao.LOGSOLICITACAO_ALL, 
				query="SELECT t FROM LogSolicitacao t " +
				"ORDER BY t.ultimaRefAtualizacao DESC"),
		@NamedQuery( name=LogSolicitacao.LOGSOLICITACAO_FIND_LAST, 
				query="SELECT t FROM LogSolicitacao t " +
				"ORDER BY t.ultimaRefAtualizacao DESC")						
				
})
@Name(LogSolicitacao.NAME)
public class LogSolicitacao extends LogAbstract {
		private static final long serialVersionUID = 1L;
		public static final String NAME = "logSolicitacao";
		public static final String LOGSOLICITACAO_ALL = "logSolicitacao.findAll";
		public static final String LOGSOLICITACAO_FIND_LAST = "logSolicitacao.findLast";		
		
}
