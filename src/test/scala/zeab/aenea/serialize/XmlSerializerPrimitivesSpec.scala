package zeab.aenea.serialize

//Imports
import zeab.aenea.XmlSerializer._
import org.scalatest.FunSuite

class XmlSerializerPrimitivesSpec extends FunSuite {

  test("Double Error Serialize") {
    val serializedXml: Either[Throwable, String] = 1.0.asXml
    val expectedError: String = "cannot serialize on a primitive"
    serializedXml match {
      case Right(_) => fail()
      case Left(ex) => assert(ex.getMessage == expectedError)
    }
  }

  test("Int Error Serialize") {
    val serializedXml: Either[Throwable, String] = 1.asXml
    val expectedError: String = "cannot serialize on a primitive"
    serializedXml match {
      case Right(_) => fail()
      case Left(ex) => assert(ex.getMessage == expectedError)
    }
  }

  test("Boolean Error Serialize") {
    val serializedXml: Either[Throwable, String] = false.asXml
    val expectedError: String = "cannot serialize on a primitive"
    serializedXml match {
      case Right(_) => fail()
      case Left(ex) => assert(ex.getMessage == expectedError)
    }
  }

  test("Short Error Serialize") {
    val short: Short = 1
    val serializedXml: Either[Throwable, String] = short.asXml
    val expectedError: String = "cannot serialize on a primitive"
    serializedXml match {
      case Right(_) => fail()
      case Left(ex) => assert(ex.getMessage == expectedError)
    }
  }

  test("Long Error Serialize") {
    val serializedXml: Either[Throwable, String] = 1L.asXml
    val expectedError: String = "cannot serialize on a primitive"
    serializedXml match {
      case Right(_) => fail()
      case Left(ex) => assert(ex.getMessage == expectedError)
    }
  }

  test("Float Error Serialize") {
    val serializedXml: Either[Throwable, String] = 1F.asXml
    val expectedError: String = "cannot serialize on a primitive"
    serializedXml match {
      case Right(_) => fail()
      case Left(ex) => assert(ex.getMessage == expectedError)
    }
  }

  test("List Error Serialize") {
    val serializedXml: Either[Throwable, String] = List("1", "2", "3").asXml
    val expectedError: String = "not implemented"
    serializedXml match {
      case Right(_) => fail()
      case Left(ex) => assert(ex.getMessage == expectedError)
    }
  }

  test("Vector Error Serialize") {
    val serializedXml: Either[Throwable, String] = Vector("1", "2", "3").asXml
    val expectedError: String = "not implemented"
    serializedXml match {
      case Right(_) => fail()
      case Left(ex) => assert(ex.getMessage == expectedError)
    }
  }

  test("Map Error Serialize") {
    val serializedXml: Either[Throwable, String] = Map("1" -> "1", "2" -> "2", "3" -> "3").asXml
    val expectedError: String = "not implemented"
    serializedXml match {
      case Right(_) => fail()
      case Left(ex) => assert(ex.getMessage == expectedError)
    }
  }

}
