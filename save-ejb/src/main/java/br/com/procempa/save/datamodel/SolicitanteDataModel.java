package br.com.procempa.save.datamodel;

import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import br.com.procempa.arquitetura.datamodel.AbstractDataModel;
import br.com.procempa.save.entity.Solicitante;
import br.com.procempa.save.iservice.ISolicitanteService;
import br.com.procempa.save.service.SolicitanteService;

/**
 * @author bridi
 * 
 */
@Name("solicitanteDataModel")
@Scope(ScopeType.CONVERSATION)
public class SolicitanteDataModel extends AbstractDataModel<Solicitante, Long> {

	private static final long serialVersionUID = 1L;
	
	private String criterioNome;
	private String criterioDocumento;	
	private Integer intervalo = 0;
	
	@In(value=SolicitanteService.SERVICE_NAME, create=true)
	private ISolicitanteService solicitanteService;

	@Override
	public Long getCount() {

				return this.rowCount = solicitanteService.getCountFiltroNome(getCriterioNome(), getCriterioDocumento());
	}

	@Override
	public Solicitante findById(Long id) {
		return solicitanteService.findById(id);
	}

	@Override
	public List<Solicitante> getList(Integer firstRow, Integer maxResults) {				
			return this.listRow = solicitanteService.getListFiltroNome(firstRow, maxResults, getCriterioNome(), getCriterioDocumento());			
	}

	public void setCriterioNome(String criterioNome) {
		this.criterioNome = criterioNome;
	}

	public String getCriterioNome() {
		return criterioNome;
	}


	public Integer getIntervalo() {
		return intervalo;
	}

	public void setIntervalo(Integer intervalo) {
		this.intervalo = intervalo;
	}

	public String getCriterioDocumento() {
		return criterioDocumento;
	}

	public void setCriterioDocumento(String criterioDocumento) {
		this.criterioDocumento = criterioDocumento;
	}
	

}
