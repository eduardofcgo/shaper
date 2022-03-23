package view

import encoding.Chromosome
import spec.RoomSpec

import scala.xml.Elem

class WebUI {

  private val baseStyling = """
            |   #house {
            |      display: inline-block;
            |   }
            |  .row {
            |     display: flex;
            |  }
            |  .cell {
            |     height: 20px;
            |     width: 20px;
            |     border: 1px solid black;
            |  }
            |  #legend {
            |     position: absolute;
            |     background-color: white;
            |  }
            |  .kitchen {
            |     background-color: red;
            |  }
            |  .room {
            |     background-color: blue;
            |  }
            |""".stripMargin

  private val script =
    """
      |document.querySelectorAll('.cell').forEach(function(roomCell) {
      |   roomCell.addEventListener('mouseover', function(e) {
      |      const legendText = e.target.getAttribute('data-legend')
      |      document.getElementById('legend').textContent = legendText
      |   })
      |})
      |
      |document.getElementById('house').addEventListener('mousemove', function(e) {
      |   const legend = document.getElementById('legend')
      |   legend.style.left = e.pageX + 15 + 'px'
      |   legend.style.top = e.pageY + 10 + 'px'
      |})
      |""".stripMargin

  private def label(spec: RoomSpec) =
    spec.getClass.getSimpleName

  def formatXML(chromosome: Chromosome): Elem = {
    val table = ChromosomeView.tabulate(chromosome)

    def classAttribute(spec: RoomSpec) = s"${label(spec).toLowerCase} cell"
    def emptyClassAttribute = "empty cell"

    <div id="house">
      {table.map(row =>
        {<div class="row">
          {row.map {
            case Some(spec) =>
              <div class={classAttribute(spec)}
                   data-legend={label(spec)}></div>
            case None =>
              <div class={emptyClassAttribute}></div>
          }}
        </div>}
    )}
    </div>
  }

  def renderPage(chromosome: Chromosome): String =
    "<!DOCTYPE html>\n" +
      <html>
        <head>
          <meta charset="utf-8"/>
          <style>
            {baseStyling}
          </style>
        </head>
        <body>
          {formatXML(chromosome)}
          <div id="legend"></div>
          <script>
            {script}
          </script>
        </body>
      </html>

}
