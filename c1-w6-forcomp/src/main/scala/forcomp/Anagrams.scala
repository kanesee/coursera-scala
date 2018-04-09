package forcomp


object Anagrams {
import Tap._

  /** A word is simply a `String`. */
  type Word = String

  /** A sentence is a `List` of words. */
  type Sentence = List[Word]

  /** `Occurrences` is a `List` of pairs of characters and positive integers saying
   *  how often the character appears.
   *  This list is sorted alphabetically w.r.t. to the character in each pair.
   *  All characters in the occurrence list are lowercase.
   *
   *  Any list of pairs of lowercase characters and their frequency which is not sorted
   *  is **not** an occurrence list.
   *
   *  Note: If the frequency of some character is zero, then that character should not be
   *  in the list.
   */
  type Occurrences = List[(Char, Int)]

  
  /** Converts the word into its character occurrence list.
   *
   *  Note: the uppercase and lowercase version of the character are treated as the
   *  same character, and are represented as a lowercase character in the occurrence list.
   *
   *  Note: you must use `groupBy` to implement this method!
   */
  def wordOccurrences(w: Word): Occurrences = {
    val cCount = w.filter(c => c != ' ').toList.map(c => (c.toLower, 1))
//    println(cCount)
    val g = cCount.groupBy(p => p._1)
//    println(g)
    val m = for((c, cs) <- g) yield c -> cs.length
    m.toList.sortBy(_._1)
  }

  /** Converts a sentence into its character occurrence list. */
  def sentenceOccurrences(s: Sentence): Occurrences = wordOccurrences(s mkString "")

  /** The `dictionaryByOccurrences` is a `Map` from different occurrences to a sequence of all
   *  the words that have that occurrence count.
   *  This map serves as an easy way to obtain all the anagrams of a word given its occurrence list.
   *
   *  For example, the word "eat" has the following character occurrence list:
   *
   *     `List(('a', 1), ('e', 1), ('t', 1))`
   *
   *  Incidentally, so do the words "ate" and "tea".
   *
   *  This means that the `dictionaryByOccurrences` map will contain an entry:
   *
   *    List(('a', 1), ('e', 1), ('t', 1)) -> Seq("ate", "eat", "tea")
   *
   */
  lazy val dictionaryByOccurrences: Map[Occurrences, List[Word]] = {
    val m = dictionary map(word => (wordOccurrences(word),  word))
    val g = m.groupBy(_._1)
    val dictOcc = g mapValues(_.map(_._2))
//    dictOcc
    dictOcc.withDefaultValue(Nil)
  }

  /** Returns all the anagrams of a given word. */
  def wordAnagrams(word: Word): List[Word] = dictionaryByOccurrences(wordOccurrences(word))

  /** Returns the list of all subsets of the occurrence list.
   *  This includes the occurrence itself, i.e. `List(('k', 1), ('o', 1))`
   *  is a subset of `List(('k', 1), ('o', 1))`.
   *  It also include the empty subset `List()`.
   *
   *  Example: the subsets of the occurrence list `List(('a', 2), ('b', 2))` are:
   *
   *    List(
   *      List(),
   *      List(('a', 1)),
   *      List(('a', 2)),
   *      List(('b', 1)),
   *      List(('a', 1), ('b', 1)),
   *      List(('a', 2), ('b', 1)),
   *      List(('b', 2)),
   *      List(('a', 1), ('b', 2)),
   *      List(('a', 2), ('b', 2))
   *    )
   *
   *  Note that the order of the occurrence list subsets does not matter -- the subsets
   *  in the example above could have been displayed in some other order.
   */
  def combinations(occurrences: Occurrences): List[Occurrences] = {
    val cs = do_combinations(occurrences)
    cs.map(c => c.sortBy(_._1))
//    cs
  }
  
  def do_combinations(occurrences: Occurrences): List[Occurrences] = {
    if( occurrences.isEmpty ) List(List())
    else { 
      val o = occurrences.head
      for {
        combos <- combinations(occurrences.tail)
        count <- 0 to o._2
      } yield {
        if( count > 0 ) (o._1, count) :: combos
        else combos
      }
    }    
  }
  // m.toList.sortBy(_._1)

