package br.com.procempa.save.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.jboss.seam.annotations.Name;

@Entity
@DiscriminatorValue(value=LogTramite.NAME)
@NamedQueries(value={
		@NamedQuery( name=LogTramite.LOGTRAMITE_ALL, 
				query="SELECT t FROM LogTramite t " +
				"ORDER BY t.ultimaRefAtualizacao DESC"),
		@NamedQuery( name=LogTramite.LOGTRAMITE_FIND_LAST, 
				query="SELECT t FROM LogTramite t " +
				"ORDER BY t.ultimaRefAtualizacao DESC")						
				
})
@Name(LogTramite.NAME)
public class LogTramite extends LogAbstract {
		private static final long serialVersionUID = 1L;
		public static final String NAME = "logTramite";
		public static final String LOGTRAMITE_ALL = "logTramite.findAll";
		public static final String LOGTRAMITE_FIND_LAST = "logTramite.findLast";		
		
}
