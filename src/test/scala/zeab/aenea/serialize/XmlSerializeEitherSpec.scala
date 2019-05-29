package zeab.aenea.serialize

//Imports
import zeab.aenea.XmlSerialize._
import zeab.aenea.modelsfortest.singleclasses.eithers.MyEitherBooleanClass
//ScalaTest
import org.scalatest.FunSuite

class XmlSerializeEitherSpec extends FunSuite {

  test("Either: Right[Boolean] Serialize") {
    val obj: MyEitherBooleanClass = MyEitherBooleanClass(Right(true))
    val serializedXml: Either[Throwable, String] = xmlSerialize(obj)
    val expectedError: String = "java.lang.Exception: Unsupported Type for Serialization: Either"
    assert {
      serializedXml match {
        case Right(_) => false
        case Left(ex) => ex.toString == expectedError
      }
    }
  }

  test("Either: Left[Throwable] Serialize") {
    val obj: MyEitherBooleanClass = MyEitherBooleanClass(Left(new Exception("error message")))
    val serializedXml: Either[Throwable, String] = xmlSerialize(obj)
    val expectedError: String = "java.lang.Exception: Unsupported Type for Serialization: Either"
    assert {
      serializedXml match {
        case Right(_) => false
        case Left(ex) => ex.toString == expectedError
      }
    }
  }

}
