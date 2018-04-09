package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._
import Math._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  lazy val genMap: Gen[Map[Int,Int]] = for {
    k <- arbitrary[Int]
    v <- arbitrary[Int]
    m <- oneOf(const(Map.empty[Int,Int]), genMap)
  } yield m.updated(k, v)

  lazy val genHeap: Gen[H] = for {
    i <- arbitrary[Int]
    h <- oneOf(const(empty), genHeap)
  } yield insert(i, h)
  
  
  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)

  property("min1") = forAll { a: Int =>
    val h = insert(a, empty)
    findMin(h) == a
  }
  
  property("gen1") = forAll { (h: H) =>
    val m = if (isEmpty(h)) 0 else findMin(h)
    findMin(insert(m, h)) == m
  }

  property("minOf2") = forAll { (a: Int, b: Int) =>
    val h = insert(a, insert(b, empty))
    if( a < b ) findMin(h) == a
    else findMin(h) == b
  }
  
  property("delete1") = forAll { a: Int =>
    val h = insert(a, empty)
    isEmpty(deleteMin(h))
  }

  
  def isMin(lastMin: Int, h: H): Boolean = {
    if( isEmpty(h) ) true
    else {
      val min = findMin(h)
      if( lastMin < min ) isMin(min, deleteMin(h))
      else false
    }    
  }
  
  property("isSorted") = forAll { (h: H) =>
    if( isEmpty(h) ) true
    else {
      val min = findMin(h)
      isMin(min, deleteMin(h))
    }
  }
  
  property("minMelding") = forAll { (h1: H, h2: H) =>
    val h3 = meld(h1, h2)
    
    val h1m = findMin(h1)
    val h2m = findMin(h2)
    val h3m = findMin(h3)
    
    if( h1m < h2m ) h1m == h3m
    else h2m == h3m
  }
  
 
}
