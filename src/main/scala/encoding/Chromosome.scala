package encoding

import ga.Mutation
import spec.{HouseSpec, RoomSpec}
import utils.IntGenerator

case class  Chromosome private
(house: EncodedHouse,
 private val _genes: Map[RoomSpec, EncodedRoom]) {

  def apply(spec: RoomSpec): EncodedRoom = _genes(spec)

  def place(roomSpec: RoomSpec, x: Int, y: Int): Chromosome = {
    val encodedRoom = RoomEncoder.encode(roomSpec, x, y, house.dimensions)
    val updatedGenes = _genes.updated(roomSpec, encodedRoom)

    copy(_genes = updatedGenes)
  }

  def placeRandomly(roomSpec: RoomSpec)(random: IntGenerator): Chromosome = {
    val randomRoom = RoomEncoder.encodeRandomly(roomSpec, house.dimensions)(random)
    val randomizedGenes = _genes.updated(roomSpec, randomRoom)

    copy(_genes = randomizedGenes)
  }

  def genes: Iterator[(RoomSpec, EncodedRoom)] = this._genes.iterator

  def mutate(room: RoomSpec, mutation: Mutation): Chromosome = {
    val mutated = _genes.updatedWith(room) {
      _.map(existing => existing.mutate(mutation))
    }
    copy(_genes = mutated)
  }

}

object ChromosomeEncoder {

  def encode(spec: HouseSpec): Chromosome = {
    val house = EncodedHouse(spec.dimensions)
    Chromosome(house, Map.empty)
  }

  def encodeRandomly(spec: HouseSpec)(random: IntGenerator): Chromosome = {
    val roomSpecs = spec.rooms

    roomSpecs.foldRight(ChromosomeEncoder.encode(spec)) {
      (roomSpec: RoomSpec, chromosome: Chromosome) =>
        chromosome.placeRandomly(roomSpec)(random)
    }
  }

}
