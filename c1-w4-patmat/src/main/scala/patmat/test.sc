package patmat

import patmat.Huffman._

object test {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  //removeChar('a', List('a', 'b', 'a'))
  //countChar('a', List('a', 'b', 'a'))
  val letterCounts = times(List('a','a','a','a','a','a','a','a','b','b','b','c','e','d','f','g','h'))
                                                  //> letterCounts  : List[(Char, Int)] = List((a,8), (b,3), (c,1), (e,1), (d,1), 
                                                  //| (f,1), (g,1), (h,1))
  
  val listLeafs = makeOrderedLeafList(letterCounts)
                                                  //> listLeafs  : List[patmat.Huffman.Leaf] = List(h, g, f, d, e, c, b, a)
  
  //val listTrees = combine(listLeafs)
  val root = until(singleton, combine)(listLeafs) //> root  : patmat.Huffman.CodeTree = (a,(((h,g),(f,d)),((e,c),b)))
  
  decode(root, List(1,0,0,0,1,0,1,0))             //> res0: List[Char] = List(h, f)
  //decode(root, List(1,1))
  decode(root, List(1,1,1,0,1,1,0,1))             //> res1: List[Char] = List(b, a, c)
  
  encode(root)(string2Chars("hf"))                //> res2: List[patmat.Huffman.Bit] = List(1, 0, 0, 0, 1, 0, 1, 0)
  encode(root)(string2Chars("bac"))               //> res3: List[patmat.Huffman.Bit] = List(1, 1, 1, 0, 1, 1, 0, 1)
  
  quickEncode(root)(string2Chars("bac"))          //> res4: List[patmat.Huffman.Bit] = List(1, 1, 1, 0, 1, 1, 0, 1)
}