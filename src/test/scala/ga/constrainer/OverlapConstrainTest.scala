package ga.constrainer

import encoding.RoomEncoder
import ga.Mutation
import ga.constrain.{MutationStrategy, Overlap}
import org.scalatest.{AppendedClues, FlatSpec, Matchers}
import spec.{HouseDimensions, Kitchen, RoomSpec}

class OverlapConstrainTest extends FlatSpec with Matchers with AppendedClues {

  private val dimensions = HouseDimensions(width=4, height=4)

  private def encode(x: Int, y: Int, room: RoomSpec) =
    RoomEncoder.encode(room, x, y, dimensions)

  "Rooms not overlapping" should "have no mutation" in {
    val roomSpec = Kitchen(width = 2, height = 2)
    val room1 = encode(x=2, y=1, roomSpec)
    val room2 = encode(x=0, y=0, roomSpec)
    val mutations = Overlap.constrain(room1, room2)

    mutations shouldEqual MutationStrategy.none withClue
      """░░□□
         ■■□□
         ■■░░
         ░░░░"""
  }

  "Rooms overlapping" should "have mutations" in {
    val roomSpec = Kitchen(width = 2, height = 2)
    val room1 = encode(x=2, y=1, roomSpec)
    val room2 = encode(x=1, y=1, roomSpec)
    val mutations = Overlap.constrain(room1, room2)
    val expected = MutationStrategy.choice(Mutation(0, 1), Mutation(0, -1))

    mutations shouldEqual expected withClue
      """░░░░
         ■▣□░
         ■▣□░
         ░░░░"""
  }

  "Room overlapping with larger room" should "have mutations" in {
    val roomSpec1 = Kitchen(width = 2, height = 4)
    val roomSpec2 = Kitchen(width = 3, height = 4)
    val room1 = encode(x=2, y=0, roomSpec1)
    val room2 = encode(x=1, y=0, roomSpec2)
    val mutations = Overlap.constrain(room1, room2)
    val expected = MutationStrategy.choice(Mutation(0, 2), Mutation(0, -2))

    mutations shouldEqual expected withClue
      """▣▣□░
         ▣▣□░
         ▣▣□░
         ▣▣□░"""
  }

}
