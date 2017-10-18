package models.LogParser

import java.io.{File, RandomAccessFile}
import java.text.{ParseException, SimpleDateFormat}

import scala.annotation.tailrec


class SearchableFile(val infile: File, val pattern: String) extends Searchable[Long, Long] {
  private val file: RandomAccessFile = new RandomAccessFile(infile, "r")
  val format: SimpleDateFormat = new SimpleDateFormat(pattern)
  private val size = file.length()

  override val first: Long = adjustedIndex(0)
  override val last: Long = adjustedIndex(size - 1)

  override def mid(idx1: Long, idx2: Long): Long = {
    val idx1End = findLineEnd(idx1)
    if (idx1 <= idx2) idx1End + (findLineStart(idx2) - idx1End) / 2
    else mid(idx2, idx1)
  }

  override def get(idx: Long): Long = {
    file.seek(adjustedIndex(idx))
    format.parse(file.readLine.trim).getTime
  }

  def adjustedIndex(idx: Long, backtrack: Boolean = true): Long = {
    if (backtrack) {
      stampedLineBack(idx) match {
        case Some(x) => findLineStart(x)
        case None => stampedLineForw(idx) match {
          case Some(x) => findLineStart(x)
          case None => throw new NoSuchElementException("No valid timestamps present in file")
        }
      }
    } else {
      stampedLineForw(idx) match {
        case Some(x) => findLineStart(x)
        case None => stampedLineBack(idx) match {
          case Some(x) => findLineStart(x)
          case None => throw new NoSuchElementException("No valid timestamps present in file")
        }
      }
    }
  }

  @tailrec
  final def findLineStart(idx: Long): Long = {
    assert(idx < size)
    if (idx <= 0) 0
    else {
      file.seek(idx)
      if (file.readByte.toChar == '\n') idx + 1
      else findLineStart(idx - 1)
    }
  }

  @tailrec
  final def findLineEnd(idx: Long): Long = {
    assert(idx <= size)
    assert(idx >= 0)
    if (idx == size) idx - 1
    else {
      file.seek(idx)
      val readChar = file.readByte.toChar
      if (readChar == '\n') idx
      else findLineEnd(idx + 1)
    }
  }

  final def isStamped(idx: Long): Boolean = {
    val lineStart = findLineStart(idx)
    try {
      file.seek(lineStart)
      format.parse(file.readLine.trim)
      true
    } catch {
      case _: ParseException => false
    }
  }

  private final def stampedLineForw(idx: Long): Option[Long] = {
    def nextLine(idx: Long) = findLineEnd(idx) + 1
    def rec(idx: Long): Option[Long] = {
      if (idx >= size) None
      else if (isStamped(idx)) Some(idx)
      else rec(nextLine(idx))
    }
    rec(nextLine(idx))
  }

  private final def stampedLineBack(idx: Long): Option[Long] = {
    def prevLine(idx: Long) = findLineStart(idx) - 1
    def rec(idx: Long): Option[Long] = {
      if (idx < 0) None
      else if (isStamped(idx)) Some(idx)
      else rec(prevLine(idx))
    }
    rec(prevLine(idx))
  }
}
