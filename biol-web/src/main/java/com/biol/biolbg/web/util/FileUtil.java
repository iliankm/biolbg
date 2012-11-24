package com.biol.biolbg.web.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileUtil {
	
	public static List<File> allFilesFromDirectory(String dirName) {
		List<File> res = new ArrayList<File>();
		File dir = new File(dirName);
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			if (files == null) {
				return res;
			}
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					res.add(files[i]);
				}
			}
		}
		return res;
	}
	
	public static void deleteImageFilesForItem(String dirName, String itemId, String newFileName) {
		List<File> files = allFilesFromDirectory(dirName);
		Iterator<File> iter = files.iterator();
		while (iter.hasNext()) {
			File file = iter.next();
			String fileName = file.getName();
			if (fileName.startsWith(itemId.concat("_"))) {
				if (!fileName.equals(newFileName)) {
					file.delete();
				}
			}
		}
	}
	
	public static String imageFileName(String dirName, Integer itemId) {
		String res = null;
		File dir = new File(dirName);
		File[] files = dir.listFiles();
		if (files == null) {
			return res;
		}
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				if (files[i].getName().startsWith(itemId.toString().concat("_"))) {
					res = files[i].getName();
				}
			}
		}
		return res;
	}
}
