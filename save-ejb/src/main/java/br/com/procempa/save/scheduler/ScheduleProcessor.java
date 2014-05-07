package br.com.procempa.save.scheduler;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.async.Asynchronous;
import org.jboss.seam.annotations.async.Expiration;
import org.jboss.seam.annotations.async.IntervalCron;
import org.jboss.seam.async.QuartzTriggerHandle;
import org.jboss.seam.log.Log;

import br.com.procempa.save.iservice.ISolicitacaoService;
import br.com.procempa.save.iservice.ISolicitanteService;
import br.com.procempa.save.service.SolicitacaoService;
import br.com.procempa.save.service.SolicitanteService;

/**
 * Timer class. The methods annotated as asynchronous will be called by Quartz or other implementations
 * as specified by @IntervalCron
 */
@AutoCreate
@Scope(ScopeType.APPLICATION)
@Name("processor")
public class ScheduleProcessor {

    @Logger
    private Log log;	
	
    @In(value=SolicitacaoService.SERVICE_NAME, create=true)
    private ISolicitacaoService solicitacaoService;

    @In(value=SolicitanteService.SERVICE_NAME, create=true)
    private ISolicitanteService solicitanteService;


    private boolean executandoImport = false;

    
    @Asynchronous
    public QuartzTriggerHandle createImportTimer(@Expiration Date when, @IntervalCron String interval) throws IOException, ParseException, ExecutionException, InterruptedException {
        if (executandoImport) {
        	return null;
        }
        
        executandoImport = true;
//        log.info("[#0] Processando importação de Solicitações com intervalo #1", new Date(), interval);
        solicitacaoService.importarSolicitacoes(interval);
//        log.info("[#0] Fim importação de Solicitações", new Date());
        solicitanteService.importarSolicitantes(interval);
        executandoImport = false;
        
        return null;
    }
}
