package br.com.procempa.save.scheduler;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;

/**
 * baseado em http://manuel-palacio.blogspot.com/2010/03/easy-pdf-generation-with-seam.html
 * @author bridi
 *
 */
@Name("schedulerController")
public class SchedulerController {

	@Logger
    private Log log;	
	
    @In
    ScheduleProcessor processor;
    
    public void startImportScheduler(String cronExpression) throws IOException, ParseException, ExecutionException, InterruptedException {
    		processor.createImportTimer(new Date(), cronExpression);    		
    }
}
