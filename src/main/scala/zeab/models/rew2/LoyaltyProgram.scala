package zeab.models.rew2

case class LoyaltyProgram(
                         tiers:List[Tier],
                         name: String,
                         `type`: String,
                         tierCount:String,
                         countryCode:String
                         )
