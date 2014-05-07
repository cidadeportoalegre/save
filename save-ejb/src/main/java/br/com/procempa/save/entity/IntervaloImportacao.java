package br.com.procempa.save.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.jboss.seam.annotations.Name;

@Entity
@DiscriminatorValue(value=IntervaloImportacao.NAME)
@NamedQueries(value={
		@NamedQuery( name=IntervaloImportacao.INTERVALOIMPORTACOES_ALL, 
				query="SELECT t FROM IntervaloImportacao t " +
				"ORDER BY t.descricao"),
		@NamedQuery( name=IntervaloImportacao.INTERVALOIMPORTACOES_BY_INTERVAL, 
				query="SELECT t FROM IntervaloImportacao t " +
				"WHERE t.interval = :interval")								
})
@Name(IntervaloImportacao.NAME)
public class IntervaloImportacao extends SaveAbstract {
		private static final long serialVersionUID = 1L;
		public static final String NAME = "intervaloImportacao";
		public static final String INTERVALOIMPORTACOES_ALL = "intervaloImportacao.findAll";
		public static final String INTERVALOIMPORTACOES_BY_INTERVAL = "intervaloImportacao.findByInterval";		
	
		private String interval;
		private String descricao;
		
		public String getInterval() {
			return interval;
		}
		public void setInterval(String interval) {
			this.interval = interval;
		}
		public String getDescricao() {
			return descricao;
		}
		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}
}
