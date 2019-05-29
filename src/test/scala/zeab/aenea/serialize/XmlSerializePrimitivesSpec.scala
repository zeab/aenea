package zeab.aenea.serialize

//Imports
import zeab.aenea.XmlSerialize._
import zeab.aenea.modelsfortest.singleclasses.primitives._

import scala.xml.Elem
import scala.xml.XML.loadString
//ScalaTest
import org.scalatest.FunSuite

class XmlSerializePrimitivesSpec extends FunSuite {

  test("Double [String] Serialize") {
    val obj: MyDoubleClass = MyDoubleClass(1.1)
    val serializedXml: Either[Throwable, String] = xmlSerialize(obj)
    val expectedXml: String = validXml("Double", 1.1.toString)
    assert {
      serializedXml match {
        case Right(xml) => xml == expectedXml
        case Left(_) => false
      }
    }
  }

  test("Double [Elem] Serialize") {
    val obj: MyDoubleClass = MyDoubleClass(1.1)
    val serializedXml: Either[Throwable, Elem] = xmlSerialize[Elem](obj)
    val expectedXml: Elem = loadString(validXml("Double", 1.1.toString))
    assert {
      serializedXml match {
        case Right(xml) => xml == expectedXml
        case Left(_) => false
      }
    }
  }


  test("Float Serialize") {
    val obj: MyFloatClass = MyFloatClass(6.1F)
    val serializedXml: Either[Throwable, String] = xmlSerialize(obj)
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
    val serializedXml: Either[Throwable, String] = xmlSerialize(obj)
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
    val serializedXml: Either[Throwable, String] = xmlSerialize(obj)
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
    val serializedXml: Either[Throwable, String] = xmlSerialize(obj)
    val expectedXml: String = validXml("Short", 8.toString)
    assert {
      serializedXml match {
        case Right(xml) => xml == expectedXml
        case Left(_) => false
      }
    }
  }

  test("Byte Serialize") {
    val obj: MyByteClass = MyByteClass('c'.toByte)
    val serializedXml: Either[Throwable, String] = xmlSerialize(obj)
    val expectedXml: String = validXml("Byte", 'c'.toByte.toString)
    assert {
      serializedXml match {
        case Right(xml) => xml == expectedXml
        case Left(_) => false
      }
    }
  }

  test("Char Serialize") {
    val obj: MyCharClass = MyCharClass('c')
    val serializedXml: Either[Throwable, String] = xmlSerialize(obj)
    val expectedXml: String = validXml("Char", 'c'.toString)
    assert {
      serializedXml match {
        case Right(xml) => xml == expectedXml
        case Left(_) => false
      }
    }
  }

  test("Boolean Serialize") {
    val obj: MyBooleanClass = MyBooleanClass(false)
    val serializedXml: Either[Throwable, String] = xmlSerialize(obj)
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
    val serializedXml: Either[Throwable, String] = xmlSerialize(obj)
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
    val serializedXml: Either[Throwable, String] = xmlSerialize(obj)
    val expectedXml: String = validXml("Any", "llama")
    assert {
      serializedXml match {
        case Right(xml) => xml == expectedXml
        case Left(_) => false
      }
    }
  }

  test("Null Serialize") {
    val obj: MyNullClass = MyNullClass(null)
    val serializedXml: Either[Throwable, String] = xmlSerialize(obj)
    val expectedXml: String = "<myNullClass><myNull/></myNullClass>"
    assert {
      serializedXml match {
        case Right(xml) => xml == expectedXml
        case Left(_) => false
      }
    }
  }

  test("Unit Serialize") {
    val obj: MyUnitClass = MyUnitClass(Unit)
    val serializedXml: Either[Throwable, String] = xmlSerialize(obj)
    val expectedError: String = "scala.ScalaReflectionException: Scala field myUnit  of class MyUnitClass isn't represented as a Java field, nor does it have a\nJava accessor method. One common reason for this is that it may be a private class parameter\nnot used outside the primary constructor."
    assert {
      serializedXml match {
        case Right(_) => false
        case Left(ex) => ex.toString == expectedError
      }
    }
  }

  def validXml(key: String, value: String): String =
    s"<my${key}Class><my$key>$value</my$key></my${key}Class>"

}
