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

import com.planner.planner.Common.FileInfo;
import com.planner.planner.Dto.FileInfoDto;

@Component
public class FileStore {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private String baseLocation;
	private String accountDir = "Account";
	private String boardDir = "Board";
	private String thumbnailDir = "Thumbnail";
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
	
	public String getAccountDir() {
		return baseLocation + File.separator + accountDir + File.separator;
	}

	public String getBoardDir() {
		return baseLocation + File.separator + boardDir + File.separator;
	}

	public String getThumbnailDir() {
		return baseLocation + File.separator + thumbnailDir + File.separator;
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

	public FileInfo createFilePath(MultipartFile file, String path) {
		String name = System.nanoTime() + "_" + file.getOriginalFilename();

//		StringBuilder builder = new StringBuilder();
//		builder.append(subLocation).append(File.separator);
//		builder.append(name);

		//builder.append(baseLocation).append("\\");
		//builder.append(file.getOriginalFilename());

		FileInfo image = createImage(name, path + name, name, file);

		return image;
	}

	public List<FileInfoDto> createFilePaths(List<MultipartFile> files, String path) {
		List<FileInfoDto> infoList = new ArrayList<FileInfoDto>();
		for(MultipartFile file : files) {
			String name = System.nanoTime() + "_" + file.getOriginalFilename();
//			StringBuilder builder = new StringBuilder();
//			builder.append(subLocation).append(File.separator);
//			builder.append(name);

			FileInfoDto fileInfo = new FileInfoDto(0, 0, 0, name, path + name, file.getContentType(), null);
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

	private FileInfo createImage(String path, String absolutePath, String name, MultipartFile imageFile) {
		FileInfo image = new FileInfo();
		image.setPath(path);
		image.setAbsolutePath(absolutePath);
		image.setName(name);
		image.setImage(imageFile);
		return image;
	}
}
