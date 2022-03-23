package encoding

import spec.HouseDimensions
import utils.ArrayUtils.{zeros, ones}
import utils.MathUtils.{toInt, pow2}

case class EncodedHouse(val dimensions: HouseDimensions) {

  val middlePatternRight: Int = toInt(
    ones(dimensions.width / 2) ++
    zeros(dimensions.width / 2))

  val middlePatternUp: Int = toInt(
    ones(dimensions.height / 2) ++
    zeros(dimensions.height / 2))

  val widthPattern: Int = pow2(dimensions.width) - 1
  val heightPattern: Int = pow2(dimensions.height) - 1

  val middlePatternDown: Int = ~middlePatternUp & heightPattern
  val middlePatternLeft: Int = ~middlePatternRight & widthPattern

}
