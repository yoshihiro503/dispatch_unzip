package org.proofcafe.dispatch_unzip

import dispatch.Http
import dispatch.Request
import java.io.InputStream

import scala.io.Source

object Main {

  def main(args: Array[String]) : Unit = {
    try {
      val uri = args(0)
      wget(uri) {
        Zip.unzip(_)
      }

    } catch {
      case e => e.printStackTrace()
    }
  }

  def wget[T](uri : String)(callback : InputStream => T) : T = {
    val http = new Http()
    val req = new Request(uri)
    http(req >> callback)
  }
}
