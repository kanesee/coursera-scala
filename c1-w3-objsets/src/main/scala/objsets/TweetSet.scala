package objsets

import TweetReader._

/**
 * A class to represent tweets.
 */
class Tweet(val user: String, val text: String, val retweets: Int) {
  override def toString: String =
    "User: " + user + "\n" +
    "Text: " + text + " [" + retweets + "]"
}

/**
 * This represents a set of objects of type `Tweet` in the form of a binary search
 * tree. Every branch in the tree has two children (two `TweetSet`s). There is an
 * invariant which always holds: for every branch `b`, all elements in the left
 * subtree are smaller than the tweet at `b`. The elements in the right subtree are
 * larger.
 *
 * Note that the above structure requires us to be able to compare two tweets (we
 * need to be able to say which of two tweets is larger, or if they are equal). In
 * this implementation, the equality / order of tweets is based on the tweet's text
 * (see `def incl`). Hence, a `TweetSet` could not contain two tweets with the same
 * text from different users.
 *
 *
 * The advantage of representing sets as binary search trees is that the elements
 * of the set can be found quickly. If you want to learn more you can take a look
 * at the Wikipedia page [1], but this is not necessary in order to solve this
 * assignment.
 *
 * [1] http://en.wikipedia.org/wiki/Binary_search_tree
 */
abstract class TweetSet {

  /**
   * This method takes a predicate and returns a subset of all the elements
   * in the original set for which the predicate is true.
   *
   * Question: Can we implment this method here, or should it remain abstract
   * and be implemented in the subclasses?
   */
    def filter(p: Tweet => Boolean): TweetSet = filterAcc(p, new Empty)
  
  /**
   * This is a helper method for `filter` that propagetes the accumulated tweets.
   */
  def filterAcc(p: Tweet => Boolean, acc: TweetSet): TweetSet

  /**
   * Returns a new `TweetSet` that is the union of `TweetSet`s `this` and `that`.
   *
   * Question: Should we implment this method here, or should it remain abstract
   * and be implemented in the subclasses?
   */
    def union(that: TweetSet): TweetSet
  
  /**
   * Returns the tweet from this set which has the greatest retweet count.
   *
   * Calling `mostRetweeted` on an empty set should throw an exception of
   * type `java.util.NoSuchElementException`.
   *
   * Question: Should we implment this method here, or should it remain abstract
   * and be implemented in the subclasses?
   */
    def mostRetweeted: Tweet
  
  /**
   * Returns a list containing all tweets of this set, sorted by retweet count
   * in descending order. In other words, the head of the resulting list should
   * have the highest retweet count.
   *
   * Hint: the method `remove` on TweetSet will be very useful.
   * Question: Should we implment this method here, or should it remain abstract
   * and be implemented in the subclasses?
   */
    def descendingByRetweet: TweetList = {
      var list : TweetList = Nil
      var newSet = this
      var hasElem = true
      while( hasElem ) {
        try {
          val mostReTweet = newSet.mostRetweeted
          
          if( list.isEmpty ) list = new Cons(mostReTweet, Nil)
          else list = list.add( mostReTweet )
          
          
          newSet = newSet.remove(mostReTweet)
        } catch {
          case nse: NoSuchElementException => hasElem = false
        }
      }      
      list
    }
    
  
  /**
   * The following methods are already implemented
   */

  /**
   * Returns a new `TweetSet` which contains all elements of this set, and the
   * the new element `tweet` in case it does not already exist in this set.
   *
   * If `this.contains(tweet)`, the current set is returned.
   */
  def incl(tweet: Tweet): TweetSet

  /**
   * Returns a new `TweetSet` which excludes `tweet`.
   */
  def remove(tweet: Tweet): TweetSet

  /**
   * Tests if `tweet` exists in this `TweetSet`.
   */
  def contains(tweet: Tweet): Boolean

  /**
   * This method takes a function and applies it to every element in the set.
   */
  def foreach(f: Tweet => Unit): Unit
  
  def elem : Tweet
  def left : TweetSet
  def right : TweetSet
}

class Empty extends TweetSet {
    def filterAcc(p: Tweet => Boolean, acc: TweetSet): TweetSet = acc
  
    def mostRetweeted: Tweet = throw new NoSuchElementException("Empty set")
    
//    def descendingByRetweetAcc(list: TweetList) : TweetList = list
    
  /**
   * The following methods are already implemented
   */

  def contains(tweet: Tweet): Boolean = false

