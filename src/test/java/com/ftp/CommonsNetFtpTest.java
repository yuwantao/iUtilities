package com.ftp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;

/**
 * Created by yuwt on 2016/5/23.
 */
public class CommonsNetFtpTest {
	@Test
	public void test() throws UnsupportedEncodingException {
		String dirName = "文萃报";
		dirName = new String(dirName.getBytes("utf-8"), "iso-8859-1");
		System.out.println(dirName);
	}

	@Test
	public void test_chinese_filename() {
		FTPClient ftp = new FTPClient();
		try {
			String server = "ftp.newspaperdirect.com";
//			String server = "192.168.10.205";
//			String server = "epub.cibtc.com.cn";
			ftp.connect(server);
			ftp.login("ChinaInterBookTrade", "a6pAM>ZZPA]a");
//			ftp.login("yuwt", "yuwt");
//			ftp.login("epubpr", "cibtc20167531");
//			ftp.login("epub025", "cibtc20169292");
			ftp.enterLocalPassiveMode();
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			ftp.setControlEncoding("utf-8");

			String dirName = "STORIES & ANECDOTES_SHORT STORY_MONTH END_20160620";
//			dirName = new String(dirName.getBytes("utf-8"), "iso-8859-1");
			ftp.makeDirectory(dirName);
			ftp.changeWorkingDirectory(dirName);
//			ftp.makeDirectory("abc");
//			ftp.changeWorkingDirectory("abc");
//			ftp.storeFile(new String("PIS-015 37° Women.xls".getBytes("utf-8"), "iso-8859-1"), new FileInputStream("e:\\data\\pr\\pis\\PIS-015 37° Women.xls"));

//			String path = "e:\\download\\tmp\\";
//			String fName = "封面.pdf";
////			FileInputStream in = new FileInputStream(path + fName);
////			ftp.storeFile(new String(fName.getBytes("utf-8"), "iso-8859-1"), in);
//
//			FileOutputStream out = new FileOutputStream(path + "1.pdf");
//			ftp.retrieveFile(fName, out);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Test
	public void get_encoding() {
		FTPClient ftp = new FTPClient();
		try {
//			String server = "ftp.newspaperdirect.com";
//			ftp.connect(server);
//			ftp.login("ChinaInterBookTrade", "a6pAM>ZZPA]a");
			String server = "192.168.10.205";
			ftp.connect(server);
			ftp.login("yuwt", "yuwt");
			ftp.enterLocalPassiveMode();
//			ftp.enterLocalActiveMode();
//			ftp.enterRemotePassiveMode();
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
//			ftp.setControlEncoding("utf-8");
			System.out.println(ftp.getAutodetectUTF8());
			System.out.println(ftp.getControlEncoding());

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test_upload_file() {
		FTPClient ftp = new FTPClient();
		try {
			String server = "192.168.10.205";
			ftp.connect(server);
			ftp.login("yuwt", "yuwt");
			ftp.enterLocalPassiveMode();
//			ftp.enterLocalActiveMode();
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			ftp.setControlEncoding("utf-8");
//			ftp.setCharset(StandardCharsets.UTF_8);

			String dirName = "YILIN GLOBAL CHILDREN’S LITERATURE_20160615";
			dirName = new String(dirName.getBytes("utf-8"), "iso-8859-1");
			ftp.makeDirectory(dirName);
			ftp.changeWorkingDirectory(dirName);
			boolean b = ftp.storeFile("nginx.pdf", new FileInputStream("e:\\download\\IntelliJIDEA_ReferenceCard.pdf"));
			if (b) {
				System.out.println("succeed");
			} else {
				System.out.println("Failed");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test_get_file_size() {
		FTPClient ftp = new FTPClient();
		try {
			String server = "192.168.10.205";
			ftp.connect(server);
			ftp.login("yuwt", "yuwt");
			ftp.enterLocalPassiveMode();
//			ftp.enterLocalActiveMode();
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			ftp.setControlEncoding("utf-8");
//			ftp.setCharset(StandardCharsets.UTF_8);
			String workingDirectory = ftp.printWorkingDirectory();
			System.out.println(workingDirectory);
			FTPFile ftpFile = ftp.mlistFile("/abc/1.pdf");
			System.out.println(ftpFile);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test_retrieve_file() throws IOException {
		FTPClient ftp = new FTPClient();
		try {
			String server = "epub.cibtc.com.cn";
			ftp.connect(server);
			ftp.login("epub002", "cibtc20164086");
//			ftp.enterLocalPassiveMode();
			ftp.enterLocalActiveMode();
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			ftp.setControlEncoding("utf-8");
//			ftp.setCharset(StandardCharsets.UTF_8);

//			String dirName = "儿童绘本";
//			dirName = new String(dirName.getBytes("utf-8"), "iso-8859-1");
//			ftp.changeWorkingDirectory(dirName);

			String path = "e:\\download\\tmp\\";
			String fName = "CHINA TODAY_CHINESE_20160805.pdf";
////			FileInputStream in = new FileInputStream(path + fName);
////			ftp.storeFile(new String(fName.getBytes("utf-8"), "iso-8859-1"), in);
//
			FileOutputStream out = new FileOutputStream(path + "1.pdf");
			ftp.retrieveFile(new String(fName.getBytes("utf-8"), "iso-8859-1"), out);

//			FTPFile[] ftpFiles = ftp.listFiles();
//			for (FTPFile f : ftpFiles) {
//				String fName = f.getName();
//				System.out.println(fName);
//				if (fName.endsWith("四封.pdf")) {
////					InputStream inputStream = ftp.retrieveFileStream(fName);
////					System.out.println(inputStream.toString());
//					File dest = FileUtils.createFile("e:\\download\\tmp\\" + "四封.pdf");
//					FileOutputStream out = new FileOutputStream(dest);
//					ftp.retrieveFile(new String(fName.getBytes("utf-8"), "iso-8859-1"), out);
//				}
//			}
//			File dest = FileUtils.createFile("e:\\download\\tmp\\封面.pdf");
//					FileOutputStream out = new FileOutputStream(dest);
////			String fileName = "上传说明.txt";
////			ftp.retrieveFile(new String(fileName.getBytes("iso-8859-1"), "utf-8"), out);
//			ftp.retrieveFile("human and nature_20160501.pdf", out);

//			FTPFile[] dirs = ftp.listDirectories();
//			for (FTPFile dir : dirs) {
//				String dirName = dir.getName();
//				ftp.changeWorkingDirectory(dirName);
//
//				FTPFile[] ftpFiles = ftp.listFiles();
//				for (FTPFile f : ftpFiles) {
//					String fileName = f.getName();
//					if (fileName.equals(".") || fileName.equals("..") || !fileName.endsWith(".pdf")) {
//						continue;
//					}
//					File dest = FileUtils.createFile("e:\\download\\tmp\\" + fileName);
//					FileOutputStream out = new FileOutputStream(dest);
//					boolean b = ftp.retrieveFile(fileName, out);
//					if (b) {
//						System.out.println("retrieve file succeed: " + fileName);
//					} else {
//						System.out.println("retrieve file failed: " + fileName);
//					}
//					out.close();
//				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Test
	public void test_make_chinese_dir() {
		FTPClient ftp = new FTPClient();
		try {
			String server = "192.168.10.205";
			ftp.connect(server);
			ftp.login("yuwt", "yuwt");
			ftp.enterLocalPassiveMode();
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			ftp.setControlEncoding("utf-8");
			String str = "光明日报";
			String dirName = new String(str.getBytes("utf-8"), "iso-8859-1");
			ftp.makeDirectory(dirName);
			ftp.changeWorkingDirectory(dirName);
			FTPFile[] ftpFiles = ftp.listFiles();
			for (FTPFile file : ftpFiles) {
				System.out.println(file.getName());
			}

			ftp.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test_remove_directory() {
		FTPClient ftp = new FTPClient();
		try {
			String server = "192.168.10.205";
			ftp.connect(server);
			System.out.println("Connected to " + server + ".");
			System.out.print(ftp.getReplyString());
			int reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				System.err.println("FTP server refused connection.");
			}

			if (ftp.login("yuwt", "yuwt")) {
				System.out.println("login to ftp server succeeded.");
				System.out.println("Remote system is " + ftp.getSystemType());
			} else {
				ftp.logout();
				System.out.println("login to ftp server failed..");
			}
			ftp.enterLocalPassiveMode();
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			ftp.setControlEncoding("utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}

		String parentDir = "/home/yuwt/ftp";
		String currentDir = "qczy";
		try {
			removeDirectory(ftp, parentDir, currentDir);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Removes a non-empty directory by delete all its sub files and
	 * sub directories recursively. And finally remove the directory.
	 */
	public static void removeDirectory(FTPClient ftpClient, String parentDir,
	                                   String currentDir) throws IOException {
		String dirToList = parentDir;
		if (!currentDir.equals("")) {
			dirToList += "/" + currentDir;
		}

		FTPFile[] subFiles = ftpClient.listFiles(dirToList);

		if (subFiles != null && subFiles.length > 0) {
			for (FTPFile aFile : subFiles) {
				String currentFileName = aFile.getName();
				if (currentFileName.equals(".") || currentFileName.equals("..")) {
					// skip parent directory and the directory itself
					continue;
				}
				String filePath = parentDir + "/" + currentDir + "/"
						+ currentFileName;
				if (currentDir.equals("")) {
					filePath = parentDir + "/" + currentFileName;
				}

				if (aFile.isDirectory()) {
					// remove the sub directory
					removeDirectory(ftpClient, dirToList, currentFileName);
				} else {
					// delete the file
					boolean deleted = ftpClient.deleteFile(filePath);
					if (deleted) {
						System.out.println("DELETED the file: " + filePath);
					} else {
						System.out.println("CANNOT delete the file: "
								+ filePath);
					}
				}
			}

			// finally, remove the directory itself
			boolean removed = ftpClient.removeDirectory(dirToList);
			if (removed) {
				System.out.println("REMOVED the directory: " + dirToList);
			} else {
				System.out.println("CANNOT remove the directory: " + dirToList);
			}
		}
	}
}
