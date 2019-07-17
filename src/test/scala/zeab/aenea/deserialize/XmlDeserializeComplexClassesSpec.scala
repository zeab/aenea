package zeab.aenea.deserialize

//Imports
import zeab.aenea.XmlDeserialize._
import zeab.aenea.modelsfortest.complexclasses.{Horse, Item, Person}
//ScalaTest
import org.scalatest.FunSuite

class XmlDeserializeComplexClassesSpec extends FunSuite {

  test("Person Deserialize") {
    val xml: String = "<person><name>bob</name><class>warlock</class><level>9</level><health>87.3</health><soulStone/><mount><name>daisy</name><speed>1.4</speed><backpack></backpack></mount><backpack><item><name>sword</name><type>attack</type></item></backpack><backpack><item><name>shield</name><type>defense</type></item></backpack><previousDestinations>Hogwarts</previousDestinations><previousDestinations>Yavin 4</previousDestinations></person>"
    xmlDeserialize[Person](xml) match {
      case Right(obj) =>
        assert(obj.name == "bob")
        assert(obj.`class` == "warlock")
        assert(obj.level == 9)
        assert(obj.health == 87.3)
        assert(obj.soulStone.isEmpty)
        assert(obj.mount.getOrElse(Horse("", 0, List.empty)).name == "daisy")
        assert(obj.mount.getOrElse(Horse("", 0, List.empty)).speed == 1.4)
        assert(obj.mount.getOrElse(Horse("", 0, List.empty)).backpack.isEmpty)
        assert(obj.previousDestinations.headOption.getOrElse("") == "Hogwarts")
        assert(obj.previousDestinations.lastOption.getOrElse("") == "Yavin 4")
        assert(obj.backpack.headOption.getOrElse(Item("", "")).name == "sword")
        assert(obj.backpack.headOption.getOrElse(Item("", "")).`type` == "attack")
        assert(obj.backpack.lastOption.getOrElse(Item("", "")).name == "shield")
        assert(obj.backpack.lastOption.getOrElse(Item("", "")).`type` == "defense")
      case Left(_) => assert(false)
    }
  }

  test("Invalid Person Deserialize") {
    val xml: String = "<person><name>bob</name><class>warlock</class><level>9</level><health>87.3</health><soulStone/><mount><name>daisy</name><speed>1.4</speed><backpack></backpack></mount><backpack><name>sword</name></backpack><backpack><name>shield</name><type>defense</type></backpack><previousDestinations>Hogwarts</previousDestinations><previousDestinations>Yavin 4</previousDestinations></person>"
    xmlDeserialize[Person](xml) match {
      case Right(_) => assert(false)
      case Left(_) => assert(true)
    }
  }

}
