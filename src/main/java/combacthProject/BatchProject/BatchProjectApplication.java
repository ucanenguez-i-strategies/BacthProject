package combacthProject.BatchProject;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class BatchProjectApplication {
	
	@Autowired
	private JobBuilderFactory job;

	@Autowired
	private StepBuilderFactory steps;

	public static void main(String[] args) {
		SpringApplication.run(BatchProjectApplication.class, args);
	}
	
	public Step step1() {
		return steps.get("step1")
				.tasklet(helloworldTasklet())
				.build();
	}

	private Tasklet helloworldTasklet() {
		return (new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				// TODO Auto-generated method stub
				return null;
			}
		});
	}

}
