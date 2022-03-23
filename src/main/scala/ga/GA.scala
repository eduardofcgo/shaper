package ga

import encoding.Chromosome
import ga.constrain.{Constrainer, RandomAdjustment, MutationAdjustment}
import utils.IntGenerator

import scala.annotation.tailrec

case class Candidate(chromosome: Chromosome, fitness: Double) {
  def isSolution: Boolean = fitness == 1
}

class GA(constrainer: Constrainer, adaptiveStrategy: AdaptiveStrategy, logger: CandidateLogger)(random: IntGenerator)  {

  private def fitness(adjustments: Iterable[MutationAdjustment]) = {
    val magnitudes = adjustments.map(_.mutation.magnitude)
    1 / (magnitudes.sum + 1)
  }

  def generateCandidate(chromosome: Chromosome): Candidate = {
    val adjustments = constrainer.constrain(chromosome)
    val f = fitness(adjustments)

    val (roomsRandomize, selectedAdjustments) =
      adaptiveStrategy.select(adjustments, f).partitionMap {
        case RandomAdjustment(roomSpec) => Left(roomSpec)
        case adjustment: MutationAdjustment => Right(adjustment)
      }

    val randomizedChromosome = roomsRandomize.toSet.foldLeft(chromosome) {
      (chromosome, roomToRandomize) =>
        chromosome.placeRandomly(roomToRandomize)(random)
    }

    val roomMutation =
      selectedAdjustments.groupMapReduce(_.room)(_.mutation)(_ + _)

    val candidateChromosome =
      roomMutation.foldLeft(randomizedChromosome) {
        (chromosome, roomMutation) =>
          chromosome.mutate _ tupled roomMutation
      }

    Candidate(candidateChromosome, fitness=f)
  }

  @tailrec
  final def search(chromosome: Chromosome): Chromosome = {
    val candidate = generateCandidate(chromosome)

    logger.log(candidate)

    if (candidate.isSolution) candidate.chromosome
    else search(candidate.chromosome)
  }

}
