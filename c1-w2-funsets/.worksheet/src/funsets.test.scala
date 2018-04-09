package funsets

  import FunSets._
  

object test {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(97); 
  println("Welcome to the Scala worksheet");$skip(31); 
  
  val s1 = singletonSet(1);System.out.println("""s1  : funsets.FunSets.Set = """ + $show(s1 ));$skip(28); ;
  val s2 = singletonSet(2);System.out.println("""s2  : funsets.FunSets.Set = """ + $show(s2 ));$skip(30); ;
  val sn1 = singletonSet(-1);System.out.println("""sn1  : funsets.FunSets.Set = """ + $show(sn1 ));$skip(30); ;
  
  val s1s2 = union(s1, s2);System.out.println("""s1s2  : funsets.FunSets.Set = """ + $show(s1s2 ));$skip(34); 
  
  println( contains(s1s2, 3) )}
  
}
