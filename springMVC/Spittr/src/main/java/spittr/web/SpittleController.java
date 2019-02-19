package spittr.web;

import java.util.List;

import org.junit.runners.Parameterized.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import spittr.Spittle;
import spittr.data.SpittleRepository;

@Controller
@RequestMapping("/spittleController")
public class SpittleController {
	
	@Autowired
	@Qualifier("spittleRepositoryImpl")
	private SpittleRepository spittleRepository;
	
	private static final String MAX_LONG_AS_STRING = Long.toString(Long.MAX_VALUE);
	
	@RequestMapping(value = "findList", method = RequestMethod.GET)
	public String findSpittleList(Model model) {
		List<Spittle> spittleList = spittleRepository.findSpittles(Long.MAX_VALUE, 20);
		model.addAttribute("spittleList", spittleList);
		return "spittles";
	}
	
	/**
	 * 映射的视图到spittleController/spittles.jsp里面，
	 * 模型model的key为spittle + List
	 */
	@RequestMapping(value = "spittles", method = RequestMethod.GET)
	public List<Spittle> findSpittleList2(Model model) {
		return spittleRepository.findSpittles(Long.MAX_VALUE, 20);
	}
	
	@RequestMapping(value = "findList3", method = RequestMethod.GET)
	public String findList3(@RequestParam(value = "max", defaultValue = "100") Long max, int count, Model model) {
		List<Spittle> spittleList = spittleRepository.findSpittles(Long.MAX_VALUE, 20);
		model.addAttribute("spittleList", spittleList);
		String.valueOf(1);
		return "spittles";
	}
	
	/**
	 * {}为占位符，与@PathVariable配合使用
	 */
	@RequestMapping("/showPath/{userId}/end")
	public void showPath(@PathVariable("userId") String userId) {
		System.out.println(userId);
	}
	
}
