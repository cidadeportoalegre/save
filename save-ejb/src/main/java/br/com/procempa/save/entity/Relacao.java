package br.com.procempa.save.entity;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.jboss.seam.annotations.Name;

@Entity
@Name("relacao")
public class Relacao extends SaveAbstract {
	private static final long serialVersionUID = 1L;
	private TipoSolicitante tipoSolicitante;
	private Animal animal;
	private Solicitante solicitante;
	private Calendar inicio;
	private Calendar fim;

	@ManyToOne(fetch=FetchType.LAZY)
	public TipoSolicitante getTipoSolicitante() {
		return tipoSolicitante;
	}
	
	public void setTipoSolicitante(TipoSolicitante tipoSolicitante) {
		this.tipoSolicitante = tipoSolicitante;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	public Animal getAnimal() {
		return animal;
	}
	
	public void setAnimal(Animal animal) {
		this.animal = animal;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	public Solicitante getSolicitante() {
		return solicitante;
	}
	
	public void setSolicitante(Solicitante solicitante) {
		this.solicitante = solicitante;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getInicio() {
		return inicio;
	}
	
	public void setInicio(Calendar inicio) {
		this.inicio = inicio;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getFim() {
		return fim;
	}
	
	public void setFim(Calendar fim) {
		this.fim = fim;
	}
}
