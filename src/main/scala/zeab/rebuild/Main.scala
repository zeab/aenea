package zeab.rebuild

import zeab.models.Item
import zeab.models.other._
import zeab.models.rew2.{LoyaltyProgram, PursePoint, RewardLevel, Tier}
import zeab.models.rewards.Backpack

object Main extends App {

  Map("salesTransactions" ->
    List(
      Map("salesTransaction" ->
        Map(
          "salesTransactionId" -> "xxx",
          "type" -> "xxx",
          "sourceSalesTransactionId" -> "xxx",
          "salesOrder" ->
            Map(
              "salesId" -> "xxx",
              "storeNumber" -> "xxx",
              "local" -> "xxx",
              "tenders" ->
                List(
              Map("tender" ->
                Map(
                  "type" -> "xxx",
                  "amount" -> "xxx",
                  "currentCode" -> "xxx",
                  "cardNumber" -> "xxx",
                  "memberUId" -> "xxx"
                )
              ),
              Map("tender" ->
                Map(
                  "type" -> "xxx",
                  "amount" -> "xxx",
                  "currentCode" -> "xxx",
                  "cardNumber" -> "xxx",
                  "memberUId" -> "xxx"
                )
              )
            ),
              "orderSummary" ->
                Map(
              "subtotalAmount" -> "xxx",
              "totalDiscountAmount" -> "xxx",
              "totalTaxAmount" -> "xxx",
              "totalAmount" -> "xxx",
              "taxes" ->
                List(
                  Map("tax" ->
                    Map("name" -> "xxx", "amount" -> "xxx")
                  ),
                  Map("tax" ->
                    Map("name" -> "xxx", "amount" -> "xxx")
                  )
                ),
              "receiptLines" ->
                List(
                  Map("receiptLine" ->
                    Map("descr" -> "xxx")
                  ),
                  Map("receiptLine" ->
                    Map("descr" -> "xxx")
                  )
                )
            )
            )
        )
      ),
      Map("salesTransaction" ->
        Map(
          "salesTransactionId" -> "xxx",
          "type" -> "xxx",
          "sourceSalesTransactionId" -> "xxx",
          "salesOrder" ->
            Map(
              "salesId" -> "xxx",
              "storeNumber" -> "xxx",
              "local" -> "xxx",
              "tenders" ->
                List(
                  Map("tender" ->
                    Map(
                      "type" -> "xxx",
                      "amount" -> "xxx",
                      "currentCode" -> "xxx",
                      "cardNumber" -> "xxx",
                      "memberUId" -> "xxx"
                    )
                  ),
                  Map("tender" ->
                    Map(
                      "type" -> "xxx",
                      "amount" -> "xxx",
                      "currentCode" -> "xxx",
                      "cardNumber" -> "xxx",
                      "memberUId" -> "xxx"
                    )
                  )
                ),
              "orderSummary" ->
                Map(
                  "subtotalAmount" -> "xxx",
                  "totalDiscountAmount" -> "xxx",
                  "totalTaxAmount" -> "xxx",
                  "totalAmount" -> "xxx",
                  "taxes" ->
                    List(
                      Map("tax" ->
                        Map("name" -> "xxx", "amount" -> "xxx")
                      ),
                      Map("tax" ->
                        Map("name" -> "xxx", "amount" -> "xxx")
                      )
                    ),
                  "receiptLines" ->
                    List(
                      Map("receiptLine" ->
                        Map("descr" -> "xxx")
                      ),
                      Map("receiptLine" ->
                        Map("descr" -> "xxx")
                      )
                    )
                )
            )
        )
      )
    )
  )

  Map("salesOrder" ->
    "salesId" -> "xxx",
    "storeNumber" -> "xxx",
    "local" -> "xxx",
    "tenders" ->
      List(
        Map("tender" ->
          Map(
            "type" -> "xxx",
            "amount" -> "xxx",
            "currentCode" -> "xxx",
            "cardNumber" -> "xxx",
            "memberUId" -> "xxx"
          )
        ),
        Map("tender" ->
          Map(
            "type" -> "xxx",
            "amount" -> "xxx",
            "currentCode" -> "xxx",
            "cardNumber" -> "xxx",
            "memberUId" -> "xxx"
          )
        )
      ),
    "orderSummary" ->
      Map(
        "subtotalAmount" -> "xxx",
        "totalDiscountAmount" -> "xxx",
        "totalTaxAmount" -> "xxx",
        "totalAmount" -> "xxx",
        "taxes" ->
          List(
            Map("tax" ->
              Map("name" -> "xxx", "amount" -> "xxx")
            ),
            Map("tax" ->
              Map("name" -> "xxx", "amount" -> "xxx")
            )
          ),
        "receiptLines" ->
          List(
            Map("receiptLine" ->
              Map("descr" -> "xxx")
            ),
            Map("receiptLine" ->
              Map("descr" -> "xxx")
            )
          )
      )
  )


