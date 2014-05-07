package br.com.procempa.save.entity;

import javax.persistence.Entity;

import org.jboss.seam.annotations.Name;

@Entity
@Name("setoremitedocto")
public class SetorEmiteDocto extends SaveAbstract {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Setor setor;
	private Documento documento;
	public Setor getSetor() {
		return setor;
	}
	public void setSetor(Setor setor) {
		this.setor = setor;
	}
	public Documento getDocumento() {
		return documento;
	}
	public void setDocumento(Documento documento) {
		this.documento = documento;
	}
								
}
