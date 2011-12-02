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
		String retorno = twitController.carrega("upp", 400.0, -22.976730, -43.195080);
		
		
		System.out.println((new GregorianCalendar().getTime()) +" - "+  retorno);
		
	}
	
	

}
