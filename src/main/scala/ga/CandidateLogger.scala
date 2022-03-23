package ga

import java.util.logging

trait CandidateLogger {

  def log(candidate: Candidate): Unit

}

class Logger(
  logger: logging.Logger,
  var avgFitness: Double = .0,
  var nCandidate: Int = 0
) extends CandidateLogger {

  override def log(candidate: Candidate): Unit = {
    if (avgFitness == .0) avgFitness = candidate.fitness

    avgFitness = (avgFitness + candidate.fitness ) / 2
    nCandidate += 1

    logger.info(() => "Fitness " + candidate.fitness)

    if (candidate.isSolution)
      logger.info(() => s"Found solution at iteration $nCandidate. Average fitness $avgFitness")
  }

}