package models.LogParser
import org.junit.Assert.assertEquals
import org.junit.Test

class SearcherTest {

  @Test
  def testArraySearch(): Unit = {

    class ArrayWrapper(array: Array[Int]) extends Searchable[Int, Int] {
      override val first: Int = 0
      override val last: Int = array.length - 1

      override def mid(idx1: Int, idx2: Int): Int = {
        if (idx1 <= idx2) idx1 + (idx2 - idx1) / 2
        else mid(idx2, idx1)
      }

      override def get(idx: Int): Int = array(idx)
    }

    val array = new ArrayWrapper(Array(3, 5, 8, 25))
    val searcher = new Searcher[Int, Int](array)

    assertEquals(Some(0), searcher.find(3))
    assertEquals(Some(0), searcher.find(4))
    assertEquals(Some(1), searcher.find(5))
    assertEquals(Some(1), searcher.find(6))
    assertEquals(Some(1), searcher.find(7))
    assertEquals(Some(2), searcher.find(8))
    assertEquals(Some(2), searcher.find(9))
    assertEquals(Some(2), searcher.find(24))
    assertEquals(Some(2), searcher.find(24))
    assertEquals(Some(3), searcher.find(25))
    assertEquals(None, searcher.find(26))
    assertEquals(None, searcher.find(2))

  }

}
