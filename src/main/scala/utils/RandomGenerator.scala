package utils

trait IntGenerator {
  def nextInt(bound: Int): Int
}

trait DoubleGenerator {
  def nextDouble(): Double
}

trait RandomChooser {
  def choose[T](seq: T*): Option[T]
}

class RandomGenerator(random: java.util.Random)
  extends IntGenerator with DoubleGenerator with RandomChooser {

  def nextInt(bound: Int): Int = random.nextInt(bound)

  def nextDouble(): Double = random.nextDouble()

  def choose[T](seq: T*): Option[T] = {
    val size = seq.size
    if (size == 0) Option.empty
    else Some(seq(nextInt(size)))
  }

}
