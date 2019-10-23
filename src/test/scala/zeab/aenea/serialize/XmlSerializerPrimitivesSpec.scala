package zeab.aenea.serialize

//Imports
import zeab.aenea.MasterSpec
import zeab.aenea.XmlSerializer._

class XmlSerializerPrimitivesSpec extends MasterSpec {

  test("Unit Error Serialize") {
    val serializedXml: Either[Throwable, String] = ().asXml()
    val expectedError: String = "Must be a case class at root level cannot serialize : BoxedUnit"
    compareErrorResults(expectedError, serializedXml)
  }

  test("BigInt Error Serialize") {
    val serializedXml: Either[Throwable, String] = BigInt(8).asXml()
    val expectedError: String = "Must be a case class at root level cannot serialize : BigInt"
    compareErrorResults(expectedError, serializedXml)
  }

  test("BigDecimal Error Serialize") {
    val serializedXml: Either[Throwable, String] = BigDecimal(8).asXml()
    val expectedError: String = "Must be a case class at root level cannot serialize : BigDecimal"
    compareErrorResults(expectedError, serializedXml)
  }

  test("Left Error Serialize") {
    val serializedXml: Either[Throwable, String] = Left("moose").asXml()
    val expectedError: String = "Must be a case class at root level cannot serialize : Left"
    compareErrorResults(expectedError, serializedXml)
  }

  test("Right Error Serialize") {
    val serializedXml: Either[Throwable, String] = Right("moose").asXml()
    val expectedError: String = "Must be a case class at root level cannot serialize : Right"
    compareErrorResults(expectedError, serializedXml)
  }

  test("None Error Serialize") {
    val serializedXml: Either[Throwable, String] = None.asXml()
    val expectedError: String = "Must be a case class at root level cannot serialize : None$"
    compareErrorResults(expectedError, serializedXml)
  }

  test("Some Error Serialize") {
    val serializedXml: Either[Throwable, String] = Some("moose").asXml()
    val expectedError: String = "Must be a case class at root level cannot serialize : Some"
    compareErrorResults(expectedError, serializedXml)
  }

  test("Double Error Serialize") {
    val serializedXml: Either[Throwable, String] = 1.0.asXml()
    val expectedError: String = "Must be a case class at root level cannot serialize : Double"
    compareErrorResults(expectedError, serializedXml)
  }

  test("Int Error Serialize") {
    val serializedXml: Either[Throwable, String] = 1.asXml()
    val expectedError: String = "Must be a case class at root level cannot serialize : Integer"
    compareErrorResults(expectedError, serializedXml)
  }

  test("Boolean Error Serialize") {
    val serializedXml: Either[Throwable, String] = false.asXml()
    val expectedError: String = "Must be a case class at root level cannot serialize : Boolean"
    compareErrorResults(expectedError, serializedXml)
  }

  test("Short Error Serialize") {
    val short: Short = 1
    val serializedXml: Either[Throwable, String] = short.asXml()
    val expectedError: String = "Must be a case class at root level cannot serialize : Short"
    compareErrorResults(expectedError, serializedXml)
  }

  test("Long Error Serialize") {
    val serializedXml: Either[Throwable, String] = 1L.asXml()
    val expectedError: String = "Must be a case class at root level cannot serialize : Long"
    compareErrorResults(expectedError, serializedXml)
  }

  test("Float Error Serialize") {
    val serializedXml: Either[Throwable, String] = 1F.asXml()
    val expectedError: String = "Must be a case class at root level cannot serialize : Float"
    compareErrorResults(expectedError, serializedXml)
  }

  test("List Error Serialize") {
    val serializedXml: Either[Throwable, String] = List("1", "2", "3").asXml()
    val expectedError: String = "Must be a case class at root level cannot serialize : $colon$colon"
    compareErrorResults(expectedError, serializedXml)
  }

  test("Vector Error Serialize") {
    val serializedXml: Either[Throwable, String] = Vector("1", "2", "3").asXml()
    val expectedError: String = "Must be a case class at root level cannot serialize : Vector"
    compareErrorResults(expectedError, serializedXml)
  }

  test("Map Error Serialize") {
    val serializedXml: Either[Throwable, String] = Map("1" -> "1", "2" -> "2", "3" -> "3").asXml()
    val expectedError: String = "Must be a case class at root level cannot serialize : Map3"
    compareErrorResults(expectedError, serializedXml)
  }

  test("Seq Error Serialize") {
    val serializedXml: Either[Throwable, String] = Seq("1", "2", "3").asXml()
    val expectedError: String = "Must be a case class at root level cannot serialize : $colon$colon"
    compareErrorResults(expectedError, serializedXml)
  }

  test("Set Error Serialize") {
    val serializedXml: Either[Throwable, String] = Set("1", "2", "3").asXml()
    val expectedError: String = "Must be a case class at root level cannot serialize : Set3"
    compareErrorResults(expectedError, serializedXml)
  }

//  test("Null Error Serialize") {
//    val serializedXml: Either[Throwable, String] = XmlSerializeNull(null).asXml()
//    val expectedError: String = "Base object cannot be null"
//    compareErrorResults(expectedError, serializedXml)
//  }

}
