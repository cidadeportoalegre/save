package br.com.procempa.save.iservice;

import java.util.List;

import javax.ejb.Local;

import br.com.procempa.arquitetura.business.interfaces.IBusiness;
import br.com.procempa.save.entity.Solicitante;

@Local
public interface ISolicitanteService extends IBusiness<Solicitante> {

		public List<Solicitante> findAll();

		public Solicitante carregaDadosCdl(Solicitante solicitante, Integer codigoCdl, String numero);

		public Long getCountFiltroNome(String criterioNome, String criterioDocumento);

		public List<Solicitante> getListFiltroNome(Integer firstRow,
				Integer maxResults, String criterioNome, String criterioDocumento);

		public void importarSolicitantes(String interval);

}