  def incl(tweet: Tweet): TweetSet = new NonEmpty(tweet, new Empty, new Empty)

  def remove(tweet: Tweet): TweetSet = this

  def foreach(f: Tweet => Unit): Unit = ()
  
  def union(that: TweetSet): TweetSet = that
  
  def elem = null
  def left = new Empty
  def right = new Empty
}

class NonEmpty(val elem: Tweet, val left: TweetSet, val right: TweetSet) extends TweetSet {

    def filterAcc(p: Tweet => Boolean, acc: TweetSet): TweetSet =
      if( p(elem) ) {
//        println(elem.retweets);
        new NonEmpty(elem, left.filterAcc(p, acc), right.filterAcc(p, acc))
//        new NonEmpty(elem, new Empty, new Empty)
//        new Empty
      }
      else {
        val newL = left.filterAcc(p, acc)
        val newR = right.filterAcc(p, acc)
        newL union newR
//        (left.union(right)).filterAcc(p, acc)        
      }
  
   def mostRetweeted: Tweet = {
     var bestRT = elem
     try {
       val leftRT = left.mostRetweeted
       if( leftRT.retweets > bestRT.retweets ) bestRT = leftRT
     } catch {
       case nse: NoSuchElementException => // do nothing 
     }
     
     try {
       val rightRT = right.mostRetweeted
       if( rightRT.retweets > bestRT.retweets ) bestRT = rightRT
     } catch {
       case nse: NoSuchElementException => // do nothing 
     }
     
     bestRT
   }
     
  /**
   * The following methods are already implemented
   */

  def contains(x: Tweet): Boolean =
    if (x.text < elem.text) left.contains(x)
    else if (elem.text < x.text) right.contains(x)
    else true

  def incl(x: Tweet): TweetSet = {
    if (x.text < elem.text) new NonEmpty(elem, left.incl(x), right)
    else if (elem.text < x.text) new NonEmpty(elem, left, right.incl(x))
    else this
  }

  def remove(tw: Tweet): TweetSet =
    if (tw.text < elem.text) new NonEmpty(elem, left.remove(tw), right)
    else if (elem.text < tw.text) new NonEmpty(elem, left, right.remove(tw))
    else left.union(right)

  def foreach(f: Tweet => Unit): Unit = {
    f(elem)
    left.foreach(f)
    right.foreach(f)
  }
    
  def union(that: TweetSet): TweetSet = {
    if( that.elem == null ) this
    else if( that.elem.text < elem.text ) new NonEmpty(elem, left.union(that), right) 
    else if( that.elem.text > elem.text ) new NonEmpty(elem, left, right.union(that))
    else new NonEmpty(elem, left.union(that.left), right.union(that.right))
//      ((left union right) union that) incl elem
  }
}

trait TweetList {
  def head: Tweet
  def tail: TweetList
  def isEmpty: Boolean
  def foreach(f: Tweet => Unit): Unit =
    if (!isEmpty) {
      f(head)
      tail.foreach(f)
    }
  
  def add(t: Tweet) : TweetList
}

object Nil extends TweetList {
  def head = throw new java.util.NoSuchElementException("head of EmptyList")
  def tail = throw new java.util.NoSuchElementException("tail of EmptyList")
  def isEmpty = true
  def add(t: Tweet) : TweetList = new Cons(t, Nil)
}

class Cons(val head: Tweet, val tail: TweetList) extends TweetList {
  def isEmpty = false
  def add(t: Tweet) : TweetList = new Cons(head, tail.add(t))
  
}


object GoogleVsApple {
  val google = List("android", "Android", "galaxy", "Galaxy", "nexus", "Nexus")
  val apple = List("ios", "iOS", "iphone", "iPhone", "ipad", "iPad")

  lazy val googleTweets: TweetSet = TweetReader.allTweets.filter(tweet => contains(tweet, google))
  lazy val appleTweets: TweetSet = TweetReader.allTweets.filter(tweet => contains(tweet, apple))
  
  def contains(tweet: Tweet, substrings: List[String]) = {
    var contains = false
    for(substring <- substrings) {
      if( tweet.text contains substring ) contains = true
    }
    contains
  }
  
  /**
   * A list of all tweets mentioning a keyword from either apple or google,
   * sorted by the number of retweets.
   */
     lazy val trending: TweetList = googleTweets.union(appleTweets).descendingByRetweet
     
  }

object Main extends App {
  // Print the trending tweets
  GoogleVsApple.trending foreach println
}
