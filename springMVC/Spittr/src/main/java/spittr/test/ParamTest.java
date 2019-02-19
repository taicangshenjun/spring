package spittr.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mockito.Mockito;

import spittr.Spittle;
import spittr.data.SpittleRepository;

public class ParamTest {

	public void shouldShowPage() {
		List<Spittle> spittles = createSpittleList(20);
		SpittleRepository mockRepository = Mockito.mock(SpittleRepository.class);
		Mockito.when(mockRepository.findSpittles(Long.MAX_VALUE, 20)).thenReturn(spittles);
	}
	
	private List<Spittle> createSpittleList(int count) {
		List<Spittle> spittles = new ArrayList<Spittle>();
		for(int i = 0; i < count; i ++) {
			spittles.add(new Spittle("Spittle" + i, new Date()));
		}
		return spittles;
	}
}
