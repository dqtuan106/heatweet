package scheduler;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

public class AgendadorCtxListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched;

		try {
			sched = sf.getScheduler();

			sched.shutdown();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		try {
			SchedulerFactory sf = new StdSchedulerFactory();

			Scheduler sched;

			sched = sf.getScheduler();

			JobDetail job = newJob(Agendador.class).withIdentity(
					"agendarCarregamentoJob", "tweets").build();
			Trigger trigger = newTrigger()
					.withIdentity("agendarCarregamentoTrigger", "tweets")
					.startNow()
					.withSchedule(
							simpleSchedule().repeatMinutelyForTotalCount(48, 30)
									).build();

			sched.scheduleJob(job, trigger);

			sched.start();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
