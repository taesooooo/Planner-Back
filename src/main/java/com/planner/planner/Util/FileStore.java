package com.planner.planner.Util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.planner.planner.Common.Image;
import com.planner.planner.Dto.FileInfoDto;

@Component
public class FileStore {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private String baseLocation;
	public static String accountDir = "Account";
	public static String boardDir = "Board";
	public static String thumbnailDir = "Thumbnail";
	
	private String tempDirName = "temp";

	public FileStore() {
		
	}

	public FileStore(String baseLocation) {
		this.baseLocation = baseLocation;
	}

	public String getBaseLocation() {
		return baseLocation + File.separator;
	}

	public void setBaseLocation(String baseLocation) {
		this.baseLocation = baseLocation;
	}
	
	public String getTempDirName() {
		return tempDirName;
	}

	public File getFile(String path) {
		File file = new File(path);
		if(file.exists()) {
			return file;
		}
		else {
			return null;
		}
	}

	public Image createFilePath(MultipartFile file, String subLocation) {
		String name = System.nanoTime() + "_" + file.getOriginalFilename();

		StringBuilder builder = new StringBuilder();
		builder.append(subLocation).append(File.separator);
		builder.append(name);

		//builder.append(baseLocation).append("\\");
		//builder.append(file.getOriginalFilename());

		Image image = createImage(builder.toString(), getBaseLocation() + builder.toString(), name, file);

		return image;
	}

	public List<FileInfoDto> createFilePaths(List<MultipartFile> files, String subLocation) {
		List<FileInfoDto> infoList = new ArrayList<FileInfoDto>();
		for(MultipartFile file : files) {
			String name = System.nanoTime() + "_" + file.getOriginalFilename();
			StringBuilder builder = new StringBuilder();
			builder.append(subLocation).append(File.separator);
			builder.append(name);

			FileInfoDto fileInfo = new FileInfoDto(0, 0, 0, name, getBaseLocation() + builder.toString(), file.getContentType(), null);
			infoList.add(fileInfo);
		}

		return infoList;
	}
	
	public void saveMultiPartFile(MultipartFile multipartFile, String path) throws IllegalStateException, IOException {
		File file = new File(path);
		
		multipartFile.transferTo(file);
	}
	
	public String saveThumbnail(BufferedImage image, File file) throws IOException {
		String fileName = file.getName();
		String extension = FilenameUtils.getExtension(fileName);
		String extensionSplitName = FilenameUtils.getBaseName(fileName) + "_thumb";
		
		File newFile = new File(baseLocation + thumbnailDir + File.separator + extensionSplitName + "." + extension);
		
		File saveFolder = new File(baseLocation + thumbnailDir);
		if(!saveFolder.exists())
		{
			logger.debug("Thumbnail 폴더가 존재하지 안아 새로 생성합니다.");
			saveFolder.mkdir();
		}
		
		ImageIO.write(image, extension, newFile);
		
		return newFile.getName();
	}

	private Image createImage(String path, String absolutePath, String name, MultipartFile imageFile) {
		Image image = new Image();
		image.setPath(path);
		image.setAbsolutePath(absolutePath);
		image.setName(name);
		image.setImage(imageFile);
		return image;
	}
}
