package br.com.procempa.save.service;

import java.util.List;

import javax.ejb.Stateless;

import org.jboss.seam.annotations.Name;

import br.com.procempa.save.entity.LogTramite;
import br.com.procempa.save.iservice.ILogTramiteService;

@Stateless
@Name(LogTramiteService.SERVICE_NAME)
public class LogTramiteService extends AbstractSaveService<LogTramite> implements ILogTramiteService {
	public static final String SERVICE_NAME = "logTramiteService";

	@SuppressWarnings("unchecked")
	@Override
	public List<LogTramite> findAll() {
				
		return createNamedQuery(LogTramite.LOGTRAMITE_ALL).getResultList();

	}


}
