package forcomp

import forcomp.Anagrams._

object testsheet {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(256); 
  /************************************************
   *
   *    UNCOMMENT THE F***ING loadDictionary call
   *
   ***********************************************/
   val list = List(1,2,3,4,5);System.out.println("""list  : List[Int] = """ + $show(list ));$skip(39); val res$0 = 
   list.map{case i => println(i); i*2};System.out.println("""res0: List[Int] = """ + $show(res$0))}
  //val occ = wordOccurrences("Zulu")
  //dictionaryByOccurrences(occ)
  //sentenceAnagrams(List("de"))
  /*
  sentenceAnagrams(List())
  sentenceAnagrams(List("Lin"))
  sentenceAnagrams(List("Linux", "rulez"))
  */
 /*
 //val senOcc = List(('e',1), ('x',1), ('u',2), ('l',1), ('r',1), ('z',1))
 val senOcc = List(('e',1),  ('x',1), ('r',1))
 val combos = combinations(senOcc)
 dictionaryByOccurrences(List(('e',1),  ('x',1), ('r',1)))
   */
 //combos.filter(combo => !dictionaryByOccurrences(combo).isEmpty)
 /*
  val sentences = List()
  val tmp = List(("e",List(("e",1))))
  tmp.flatMap(p =>
    sentences map(s => p._1 :: s))
    */
}
