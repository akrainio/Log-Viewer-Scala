package models

import scala.reflect.io.File

/**
  * Created by akrainio on 9/30/17.
  */
trait Finder[I, R] {

  // Pattern defining stamp format
  val pattern: String
  // Number of inner layers (indexes)
  def size: R
  // The first inner layer
  def first: I
  // The last inner layer
  def last: I
  // Get the stamp at given index
  def getStamp(index: R): String
  // Get the first and last stamps
  def getStampRange: (String, String)
  // Find index of
  def find(stamp: String, inclusive: Boolean): R
  def getFragment(startStamp: Option[String], endStamp: Option[String]): (R, R)
  def comparator(stamp1: String, stamp2: String): Int

}

object LogParser {

  def getFragment(file: File, startStamp: Option[])

}
