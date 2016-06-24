package com.jsch;

import com.jcraft.jsch.*;

import java.util.Hashtable;

/**
 * Created by yuwt on 2016/5/13.
 */
public class TestJsch {
	public static void main(String[] args) {
		JSch jsch = new JSch();
		try {
			Session jschSession = jsch.getSession("test2", "192.168.10.205", 22);
			jschSession.setPassword("abc123");
			Hashtable<String,String> jschConfig = new Hashtable<>();
			jschConfig.put("StrictHostKeyChecking", "no");
			jschSession.setConfig(jschConfig);
			jschSession.connect();
//			Channel channel = jschSession.openChannel("exec");
//			((ChannelExec) channel).setCommand("/root/test.sh");
//			channel.connect();
////			channel.start();
//			channel.disconnect();
			jschSession.disconnect();
		} catch (JSchException e) {
			e.printStackTrace();
		}
	}
}
