package com.infernosstudio.johny;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileZipper {
	static public void zipDir(String sourceDir, File targetFile) {
		List<String> fileList = FileZipper.generateFileList(sourceDir, new File(sourceDir + "/"));
		FileZipper.zipIt(sourceDir, fileList, targetFile);
	}

	public static void zipIt(String sourceDir, List<String> fileList, File zipFile) {

		byte[] buffer = new byte[1024];

		try {

			FileOutputStream fos = new FileOutputStream(zipFile);
			ZipOutputStream zos = new ZipOutputStream(fos);

			System.out.println("Output to Zip : " + zipFile);

			for (String file : fileList) {

				System.out.println("File Added : " + file);
				ZipEntry ze = new ZipEntry(file);
				zos.putNextEntry(ze);

				FileInputStream in = new FileInputStream(sourceDir + File.separator + file);

				int len;
				while ((len = in.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}

				in.close();
			}

			zos.closeEntry();
			// remember close it
			zos.close();

			System.out.println("Done");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static List<String> generateFileList(String sourceDir, File node) {
		List<String> fileList = new ArrayList<String>();
		if (node.isFile()) {
			fileList.add(FileZipper.generateZipEntry(sourceDir, node.getAbsoluteFile().toString()));
		}

		if (node.isDirectory()) {
			String[] subNodes = node.list();
			for (String filename : subNodes) {
				fileList.addAll(FileZipper.generateFileList(sourceDir, new File(node, filename)));
			}
		}
		return fileList;

	}

	private static String generateZipEntry(String sourceDir, String file) {
		return file.substring(sourceDir.length() + 1, file.length());
	}
}