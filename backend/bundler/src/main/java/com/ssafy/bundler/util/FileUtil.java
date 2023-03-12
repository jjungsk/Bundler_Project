package com.ssafy.bundler.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.StringTokenizer;

import org.springframework.stereotype.Component;

@Component
public class FileUtil {
	private static final String staticResourcesPath = "/src/main/resources/static";
	private static final String customPath = "/md";
	private static final String path = Paths.get(System.getProperty("user.dir"), staticResourcesPath).toString();

	public FileUtil() {
		mkdir("");
	}

	public String getStaticPath() {
		return path;
	}

	public String mkdir(String subPath) {
		File Folder = Paths.get(path, customPath, subPath).toFile();

		System.out.println(Folder.getPath());

		if (!Folder.exists()) {
			try {
				Folder.mkdir(); //폴더 생성합니다.
				System.out.println("폴더가 생성되었습니다.");
			} catch (Exception e) {
				e.getStackTrace();
			}
		} else {
			System.out.println("이미 폴더가 생성되어 있습니다.");
		}

		return Folder.getAbsolutePath();
	}

	public String write(String subPath, String category, String title, String content, Long uniqueId) {
		String path = mkdir(subPath);
		path = mkdir(Paths.get(subPath, category).toString());

		String str = "# " + title + "\n";

		StringTokenizer st = new StringTokenizer(content, "\n");

		while (st.hasMoreTokens()) {
			str += "\n* " + st.nextToken();
		}

		String res = null;

		try {
			File file = Paths.get(path, title + uniqueId + ".md").toFile();
			file.createNewFile();

			BufferedWriter output = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(file.getPath()), "UTF-8"));
			output.write(str);
			output.close();
			res = file.getPath();

			System.out.println("파일 작성 성공");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("파일 작성 실패");
		}

		return res;
	}

	public byte[] write(String title, String content) {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("# ").append(title).append(" :file_folder: ");

		StringTokenizer st = new StringTokenizer(content, "\n");

		while (st.hasMoreTokens()) {
			stringBuilder.append("\n<br/>\n").append(st.nextToken());
		}

		return stringBuilder.toString().getBytes(StandardCharsets.UTF_8);
	}
}
