package reductions

import java.util.concurrent._
import scala.collection._
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import common._

import ParallelParenthesesBalancing._

@RunWith(classOf[JUnitRunner])
class ParallelParenthesesBalancingSuite extends FunSuite {

  test("balance should work for empty string") {
    def check(input: String, expected: Boolean) =
      assert(balance(input.toArray) == expected,
        s"balance($input) should be $expected")

    check("", true)
  }
  
  test("balance should work for string of length 1") {
    def check(input: String, expected: Boolean) =
      assert(balance(input.toArray) == expected,
        s"balance($input) should be $expected")

    check("(", false)
    check(")", false)
    check(".", true)
  }

  test("balance should work for string of length 2") {
    def check(input: String, expected: Boolean) =
      assert(balance(input.toArray) == expected,
        s"balance($input) should be $expected")

    check("()", true)
    check(")(", false)
    check("((", false)
    check("))", false)
    check(".)", false)
    check(".(", false)
    check("(.", false)
    check(").", false)
  }

  /***************************************************
   * parBalance
   ****************************************************/
  
  val threshold = 4
  
  test("parBalance should work for empty string") {
    def check(input: String, expected: Boolean) =
      assert(parBalance(input.toArray, threshold) == expected,
        s"balance($input) should be $expected")

    check("", true)
  }

  test("parBalance should work for string of length 1") {
    def check(input: String, expected: Boolean) =
      assert(parBalance(input.toArray, threshold) == expected,
        s"balance($input) should be $expected")

    check("(", false)
    check(")", false)
    check(".", true)
  }
  
  test("parBalance should work for string of length 2") {
    def check(input: String, expected: Boolean) =
      assert(parBalance(input.toArray, threshold) == expected,
        s"balance($input) should be $expected")

    check("()", true)
    check(")(", false)
    check("((", false)
    check("))", false)
    check(".)", false)
    check(".(", false)
    check("(.", false)
    check(").", false)
  }
  
  test("parBalance should work for random string of various lengths") {
    def check(input: String, expected: Boolean) =
      assert(parBalance(input.toArray, threshold) == expected,
        s"balance($input) should be $expected")

    check("(if (zero? x) max (/ 1 x))", true)
    check("I told him (that it's not (yet) done). (But he wasn't listening)", true)
    check("(o_()", false)
    check(":-)", false)
    check("())(", false)
  }    
}