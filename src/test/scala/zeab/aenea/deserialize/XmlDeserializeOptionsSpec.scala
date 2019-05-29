package zeab.aenea.deserialize

//Imports
import zeab.aenea.XmlDeserialize._
import zeab.aenea.modelsfortest.singleclasses.options._
//ScalaTest
import org.scalatest.FunSuite

class XmlDeserializeOptionsSpec extends FunSuite {

  test("Option: None Deserialize") {
    val expectedType: String = "MyOptionStringClass"
    val xml: String = "<myOptionStringClass><myOptionString/></myOptionStringClass>"

    val ee = xmlDeserialize[MyOptionStringClass](xml)

    xmlDeserialize[MyOptionStringClass](xml) match {
      case Right(obj) =>
        assert(expectedType == obj.getClass.getSimpleName)
      case Left(_) => assert(false)
    }
  }

  test("Option: List[Any] Deserialize") {
    val expectedType: String = "MyOptionListClass"
    val xml: String = "<myOptionListClass><myOptionList>9</myOptionList><myOptionList>llama</myOptionList></myOptionListClass>"
    xmlDeserialize[MyOptionListClass](xml) match {
      case Right(obj) =>
        assert(expectedType == obj.getClass.getSimpleName)
      case Left(_) => assert(false)
    }
  }

  //TODO Expand this...

}