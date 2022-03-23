package spec

sealed trait Constraint extends Product with Serializable
case object AtTheBack extends Constraint
case object Guillotine extends Constraint
final case class NextTo(rooms: Set[RoomSpec]) extends Constraint
final case class Overlap(rooms: Set[RoomSpec]) extends Constraint
