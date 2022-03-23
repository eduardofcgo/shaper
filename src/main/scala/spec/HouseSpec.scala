package spec

import utils.MathUtils

case class HouseDimensions(width: Int, height: Int)

sealed trait RoomSpec {
  val width: Int
  val height: Int
}

final case class Kitchen(width: Int, height: Int) extends RoomSpec
final case class Room(width: Int, height: Int) extends RoomSpec

case class HouseSpec private
  (dimensions: HouseDimensions,
   private val roomConstraints: Map[RoomSpec, Set[Constraint]]) {

  def rooms: Set[RoomSpec] = roomConstraints.keys.toSet

  def constraints(roomSpec: RoomSpec): Set[Constraint] =
    roomConstraints.getOrElse(roomSpec, Set.empty)

  def place(room: RoomSpec, constraints: Constraint*): HouseSpec = {
    val constraintsSet = constraints.toSet

    if (constraintsSet.contains(Guillotine) && !MathUtils.isEven(dimensions.width))
      throw new IllegalArgumentException(s"Uneven width ${dimensions.width} for guillotined placement constraint")
    if (constraintsSet.contains(AtTheBack) && !MathUtils.isEven(dimensions.height))
      throw new IllegalArgumentException(s"Uneven height ${dimensions.width} for AtTheBack constraint")

    val newRooms = roomConstraints + ((room, constraintsSet))
    copy(roomConstraints = newRooms)
  }

}

object HouseSpec {

  def build(width: Int, height: Int): HouseSpec =
    build(HouseDimensions(width, height))

  def build(dimensions: HouseDimensions): HouseSpec = dimensions match {
    case HouseDimensions(width, height) =>
      if (width <= 0 || height <= 0)
        throw new IllegalArgumentException(s"Width and height should be greater than 0")

      HouseSpec(dimensions, Map.empty)
  }

}
