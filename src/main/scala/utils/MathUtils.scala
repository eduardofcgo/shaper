package utils

object MathUtils {

  def isEven(n: Int): Boolean = n % 2 == 0

  def pow2(exp: Int): Int = 1 << exp

  def toInt(array: Seq[Int]): Int =
    array.foldLeft(0) { (acc, element) =>
      (acc << 1) + element
    }

}
