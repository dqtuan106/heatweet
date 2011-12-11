package scheduler;

import java.util.GregorianCalendar;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import controller.TwitController;

public class Agendador implements  Job{

	TwitController twitController;
	
	public Agendador() {
		twitController = new TwitController();
	}
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		String retorno = twitController.carrega("occupy || ocupa || occupe", 30000.0, 40.713696, -74.007339,"2228251");
		
		
		System.out.println((new GregorianCalendar().getTime()) +" - "+  retorno);
		
	}
	
	

}
