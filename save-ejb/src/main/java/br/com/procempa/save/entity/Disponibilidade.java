package br.com.procempa.save.entity;

import javax.persistence.Entity;

import org.jboss.seam.annotations.Name;

@Entity
@Name("disponibilidade")
public class Disponibilidade extends SaveAbstract {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Setor setor;
	private Integer horaInicial;
	private Integer horaFinal;
	private Integer quantidade;
	private String  diaSemana;
	private String  ageQuantHorario;
	private Integer tempoConsulta;
	public Setor getSetor() {
		return setor;
	}
	public void setSetor(Setor setor) {
		this.setor = setor;
	}
	public Integer getHoraInicial() {
		return horaInicial;
	}
	public void setHoraInicial(Integer horaInicial) {
		this.horaInicial = horaInicial;
	}
	public Integer getHoraFinal() {
		return horaFinal;
	}
	public void setHoraFinal(Integer horaFinal) {
		this.horaFinal = horaFinal;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public String getDiaSemana() {
		return diaSemana;
	}
	public void setDiaSemana(String diaSemana) {
		this.diaSemana = diaSemana;
	}
	public String getAgeQuantHorario() {
		return ageQuantHorario;
	}
	public void setAgeQuantHorario(String ageQuantHorario) {
		this.ageQuantHorario = ageQuantHorario;
	}
	public Integer getTempoConsulta() {
		return tempoConsulta;
	}
	public void setTempoConsulta(Integer tempoConsulta) {
		this.tempoConsulta = tempoConsulta;
	}

	
}
