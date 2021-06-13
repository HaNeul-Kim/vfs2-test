package com.tistory.hskimsky.vfs2;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hskimsky
 * @version 0.1
 * @since 2021-06-13
 */
public class VFS2Test {

  private static final Logger logger = LoggerFactory.getLogger(VFS2Test.class);

  private static FileSystemManager manager;
  private static int setupCount;

  @BeforeClass
  public static void setup() throws FileSystemException {
    manager = VFS.getManager();
    // ((DefaultFileSystemManager) manager).addProvider("tar", new TarFileProvider());
    // ((DefaultFileSystemManager) manager).addProvider(new String[]{"tar:gz", "tgz"}, new TgzFileProvider());
    String[] schemes = manager.getSchemes();
    for (String scheme : schemes) {
      logger.info("scheme = {}", scheme);
    }
    setupCount++;
    logger.info("setupCount = {}", setupCount);
  }

  @Test
  public void jarTest() throws FileSystemException {
    // FileObject jarFile = manager.resolveFile("jar:file:///f:/projects/spring-websocket-test/target/test-classes/file/maven-jar-plugin-3.2.0.jar");
    FileObject jarFile = manager.resolveFile("jar:res:/file/maven-jar-plugin-3.2.0.jar");
    FileObject[] children = jarFile.getChildren();
    Assert.assertEquals(2, children.length);
    Assert.assertEquals("META-INF", children[0].getName().getBaseName());
    Assert.assertEquals("org", children[1].getName().getBaseName());
  }

  @Test
  public void zipTest() throws FileSystemException {
    // FileObject zipFile = manager.resolveFile("zip:file:///F:/projects/spring-websocket-test/target/test-classes/file/apache-maven-3.8.1-bin.zip");
    FileObject zipFile = manager.resolveFile("zip:res:/file/apache-maven-3.8.1-bin.zip");
    FileObject[] children = zipFile.getChildren();
    Assert.assertEquals(1, children.length);
    Assert.assertEquals("apache-maven-3.8.1", children[0].getName().getBaseName());
  }

  @Test
  public void tarGzTest() throws FileSystemException {
    // org.apache.commons.vfs2.FileSystemException: Could not find file with URI "tar:gz:file:///f:/projects/spring-websocket-test/target/test-classes/file/apache-maven-3.8.1-bin.tar.gz" because it is a relative path, and no base URI was provided.
    // => add dependency org.apache.commons:commons-compress:1.20
    // FileObject tarGzFile = manager.resolveFile("tgz:/f:/projects/spring-websocket-test/src/test/resources/file/apache-maven-3.8.1-bin.tar.gz");
    // FileObject tarGzFile = manager.resolveFile("tgz:file:///f:/projects/spring-websocket-test/target/test-classes/file/apache-maven-3.8.1-bin.tar.gz");
    // org.apache.commons.vfs2.FileSystemException: Unknown message with code "Could not open Tar file "C:\Users\hskim\AppData\Local\Temp\vfs_cache\tmp_604_".".
    // => tar:gz:... -> tgz:...
    // FileObject tarGzFile = manager.resolveFile("tar:gz:res:/file/apache-maven-3.8.1-bin.tar.gz");
    FileObject tarGzFile = manager.resolveFile("tgz:res:/file/apache-maven-3.8.1-bin.tar.gz");
    FileObject[] children = tarGzFile.getChildren();
    Assert.assertEquals(1, children.length);
    Assert.assertEquals("apache-maven-3.8.1", children[0].getName().getBaseName());
  }
}
