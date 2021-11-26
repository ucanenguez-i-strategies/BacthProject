package combacthProject.BatchProject.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class HwJobExecutionListener implements JobExecutionListener{

	@Override
	public void beforeJob(JobExecution jobExecution) {
		System.out.println("before starting the Job - Job Name:" + jobExecution.getJobInstance().getJobName());
		System.out.println("before starting the job" + jobExecution.getExecutionContext().toString());
		jobExecution.getExecutionContext().put("Mi nombre", "Uriel");
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		System.out.println("after starting the job - Job Execution Context"+jobExecution.getExecutionContext().toString());
	}

}
