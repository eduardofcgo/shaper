package ga.constrain

import ga.Mutation

sealed trait MutationStrategy
    extends Product
    with Serializable {
}

case object NoMutation extends MutationStrategy

final case class ChoiceMutations(mutation1: Mutation, mutation2: Mutation)
  extends MutationStrategy {

  override def equals(obj: Any): Boolean = obj match {
    case ChoiceMutations(m1, m2) =>
      (m1 == mutation1 && m2 == mutation2) || (m1 == mutation2 && m2 == mutation1)
    case _ => false
  }
}

final case class SingleMutation(mutation: Mutation)
  extends MutationStrategy

object MutationStrategy {
  final def none: MutationStrategy = NoMutation
  final def single(mutation: Mutation): MutationStrategy = SingleMutation(mutation)
  final def choice(mutation1: Mutation, mutation2: Mutation): MutationStrategy =
    ChoiceMutations(mutation1, mutation2)
}
