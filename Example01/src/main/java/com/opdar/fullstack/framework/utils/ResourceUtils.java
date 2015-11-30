package com.opdar.fullstack.framework.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ResourceUtils {

	public interface FileFinder {
		//后缀
		String suffix();
		//搜索的包名
		String getPackageName();
		//找到文件后将被调用
		void call(String packageName, String file);
	}

	public static void find(FileFinder finder) {
		String packageName = finder.getPackageName();
		String packageDirName = packageName.replace('.', '/');
		Enumeration<URL> dirs;
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			dirs = loader.getResources(packageDirName);
			while (dirs.hasMoreElements()) {
				URL url = dirs.nextElement();
				String protocol = url.getProtocol();
				if ("file".equals(protocol)) {
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					findFile(packageName, filePath, finder);
				} else if ("jar".equals(protocol)) {
					findFileInJar(url,finder);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void findFileInJar(URL url,FileFinder finder) throws IOException {
		String packageName = finder.getPackageName();
		String packageDirName = packageName.replace('.', '/');
		JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
		Enumeration<JarEntry> entries = jar.entries();
		while (entries.hasMoreElements()) {
			JarEntry entry = entries.nextElement();
			String name = entry.getName();
			if (name.charAt(0) == '/') {
				name = name.substring(1);
			}
			if (name.startsWith(packageDirName)) {
				int idx = name.lastIndexOf('/');
				if (idx != -1) {
					packageName = name.substring(0, idx).replace('/', '.');
				}
				if ((idx != -1)) {
					if (name.endsWith(finder.suffix()) && !entry.isDirectory()) {
						String className = name.substring(packageName.length() + 1, name.length() - 6);
						if (packageName != null && packageName.trim().length() > 0) {
							if (packageName.charAt(packageName.length() - 1) != '.') {
								packageName = packageName.concat(".");
							}
						}
						finder.call(packageName, className);
					}
				}
			}
		}
	}

	private static void findFile(String packageName, String packagePath, final FileFinder finder) {
		File dir = new File(packagePath);
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}
		File[] dirfiles = dir.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return (file.isDirectory()) || (file.getName().endsWith(finder.suffix()));
			}
		});
		for (File file : dirfiles) {
			if (packageName != null && packageName.trim().length() > 0) {

				if (packageName.charAt(packageName.length() - 1) != '.') {
					packageName = packageName.concat(".");
				}
			}
			if (file.isDirectory()) {
				findFile(packageName + file.getName(), file.getAbsolutePath(), finder);
			} else {
				String className = file.getName().substring(0, file.getName().length() - 6);
				finder.call(packageName, className);
			}
		}
	}
}
