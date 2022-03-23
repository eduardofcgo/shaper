package ga.constrain

import encoding.{EncodedHouse, EncodedRoom}
import ga.Mutation

object Guillotine {

  def constrain(room: EncodedRoom, house: EncodedHouse): MutationStrategy = {
    val overlapLeft = Integer.bitCount(house.middlePatternLeft & room.widthString)
    val overlapRight = Integer.bitCount(house.middlePatternRight & room.widthString)

    val moveLeft = Mutation(overlapLeft, 0)
    val moveRight = Mutation(-overlapRight, 0)

    if (overlapLeft == 0 || overlapRight == 0) MutationStrategy.none
    else if (overlapRight > overlapLeft) MutationStrategy.single(moveLeft)
    else if (overlapRight < overlapLeft) MutationStrategy.single(moveRight)
    else MutationStrategy.choice(moveLeft, moveRight)
  }

}