  Map("orderSummary" ->
    "subtotalAmount" -> "xxx",
    "totalDiscountAmount" -> "xxx",
    "totalTaxAmount" -> "xxx",
    "totalAmount" -> "xxx",
    "taxes" ->
      List(
      Map("tax" ->
        Map("name" -> "xxx", "amount" -> "xxx")
      ),
      Map("tax" ->
        Map("name" -> "xxx", "amount" -> "xxx")
      )
    ),
    "receiptLines" ->
      List(
        Map("receiptLine" ->
          Map("descr" -> "xxx")
        ),
        Map("receiptLine" ->
          Map("descr" -> "xxx")
        )
      )
  )

  Map("loyaltyPrograms" ->
    List(
      Map("loyaltyProgram" ->
        Map(
          "tiers" ->
            List(
              Map("tier" ->
                Map(
                  "rewardLevels" ->
                    List(
                      Map("rewardLevel" ->
                        Map("name" -> "xxx", "points" -> "xxx", "rewardCode" -> "xxx")
                      ),
                      Map("rewardLevel" ->
                        Map("name" -> "xxx", "points" -> "xxx", "rewardCode" -> "xxx")
                      ),
                      Map("rewardLevel" ->
                        Map("name" -> "xxx", "points" -> "xxx", "rewardCode" -> "xxx")
                      )
                    ),
                  "pursePoint" ->
                    Map(
                      "entry" -> "xxx",
                      "exit" -> "xxx",
                      "reeval" -> "0"
                    ),
                  "tierNumber" -> "2"
                )
              ),
              Map("tier" ->
                Map(
                  "rewardsLevels" ->
                    List(
                      Map("rewardLevel" ->
                        Map("name" -> "xxx", "points" -> "xxx", "rewardCode" -> "xxx")
                      ),
                      Map("rewardLevel" ->
                        Map("name" -> "xxx", "points" -> "xxx", "rewardCode" -> "xxx")
                      ),
                      Map("rewardLevel" ->
                        Map("name" -> "xxx", "points" -> "xxx", "rewardCode" -> "xxx")
                      )
                    ),
                  "pursePoint" ->
                    Map(
                      "entry" -> "xxx",
                      "exit" -> "xxx",
                      "reeval" -> "0"
                    ),
                  "tierNumber" -> "2"
                )
              )
            ),
          "name" -> "xxx",
          "type" -> "xxx",
          "tierCount" -> "xxx",
          "countryCode" -> "xxx"
        )
      ),
      Map("loyaltyProgram" ->
        Map(
          "tiers" ->
            List(
              Map("tier" ->
                Map(
                  "rewardsLevels" ->
                    List(
                      Map("rewardLevel" ->
                        Map("name" -> "xxx", "points" -> "xxx", "rewardCode" -> "xxx")
                      ),
                      Map("rewardLevel" ->
                        Map("name" -> "xxx", "points" -> "xxx", "rewardCode" -> "xxx")
                      ),
                      Map("rewardLevel" ->
                        Map("name" -> "xxx", "points" -> "xxx", "rewardCode" -> "xxx")
                      )
                    ),
                  "pursePoint" ->
                    Map(
                      "entry" -> "xxx",
                      "exit" -> "xxx",
                      "reeval" -> "0"
                    ),
                  "tierNumber" -> "2"
                )
              ),
              Map("tier" ->
                Map(
                  "rewardsLevels" ->
                    List(
                      Map("rewardLevel" ->
                        Map("name" -> "xxx", "points" -> "xxx", "rewardCode" -> "xxx")
                      ),
                      Map("rewardLevel" ->
                        Map("name" -> "xxx", "points" -> "xxx", "rewardCode" -> "xxx")
                      ),
                      Map("rewardLevel" ->
                        Map("name" -> "xxx", "points" -> "xxx", "rewardCode" -> "xxx")
                      )
                    ),
                  "pursePoint" ->
                    Map(
                      "entry" -> "xxx",
                      "exit" -> "xxx",
                      "reeval" -> "0"
                    ),
                  "tierNumber" -> "2"
                )
              )
            ),
          "name" -> "xxx",
          "type" -> "xxx",
          "tierCount" -> "xxx",
          "countryCode" -> "xxx"
        )
      ),
      Map("loyaltyProgram" ->
        Map(
          "tiers" ->
            List(
              Map("tier" ->
                Map(
                  "rewardsLevels" ->
                    List(
                      Map("rewardLevel" ->
                        Map("name" -> "xxx", "points" -> "xxx", "rewardCode" -> "xxx")
                      ),
                      Map("rewardLevel" ->
                        Map("name" -> "xxx", "points" -> "xxx", "rewardCode" -> "xxx")
                      ),
                      Map("rewardLevel" ->
                        Map("name" -> "xxx", "points" -> "xxx", "rewardCode" -> "xxx")
                      )
                    ),
                  "pursePoint" ->
                    Map(
                      "entry" -> "xxx",
                      "exit" -> "xxx",
                      "reeval" -> "0"
                    ),
                  "tierNumber" -> "2"
                )
              ),
              Map("tier" ->
                Map(
                  "rewardsLevels" ->
                    List(
                      Map("rewardLevel" ->
                        Map("name" -> "xxx", "points" -> "xxx", "rewardCode" -> "xxx")
                      ),
                      Map("rewardLevel" ->
                        Map("name" -> "xxx", "points" -> "xxx", "rewardCode" -> "xxx")
                      ),
                      Map("rewardLevel" ->
                        Map("name" -> "xxx", "points" -> "xxx", "rewardCode" -> "xxx")
                      )
                    ),
                  "pursePoint" ->
                    Map(
                      "entry" -> "xxx",
                      "exit" -> "xxx",
                      "reeval" -> "0"
                    ),
                  "tierNumber" -> "2"
                )
              )
            ),
          "name" -> "xxx",
          "type" -> "xxx",
          "tierCount" -> "xxx",
          "countryCode" -> "xxx"
        )
      )
    )
  )

