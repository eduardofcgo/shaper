package ga

import ga.constrain.{Adjustment, RandomAdjustment, MutationAdjustment}
import utils.{DoubleGenerator, RandomGenerator}

class AdaptiveStrategy(random: DoubleGenerator) {

  private final val avgFitness = 0.516

  private def probabilityRandomMutation(fitness: Double) =
    (1 - fitness) / (1 - avgFitness)

  def select(adjustments: Iterable[MutationAdjustment], fitness: Double): Iterable[Adjustment] = {
    val p = probabilityRandomMutation(fitness)
    adjustments.map { adjustment =>
      val room = adjustment.room
      if (random.nextDouble() > p) RandomAdjustment(room)
      else adjustment
    }
  }

}
