package combacthProject.BatchProject.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class HwStepExecutioListener implements StepExecutionListener{

	@Override
	public void beforeStep(StepExecution stepExecution) {
		System.out.println( "este es antes del StepExcetuion" + stepExecution.getJobExecution().getExecutionContext());
		
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		System.out.println("Este es despues Step Execution " + stepExecution.getJobExecution().getExecutionContext());
		return null;
	}

}
