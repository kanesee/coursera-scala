package recfun

object Main {
  def main(args: Array[String]) {
//    println("Pascal's Triangle")
//    for (row <- 0 to 10) {
//      for (col <- 0 to row)
//        print(pascal(col, row) + " ")
//      println()
//    }
    
//    println( balance("(if (zero? x) max (/ 1 x))".toList) )
//    println( balance("I told him (that it’s not (yet) done). (But he wasn’t listening)".toList) )
//    println( balance(":-)".toList) )
//    println( balance("())(".toList) )
//    println( balance("()())".toList) )
    
      println( countChange(6, List(2,3)) )
      println( countChange(4, List(1,2)) )
//      println( countChange(300,List(5,10,20,50,100,200,500)) )
//      println( countChange(301,List(5,10,20,50,100,200,500)) )
    }

  /**
   * Exercise 1
   */
    def pascal(c: Int, r: Int): Int = {
      if( c == 0 || r == c ) 1
      else pascal(c-1, r-1) + pascal(c, r-1)
    }
  
  /**
   * Exercise 2
   */
    def balance(chars: List[Char]): Boolean = {
      
      def isBalance(chars: List[Char], numLeftParen: Int): Boolean = {
        if( numLeftParen < 0 )
          false
        else
        if( chars.isEmpty )
          numLeftParen==0
        else {
          if( chars.head == '(' )
            isBalance(chars.tail, numLeftParen+1)
          else
          if( chars.head == ')' )
            isBalance(chars.tail, numLeftParen-1)
          else
            isBalance(chars.tail, numLeftParen)
          
        }
        
      }

      isBalance(chars, 0)
    }
  
  /**
   * Exercise 3
   */
    def countChange(money: Int, coins: List[Int]): Int = {

        def countCombos(money: Int, coins: List[Int], combos: Int) : Int = {
          if( money == 0 ) combos
          else if( coins.isEmpty ) 0
          else {
//            val coin = coins.head
//            if( coin <= money ) {
//              countCombos(money-coin, coins, combos+1)
//            } else {
//              countCombos(money, coins.tail, combos)
//            }
            var total = 0;
            for( (coin,i) <- coins.view.zipWithIndex ) {
              val remainingCoins = coins.patch(i,Nil,1)
              if( coin <= money ) {
                total += countCombos(money-coin, coins, combos+1)
//                total += countCombos(
              } else {
                total += countCombos(money, remainingCoins, combos)
              }
            }
            total
          }
        }
        
       countCombos(money, coins, 0)
    }
  }
