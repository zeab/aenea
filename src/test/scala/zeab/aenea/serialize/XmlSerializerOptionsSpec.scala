package zeab.aenea.serialize

//Imports
import zeab.aenea.MasterSpec
import zeab.aenea.XmlSerializer._
import zeab.aenea.modelsfortest.singleclasses.primitives.MyJavaDoubleClass

class XmlSerializerOptionsSpec extends MasterSpec {

  test("Null as blank Serialize") {
    val obj: MyJavaDoubleClass = MyJavaDoubleClass(null)
    val serializedXml: Either[Throwable, String] = obj.asXml(Map("isnullaccepted" -> "true"))
    val expectedXml: String = s"<myJavaDoubleClass/>"
    compareResults(expectedXml, serializedXml)
  }

}
