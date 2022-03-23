package ga.constrain

import encoding.{EncodedHouse, EncodedRoom}
import ga.Mutation

object AtTheBack {

  def constrain(room: EncodedRoom, house: EncodedHouse): MutationStrategy = {
    val overlap = Integer.bitCount(house.middlePatternDown & room.heightString)

    if (overlap > 0) MutationStrategy.single(Mutation(x = 0, y = -overlap))
    else MutationStrategy.none
  }

}
