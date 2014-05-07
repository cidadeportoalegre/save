package br.com.procempa.save.entity;

import java.math.BigInteger;
import java.util.Calendar;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForceDiscriminator;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="discriminator", discriminatorType=DiscriminatorType.STRING)
@ForceDiscriminator
@Table(name="LogImportacao")
public abstract class LogAbstract extends SaveAbstract {

	private static final long serialVersionUID = 1L;

	private Calendar dataStartLogImportacao;
	private Calendar dataEndLogImportacao;		
	private BigInteger ultimaRefAtualizacao;
	private Boolean sucessoImportacao;
	private String interval;
	private Integer nRegistrosImportados; 
	private String mensagem;
	
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getDataStartLogImportacao() {
		return dataStartLogImportacao;
	}
	public void setDataStartLogImportacao(Calendar dataStartLogImportacao) {
		this.dataStartLogImportacao = dataStartLogImportacao;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getDataEndLogImportacao() {
		return dataEndLogImportacao;
	}
	public void setDataEndLogImportacao(Calendar dataEndLogImportacao) {
		this.dataEndLogImportacao = dataEndLogImportacao;
	}
	public BigInteger getUltimaRefAtualizacao() {
		return ultimaRefAtualizacao;
	}
	public void setUltimaRefAtualizacao(BigInteger ultimaRefAtualizacao) {
		this.ultimaRefAtualizacao = ultimaRefAtualizacao;
	}
	public Boolean getSucessoImportacao() {
		return sucessoImportacao;
	}
	public void setSucessoImportacao(Boolean sucessoImportacao) {
		this.sucessoImportacao = sucessoImportacao;
	}
	public String getInterval() {
		return interval;
	}
	public void setInterval(String interval) {
		this.interval = interval;
	}
	public Integer getnRegistrosImportados() {
		return nRegistrosImportados;
	}
	public void setnRegistrosImportados(Integer nRegistrosImportados) {
		this.nRegistrosImportados = nRegistrosImportados;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
}



