package zeab.aenea.deserialize

//Imports
import zeab.aenea.XmlDeserialize._
import zeab.aenea.modelsfortest.singleclasses.collections.lists.{MyListDoubleClass, MyListOptionClass}
//ScalaTest
import org.scalatest.FunSuite

class XmlDeserializeListsSpec extends FunSuite {

  test("List: Double Deserialize") {
    val expectedType: String = "MyListDoubleClass"
    val xml: String = "<myListDoubleClass><myListDouble>1.0</myListDouble><myListDouble>5.3</myListDouble><myListDouble>7.0</myListDouble><myListDouble>3.0</myListDouble><myListDouble>2.1</myListDouble></myListDoubleClass>"
    xmlDeserialize[MyListDoubleClass](xml) match {
      case Right(obj) =>
        assert(expectedType == obj.getClass.getSimpleName)
      case Left(_) => assert(false)
    }
  }

  test("List: Option[Any] Deserialize") {
    val expectedType: String = "MyListOptionClass"
    val xml: String = "<myListOptionClass><myListOption>9</myListOption><myListOption>llama</myListOption><myListOption/></myListOptionClass>"
    xmlDeserialize[MyListOptionClass](xml) match {
      case Right(obj) =>
        assert(expectedType == obj.getClass.getSimpleName)
      case Left(_) => assert(false)
    }
  }

}