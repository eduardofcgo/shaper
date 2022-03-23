package ga.constrainer

import encoding.{EncodedHouse, RoomEncoder}
import ga.Mutation
import ga.constrain.{AtTheBack, MutationStrategy}
import org.scalatest._
import flatspec._
import org.scalatest.matchers.should.Matchers._
import spec.{HouseDimensions, Kitchen, RoomSpec}

class AtTheBackConstrainTest extends AnyFlatSpec with AppendedClues {

  private val dimensions = HouseDimensions(width = 4, height = 4)
  private val house = EncodedHouse(dimensions)
  private val roomSpec: RoomSpec = Kitchen(width = 2, height = 2)

  private def place(x: Int, y: Int) =
    RoomEncoder.encode(roomSpec, x, y, dimensions)

  "Room at the back" should "have no mutation" in {
    val room = place(x=0, y=2)
    val mutation = AtTheBack.constrain(room, house)

    mutation shouldEqual MutationStrategy.none withClue
      """░░░░
         ░░░░
         ░░■■
         ░░■■"""
  }

  "Room in the middle" should "should have mutation" in {
    val room = place(x=0, y=1)
    val mutation: MutationStrategy = AtTheBack.constrain(room, house)
    val expected = MutationStrategy.single(Mutation(0, -1))

    mutation shouldEqual expected withClue
      """░░░░
         ░░■■
         ░░■■
         ░░░░"""
  }

  "Room at the front" should "should have mutation" in {
    val room = place(x=1, y=0)
    val mutation = AtTheBack.constrain(room, house)
    val expected = MutationStrategy.single(Mutation(0, -2))

    mutation shouldEqual expected withClue
      """■■░░
         ■■░░
         ░░░░
         ░░░░"""
  }

}
