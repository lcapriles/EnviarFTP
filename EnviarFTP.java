package com.qualita.util.SFTP;

import com.jcraft.jsch.*;

public class EnviarFTP {
  private static final String REMOTE_HOST = "lachlinux";
  private static final String USERNAME = "ubuntu";
  private static final int REMOTE_PORT = 22;
  public static void main(String[] args) {
      String localFile = "/home/lcapriles/Documents/Prueba.txt";
      String remoteFile = "/home/ubuntu/Test3.txt";
      Session jschSession = null;
      try {

          JSch jsch = new JSch();
          //jsch.setKnownHosts("/home/lcapriles/.ssh/hosts");
          jsch.setKnownHosts("./ssh/hosts");
          jschSession = jsch.getSession(USERNAME, REMOTE_HOST, REMOTE_PORT);
          // private key location
          //jsch.addIdentity("/home/lcapriles/.ssh/ssh-key-lachlinux.key");
          jsch.addIdentity("./ssh/ssh-key-lachlinux.key");
          jschSession.connect(10000);
          Channel sftp = jschSession.openChannel("sftp");
          sftp.connect(5000);
          ChannelSftp channelSftp = (ChannelSftp) sftp;
          // transfer file from local to remote server
          channelSftp.put(localFile, remoteFile);
          channelSftp.exit();
      } catch (JSchException | SftpException e) {
          e.printStackTrace();
      } finally {
          if (jschSession != null) {
              jschSession.disconnect();
          }
      }

      System.out.println("Done");
  }
}

