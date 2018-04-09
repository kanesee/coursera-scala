package objsets

object test {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(74); 
  println("Welcome to the Scala worksheet");$skip(56); 
  
  def printall = (elem: Tweet) => println(elem.text);System.out.println("""printall: => objsets.Tweet => Unit""");$skip(78); 
  
  val ts1 = new NonEmpty(new Tweet("kane", "a", 10), new Empty, new Empty);System.out.println("""ts1  : objsets.NonEmpty = """ + $show(ts1 ));$skip(51); 
  
  val ts2 = ts1.incl(new Tweet("kane","b", 20));System.out.println("""ts2  : objsets.TweetSet = """ + $show(ts2 ));$skip(23); 
  ts2.foreach(println);$skip(51); 
  
  val ts3 = ts2.incl(new Tweet("kane","c", 30));System.out.println("""ts3  : objsets.TweetSet = """ + $show(ts3 ));$skip(23); 
  ts3.foreach(println);$skip(78); 
  val ts1_1 = new NonEmpty(new Tweet("kane", "a1", 10), new Empty, new Empty);System.out.println("""ts1_1  : objsets.NonEmpty = """ + $show(ts1_1 ));$skip(85); 
  
//  ts3.foreach(printall)
  //val ts4 = ts1 union ts1_1
  val ts4 = ts3 union ts2;System.out.println("""ts4  : objsets.TweetSet = """ + $show(ts4 ));$skip(27); 
  
  ts4.foreach(printall);$skip(43); val res$0 = 
  ts1.filter(tweet => tweet.retweets > 10);System.out.println("""res0: objsets.TweetSet = """ + $show(res$0));$skip(105); 
                                                  
  val ts2f = ts2.filter(tweet => tweet.retweets > 10);System.out.println("""ts2f  : objsets.TweetSet = """ + $show(ts2f ));$skip(25); 
  ts2f.foreach(printall);$skip(55); 
 
  val ts3f = ts4.filter(tweet => tweet.retweets > 1);System.out.println("""ts3f  : objsets.TweetSet = """ + $show(ts3f ));$skip(25); 
  ts3f.foreach(printall);$skip(33); 
  
  val mRT = ts3.mostRetweeted;System.out.println("""mRT  : objsets.Tweet = """ + $show(mRT ));$skip(97); 
  // val emptyRT = new Empty
  // emptyRT.mostRetweeted
  
  val tlist = ts3.descendingByRetweet;System.out.println("""tlist  : objsets.TweetList = """ + $show(tlist ));$skip(25); 
  tlist.foreach(println);$skip(42); 
  
  val gts = GoogleVsApple.googleTweets;System.out.println("""gts  : objsets.TweetSet = """ + $show(gts ));$skip(24); 
  gts.foreach(printall)}
  
}
