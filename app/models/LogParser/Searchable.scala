package models.LogParser

trait Searchable[Elem, Index] {
  val first: Index
  val last: Index
  def mid(idx1: Index, idx2: Index): Index
  def get(idx: Index): Elem
}
