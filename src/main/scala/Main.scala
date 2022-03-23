import encoding.{Chromosome, ChromosomeEncoder}
import ga.constrain.Constrainer
import ga.{AdaptiveStrategy, GA, Logger}
import view.WebUI
import spec.{AtTheBack, HouseSpec, Kitchen, Room}

import java.util.logging
import utils.RandomGenerator

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}

object Main {

  def main(args: Array[String]): Unit = {
    val twoBedroom = {
      val kitchen = Kitchen(width = 4, height = 7)
      val room = Room(width = 5, height = 5)
      HouseSpec build(width = 10, height = 16) place(kitchen, AtTheBack) place(room)
    }

    val gaLogger = logging.Logger.getLogger("GA")
    val logger = new Logger(gaLogger)

    val random = new RandomGenerator(new java.util.Random())
    val constrainer = Constrainer.createGuillotined(twoBedroom, random)
    val aga = new AdaptiveStrategy(random)
    val ga = new GA(constrainer, aga, logger)(random)

    val chromosome = ChromosomeEncoder.encodeRandomly(twoBedroom)(random)
    val solution: Chromosome = ga.search(chromosome)

    val ui = new WebUI()
    val webPage = ui.renderPage(solution)

    Files.write(
      Paths.get("house.html"),
      webPage.getBytes(StandardCharsets.UTF_8))
  }

}
