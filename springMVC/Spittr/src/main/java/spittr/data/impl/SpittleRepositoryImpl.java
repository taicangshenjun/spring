package spittr.data.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import spittr.Spittle;
import spittr.data.SpittleRepository;

@Service("spittleRepositoryImpl")
public class SpittleRepositoryImpl implements SpittleRepository {

	public List<Spittle> findSpittles(long max, int count) {
		List<Spittle> spittleList = new ArrayList<Spittle>();
		for(int i = 0; i < count; i ++) {
			spittleList.add(new Spittle("Spittle" + i, new Date()));
		}
		return spittleList;
	}

}
