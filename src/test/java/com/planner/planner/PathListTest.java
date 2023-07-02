package com.planner.planner;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMethod;

import com.planner.planner.Interceptor.PathList;

public class PathListTest {
	
	@Test
	public void isPass_pass_test() {
		PathList pl = new PathList();
		pl.excludePath("/api/test", RequestMethod.GET);
		pl.addPath("/api/test", RequestMethod.GET);
		
		assertThat(pl.isPass("/api/test", RequestMethod.GET)).isEqualTo(true);
	}
	
	@Test
	public void isPass_pass_2_test() {
		PathList pl = new PathList();
		pl.excludePath("/api/*", RequestMethod.GET);
		pl.addPath("/api/*", RequestMethod.GET);
		
		assertThat(pl.isPass("/api/test", RequestMethod.GET)).isEqualTo(true);
	}
	
	@Test
	public void isPass_pass_3_test() {
		PathList pl = new PathList();
		pl.excludePath("/api/test/test", null);
		pl.addPath("/api/test", RequestMethod.GET);
		
		assertThat(pl.isPass("/api/test/test", RequestMethod.GET)).isEqualTo(true);
	}
	
	@Test
	public void isPass_pass_4_test() {
		PathList pl = new PathList();
		pl.excludePath("/api/test/test", RequestMethod.GET);
		pl.addPath("/api/test", RequestMethod.GET);
		
		assertThat(pl.isPass("/api/test/test", null)).isEqualTo(true);
	}
	
	@Test
	public void isPass_notPass_test() {
		PathList pl = new PathList();
		pl.addPath("/api/test", RequestMethod.GET);
		
		assertThat(pl.isPass("/api/test", RequestMethod.GET)).isEqualTo(false);
	}
	
	@Test
	public void isPass_notPass_2_test() {
		PathList pl = new PathList();
		pl.excludePath("/api/test/test", RequestMethod.GET);
		pl.addPath("/api/*", RequestMethod.GET);
		
		assertThat(pl.isPass("/api/test", RequestMethod.GET)).isEqualTo(false);
	}
	
	@Test
	public void isPass_notPass_3_test() {
		PathList pl = new PathList();
		pl.excludePath("/api/test/test", RequestMethod.GET);
		pl.addPath("/api/test", RequestMethod.GET);
		
		assertThat(pl.isPass("/api/test", null)).isEqualTo(false);
	}
	
	@Test
	public void isPass_notPass_4_test() {
		PathList pl = new PathList();
		pl.excludePath("/api/test/test", RequestMethod.GET);
		pl.addPath("/api/test", null);
		
		assertThat(pl.isPass("/api/test", RequestMethod.POST)).isEqualTo(false);
	}
}
