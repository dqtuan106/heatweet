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
		String retorno = twitController.carrega("desemprego", 2000.0, -10.1059, -48.201);
		
		System.out.println((new GregorianCalendar().getTime()) +" - "+  retorno);
		
	}
	
	

}
