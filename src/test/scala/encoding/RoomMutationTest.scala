package encoding

import ga.Mutation
import org.scalatest.AppendedClues
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spec.{HouseDimensions, Kitchen}

class RoomMutationTest extends AnyFlatSpec with Matchers with AppendedClues {

  private val dimensions = HouseDimensions(4, 4)
  private val roomSpec = Kitchen(2, 2)

  private def encode(x: Int, y: Int) =
    RoomEncoder.encode(roomSpec, x, y, dimensions)

  "Mutate room" should "move it to the left" in {
    val room = encode(x = 0, y = 0)
    val beforeAfter =
      """░░■■
         ░░■■
         ░░░░
         ░░░░

         ■■░░
         ■■░░
         ░░░░
         ░░░░"""
    val mutation = Mutation(x = 2, y = 0)
    val mutated = room.mutate(mutation)

    val expected = encode(x = 2, y = 0)
    mutated shouldBe expected withClue beforeAfter
  }

  "Mutate room" should "move it to the right" in {
    val room = encode(x = 2, y = 1)
    val beforeAfter =
      """░░░░
         ■■░░
         ■■░░
         ░░░░

         ░░░░
         ░░■■
         ░░■■
         ░░░░"""
    val mutation = Mutation(x = -2, y = 0)
    val mutated = room.mutate(mutation)

    val expected = encode(x = 0, y = 1)
    mutated shouldBe expected withClue beforeAfter
  }

  "Mutate room" should "move it up" in {
    val room = encode(x = 0, y = 2)
    val beforeAfter =
      """░░░░
         ░░░░
         ░░■■
         ░░■■"

         ░░■■
         ░░■■
         ░░░░
         ░░░░"""
    val mutation = Mutation(x = 0, y = -2)
    val mutated = room.mutate(mutation)

    val expected = encode(x = 0, y = 0)
    mutated shouldBe expected withClue beforeAfter
  }

  "Mutate room" should "move it down" in {
    val room = encode(x = 1, y = 0)
    val beforeAfter =
      """░■■░
         ░■■░
         ░░░░
         ░░░░"

         ░░░░
         ░■■░
         ░■■░
         ░░░░"""
    val mutation = Mutation(x = 0, y = 1)
    val mutated = room.mutate(mutation)

    val expected = encode(x = 1, y = 1)
    mutated shouldBe expected withClue beforeAfter
  }

  "Mutate room" should "from upper left corner into opposite corner" in {
    val room = encode(x = 2, y = 0)
    val beforeAfter =
      """■■░░
         ■■░░
         ░░░░
         ░░░░"

         ░░░░
         ░░░░
         ░░■■
         ░░■■"""
    val mutation = Mutation(x = -2, y = 2)
    val mutated = room.mutate(mutation)

    val expected = encode(x = 0, y = 2)
    mutated shouldBe expected withClue beforeAfter
  }

  "Mutate room" should "from bottom right corner into opposite corner" in {
    val room = encode(x = 0, y = 2)
    val beforeAfter =
      """░░░░
         ░░░░
         ░░■■
         ░░■■

         ■■░░
         ■■░░
         ░░░░
         ░░░░"""
    val mutation = Mutation(x = 2, y = -2)
    val mutated = room.mutate(mutation)

    val expected = encode(x = 2, y = 0)
    mutated shouldBe expected withClue beforeAfter
  }

}
