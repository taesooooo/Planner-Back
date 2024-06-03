package com.planner.planner;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.planner.planner.Util.ImageUtil;

public class ImageUtilTest {
	
	@Test
	public void imageResizeTest() throws IOException {
		ImageUtil util = new ImageUtil();
		
		BufferedImage resizeImage = util.resize(new File("C:\\Users\\Bear\\Desktop\\temp.jpg"));
		
	}
}
