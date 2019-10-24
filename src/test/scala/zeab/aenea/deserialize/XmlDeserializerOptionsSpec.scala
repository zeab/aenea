package zeab.aenea.deserialize

//Imports
import zeab.aenea.MasterSuite
import zeab.aenea.XmlDeserializer._
import zeab.aenea.modelsfortest.complexclasses.{Backpack, Item}

class XmlDeserializerOptionsSpec extends MasterSuite {

  test("Backpack: Vector Enabled Deserialize") {
    val xml: String = "<backpack><owner>bert</owner><currentWeight>5</currentWeight><items><item><name>sword-of-death</name><type>sword</type></item><item><name>shieldhand</name><type>shield</type></item></items></backpack>"
    val obj: Either[Throwable, Backpack] = xml.fromXml[Backpack]()
    val expected: Backpack = Backpack("bert", 5, Vector(Item("sword-of-death", "sword"), Item("shieldhand", "shield")))
    compareObj(obj, expected)
  }

  test("Backpack: Vector Disabled Deserialize") {
    val xml: String = "<backpack><owner>bert</owner><currentWeight>5</currentWeight><items><name>sword-of-death</name><type>sword</type></items><items><name>shieldhand</name><type>shield</type></items></backpack>"
    val obj: Either[Throwable, Backpack] = xml.fromXml[Backpack]()
    val expected: Backpack = Backpack("bert", 5, Vector(Item("sword-of-death", "sword"), Item("shieldhand", "shield")))
    compareObj(obj, expected)
  }

}
