//package zeab.aenea.deserialize
//
////Imports
//import zeab.aenea.XmlDeserialize._
//import zeab.aenea.modelsfortest.singleclasses.primitives._
////ScalaTest
//import org.scalatest.FunSuite
//
//class XmlDeserializePrimitivesSpec extends FunSuite {
//
//  test("Invalid Double Deserialize") {
//    val xml: String = makeValidXml("Double", "llama")
//    xmlDeserialize[MyDoubleClass](xml) match {
//      case Right(_) => assert(false)
//      case Left(_) => assert(true)
//    }
//  }
//
//  test("Double Deserialize") {
//    val expectedType: String = "MyDoubleClass"
//    val xml: String = makeValidXml("Double", 1.1.toString)
//    xmlDeserialize[MyDoubleClass](xml) match {
//      case Right(obj) =>
//        assert(expectedType == obj.getClass.getSimpleName)
//      case Left(_) => assert(false)
//    }
//  }
//
//  test("Float Deserialize") {
//    val expectedType: String = "MyFloatClass"
//    val xml: String = makeValidXml("Float", 6.1.toString)
//    xmlDeserialize[MyFloatClass](xml) match {
//      case Right(obj) =>
//        assert(expectedType == obj.getClass.getSimpleName)
//      case Left(_) => assert(false)
//    }
//  }
//
//  test("Long Deserialize") {
//    val expectedType: String = "MyLongClass"
//    val xml: String = makeValidXml("Long", 6.toString)
//    xmlDeserialize[MyLongClass](xml) match {
//      case Right(obj) =>
//        assert(expectedType == obj.getClass.getSimpleName)
//      case Left(_) => assert(false)
//    }
//  }
//
//  test("Int Deserialize") {
//    val expectedType: String = "MyIntClass"
//    val xml: String = makeValidXml("Int", 6.toString)
//    xmlDeserialize[MyIntClass](xml) match {
//      case Right(obj) =>
//        assert(expectedType == obj.getClass.getSimpleName)
//      case Left(_) => assert(false)
//    }
//  }
//
//  test("Short Deserialize") {
//    val expectedType: String = "MyShortClass"
//    val xml: String = makeValidXml("Short", 6.toString)
//    xmlDeserialize[MyShortClass](xml) match {
//      case Right(obj) =>
//        assert(expectedType == obj.getClass.getSimpleName)
//      case Left(_) => assert(false)
//    }
//  }
//
//  test("Byte Deserialize") {
//    val expectedType: String = "MyByteClass"
//    val xml: String = makeValidXml("Byte", 6.toString)
//    xmlDeserialize[MyByteClass](xml) match {
//      case Right(obj) =>
//        assert(expectedType == obj.getClass.getSimpleName)
//      case Left(_) => assert(false)
//    }
//  }
//
//  //  test("Char Deserialize") {
//  //    val expectedType: String = "MyCharClass"
//  //    val xml: String = makeValidXml("Char", 'c'.toString)
//  //    xmlDeserialize[MyCharClass](xml) match {
//  //      case Right(obj) =>
//  //        assert(expectedType == obj.getClass.getSimpleName)
//  //      case Left(_) => assert(false)
//  //    }
//  //  }
//
//  test("Boolean Deserialize") {
//    val expectedType: String = "MyBooleanClass"
//    val xml: String = makeValidXml("Boolean", false.toString)
//    xmlDeserialize[MyBooleanClass](xml) match {
//      case Right(obj) =>
//        assert(expectedType == obj.getClass.getSimpleName)
//      case Left(_) => assert(false)
//    }
//  }
//
//  test("String Deserialize") {
//    val expectedType: String = "MyStringClass"
//    val xml: String = makeValidXml("String", "llama")
//    xmlDeserialize[MyStringClass](xml) match {
//      case Right(obj) =>
//        assert(expectedType == obj.getClass.getSimpleName)
//      case Left(_) => assert(false)
//    }
//  }
//
//  def makeValidXml(key: String, value: String): String =
//    s"<my${key}Class><my$key>$value</my$key></my${key}Class>"
//
//}
