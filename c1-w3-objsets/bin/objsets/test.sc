package objsets

object test {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  def printall = (elem: Tweet) => println(elem.text)
                                                  //> printall: => objsets.Tweet => Unit
  
  val ts1 = new NonEmpty(new Tweet("kane", "a", 10), new Empty, new Empty)
                                                  //> ts1  : objsets.NonEmpty = objsets.NonEmpty@17c68925
  
  val ts2 = ts1.incl(new Tweet("kane","b", 20))   //> ts2  : objsets.TweetSet = objsets.NonEmpty@71be98f5
  ts2.foreach(println)                            //> User: kane
                                                  //| Text: a [10]
                                                  //| User: kane
                                                  //| Text: b [20]
  
  val ts3 = ts2.incl(new Tweet("kane","c", 30))   //> ts3  : objsets.TweetSet = objsets.NonEmpty@6fadae5d
  ts3.foreach(println)                            //> User: kane
                                                  //| Text: a [10]
                                                  //| User: kane
                                                  //| Text: b [20]
                                                  //| User: kane
                                                  //| Text: c [30]
  val ts1_1 = new NonEmpty(new Tweet("kane", "a1", 10), new Empty, new Empty)
                                                  //> ts1_1  : objsets.NonEmpty = objsets.NonEmpty@17f6480
  
//  ts3.foreach(printall)
  //val ts4 = ts1 union ts1_1
  val ts4 = ts3 union ts2                         //> ts4  : objsets.TweetSet = objsets.NonEmpty@2d6e8792
  
  ts4.foreach(printall)                           //> a
                                                  //| b
                                                  //| c
  ts1.filter(tweet => tweet.retweets > 10)        //> res0: objsets.TweetSet = objsets.Empty@2812cbfa
                                                  
  val ts2f = ts2.filter(tweet => tweet.retweets > 10)
                                                  //> ts2f  : objsets.TweetSet = objsets.NonEmpty@2acf57e3
  ts2f.foreach(printall)                          //> b
 
  val ts3f = ts4.filter(tweet => tweet.retweets > 1)
                                                  //> ts3f  : objsets.TweetSet = objsets.NonEmpty@506e6d5e
  ts3f.foreach(printall)                          //> a
                                                  //| b
                                                  //| c
  
  val mRT = ts3.mostRetweeted                     //> mRT  : objsets.Tweet = User: kane
                                                  //| Text: c [30]
  // val emptyRT = new Empty
  // emptyRT.mostRetweeted
  
  val tlist = ts3.descendingByRetweet             //> tlist  : objsets.TweetList = objsets.Cons@96532d6
  tlist.foreach(println)                          //> User: kane
                                                  //| Text: c [30]
                                                  //| User: kane
                                                  //| Text: b [20]
                                                  //| User: kane
                                                  //| Text: a [10]
  
  val gts = GoogleVsApple.googleTweets            //> gts  : objsets.TweetSet = objsets.NonEmpty@24a35978
  gts.foreach(printall)                           //> BlueStacks and AMD Bring 500,000 Android Apps to Windows 8: http://t.co/Gsk
                                                  //| uXhRo by @alexandra_chang
                                                  //| First iPhone 5 Benchmarks: Screaming Fast, Yes, But Just Shy of Galaxy S II
                                                  //| I  http://t.co/QIAhda3L by @redgirlsays
                                                  //| Camera contest:  Apple iPhone 5 vs. Samsung Galaxy S3 vs. HTC One X http://
                                                  //| t.co/PmbhNgrd
                                                  //| A mathematician accurately predicted when Android's app store would hit 25 
                                                  //| billion downloads http://t.co/VFLBJ0z3
                                                  //| How to switch from iPhone to Android http://t.co/M8I9lwua
                                                  //| How to lock down and find Android and Windows phones http://t.co/mRw8P80z
                                                  //| How to make your Android phone look like an iPhone 5 http://t.co/tZZYb8Ti
                                                  //| How to set up an Android tablet as a second display for your PC or Mac http
                                                  //| ://t.co/YynJll9N
                                                  //| If you watch television regularly, second-screen app Zeebox for Android &am
                                                  //| p; iOS makes an excellent companion http://t.co/buVYA8E7
                                                  //| RT @CNETNews: Judge Lucy Koh now has the authority to lift the sa
                                                  //| Output exceeds cutoff limit.
  
}