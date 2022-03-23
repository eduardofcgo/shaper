package ga.constrainer

import encoding.{EncodedHouse, EncodedRoom, RoomEncoder}
import ga.Mutation
import ga.constrain.{Guillotine, MutationStrategy}
import org.scalatest.{AppendedClues, FlatSpec, Matchers}
import spec.{HouseDimensions, Kitchen, RoomSpec}

class GuillotineConstrainTest extends FlatSpec with Matchers with AppendedClues {

  private val dimensions = HouseDimensions(width=4, height=4)
  private val house = new EncodedHouse(dimensions)

  private def encode(x: Int, y: Int, room: RoomSpec) =
    RoomEncoder.encode(room, x, y, dimensions)

  "Room at the left" should "have no mutation" in {
    val roomSpec = Kitchen(width = 2, height = 2)
    val room = encode(x=2, y=1, roomSpec)
    val mutations = Guillotine.constrain(room, house)

    mutations shouldEqual MutationStrategy.none withClue
      """░░░░
         ■■░░
         ■■░░
         ░░░░"""
  }

  "Room at the right" should "have no mutation" in {
    val roomSpec = Kitchen(width = 2, height = 2)
    val room = encode(x=0, y=2, roomSpec)
    val mutations = Guillotine.constrain(room, house)

    mutations shouldEqual MutationStrategy.none withClue
      """░░░░
         ░░░░
         ░░■■
         ░░■■"""
  }

  "Room in the middle" should "should have two possible mutations" in {
    val roomSpec = Kitchen(width = 2, height = 2)
    val room = encode(x=1, y=1, roomSpec)
    val mutations = Guillotine.constrain(room, house)
    val expected = MutationStrategy.choice(Mutation(-1, 0), Mutation(1, 0))

    mutations shouldEqual expected withClue
      """░░░░
         ░■■░
         ░■■░
         ░░░░"""
  }

  "Larger room at the middle left" should "should have mutation" in {
    val roomSpec = Kitchen(width = 3, height = 2)
    val room = encode(x=1, y=0, roomSpec)
    val mutations = Guillotine.constrain(room, house)
    val expected = MutationStrategy.single(Mutation(1, 0))

    mutations shouldEqual expected withClue
      """■■■░
         ■■■░
         ░░░░
         ░░░░"""
  }

  "Larger room at the middle right" should "should have mutation" in {
    val roomSpec = Kitchen(width = 3, height = 2)
    val room = encode(x=0, y=1, roomSpec)
    val mutations = Guillotine.constrain(room, house)
    val expected = MutationStrategy.single(Mutation(-1, 0))

    mutations shouldEqual expected withClue
      """░░░░
         ░■■■
         ░■■■
         ░░░░"""
  }

}
