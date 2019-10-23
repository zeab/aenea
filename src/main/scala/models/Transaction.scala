package models

case class Transaction(
                        transactionId: String,
                        somethingElse: String,
                        redemptions: Vector[Redemption]
                      )
