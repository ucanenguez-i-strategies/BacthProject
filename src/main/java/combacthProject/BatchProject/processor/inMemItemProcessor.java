package combacthProject.BatchProject.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class inMemItemProcessor implements ItemProcessor<Integer,Integer> {

	@Override
	public Integer process(Integer item) throws Exception {
		// TODO Auto-generated method stub
		return Integer.sum(10, item);
	}

}
