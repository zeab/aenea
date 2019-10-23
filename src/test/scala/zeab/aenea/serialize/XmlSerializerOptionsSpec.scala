package zeab.aenea.serialize

//Imports
import zeab.aenea.MasterSpec
import zeab.aenea.XmlSerializer._
import zeab.aenea.modelsfortest.complexclasses.Item
import zeab.aenea.modelsfortest.singleclasses.primitives.{MyJavaDoubleClass, MyStringClass}

class XmlSerializerOptionsSpec extends MasterSpec {

  test("Double Null as blank Serialize") {
    val obj: MyJavaDoubleClass = MyJavaDoubleClass(null)
    val serializedXml: Either[Throwable, String] = obj.asXml(Map("isnullaccepted" -> "true"))
    val expectedXml: String = s"<myJavaDoubleClass/>"
    compareResults(expectedXml, serializedXml)
  }

  test("String Null as blank Serialize") {
    val obj: MyStringClass = MyStringClass(null)
    val serializedXml: Either[Throwable, String] = obj.asXml(Map("isnullaccepted" -> "true"))
    val expectedXml: String = s"<myStringClass/>"
    compareResults(expectedXml, serializedXml)
  }

  test("Item Null as blank Serialize") {
    val obj: Item = Item("sword", null)
    val serializedXml: Either[Throwable, String] = obj.asXml(Map("isnullaccepted" -> "true"))
    val expectedXml: String = s"<item><name>sword</name></item>"
    compareResults(expectedXml, serializedXml)
  }

}