  Map("loyaltyProgram" ->
    Map(
      "tiers" ->
        List(
          Map("tier" ->
            Map(
              "rewardsLevels" ->
                List(
                  Map("rewardLevel" ->
                    Map("name" -> "xxx", "points" -> "xxx", "rewardCode" -> "xxx")
                  ),
                  Map("rewardLevel" ->
                    Map("name" -> "xxx", "points" -> "xxx", "rewardCode" -> "xxx")
                  ),
                  Map("rewardLevel" ->
                    Map("name" -> "xxx", "points" -> "xxx", "rewardCode" -> "xxx")
                  )
                ),
              "pursePoint" ->
                Map(
                  "entry" -> "xxx",
                  "exit" -> "xxx",
                  "reeval" -> "0"
                ),
              "tierNumber" -> "2"
            )
          ),
          Map("tier" ->
            Map(
              "rewardsLevels" ->
                List(
                  Map("rewardLevel" ->
                    Map("name" -> "xxx", "points" -> "xxx", "rewardCode" -> "xxx")
                  ),
                  Map("rewardLevel" ->
                    Map("name" -> "xxx", "points" -> "xxx", "rewardCode" -> "xxx")
                  ),
                  Map("rewardLevel" ->
                    Map("name" -> "xxx", "points" -> "xxx", "rewardCode" -> "xxx")
                  )
                ),
              "pursePoint" ->
                Map(
                  "entry" -> "xxx",
                  "exit" -> "xxx",
                  "reeval" -> "0"
                ),
              "tierNumber" -> "2"
            )
          )
        ),
      "name" -> "xxx",
      "type" -> "xxx",
      "tierCount" -> "xxx",
      "countryCode" -> "xxx"
    )
  )