  /** Subtracts occurrence list `y` from occurrence list `x`.
   *
   *  The precondition is that the occurrence list `y` is a subset of
   *  the occurrence list `x` -- any character appearing in `y` must
   *  appear in `x`, and its frequency in `y` must be smaller or equal
   *  than its frequency in `x`.
   *
   *  Note: the resulting value is an occurrence - meaning it is sorted
   *  and has no zero-entries.
   */
  def subtract(x: Occurrences, y: Occurrences): Occurrences = {
    val xm = x.toMap
    var tmp = xm
    for (oy <- y) tmp = tmp updated(oy._1, xm(oy._1) - oy._2)

//    println(tmp)
    
    val retval = tmp.toList.filter{ case (k,v) => v > 0 }

    retval.sortWith(_._1 < _._1)

  }

  /** Returns a list of all anagram sentences of the given sentence.
   *
   *  An anagram of a sentence is formed by taking the occurrences of all the characters of
   *  all the words in the sentence, and producing all possible combinations of words with those characters,
   *  such that the words have to be from the dictionary.
   *
   *  The number of words in the sentence and its anagrams does not have to correspond.
   *  For example, the sentence `List("I", "love", "you")` is an anagram of the sentence `List("You", "olive")`.
   *
   *  Also, two sentences with the same words but in a different order are considered two different anagrams.
   *  For example, sentences `List("You", "olive")` and `List("olive", "you")` are different anagrams of
   *  `List("I", "love", "you")`.
   *
   *  Here is a full example of a sentence `List("Yes", "man")` and its anagrams for our dictionary:
   *
   *    List(
   *      List(en, as, my),
   *      List(en, my, as),
   *      List(man, yes),
   *      List(men, say),
   *      List(as, en, my),
   *      List(as, my, en),
   *      List(sane, my),
   *      List(Sean, my),
   *      List(my, en, as),
   *      List(my, as, en),
   *      List(my, sane),
   *      List(my, Sean),
   *      List(say, men),
   *      List(yes, man)
   *    )
   *
   *  The different sentences do not have to be output in the order shown above - any order is fine as long as
   *  all the anagrams are there. Every returned word has to exist in the dictionary.
   *
   *  Note: in case that the words of the sentence are in the dictionary, then the sentence is the anagram of itself,
   *  so it has to be returned in this list.
   *
   *  Note: There is only one anagram of an empty sentence.
   */
  def sentenceAnagrams(sentence: Sentence): List[Sentence] = {
    if( sentence.isEmpty ) List(List())
    else {
      val senOcc = sentenceOccurrences(sentence)
      
      val ans = occurrenceAnagrams(senOcc)
      
      if( !ans.isEmpty ) ans
      else List(List())
    }
  }
  
//  def occurrenceAnagrams(senOcc: Occurrences) : List[Sentence] = {
//    val combos : List[Occurrences] = combinations(senOcc)
////      println("COMBOS: " + combos)
////    val tmp =
//    for {
//      combo <- combos
//      word <- dictionaryByOccurrences(combo)
//      sentence <- occurrenceAnagrams(subtract(senOcc, combo))
//      if( !word.isEmpty )
//    } yield {
//      if( !sentence.isEmpty )  word :: sentence
//      else List(word)
//    }
//    
//  }
  
//  def occurrenceAnagrams(senOcc: Occurrences, verifiedOcc : List[Occurrences]) : List[List[Occurrences]] = {
////    println("senOcc: " + senOcc)
////    println("verifiedOcc: " + verifiedOcc)
//    if( senOcc.isEmpty ) {
////      println("terminal: " + verifiedOcc)
//      List(verifiedOcc)
//    }
//    else {
//      val combos : List[Occurrences] = combinations(senOcc)
////      println("combos: " + combos)
//      val filteredCombos = combos.filter(combo => !dictionaryByOccurrences(combo).isEmpty)
////      println("filteredcombos: " + filteredCombos)
//      for {
//        combo <- filteredCombos
//        occurrence <- occurrenceAnagrams(subtract(senOcc, combo), combo :: verifiedOcc)
//        if( !occurrence.isEmpty )
//      } yield occurrence
//    }    
////    List()
//  }
  
