package view

import encoding.Chromosome
import spec.RoomSpec

object ChromosomeView {

  type Table = Array[Array[Option[RoomSpec]]]

  def tabulate(chromosome: Chromosome): Table = {
    val dimensions = chromosome.house.dimensions

    def defaultCell(x: Int, y: Int): Option[RoomSpec] = Option.empty
    val table = Array.tabulate(dimensions.height, dimensions.width)(defaultCell)

    chromosome.genes foreach {
      case (spec, room) =>
        (room.x to room.xx) foreach { x =>
          (room.y to room.yy) foreach { y =>
            table(y).update(x, Some(spec))
          }
        }
    }

    table
  }

}
