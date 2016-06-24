package com.ftp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;

/**
 * Created by yuwt on 2016/5/12.
 */
public class FtpTest {
	public static void main(String[] args) {
		FTPClient ftp = new FTPClient();
//		FTPClientConfig config = new FTPClientConfig();
//		ftp.configure(config);
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

			ftp.makeDirectory("gmrb");
			ftp.makeDirectory("gmrb/2016");
//
//			System.out.println("ftp status: " + ftp.getStatus());
//
//			String workingDir = "qczy/2016-01-15";
//			ftp.changeWorkingDirectory(workingDir);
//			FTPFile[] ftpFiles = ftp.listFiles();
//			System.out.println("file nums: " + ftpFiles.length);
//			for (FTPFile f : ftpFiles) {
////				System.out.println(f.getName());
//				String fileName = f.getName();
//				File dest = FileUtils.createFile("e:\\download\\ftp\\download\\qczy\\2016-01-15\\" + fileName);
//				FileOutputStream out = new FileOutputStream(dest);
//				boolean b = ftp.retrieveFile(fileName, out);
//				if (b) {
//					System.out.println("retrieve file succeed: " + fileName);
//				}
//				out.close();
//			}

//			FileInputStream inputStream = new FileInputStream("e:\\download\\nginx.pdf");
//			if (ftp.storeFile("nginx1.pdf", inputStream)) {
//				System.out.println("store file to server succeeded.");
//			}

//			File file = FileUtils.createFile("e:\\download\\ftp\\nginx.pdf");
//			FileOutputStream out = new FileOutputStream(file);
//			ftp.retrieveFile("nginx.pdf", out);
//			out.close();
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
}
