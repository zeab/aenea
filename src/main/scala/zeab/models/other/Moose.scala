package zeab.models.other

import zeab.models.Item

case class Moose(
                items:List[Option[Item]],
                others:Option[List[Item]]
                )
