package models.LogParser
import java.io.File

import org.junit.Assert.assertEquals
import org.junit.{BeforeClass, Test}
/**
  * Created by akrainio on 10/5/17.
  */
class FileFinderTest {

  var file: File = _
  var finder: FileFinder = _

  @BeforeClass
  def setup(): Unit = {
    file = new File("testfile")
    finder = new FileFinder(file, "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
  }

  @Test
  def testGetLayer(): Unit = {
    assertEquals("foo", finder.getLayer(0))
  }
}
