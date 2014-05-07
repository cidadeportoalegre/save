package br.com.procempa.save.entity;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.jboss.seam.annotations.Name;

@Entity
@NamedQueries(value={
		@NamedQuery( name=Secretaria.FIND_ALL, 
				query="SELECT t FROM Secretaria t " +
				"ORDER BY t.nome ASC"),
		@NamedQuery( name=Secretaria.FIND_BY_CODIGO_FALA_POA, 
				query="SELECT t FROM Secretaria t " +
				"WHERE t.codigoFalaPoa = :codigoFalaPoa")				
})
@Name("secretaria")
public class Secretaria extends SaveAbstract {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String FIND_ALL = "secretaria.findAll";	
	public static final String FIND_BY_CODIGO_FALA_POA = "secretaria.findByCodigoFalaPoa";	
	
	
	private String nome;
	private String sigla;
	private Integer codigoFalaPoa;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public Integer getCodigoFalaPoa() {
		return codigoFalaPoa;
	}
	public void setCodigoFalaPoa(Integer codigoFalaPoa) {
		this.codigoFalaPoa = codigoFalaPoa;
	}
	
}
