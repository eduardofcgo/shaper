package ga.constrain

import encoding.EncodedRoom
import ga.Mutation

object Overlap {

  def constrain(room1: EncodedRoom, room2: EncodedRoom): MutationStrategy = {
    val widthOverlap = Integer.bitCount(room1.widthString & room2.widthString)
    val heightOverlap = Integer.bitCount(room1.heightString & room2.heightString)

    if (widthOverlap == 0 || heightOverlap == 0) MutationStrategy.none
    else {
      val lightestRoomWeight = Integer.min(room1.width, room2.width)
      val moveDistance = Integer.max(1, heightOverlap / lightestRoomWeight)

      MutationStrategy.choice(
        Mutation(x = 0, y = moveDistance),
        Mutation(x = 0, y = -moveDistance))
    }
  }

}
