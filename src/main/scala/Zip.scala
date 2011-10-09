package org.proofcafe.dispatch_unzip

import java.io.File
import java.io.InputStream
import java.io.FileOutputStream
import java.util.zip.ZipInputStream
import java.util.zip.ZipEntry

object Zip {

  /**
   * InputStreamとして受け取ったzippedバイナリをカレントディレクトリに展開
   * する関数。
   * 極めて手続的な実装orz。
   * だってZipInputStreamが持つ複数のentryとstreamを両方参照しながらループ
   * しないといけないんだもん。
   */
  def unzip(is : InputStream) : Unit = {
    val zis = new ZipInputStream(is);
    var entry = zis.getNextEntry()
    while (entry != null) {
      if (entry.isDirectory()) {
        new File(entry.getName()).mkdirs();
      } else {
        val parent = new File(entry.getName()).getParentFile();
        if(parent != null){
          parent.mkdirs();
        }
        val out = new FileOutputStream(entry.getName());
        val buf : Array[Byte] = new Array[Byte](1024);
        var size : Int = zis.read(buf);
        while (size != -1) {
          out.write(buf, 0, size);
          size = zis.read(buf)
        }
        out.close();
      }
      zis.closeEntry();
      entry = zis.getNextEntry()
    }
    zis.close();
  }
}
