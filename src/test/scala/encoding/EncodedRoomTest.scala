package encoding

import org.scalatest.{AppendedClues, FlatSpec, Matchers}
import spec.{HouseDimensions, Kitchen}


class EncodedRoomTest extends FlatSpec with Matchers with AppendedClues {

  private val dimensions = HouseDimensions(4, 4)
  private val kitchen = Kitchen(2, 2)

  "Room" should "be at top right" in {
    val encoded = RoomEncoder.encode(kitchen, x=0, y=0, dimensions)
    val placement =
      """░░■■
         ░░■■
         ░░░░
         ░░░░"""
    withClue(placement) {
      encoded.x should equal (0)
      encoded.xx should equal (1)
      encoded.y should equal (0)
      encoded.yy should equal (1)
    }
  }

  it should "be at the bottom" in {
    val encoded = RoomEncoder.encode(kitchen, x=1, y=2, dimensions)
    val placement =
      """░░░░
         ░░░░
         ░■■░
         ░■■░"""
    withClue(placement) {
      encoded.x should equal (1)
      encoded.xx should equal (2)
      encoded.y should equal (2)
      encoded.yy should equal (3)
    }
  }

  it should "be in the center" in {
    val encoded = RoomEncoder.encode(kitchen, x=1, y=1, dimensions)
    val placement =
      """░░░░
         ░■■░
         ░■■░
         ░░░░"""
    withClue(placement) {
      encoded.x should equal (1)
      encoded.xx should equal (2)
      encoded.y should equal (1)
      encoded.yy should equal (2)
    }
  }

  "Larger room" should "be at top left" in {
    val encoded = RoomEncoder.encode(Kitchen(3, 2), x=1, y=0, dimensions)
    val placement =
      """■■■░
         ■■■░
         ░░░░
         ░░░░"""
    withClue(placement) {
      encoded.x should equal (1)
      encoded.xx should equal (3)
      encoded.y should equal (0)
      encoded.yy should equal (1)
    }
  }
}
