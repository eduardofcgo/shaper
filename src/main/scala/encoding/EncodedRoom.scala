package encoding

import ga.Mutation
import spec.{HouseDimensions, RoomSpec}
import utils.ArrayUtils.{ones, zeros}
import utils.IntGenerator
import utils.MathUtils.toInt

case class EncodedRoom(widthString: Int, heightString: Int) {

  val width: Int = Integer.bitCount(widthString)
  val height: Int = Integer.bitCount(heightString)

  val x: Int = Integer.numberOfTrailingZeros(widthString)
  val y: Int = Integer.numberOfTrailingZeros(heightString)

  val xx: Int = x + width - 1
  val yy: Int = y + height - 1

  private def shift(string: Int, directedAmount: Int): Int = {
    val amount = Math.abs(directedAmount)
    if (directedAmount > 0) string << amount
    else string >> amount
  }

  def mutate(mutation: Mutation): EncodedRoom =
    EncodedRoom(shift(widthString, mutation.x),
                shift(heightString, mutation.y))

  override def toString: String = s"EncodedRoom($x, $y)"

}

object RoomEncoder {

  private def widthString(x: Int, roomWidth: Int, houseWidth: Int): Seq[Int] =
    zeros(houseWidth - roomWidth - x) ++
    ones(roomWidth) ++
    zeros(x)

  private def heightString(y: Int, roomHeight: Int, houseHeight: Int): Seq[Int] =
    zeros(houseHeight - roomHeight - y) ++
    ones(roomHeight) ++
    zeros(y)

  def encodeRandomly(room: RoomSpec, house: HouseDimensions)(random: IntGenerator): EncodedRoom = {
    val x = random.nextInt(house.width - room.width + 1)
    val y = random.nextInt(house.height - room.height + 1)

    encode(room, x, y, house)
  }

  def encode(room: RoomSpec, x: Int, y: Int, house: HouseDimensions): EncodedRoom =
    EncodedRoom(toInt(widthString(x, room.width, house.width)),
                toInt(heightString(y, room.height, house.height)))

}
