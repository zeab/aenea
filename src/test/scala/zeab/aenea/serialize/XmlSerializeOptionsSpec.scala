package zeab.aenea.serialize

//Imports
import zeab.aenea.XmlSerialize._
import zeab.aenea.modelsfortest.singleclasses.options._
//ScalaTest
import org.scalatest.FunSuite

//TODO Unit...and other various lesser used types for case classes like this...
class XmlSerializeOptionsSpec extends FunSuite {

  test("Option: None Serialize") {
    val obj: MyOptionStringClass = MyOptionStringClass(None)
    val serializedXml: Either[Throwable, String] = xmlSerialize(obj)
    val expectedXml: String = "<myOptionStringClass><myOptionString/></myOptionStringClass>"
    assert {
      serializedXml match {
        case Right(xml) => xml == expectedXml
        case Left(_) => false
      }
    }
  }

  test("Option: Some[String] Serialize") {
    val obj: MyOptionStringClass = MyOptionStringClass(Some("llama"))
    val serializedXml: Either[Throwable, String] = xmlSerialize(obj)
    val expectedXml: String = validXml("String", "llama")
    assert {
      serializedXml match {
        case Right(xml) => xml == expectedXml
        case Left(_) => false
      }
    }
  }

  test("Option: Some[Int] Serialize") {
    val obj: MyOptionIntClass = MyOptionIntClass(Some(8))
    val serializedXml: Either[Throwable, String] = xmlSerialize(obj)
    val expectedXml: String = validXml("Int", 8.toString)
    assert {
      serializedXml match {
        case Right(xml) => xml == expectedXml
        case Left(_) => false
      }
    }
  }

  test("Option: Some[Boolean] Serialize") {
    val obj: MyOptionBooleanClass = MyOptionBooleanClass(Some(false))
    val serializedXml: Either[Throwable, String] = xmlSerialize(obj)
    val expectedXml: String = validXml("Boolean", false.toString)
    assert {
      serializedXml match {
        case Right(xml) => xml == expectedXml
        case Left(_) => false
      }
    }
  }

  test("Option: Some[Byte] Serialize") {
    val obj: MyOptionByteClass = MyOptionByteClass(Some('c'.toByte))
    val serializedXml: Either[Throwable, String] = xmlSerialize(obj)
    val expectedXml: String = validXml("Byte", 'c'.toByte.toString)
    assert {
      serializedXml match {
        case Right(xml) => xml == expectedXml
        case Left(_) => false
      }
    }
  }

  test("Option: Some[Char] Serialize") {
    val obj: MyOptionCharClass = MyOptionCharClass(Some('c'))
    val serializedXml: Either[Throwable, String] = xmlSerialize(obj)
    val expectedXml: String = validXml("Char", 'c'.toString)
    assert {
      serializedXml match {
        case Right(xml) => xml == expectedXml
        case Left(_) => false
      }
    }
  }

  test("Option: Some[Double] Serialize") {
    val obj: MyOptionDoubleClass = MyOptionDoubleClass(Some(1.1))
    val serializedXml: Either[Throwable, String] = xmlSerialize(obj)
    val expectedXml: String = validXml("Double", 1.1.toString)
    assert {
      serializedXml match {
        case Right(xml) => xml == expectedXml
        case Left(_) => false
      }
    }
  }

  test("Option: Some[Float] Serialize") {
    val obj: MyOptionFloatClass = MyOptionFloatClass(Some(1.1F))
    val serializedXml: Either[Throwable, String] = xmlSerialize(obj)
    val expectedXml: String = validXml("Float", 1.1.toString)
    assert {
      serializedXml match {
        case Right(xml) => xml == expectedXml
        case Left(_) => false
      }
    }
  }

  test("Option: Some[Long] Serialize") {
    val obj: MyOptionLongClass = MyOptionLongClass(Some(1L))
    val serializedXml: Either[Throwable, String] = xmlSerialize(obj)
    val expectedXml: String = validXml("Long", 1.toString)
    assert {
      serializedXml match {
        case Right(xml) => xml == expectedXml
        case Left(_) => false
      }
    }
  }

  test("Option: Some[Short] Serialize") {
    val obj: MyOptionShortClass = MyOptionShortClass(Some(1))
    val serializedXml: Either[Throwable, String] = xmlSerialize(obj)
    val expectedXml: String = validXml("Short", 1.toString)
    assert {
      serializedXml match {
        case Right(xml) => xml == expectedXml
        case Left(_) => false
      }
    }
  }

  test("Option: List[Any] Serialize") {
    val obj: MyOptionListClass = MyOptionListClass(Some(List(9, "llama")))
    val serializedXml: Either[Throwable, String] = xmlSerialize(obj)
    val expectedXml: String = "<myOptionListClass><myOptionList>9</myOptionList><myOptionList>llama</myOptionList></myOptionListClass>"
    assert {
      serializedXml match {
        case Right(xml) => xml == expectedXml
        case Left(_) => false
      }
    }
  }

  def validXml(key: String, value: String): String =
    s"<myOption${key}Class><myOption$key>$value</myOption$key></myOption${key}Class>"

}