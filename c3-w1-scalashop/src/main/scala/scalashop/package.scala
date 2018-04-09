
import common._

package object scalashop {

  /** The value of every pixel is represented as a 32 bit integer. */
  type RGBA = Int

  /** Returns the red component. */
  def red(c: RGBA): Int = (0xff000000 & c) >>> 24

  /** Returns the green component. */
  def green(c: RGBA): Int = (0x00ff0000 & c) >>> 16

  /** Returns the blue component. */
  def blue(c: RGBA): Int = (0x0000ff00 & c) >>> 8

  /** Returns the alpha component. */
  def alpha(c: RGBA): Int = (0x000000ff & c) >>> 0

  /** Used to create an RGBA value from separate components. */
  def rgba(r: Int, g: Int, b: Int, a: Int): RGBA = {
    (r << 24) | (g << 16) | (b << 8) | (a << 0)
  }

  /** Restricts the integer into the specified range. */
  def clamp(v: Int, min: Int, max: Int): Int = {
    if (v < min) min
    else if (v > max) max
    else v
  }

  /** Image is a two-dimensional matrix of pixel values. */
  class Img(val width: Int, val height: Int, private val data: Array[RGBA]) {
    def this(w: Int, h: Int) = this(w, h, new Array(w * h))
    def apply(x: Int, y: Int): RGBA = data(y * width + x)
    def update(x: Int, y: Int, c: RGBA): Unit = data(y * width + x) = c
  }

  /** Computes the blurred RGBA value of a single pixel of the input image. */
  def boxBlurKernel(src: Img, x: Int, y: Int, radius: Int): RGBA = {
    if( radius == 0 ) {
      src(x,y)
    } else {
      val minX = clamp(x-radius, 0, src.width-1)
      val maxX = clamp(x+radius, 0, src.width-1)
      val minY = clamp(y-radius, 0, src.height-1)
      val maxY = clamp(y+radius, 0, src.height-1)
      
      var reds=0
      var greens=0
      var blues=0
      var alphas = 0
      var numPixels = 0
      
      var pX = minX
      while(pX <= maxX) {
        var pY = minY
        while(pY <= maxY) {
          val pixel = src(pX, pY)
          reds = reds + red(pixel)
          greens = greens + green(pixel)
          blues = blues + blue(pixel)
          alphas = alphas + alpha(pixel)
          
          numPixels = numPixels + 1 // can also calculate this value          }
          
          pY = pY + 1
        }
        pX = pX + 1
      }
      
      reds = reds / numPixels
      greens = greens / numPixels
      blues = blues / numPixels
      alphas = alphas / numPixels
      
      rgba(reds, greens, blues, alphas)
    }
  }

}
