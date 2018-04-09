package streams

import streams.GameDef
import streams.StringParserTerrain

object test {
  def main(args: Array[String]) = {
    val gd = new GameDef
    val spt = new StringParserTerrain
    val levelVector = Vector(Vector('S', 'T'), Vector('o', 'o'), Vector('o', 'o'))
    //val exists = spt.terrainFunction(levelVector)(gd.Pos(0,1))
    //exists(new Pos(0,1))
    //exists
  }
  
  main(null)
}