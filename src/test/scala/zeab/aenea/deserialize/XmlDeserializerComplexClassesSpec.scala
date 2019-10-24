package zeab.aenea.deserialize

//Imports
import zeab.aenea.MasterSuite
import zeab.aenea.XmlDeserializer._
import zeab.aenea.modelsfortest.complexclasses.{Horse, Item, Person}

class XmlDeserializerComplexClassesSpec extends MasterSuite {

  test("Person: Variation(1) Deserialize") {
    val xml: String = "<person><name>bob</name><class>warlock</class><level>9</level><health>87.3</health><soulStone/><mount><name>daisy</name><speed>1.4</speed><backpack/></mount><backpack><name>sword</name><type>attack</type></backpack><backpack><name>shield</name><type>defense</type></backpack><previousDestinations>Hogwarts</previousDestinations><previousDestinations>Yavin 4</previousDestinations></person>"
    val obj: Either[Throwable, Person] = xml.fromXml[Person]()
    val mount: Horse = Horse("daisy", 1.4, List.empty)
    val backpack: List[Item] = List(Item("sword", "attack"), Item("shield", "defense"))
    val expected: Person = Person("bob", "warlock", 9, 87.3, None, Some(mount), backpack, List("Hogwarts", "Yavin 4"))
    compareObj(obj, expected)
  }

  test("Person: Variation(2) Deserialize") {
    val xml: String = "<person><name>bob</name><class>warlock</class><level>9</level><health>87.3</health><soulStone>true</soulStone><mount/><backpack/><previousDestinations/></person>"
    val obj: Either[Throwable, Person] = xml.fromXml[Person]()
    val expected: Person = Person("bob", "warlock", 9, 87.3, Some(true), None, List.empty, List.empty)
    compareObj(obj, expected)
  }

}