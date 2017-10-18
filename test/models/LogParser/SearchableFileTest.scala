package models.LogParser
import java.io.{File, RandomAccessFile}

import org.junit.Assert.assertEquals
import org.junit.{BeforeClass, Test}
import models.LogParser.SearchableFileTest._

class SearchableFileTest {

  @Test
  def findLineStartEndTest(): Unit = {
    val start = searchableFile.findLineStart(1000)
    val end = searchableFile.findLineEnd(1000)

    val buffer = new Array[Byte]((end - start).toInt)
    randomAccessFile.seek(start)
    randomAccessFile.readFully(buffer)
    randomAccessFile.seek(end)
    val c = randomAccessFile.readByte.toChar
    assertEquals('\n', c)
  }

  @Test
  def stampedLineTest(): Unit = {
    assertEquals(true, searchableFile.isStamped(1000))
  }
}

object SearchableFileTest {

  var file: File = _
  var searchableFile: SearchableFile = _
  var randomAccessFile: RandomAccessFile = _

  @BeforeClass
  def setUp(): Unit = {
    file = new File("testfile")
    searchableFile = new SearchableFile(file, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    randomAccessFile = new RandomAccessFile(file, "r")
  }
}
