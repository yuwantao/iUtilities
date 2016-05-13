package com.ftp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
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
			ftp.setFileType(FTP.ASCII_FILE_TYPE);
			ftp.setControlEncoding("utf-8");

//			if (ftp.makeDirectory("tmp")) {
//				System.out.println("make directory succeeded.");
//			}
//
//			System.out.println("ftp status: " + ftp.getStatus());
//
			FTPFile[] ftpFiles = ftp.listFiles();
			for (FTPFile f : ftpFiles) {
				System.out.println(f.getName());
			}
//			FileInputStream inputStream = new FileInputStream("e:\\download\\nginx.pdf");
//			if (ftp.storeFile("nginx.pdf", inputStream)) {
//				System.out.println("store file to server succeeded.");
//			}

//			File file = FileUtils.createFile("e:\\download\\ftp\\nginx.pdf");
//			ftp.retrieveFile("nginx.pdf", new FileOutputStream(file));
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
