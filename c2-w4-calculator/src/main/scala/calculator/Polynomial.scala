package calculator

import scala.math._

object Polynomial {
  def computeDelta(a: Signal[Double], b: Signal[Double],
      c: Signal[Double]): Signal[Double] = {
    Signal(pow(b(),2)-4*a()*c())
  }

  def computeSolutions(a: Signal[Double], b: Signal[Double],
      c: Signal[Double], delta: Signal[Double]): Signal[Set[Double]] = {
    // This won't work. ans1/ans2 gets evaluated just once so it's like
    // setting a constant signal value
//    val ans1 = (-b() + sqrt(computeDelta(a,b,c)())) / 2*a()
//    val ans2 = (-b() - sqrt(computeDelta(a,b,c)())) / 2*a()
//    Signal( Set(ans1, ans2) )
    
    // by wrapping ans1/ans2 as a signal, we can get its value
    // when its needed by a call to computeSolutions's signal value
    val ans1 = Signal( (-b() + sqrt(computeDelta(a,b,c)())) / 2*a() )
    val ans2 = Signal( (-b() - sqrt(computeDelta(a,b,c)())) / 2*a() )
    Signal( Set(ans1(), ans2()) )
    
    // this will also work but it's harder to read
//    Signal( Set((-b() + sqrt(computeDelta(a,b,c)())) / 2*a(), (-b() - sqrt(computeDelta(a,b,c)())) / 2*a()) )
  }
}
