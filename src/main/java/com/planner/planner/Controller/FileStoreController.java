package com.planner.planner.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.planner.planner.Dto.FileInfoDto;
import com.planner.planner.Dto.UploadFileDto;
import com.planner.planner.Service.FileService;
import com.planner.planner.Util.ResponseMessage;
import com.planner.planner.Util.UserIdUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value="/api/upload")
public class FileStoreController {
	
	private FileService fileService;
	
	public FileStoreController(FileService fileService) {
		this.fileService = fileService;
	}
	
	@PostMapping(value="/file-upload")
	public ResponseEntity<Object> uploadFile(HttpServletRequest req, @RequestPart List<MultipartFile> files) throws Exception {
		int userId = UserIdUtil.getUserId(req);
		List<String> fileList = fileService.fileUpload(userId, files);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(true, "", fileList));
	}
	
	@GetMapping(value="/files/{fileName}")
	public ResponseEntity<byte[]> loadFile(HttpServletRequest req, @PathVariable String fileName) throws Exception {
		UploadFileDto data  = fileService.loadImage(fileName);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.parseMediaType(data.getType())).body(data.getData());
	}
	
	@DeleteMapping(value="/files/{fileName}")
	public ResponseEntity<Object> deleteFile(HttpServletRequest req, @PathVariable String fileName) throws Exception {
		int userId = UserIdUtil.getUserId(req);
		FileInfoDto fileInfo = fileService.findFileInfo(fileName);
		
		if(fileInfo.getFileWriterId() != userId) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseMessage(true, "접근할 권한이 없습니다."));
		}
		
		fileService.deleteUploadFile(fileInfo);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, ""));
	}
}
