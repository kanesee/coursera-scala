package forcomp

import forcomp.Anagrams._

object testsheet {
  /************************************************
   *
   *    UNCOMMENT THE F***ING loadDictionary call
   *
   ***********************************************/
   val list = List(1,2,3,4,5)                     //> list  : List[Int] = List(1, 2, 3, 4, 5)
   list.map{case i => println(i); i*2}            //> 1
                                                  //| 2
                                                  //| 3
                                                  //| 4
                                                  //| 5
                                                  //| res0: List[Int] = List(2, 4, 6, 8, 10)
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