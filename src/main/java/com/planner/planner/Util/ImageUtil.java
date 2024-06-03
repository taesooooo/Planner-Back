package com.planner.planner.Util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

@Component
public class ImageUtil {
	
	private int width = 300;
	private int height = 300;
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public BufferedImage resize(File imagePath) throws IOException {
		
		Image image = ImageIO.read(imagePath);
		Image resizeObject = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		
		BufferedImage resizeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		Graphics g = resizeImage.getGraphics();
		g.drawImage(resizeObject, 0, 0, null);
		g.dispose();
		
		return resizeImage;
	}
}