    /** The dictionary is simply a sequence of words.
   *  It is predefined and obtained as a sequence using the utility method `loadDictionary`.
   */
  val dictionary: List[Word] = loadDictionary
//  val dictionary = List("Lin", "Zulu")

//  val dictionary : List[Word] = List(
//      List("Rex", "Lin", "Zulu"),
//      List("nil"),
//      List("Uzi"),
//      List("Linux", "rulez")
//    ).flatten

//  def occurrenceAnagrams(senOcc: Occurrences) : List[Sentence] = {
//    val combos : List[Occurrences] = combinations(senOcc)
////      println("COMBOS: " + combos)
//    val tmp =
//    combos.map(combo =>
//      dictionaryByOccurrences(combo).map(word =>
//        occurrenceAnagrams(subtract(senOcc, combo)).map(sentence => word :: sentence)
//        )
//      ).tap(soFar => println("So far: " + soFar))
//    
////    tmp
//    List()
//  }
  
  def occurrenceAnagrams(senOcc: Occurrences) : List[Sentence] = {
    val combos : List[Occurrences] = combinations(senOcc)
//      println("COMBOS: " + combos)
//    println("============")
    val words =
    for {
      combo <- combos
      word <- dictionaryByOccurrences(combo)
      if( !word.isEmpty )
    } yield (word, combo, subtract(senOcc, combo))
    
//    println("tmp: " + tmp)
//    tmp.map(t => println("word: "+t._1))
    
    val sentences : List[(List[Word],Occurrences)] = 
    for { 
      w <- words
      sentence <- occurrenceAnagrams(w._3)
      if( !sentence.isEmpty )
    } yield (sentence, w._3)
//    } yield sentence
    
//    println("sentences: " + remainingSentences);
    
    if( sentences.isEmpty ) words.filter(p => p._3.isEmpty).flatMap(p => List(List(p._1)))
    else {
//      val retval = 
//      words.flatMap(w => 
//        sentences map(s => if( w._3 == s._2 ) w._1 :: s._1))
////        sentences map(s => w._1 :: s))
//
      for {
        word <- words
        sentence <- sentences
        if( word._3 == sentence._2 )
      } yield word._1 :: sentence._1
      
//        println("retval: " +retval)
//        retval
    }
  //    List()
  }

  
//  def occurrenceAnagrams(senOcc: Occurrences, combos : List[Occurrences]) : List[Sentence] = combos match {
//    case List() => List()
//    case combo :: cs => {
//      println("combo: " + combo)
//      var remainingSenOcc : Occurrences = Nil
//      try {
//        remainingSenOcc = subtract(senOcc, combo)
//      } catch {
//        case nseE : NoSuchElementException => List()
//      }
//      if( remainingSenOcc == Nil ) List()
//      else {
//        println("remainingSenOcc: " + remainingSenOcc)
//        val words : List[Word] = dictionaryByOccurrences(combo)
//        println("words: " + words)
//        val sentences : List[Sentence] = words.map(w => List(w))
//        println("sentences: " + sentences)
//        val remainingSentences : List[Sentence] = occurrenceAnagrams(remainingSenOcc, cs)
//        if( sentences.isEmpty ) remainingSentences
//        else if( remainingSentences.isEmpty ) sentences
//        else {
//          val tmp : List[Sentence] =
//          for {
//            sentence <- sentences
//            remainingSentence <- remainingSentences
//          } yield sentence ++ remainingSentence
//          println("tmp: " + tmp)
//          tmp
//    //      List()
//        }
//      }
//    }
//  }
  
//  def occurrenceAnagrams(senOcc: Occurrences) : List[Sentence] = {
//    if( senOcc.isEmpty ) List()
//    else {
//      val combos = combinations(senOcc)
//      val tmp =
//      for {
//        combo <- combos
//        words <- dictionaryByOccurrences(combo)
////        word <- words
//      } yield words :: occurrenceAnagrams(subtract(senOcc, combo))
//      
//      println(tmp)
//      
//      tmp.map(l => List(l.head)) 
//      List()
//      
//    }
//  }
  
}

object Tap {
  implicit def any2Tapper[A](toTap: A): Tapper[A] = new Tapper(toTap)
  
  class Tapper[A](tapMe: A) {
    def tap(f: (A) => Unit): A = {
      f(tapMe)
      tapMe
    }
  }
}