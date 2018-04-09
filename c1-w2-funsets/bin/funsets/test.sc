package funsets

  import FunSets._
  

object test {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  val s1 = singletonSet(1);                       //> s1  : funsets.FunSets.Set = <function1>
  val s2 = singletonSet(2);                       //> s2  : funsets.FunSets.Set = <function1>
  val sn1 = singletonSet(-1);                     //> sn1  : funsets.FunSets.Set = <function1>
  
  val s1s2 = union(s1, s2)                        //> s1s2  : funsets.FunSets.Set = <function1>
  
  println( contains(s1s2, 3) )                    //> false
  
}