import scala.io.Source

class ScalaExam {
  object Beer {
    def main(arg : Array[String]): Unit = {
      var n : Int = 2;
      while( n <= 6) {
        println(s"Hello ${n} bottles of beer")
        n += 1
      }
      // 2 ~ 6 foreach (n)
      2 to 6 foreach { n => println(s"Hello ${n} bottles of beer")}

      // Map<String, Int>
      val authorsToAge = Map("Raoul" -> 23, "Mario" -> 40, "Alan" -> 53);

      // List<String>
      val authors = List("Raoul", "InWoo", "Alan")

      // Set<Int>
      val numbers = Set(1, 2, 3, 4)
      // numbers 요소와 9 요소를 포함한 새로운 Set 생성
      val newNumbers = numbers + 9

      // filter : 길이가 10 이상인 행만 가져온 뒤
      // Map : 대문자로 변경한다.
      val fileLines = Source.fromFile("data.txt").getLines().toList
      val linesLongUpper = fileLines.filter(l => l.length() > 10)
                          .map(l => l.toUpperCase())

      // 병렬 비슷하게 parallel In Scala
//      val linesLongUpper2 = fileLines.par filter (_.length() > 10) map(_.toUpperCase())

      // option == java.optional
      /*
      def getCarInsuranceName(person: Option[person], minAge: Int) =
          person.filter(_.getAge() >= minAge)
          .flatMap(_.getCar)
          .flatMap(_.getInsurance)
          .map(_.getName)
          .getOrElse("Unknown")
       */
    }
  }
}
