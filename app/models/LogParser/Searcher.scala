package models.LogParser

class Searcher[Elem, Index](searchable: Searchable[Elem, Index])
                           (implicit ordering: Ordering[Elem]) {

  import scala.math.Ordering.Implicits._
  import searchable._

  private def binarySearch(elem: Elem, min: Index, max: Index): Index = {
    val midIndex = mid(min, max)
    val midElem = get(midIndex)

    if (midElem == elem) midIndex
    else if (elem == get(max)) max
    else if (midElem == get(min)) midIndex
    else if (midElem > elem) binarySearch(elem, min, midIndex)
    else binarySearch(elem, midIndex, max)
  }

  def find(elem: Elem): Option[Index] = {
    if (elem < get(searchable.first)) None
    else if (elem > get(last)) {
      None
    }
    else {
      Some(binarySearch(elem, first, last))
    }
  }

}
