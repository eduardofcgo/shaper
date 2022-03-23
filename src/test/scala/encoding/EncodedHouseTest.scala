package encoding

import org.scalatest.{FlatSpec, Matchers}
import spec.HouseDimensions
import utils.MathUtils

class EncodedHouseTest extends FlatSpec with Matchers {

  private val dimensions = HouseDimensions(4, 6)
  private val house = new EncodedHouse(dimensions)

  "House" should "have correct middlePatternDown" in {
    val expected = MathUtils.toInt(Seq(0, 0, 0, 1, 1, 1))
    house.middlePatternDown should equal (expected)
  }

  it should "have correct middlePatternUp" in {
    val expected = MathUtils.toInt(Seq(1, 1, 1, 0, 0, 0))
    house.middlePatternUp should equal (expected)
  }

  it should "have correct middlePatternRight" in {
    val expected = MathUtils.toInt(Seq(1, 1, 0, 0))
    house.middlePatternRight should equal (expected)
  }

  it should "have correct middlePatternLeft" in {
    val expected = MathUtils.toInt(Seq(0, 0, 1, 1))
    house.middlePatternLeft should equal (expected)
  }

}
