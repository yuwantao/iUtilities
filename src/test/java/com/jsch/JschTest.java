package com.jsch;

import com.jcraft.jsch.*;
import com.utils.FileUtils;
import org.junit.Test;

import java.io.*;
import java.util.Hashtable;
import java.util.Vector;

import static com.jcraft.jsch.ChannelSftp.LsEntrySelector;

/**
 * Created by yuwt on 2016/5/20.
 */
public class JschTest {

	@Test
	public void create_linux_user() {
		JSch jsch = new JSch();
		try {
			Session jschSession = jsch.getSession("root", "192.168.10.205", 22);
			jschSession.setPassword("dev2014<>?");
			Hashtable<String,String> jschConfig = new Hashtable<>();
			jschConfig.put("StrictHostKeyChecking", "no");
			jschSession.setConfig(jschConfig);
			jschSession.connect();
//			Channel channel = jschSession.openChannel("shell");
////			((ChannelExec) channel).setCommand("useradd test2");
//			InputStream in = System.in;
//			channel.setInputStream(in);
//			PrintStream out = System.out;
//			channel.setOutputStream(out);
//			channel.connect();
//			byte[] buf = new byte[1024];
//			while (true) {
//				in.read(buf, 0, 8);
//
//			}

			Channel channel = jschSession.openChannel("exec");
			((ChannelExec)channel).setCommand("echo test2:abc1234 | /usr/sbin/chpasswd");
			InputStream commandOutput = channel.getInputStream();
			InputStream errStream = ((ChannelExec) channel).getErrStream();
			channel.connect();

			StringBuilder outputBuffer = new StringBuilder();
			int readByte = commandOutput.read();
			while(readByte != 0xffffffff)
			{
				outputBuffer.append((char)readByte);
				readByte = commandOutput.read();
			}

			StringBuilder errBuffer = new StringBuilder();
			int errByte = errStream.read();
			while ((errByte != -1)) {
				errBuffer.append((char)errByte);
				errByte = errStream.read();
			}

			channel.disconnect();
			System.out.println("output: " + outputBuffer.toString());
			System.out.println("err: " + errBuffer.toString());
			if (errBuffer.toString().contains("already exists")) {
				System.out.println("user already exists");
			}

//			out.println("ls /root");
//			out.flush();
//			PrintStream err = System.err;
//			channel.setExtOutputStream(err);

//			out.println("useradd test1");
////			System.out.println(IOUtils.toString(err, "utf-8"));
//			out.println("exit");
//			out.flush();
//			System.out.println("in:" + IOUtils.toString(channel.getInputStream(), "utf-8"));
//			System.out.println("err:" + IOUtils.toString(((ChannelExec) channel).getErrStream(), "utf-8"));
//			System.out.println("ext in:" + IOUtils.toString(channel.getExtInputStream(), "utf-8"));
//			System.out.println("exit status:" + channel.getExitStatus());

//			in.close();
//			out.close();
//			err.close();

//			channel.start();
//			channel.disconnect();
//			System.out.println("exit status:" + channel.getExitStatus());
//			jschSession.disconnect();
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void get_remote_file() {
		JSch jsch = new JSch();
		try {
			Session jschSession = jsch.getSession("root", "192.168.10.195", 22);
			jschSession.setPassword("dev2014<>?");
			Hashtable<String,String> jschConfig = new Hashtable<>();
			jschConfig.put("StrictHostKeyChecking", "no");
			jschSession.setConfig(jschConfig);
			jschSession.connect();
			Channel channel = jschSession.openChannel("sftp");
			Channel copyChannel = jschSession.openChannel("sftp");
			channel.connect();
			copyChannel.connect();
			ChannelSftp channelSftp = (ChannelSftp)channel;
			ChannelSftp copyChannelSftp = (ChannelSftp)copyChannel;
			copyChannelSftp.cd("/root/crawler-newspaper/data/epaper/光明日报/2016-05-20/");
			channelSftp.ls("/root/crawler-newspaper/data/epaper/光明日报/2016-05-20/", (e) -> {
				System.out.println(e.getFilename());
				if (!e.getFilename().equals(".")  && !e.getFilename().equals("..")) {
					try {
						InputStream in = copyChannelSftp.get(e.getFilename());
						byte[] buffer = new byte[1024];
						BufferedInputStream bis = new BufferedInputStream(in);
						File file = FileUtils.createFile("e:\\download\\crawler-newspaper\\gmrb\\" + e.getFilename());
						BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
						int c;
						while ((c = bis.read(buffer)) > 0) {
							bos.write(buffer, 0, c);
						}
						bis.close();
						bos.close();
					} catch (SftpException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				return LsEntrySelector.CONTINUE;
			});
//			channel.start();
			channel.disconnect();
			copyChannel.disconnect();
			jschSession.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void clear_old_files() {
		JSch jsch = new JSch();
		try {
			Session jschSession = jsch.getSession("root", "192.168.10.195", 22);
			jschSession.setPassword("dev2014<>?");
			Hashtable<String,String> jschConfig = new Hashtable<>();
			jschConfig.put("StrictHostKeyChecking", "no");
			jschSession.setConfig(jschConfig);
			jschSession.connect();
			Channel channel = jschSession.openChannel("sftp");
			ChannelSftp channelSftp = (ChannelSftp) channel;
			channelSftp.connect();
			String oldPath = "/root/crawler-newspaper/data/epaper/光明日报/2016-05-03/";
			deleteDirectory(channelSftp, oldPath);

			channelSftp.disconnect();
			jschSession.disconnect();
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (SftpException e) {
			e.printStackTrace();
		}
	}

	public static void deleteDirectory(ChannelSftp sftp, String oldestBackup) throws SftpException {
		if (isDir(sftp, oldestBackup)) {
			sftp.cd(oldestBackup);
			Vector<ChannelSftp.LsEntry> entries = sftp.ls(".");
			for (ChannelSftp.LsEntry entry: entries) {
				if (!entry.getFilename().equals(".")  && !entry.getFilename().equals("..")) {
					deleteDirectory(sftp, entry.getFilename());
				}
			}
			sftp.cd("..");
			sftp.rmdir(oldestBackup);
		} else {
			sftp.rm(oldestBackup);
		}
	}

	private static boolean isDir(ChannelSftp sftp, String entry) throws SftpException {
		return sftp.stat(entry).isDir();
	}
}