  Map(
    "tiers" ->
      List(
      Map("tier" ->
        Map(
          "rewardsLevels" ->
            List(
              Map("rewardLevel" ->
                Map("name" -> "xxx", "points" -> "xxx", "rewardCode" -> "xxx")
              ),
              Map("rewardLevel" ->
                Map("name" -> "xxx", "points" -> "xxx", "rewardCode" -> "xxx")
              ),
              Map("rewardLevel" ->
                Map("name" -> "xxx", "points" -> "xxx", "rewardCode" -> "xxx")
              )
            ),
          "pursePoint" ->
            Map(
              "entry" -> "xxx",
              "exit" -> "xxx",
              "reeval" -> "0"
            ),
          "tierNumber" -> "2"
        )
      ),
      Map("tier" ->
        Map(
          "rewardsLevels" ->
            List(
              Map("rewardLevel" ->
                Map("name" -> "xxx", "points" -> "xxx", "rewardCode" -> "xxx")
              ),
              Map("rewardLevel" ->
                Map("name" -> "xxx", "points" -> "xxx", "rewardCode" -> "xxx")
              ),
              Map("rewardLevel" ->
                Map("name" -> "xxx", "points" -> "xxx", "rewardCode" -> "xxx")
              )
            ),
          "pursePoint" ->
            Map(
              "entry" -> "xxx",
              "exit" -> "xxx",
              "reeval" -> "0"
            ),
          "tierNumber" -> "2"
        )
      )
    ),
    "name" -> "xxx",
    "type" -> "xxx",
    "tierCount" -> "xxx",
    "countryCode" -> "xxx"
  )

  List(
    Map("tier" ->
      Map(
        "rewardsLevels" ->
          List(
            Map("rewardLevel" ->
              Map("name" -> "xxx", "points" -> "xxx", "rewardCode" -> "xxx")
            ),
            Map("rewardLevel" ->
              Map("name" -> "xxx", "points" -> "xxx", "rewardCode" -> "xxx")
            ),
            Map("rewardLevel" ->
              Map("name" -> "xxx", "points" -> "xxx", "rewardCode" -> "xxx")
            )
          ),
        "pursePoint" ->
          Map(
            "entry" -> "xxx",
            "exit" -> "xxx",
            "reeval" -> "0"
          ),
        "tierNumber" -> "2"
      )
    ),
    Map("tier" ->
      Map(
        "rewardsLevels" ->
          List(
            Map("rewardLevel" ->
              Map("name" -> "xxx", "points" -> "xxx", "rewardCode" -> "xxx")
            ),
            Map("rewardLevel" ->
              Map("name" -> "xxx", "points" -> "xxx", "rewardCode" -> "xxx")
            ),
            Map("rewardLevel" ->
              Map("name" -> "xxx", "points" -> "xxx", "rewardCode" -> "xxx")
            )
          ),
        "pursePoint" ->
          Map(
            "entry" -> "xxx",
            "exit" -> "xxx",
            "reeval" -> "0"
          ),
        "tierNumber" -> "2"
      )
    )
  )

  List(
    Map("rewardLevel" ->
      Map("name" -> "xxx", "points" -> "xxx", "rewardCode" -> "xxx")
    ),
    Map("rewardLevel" ->
      Map("name" -> "xxx", "points" -> "xxx", "rewardCode" -> "xxx")
    ),
    Map("rewardLevel" ->
      Map("name" -> "xxx", "points" -> "xxx", "rewardCode" -> "xxx")
    )
  )

  val a = 9
  val b = 9.0
  val c = "moose"

  val d = Item("moose", "llama")
  val e = List("moose", "llama", "dog")
  val f = Seq("moose", "llama", "dog")

  val g = Person("ob", Some(Horse("asd", Some(Saddle("leather")))))
  val h = Item("", "")
  val i = Jedi(List(LightSaber("blue"), LightSaber("green")))
  val j = Moose(
    List(Some(Item("ee", "rr")), None, Some(Item("mm", "yy"))),
    Some(List(Item("xx", "yy"), Item("qq", "nn")))
  )
  val k = Speeder(None)
  val l = Crab(List("ww", "xx"))

  val m = Map("momajamma" -> j)
  val n =
    LoyaltyProgram(
      List(
        Tier(
          List(
            RewardLevel(
              "sdf",
              "sgf",
              "sefe"
            ),
            RewardLevel(
              "sdf",
              "sgf",
              "sefe"
            )
          ),
          PursePoint(
            "ex",
            "exit",
            "fgff"
          ),
          "ss"
        )
      ),
      "moose",
      "llama",
      "3",
      "us"
    )
  val o = Guard(Set(Item("fwef", "asd"), Item("fwef", "asd")))

  val s = Llama(Vector(Item("fwef", "asd"), Item("fwef", "asd")))

  val p = Llama2(List(Item("fwef", "asd"), Item("fwef", "asd")))

  val q = Map("loyaltyPrograms" -> List(n, n))

  val z = XmlSerializer.xmlSerialize(q)

  println(z)

}
