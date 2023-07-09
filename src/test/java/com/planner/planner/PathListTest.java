package com.planner.planner;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMethod;

import com.planner.planner.Interceptor.PathList;

public class PathListTest {

	@Test
	public void isPass_pass_1_test() {
		PathList pl = new PathList();
		pl.excludePath("/api/test/test", null);
		
		assertThat(pl.isPass("/api/test/test", RequestMethod.GET)).isEqualTo(true);
	}
	
	@Test
	public void isPass_pass_2_test() {
		PathList pl = new PathList();
		pl.excludePath("/api/test/test", RequestMethod.GET);
		pl.excludePath("/api/test2", null);
		
		assertThat(pl.isPass("/api/test2", RequestMethod.POST)).isEqualTo(true);
	}
	
	@Test
	public void isPass_pass_3_test() {
		PathList pl = new PathList();
		pl.addPath("/api/test", RequestMethod.GET);
		
		assertThat(pl.isPass("/api/test/test", RequestMethod.GET)).isEqualTo(true);
	}
	
	@Test
	public void isPass_not_pass_1_test() {
		PathList pl = new PathList();
		pl.addPath("/api/test/test", null);
		
		assertThat(pl.isPass("/api/test/test", RequestMethod.GET)).isEqualTo(false);
	}
	
	@Test
	public void isPass_not_pass_2_test() {
		PathList pl = new PathList();
		pl.addPath("/api/test/test", RequestMethod.GET);
		
		assertThat(pl.isPass("/api/test/test", RequestMethod.GET)).isEqualTo(false);
	}
	
	@Test
	public void isPass_not_pass_3_test() {
		PathList pl = new PathList();
		pl.addPath("/api/test/*", RequestMethod.GET);
		
		assertThat(pl.isPass("/api/test/test", RequestMethod.GET)).isEqualTo(false);
	}
	
}
