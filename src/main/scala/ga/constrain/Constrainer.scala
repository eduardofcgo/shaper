package ga.constrain

import encoding.Chromosome
import ga.Mutation
import spec.{Constraint, HouseSpec, NextTo => NextToConstraint, RoomSpec, AtTheBack => AtTheBackConstraint, Guillotine => GuillotineConstraint, Overlap => OverlapConstraint}
import utils.RandomChooser

sealed trait Adjustment
case class RandomAdjustment(roomSpec: RoomSpec) extends Adjustment
case class MutationAdjustment(room: RoomSpec, mutation: Mutation) extends Adjustment

class Constrainer(houseSpec: HouseSpec,
                  defaultConstraints: Set[Constraint],
                  random: RandomChooser) {

  private def constraints(room: RoomSpec) =
    houseSpec.constraints(room) ++ defaultConstraints

  private def createAdjustment(spec: RoomSpec, mutationStrategy: MutationStrategy) =
    mutationStrategy match {
      case NoMutation => Option.empty
      case SingleMutation(mutation) => Some(MutationAdjustment(spec, mutation))
      case ChoiceMutations(mutation1, mutation2) =>
        val chosenMutation = random.choose(mutation1, mutation2)
        Some(MutationAdjustment(spec, chosenMutation.get))
    }

  def constrain(chromosome: Chromosome): Iterable[MutationAdjustment] = {
    chromosome.genes flatMap {
      case (spec, room) =>
        constraints(spec) flatMap {
          case AtTheBackConstraint =>
            val mutationStrategy = AtTheBack.constrain(room, chromosome.house)
            createAdjustment(spec, mutationStrategy)
          case GuillotineConstraint =>
            val mutationStrategy = Guillotine.constrain(room, chromosome.house)
            createAdjustment(spec, mutationStrategy)
          case NextToConstraint(nextToRoomsSpec) =>
            None
          case OverlapConstraint(nonOverlappingRoomsSpec) =>
            for {
              nonOverlappingSpec <- nonOverlappingRoomsSpec
              roomToMutate <- random.choose(spec, nonOverlappingSpec)
              adjustment <- {
                val nonOverlappingRoom = chromosome(nonOverlappingSpec)
                val mutationStrategy = Overlap.constrain(nonOverlappingRoom, room)
                createAdjustment(roomToMutate, mutationStrategy)
              }
            } yield adjustment
        }
    }
  }.to(Iterable)

}

object Constrainer {

  def createDefault(houseSpec: HouseSpec, random: RandomChooser): Constrainer = {
    val defaultConstraints = Set(GuillotineConstraint, OverlapConstraint(houseSpec.rooms))

    new Constrainer(houseSpec, defaultConstraints, random)
  }

  def createGuillotined(houseSpec: HouseSpec, random: RandomChooser): Constrainer =
    new Constrainer(houseSpec, Set(OverlapConstraint(houseSpec.rooms)), random)

  def createUnconstrained(houseSpec: HouseSpec, random: RandomChooser): Constrainer =
    new Constrainer(houseSpec, Set.empty, random)

}
