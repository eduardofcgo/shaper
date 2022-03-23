package ga

case class Mutation(x: Int, y: Int) {

  def magnitude: Double = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2))

  def + (mutation: Mutation): Mutation = Mutation(x + mutation.x, y + mutation.y)

}
