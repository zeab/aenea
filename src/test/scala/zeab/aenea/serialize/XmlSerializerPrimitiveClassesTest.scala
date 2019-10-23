package zeab.aenea.serialize

//Imports
import zeab.aenea.MasterTest
import zeab.aenea.XmlSerializer._
import zeab.aenea.modelsfortest.singleclasses.primitives._

class XmlSerializerPrimitiveClassesTest extends MasterTest {

  test("Double: Serialize") {
    val obj: MyDoubleClass = MyDoubleClass(1.1)
    val serializedXml: Either[Throwable, String] = obj.asXml()
    val expectedXml: String = validXml("Double", 1.1.toString)
    compareResults(expectedXml, serializedXml)
  }

  test("Float: Serialize") {
    val obj: MyFloatClass = MyFloatClass(6.1F)
    val serializedXml: Either[Throwable, String] = obj.asXml()
    val expectedXml: String = validXml("Float", 6.1.toString)
    compareResults(expectedXml, serializedXml)
  }

  test("Long: Serialize") {
    val obj: MyLongClass = MyLongClass(6L)
    val serializedXml: Either[Throwable, String] = obj.asXml()
    val expectedXml: String = validXml("Long", 6.toString)
    compareResults(expectedXml, serializedXml)
  }

  test("Int: Serialize") {
    val obj: MyIntClass = MyIntClass(8)
    val serializedXml: Either[Throwable, String] = obj.asXml()
    val expectedXml: String = validXml("Int", 8.toString)
    compareResults(expectedXml, serializedXml)
  }

  test("Short: Serialize") {
    val obj: MyShortClass = MyShortClass(8)
    val serializedXml: Either[Throwable, String] = obj.asXml()
    val expectedXml: String = validXml("Short", 8.toString)
    compareResults(expectedXml, serializedXml)
  }

  test("Boolean: Serialize") {
    val obj: MyBooleanClass = MyBooleanClass(false)
    val serializedXml: Either[Throwable, String] = obj.asXml()
    val expectedXml: String = validXml("Boolean", false.toString)
    compareResults(expectedXml, serializedXml)
  }

  test("String: Serialize") {
    val obj: MyStringClass = MyStringClass("llama")
    val serializedXml: Either[Throwable, String] = obj.asXml()
    val expectedXml: String = validXml("String", "llama")
    compareResults(expectedXml, serializedXml)
  }

  //TODO Decide if that is actually the behavior we want... since by it self it doesn't matter but circe i know does not do any
  test("Any: Serialize") {
    val obj: MyAnyClass = MyAnyClass("llama")
    val serializedXml: Either[Throwable, String] = obj.asXml()
    val expectedXml: String = validXml("Any", "llama")
    compareResults(expectedXml, serializedXml)
  }

}
