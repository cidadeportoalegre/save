package br.com.procempa.save.entity;

import br.com.procempa.commons.model.beans.EntityImpl;

public abstract class SaveAbstract extends EntityImpl {

	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return null == getId() ? "" : "ID: " + getId();
	}

}
