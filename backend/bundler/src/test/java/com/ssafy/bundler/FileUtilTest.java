package com.ssafy.bundler;

import org.junit.jupiter.api.Test;

import com.ssafy.bundler.util.FileUtil;

class FileUtilTest {
	FileUtil fileUtil = new FileUtil();

	@Test
	void getPath() {
		System.out.println(fileUtil.getStaticPath());
	}

	@Test
	void mkdirTest() {
		System.out.println(fileUtil.mkdir("testdir"));
	}

	@Test
	void write() {
		System.out.println(fileUtil.write("testUserUniquePath", "CS", "네트워크 뿌셔버려", "OSI 7 계층이란\n네트워크 계층이다.", 1L));
	}
}