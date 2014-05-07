package br.com.procempa.save.entity;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.jboss.seam.annotations.Name;

@Entity
@Name("internacao")
public class Internacao extends SaveAbstract {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AnimalTemAtendimento animaltematendimento;
	private Setor setor;
	private Box box;
	private Calendar ocupacaoBox;
	private Calendar liberacaoBox;
	public AnimalTemAtendimento getAnimaltematendimento() {
		return animaltematendimento;
	}
	public void setAnimaltematendimento(AnimalTemAtendimento animaltematendimento) {
		this.animaltematendimento = animaltematendimento;
	}
	public Setor getSetor() {
		return setor;
	}
	public void setSetor(Setor setor) {
		this.setor = setor;
	}
	public Box getBox() {
		return box;
	}
	public void setBox(Box box) {
		this.box = box;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getOcupacaoBox() {
		return ocupacaoBox;
	}
	public void setOcupacaoBox(Calendar ocupacaoBox) {
		this.ocupacaoBox = ocupacaoBox;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLiberacaoBox() {
		return liberacaoBox;
	}
	public void setLiberacaoBox(Calendar liberacaoBox) {
		this.liberacaoBox = liberacaoBox;
	}
						
}
