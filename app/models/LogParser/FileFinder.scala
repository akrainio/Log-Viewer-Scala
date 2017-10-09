package models.LogParser

import java.io.{File, RandomAccessFile}
import java.text.{ParseException, ParsePosition, SimpleDateFormat}

/**
  * Created by akrainio on 10/1/17.
  */
class FileFinder(val infile: File, protected override val pattern: String) extends Finder[Line, Long] {

  private val file: RandomAccessFile = new RandomAccessFile(infile, "r")

  private val format: SimpleDateFormat = new SimpleDateFormat(pattern)

  protected override val size: Long = file.length()

  override val first: Line = getLayer(0)

  override val last: Line = getLayer(size - 1)

  override def getFragment(startStamp: Option[String], endStamp: Option[String]): (Int, Int) = ???

  override def getLayer(index: Long, backtrack: Boolean = false): Line = {
    def findLineStart(index: Long): Long = {
      file.seek(index)
      if (index <= 0) 0
      else if (file.readChar == '\n') index + 1
      else findLineStart(index - 1)
    }
    val startIndex = findLineStart(index)
    file.seek(startIndex)
    val line = file.readLine()
    try {
      new Line(startIndex, line.length + 1, line, format)
    } catch {
      case e: ParseException => if (backtrack) {
        if (startIndex == 0) {
          throw new Exception("Nowhere to backtrack to")
        } else {
          getLayer(startIndex - 2, backtrack = true)
        }
      } else getLayer(line.length + 1)
    }
  }

  protected override def getStamp(layer: Line): String = layer.stamp

  protected override def getStampRange: (String, String) = (getStamp(first), getStamp(last))

  protected override def getIndex(stamp: String): Long = ???

  override protected def comparator(stamp1: String, stamp2: String): Int = {
    format.parse(stamp1).compareTo(format.parse(stamp2))
  }

}

class Line(private val startIndex: Long, private val endIndex: Long, line: String, format: SimpleDateFormat) {
  val stamp: String = {
    val parsePosition = new ParsePosition(0)
    format.parse(line, parsePosition)
    line.substring(0, parsePosition.getIndex)
  }
}

