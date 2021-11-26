package combacthProject.BatchProject.config;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import combacthProject.BatchProject.listener.HwJobExecutionListener;
import combacthProject.BatchProject.listener.HwStepExecutioListener;
import combacthProject.BatchProject.model.Product;
import combacthProject.BatchProject.processor.inMemItemProcessor;
import combacthProject.BatchProject.reader.InMemReader;
import combacthProject.BatchProject.writer.ConsoleItemWriter;

@EnableBatchProcessing
@Configuration
public class BatchConfiguration {
	@Autowired
	private JobBuilderFactory jobs;

	@Autowired
	private StepBuilderFactory steps;
	
	@Autowired
	private HwJobExecutionListener hwJobExecutionListener;
	
	@Autowired
	private HwStepExecutioListener hwStepExecutionListener;
	
	@Autowired
	private inMemItemProcessor MenItemProcessor;
	
	@Bean
	public Step step1() {
		return steps.get("step1")
				.listener(hwStepExecutionListener)
				.tasklet(helloworldTasklet())
				.build();
	}
	
	@Bean
	public ItemReader reader() {
		return new InMemReader();
	}
	
	
	@Bean
	public Step step2() {
		return steps.get("step2")
				.<Integer,Integer>chunk(3)
				.reader(flatFileItemReader())
				.writer(new ConsoleItemWriter())
				.build();
	}

	private Tasklet helloworldTasklet() {
		return (new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("hello world");
				return RepeatStatus.FINISHED;
			}
		});
	}
	
	@Bean
	public FlatFileItemReader flatFileItemReader() {
		FlatFileItemReader reader = new FlatFileItemReader();
		// step 1 permite leer y saber donde esta el archivo
		reader.setResource(new FileSystemResource("input/product.csv"));
		
		//Crea el liner mapper
		reader.setLineMapper(
			new DefaultLineMapper<Product>(){
				{
						setLineTokenizer(new DelimitedLineTokenizer() {
							{
								setNames(new String[]{"productId","prodName","productDesc","price","unit"});
							}
						});
						
						setFieldSetMapper(new BeanWrapperFieldSetMapper<Product>() {
							{
								setTargetType(Product.class);
							}
						});
					}
				}
		);
		
		//step 3 decirle al reader que salte el encabezado
		reader.setLinesToSkip(1);
		return reader;
	}
	
	@Bean
	public Job helloworldJob() {
		return jobs.get("helloworldJob")
				/*Realiza un autoincrement en el id del registro del job que se registra en la base de datos*/
				.incrementer(new RunIdIncrementer())
				.listener(hwJobExecutionListener)
				/*Paso inicial*/
				.start(step1())
				/*Los pasos siguientes que ejecuta el job 
				 * NOTA: se pueden agregar cuantos pasos sean necesarios
				 **/
				.next(step2())
				.build();
	}
}
