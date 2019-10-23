package zeab.aenea.serialize

//Imports
import zeab.aenea.MasterSuite
import zeab.aenea.XmlSerializer._
import zeab.aenea.modelsfortest.complexclasses.{Backpack, Item}
import zeab.aenea.modelsfortest.singleclasses.primitives.{MyJavaDoubleClass, MyStringClass}

class XmlSerializerOptionsSpec extends MasterSuite {

  test("Double: Null Serialize") {
    val obj: MyJavaDoubleClass = MyJavaDoubleClass(null)
    val serializedXml: Either[Throwable, String] = obj.asXml(Map("isnullaccepted" -> "true"))
    val expectedXml: String = s"<myJavaDoubleClass/>"
    compareResults(expectedXml, serializedXml)
  }

  test("String: Null Serialize") {
    val obj: MyStringClass = MyStringClass(null)
    val serializedXml: Either[Throwable, String] = obj.asXml(Map("isnullaccepted" -> "true"))
    val expectedXml: String = s"<myStringClass/>"
    compareResults(expectedXml, serializedXml)
  }

  test("Item: Null Serialize") {
    val obj: Item = Item("sword", null)
    val serializedXml: Either[Throwable, String] = obj.asXml(Map("isnullaccepted" -> "true"))
    val expectedXml: String = s"<item><name>sword</name></item>"
    compareResults(expectedXml, serializedXml)
  }

  test("Backpack: Vector Wrap Enabled Serialize") {
    val obj: Backpack = Backpack(Vector(Item("sword of awesome", "sword"), Item("shield hand", "shield")))
    val serializedXml: Either[Throwable, String] = obj.asXml(Map("isvectorwrapped" -> "true"))
    val expectedXml: String = "<backpack><items><item><name>sword of awesome</name><type>sword</type></item><item><name>shield hand</name><type>shield</type></item></items></backpack>"
    compareResults(expectedXml, serializedXml)
  }

  test("Backpack: Vector Wrap Disabled Serialize") {
    val obj: Backpack = Backpack(Vector(Item("sword of awesome", "sword"), Item("shield hand", "shield")))
    val serializedXml: Either[Throwable, String] = obj.asXml()
    val expectedXml: String = "<backpack><items><name>sword of awesome</name><type>sword</type></items><items><name>shield hand</name><type>shield</type></items></backpack>"
    compareResults(expectedXml, serializedXml)
  }

}
