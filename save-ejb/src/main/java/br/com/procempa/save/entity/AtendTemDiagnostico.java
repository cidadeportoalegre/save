package br.com.procempa.save.entity;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.jboss.seam.annotations.Name;

@Entity
@Name("atendtemdiagnostico")
public class AtendTemDiagnostico extends SaveAbstract {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1272003860520735679L;
	private AnimalTemAtendimento animaltematendimento;
	private Diagnostico diagnostico;
	private Calendar dataConstatado;
	public AnimalTemAtendimento getAnimaltematendimento() {
		return animaltematendimento;
	}
	public void setAnimaltematendimento(AnimalTemAtendimento animaltematendimento) {
		this.animaltematendimento = animaltematendimento;
	}
	public Diagnostico getDiagnostico() {
		return diagnostico;
	}
	public void setDiagnostico(Diagnostico diagnostico) {
		this.diagnostico = diagnostico;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getDataConstatado() {
		return dataConstatado;
	}
	public void setDataConstatado(Calendar dataConstatado) {
		this.dataConstatado = dataConstatado;
	}
					
}
