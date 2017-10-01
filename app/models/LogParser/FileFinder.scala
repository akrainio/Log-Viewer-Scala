package models.LogParser

import java.io.{File, RandomAccessFile}
import java.text.SimpleDateFormat
import java.util.Comparator

/**
  * Created by akrainio on 10/1/17.
  */
class FileFinder(val file: File, protected override val pattern: String) extends Finder[Line, Long] {

  private val randomAccessFile: RandomAccessFile = new RandomAccessFile(file, "r")

  private val format: SimpleDateFormat = new SimpleDateFormat(pattern)

  protected override val size: Long = file.length()

  override val first: Line = getLayer(0)

  override val last: Line = getLayer(size - 1)

  override def getFragment(startStamp: Option[String], endStamp: Option[String]): (Int, Int) = ???

  protected override def getLayer(index: Long): Line = ???

  protected override def getStamp(layer: Line): String = ???

  protected override def getStampRange: (String, String) = ???

  protected override def getIndex(stamp: String): Long = ???

  override protected def comparator(stamp1: String, stamp2: String): Int = {
    format.parse(stamp1).compareTo(format.parse(stamp2))
  }

}

class Line(line: String) {
  val startIndex: Long = ???
  val endIndex: Long = ???
  val stamp: String = ???
}