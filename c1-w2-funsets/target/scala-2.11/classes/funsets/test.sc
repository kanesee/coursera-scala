package funsets

object test {
  println("Welcome to the Scala worksheet")
  
  import FunSets._
  
  val s1 = singletonSet(1);
  val s2 = singletonSet(2);
  val sn1 = singletonSet(-1);
  
  println( contains(s1, 1) )
  
}