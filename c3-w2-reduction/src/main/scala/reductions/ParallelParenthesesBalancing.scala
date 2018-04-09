package reductions

import scala.annotation._
import org.scalameter._
import common._

object ParallelParenthesesBalancingRunner {

  @volatile var seqResult = false

  @volatile var parResult = false

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 40,
    Key.exec.maxWarmupRuns -> 80,
    Key.exec.benchRuns -> 120,
    Key.verbose -> true
  ) withWarmer(new Warmer.Default)

  def main(args: Array[String]): Unit = {
    val length = 100000000
    val chars = new Array[Char](length)
    val threshold = 10000
    val seqtime = standardConfig measure {
      seqResult = ParallelParenthesesBalancing.balance(chars)
    }
    println(s"sequential result = $seqResult")
    println(s"sequential balancing time: $seqtime ms")

    val fjtime = standardConfig measure {
      parResult = ParallelParenthesesBalancing.parBalance(chars, threshold)
    }
    println(s"parallel result = $parResult")
    println(s"parallel balancing time: $fjtime ms")
    println(s"speedup: ${seqtime / fjtime}")
  }
}

object ParallelParenthesesBalancing {

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
   */
  def balance(chars: Array[Char]): Boolean = {
    def isBalance(chars: Array[Char], numLeftParen: Int): Boolean = {
      if( numLeftParen < 0 )
        false
      else
      if( chars.isEmpty )
        numLeftParen==0
      else {
        if( chars.head == '(' )
          isBalance(chars.tail, numLeftParen+1)
        else
        if( chars.head == ')' )
          isBalance(chars.tail, numLeftParen-1)
        else
          isBalance(chars.tail, numLeftParen)
      }

    }

      isBalance(chars, 0)
  }

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
   */
  def parBalance(chars: Array[Char], threshold: Int): Boolean = {

    def traverse(idx: Int, until: Int, arg1: Int, arg2: Int) : (Int,Int) = {
      
      def do_traverse(charsSlice: Array[Char], acc1: Int, acc2: Int) : (Int,Int) = {
        if( charsSlice.isEmpty ) (acc1, acc2)
        else if( charsSlice.head == '(' ) do_traverse(charsSlice.tail, acc1+1, acc2)
        else if( charsSlice.head == ')' ) {
          if( acc1 > 0 ) do_traverse(charsSlice.tail, acc1-1, acc2)
          else do_traverse(charsSlice.tail, acc1, acc2+1)
        } else do_traverse(charsSlice.tail, acc1, acc2)
      }
      
      do_traverse(chars.slice(idx, until), arg1, arg2)
    }

    def reduce(from: Int, until: Int) : (Int,Int) = {
      if( until - from < threshold ) {
        traverse(from, until, 0, 0)
      } else {
        val mid = (from + until) / 2
        val ((l1,r1), (l2,r2)) = parallel(reduce(from, mid), reduce(mid, until))
        
//        (l1+l2-r1, r1+r2-l2)
        val overlap = scala.math.min(l1, r2)
        (l1+l2 - overlap, r1+r2 - overlap)
      }
    }

    val res = reduce(0, chars.length)
//    println("here: "+ res)
    res == (0,0)
  }

  // For those who want more:
  // Prove that your reduction operator is associative!

}
