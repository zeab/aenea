package zeab.aenea.serialize

//Imports
import zeab.aenea.XmlSerializer._
import zeab.aenea.modelsfortest.singleclasses.primitives._
//ScalaTest
import org.scalatest.{FunSuite, Assertion}

class XmlSerializerPrimitiveClassesSpec extends FunSuite {

  test("Double Serialize") {
    val obj: MyDoubleClass = MyDoubleClass(1.1)
    val serializedXml: Either[Throwable, String] = obj.asXml
    val expectedXml: String = validXml("Double", 1.1.toString)
    assert {
      serializedXml match {
        case Right(xml) => xml == expectedXml
        case Left(_) => false
      }
    }
  }

  test("Float Serialize") {
    val obj: MyFloatClass = MyFloatClass(6.1F)
    val serializedXml: Either[Throwable, String] = obj.asXml
    val expectedXml: String = validXml("Float", 6.1.toString)
    assert {
      serializedXml match {
        case Right(xml) => xml == expectedXml
        case Left(_) => false
      }
    }
  }

  test("Long Serialize") {
    val obj: MyLongClass = MyLongClass(6L)
    val serializedXml: Either[Throwable, String] = obj.asXml
    val expectedXml: String = validXml("Long", 6.toString)
    assert {
      serializedXml match {
        case Right(xml) => xml == expectedXml
        case Left(_) => false
      }
    }
  }

  test("Int Serialize") {
    val obj: MyIntClass = MyIntClass(8)
    val serializedXml: Either[Throwable, String] = obj.asXml
    val expectedXml: String = validXml("Int", 8.toString)
    assert {
      serializedXml match {
        case Right(xml) => xml == expectedXml
        case Left(_) => false
      }
    }
  }

  test("Short Serialize") {
    val obj: MyShortClass = MyShortClass(8)
    val serializedXml: Either[Throwable, String] = obj.asXml
    val expectedXml: String = validXml("Short", 8.toString)
    assert {
      serializedXml match {
        case Right(xml) => xml == expectedXml
        case Left(_) => false
      }
    }
  }

  test("Boolean Serialize") {
    val obj: MyBooleanClass = MyBooleanClass(false)
    val serializedXml: Either[Throwable, String] = obj.asXml
    val expectedXml: String = validXml("Boolean", false.toString)
    assert {
      serializedXml match {
        case Right(xml) => xml == expectedXml
        case Left(_) => false
      }
    }
  }

  test("String Serialize") {
    val obj: MyStringClass = MyStringClass("llama")
    val serializedXml: Either[Throwable, String] = obj.asXml
    val expectedXml: String = validXml("String", "llama")
    assert {
      serializedXml match {
        case Right(xml) => xml == expectedXml
        case Left(_) => false
      }
    }
  }

  test("Any Serialize") {
    val obj: MyAnyClass = MyAnyClass("llama")
    val serializedXml: Either[Throwable, String] = obj.asXml
    val expectedXml: String = validXml("Any", "llama")
    assert {
      serializedXml match {
        case Right(xml) => xml == expectedXml
        case Left(_) => false
      }
    }
  }

  def validXml(key: String, value: String): String =
    s"<my${key}Class><my$key>$value</my$key></my${key}Class>"

  def validateXml(serializedXml: Either[Throwable, String], expectedError:String): Assertion ={
    assert {
      serializedXml match {
        case Right(_) => false
        case Left(ex) => ex.toString == expectedError
      }
    }
  }

}
