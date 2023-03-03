package com.planner.planner;

import static org.junit.Assert.*;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

public class RegularExpressionTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void getImgTag() {
		String testText = " <p>게시글 내용 테스트</p>\r\n"
				+ " <p>여기 너무 재미있어요!</p>\r\n"
				+ " <p>여기도 재미있었어요!\r\n"
				+ " 하지만 아쉬운점도 있었죠.\r\n"
				+ " 아쉬운점은 모르겠어요!\r\n"
				+ " </p>\r\n"
				+ " <p><img alt=\"\" src=\"img/temp/1230231515_img.png\" style=\"height:100px; width:100px\" /></p>\r\n"
				+ " <p>테스트테스트<p>\r\n"
				+ " <p><img alt=\"\" src=\"img/temp/1230231515_img.png\" style=\"height:100px; width:100px\" /></p>\r\n"
				+ " <p>어쩌면 아쉽지 않았을지도..</p>\r\n"
				+ " <p>그치만 재미있었어요.</p>\r\n"
				+ " <p><img alt=\"\" src=\"img/temp/1230231515_img.png\" style=\"height:100px; width:100px\" /></p>\r\n"
				+ " <p><img alt=\"\" src=\"img/temp/1230231515_img.png\" style=\"height:100px; width:100px\" /></p>\r\n";
		Pattern pattern = Pattern.compile("<img.*src=([\"|'].*(\\/temp\\/)(\\.*[^\"]*)[\"|'])");
		Matcher matcher = pattern.matcher(testText);
		String searchText = "/temp/";
		String replaceText = "/";
		int correctionValue = 0;
		StringBuilder sb = new StringBuilder(testText);
		System.out.println(correctionValue);
		while(matcher.find()) {
			String replaceStr = matcher.group(1).replace(searchText, replaceText);
			sb.replace(matcher.start(1)+correctionValue, matcher.end(1)+correctionValue, replaceStr);
			correctionValue -= searchText.length() - replaceText.length();
			System.out.println(sb.toString());
		}

		//assertEquals(matcher.group(1),"\"img/1230231515_img.png\"");
	}

}
