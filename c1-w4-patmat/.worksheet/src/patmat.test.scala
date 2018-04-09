package patmat

import patmat.Huffman._

object test {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(98); 
  println("Welcome to the Scala worksheet");$skip(186); 
  
  //removeChar('a', List('a', 'b', 'a'))
  //countChar('a', List('a', 'b', 'a'))
  val letterCounts = times(List('a','a','a','a','a','a','a','a','b','b','b','c','e','d','f','g','h'));System.out.println("""letterCounts  : List[(Char, Int)] = """ + $show(letterCounts ));$skip(55); 
  
  val listLeafs = makeOrderedLeafList(letterCounts);System.out.println("""listLeafs  : List[patmat.Huffman.Leaf] = """ + $show(listLeafs ));$skip(92); 
  
  //val listTrees = combine(listLeafs)
  val root = until(singleton, combine)(listLeafs);System.out.println("""root  : patmat.Huffman.CodeTree = """ + $show(root ));$skip(41); val res$0 = 
  
  decode(root, List(1,0,0,0,1,0,1,0));System.out.println("""res0: List[Char] = """ + $show(res$0));$skip(66); val res$1 = 
  //decode(root, List(1,1))
  decode(root, List(1,1,1,0,1,1,0,1));System.out.println("""res1: List[Char] = """ + $show(res$1));$skip(38); val res$2 = 
  
  encode(root)(string2Chars("hf"));System.out.println("""res2: List[patmat.Huffman.Bit] = """ + $show(res$2));$skip(36); val res$3 = 
  encode(root)(string2Chars("bac"));System.out.println("""res3: List[patmat.Huffman.Bit] = """ + $show(res$3));$skip(44); val res$4 = 
  
  quickEncode(root)(string2Chars("bac"));System.out.println("""res4: List[patmat.Huffman.Bit] = """ + $show(res$4))}
}
