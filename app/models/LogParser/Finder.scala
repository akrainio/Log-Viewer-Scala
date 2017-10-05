package models.LogParser

/**
  * Created by akrainio on 9/30/17.
  */
abstract class Finder[L, R] {

  // Number of inner layers (indexes)
  protected val size: R

  // The first inner layer
  val first: L

  // The last inner layer
  val last: L

  // Finds the start and end index of given stamps
  def getFragment(startStamp: Option[String], endStamp: Option[String]): (R, R)

  // Get the layer at given index
  protected def getLayer(index: R, backtrack: Boolean): L

  // Get the stamp at given index
  protected def getStamp(layer: L): String

  // Get the first and last stamps
  protected def getStampRange: (String, String)

  // Find index of given stamp
  protected def getIndex(stamp: String): R

  // Pattern defining stamp format
  protected val pattern: String

  // Compares the two given stamps using the pattern
  protected def comparator(stamp1: String, stamp2: String): Int

}
