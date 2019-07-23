//package zeab.obsolte
//
//import scala.xml.{Elem, Node}
//
//object XmlHelpers {
//
//  implicit class NodeConvert(val node: Node) extends AnyVal {
//    def toElem: Elem =
//      Elem.apply(node.prefix, node.label, node.attributes, node.scope, true, node.child :_*)
//  }
//
//}
