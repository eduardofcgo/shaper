package view

import encoding.ChromosomeEncoder
import org.scalatest.StreamlinedXmlEquality._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spec.{HouseSpec, Kitchen}

class WebUITest extends AnyFlatSpec with Matchers {

  "WebUI" should "format house xml" in {
    val spec = HouseSpec build(width=5, height=5)

    val chromosome = ChromosomeEncoder.encode(spec)
      .place(Kitchen(width=3, height=4), x=1, y=1)

    val ui = new WebUI()

    val expected = {
      <div id="house">
        <div class="row">
          <div class="empty cell"></div>
          <div class="empty cell"></div>
          <div class="empty cell"></div>
          <div class="empty cell"></div>
          <div class="empty cell"></div>
        </div>
        <div class="row">
          <div class="empty cell"></div>
          <div class="kitchen cell" data-legend="Kitchen"></div>
          <div class="kitchen cell" data-legend="Kitchen"></div>
          <div class="kitchen cell" data-legend="Kitchen"></div>
          <div class="empty cell"></div>
        </div>
        <div class="row">
          <div class="empty cell"></div>
          <div class="kitchen cell" data-legend="Kitchen"></div>
          <div class="kitchen cell" data-legend="Kitchen"></div>
          <div class="kitchen cell" data-legend="Kitchen"></div>
          <div class="empty cell"></div>
        </div>
        <div class="row">
          <div class="empty cell"></div>
          <div class="kitchen cell" data-legend="Kitchen"></div>
          <div class="kitchen cell" data-legend="Kitchen"></div>
          <div class="kitchen cell" data-legend="Kitchen"></div>
          <div class="empty cell"></div>
        </div>
        <div class="row">
          <div class="empty cell"></div>
          <div class="kitchen cell" data-legend="Kitchen"></div>
          <div class="kitchen cell" data-legend="Kitchen"></div>
          <div class="kitchen cell" data-legend="Kitchen"></div>
          <div class="empty cell"></div>
        </div>
      </div>
    }

    ui.formatXML(chromosome) should equal(expected)
  }


}
