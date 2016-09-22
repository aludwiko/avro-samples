package com.giampaolotrapasso.avrosamples

object Movie {

  type Country = String
  type Year    = Int

  // version 1 - starting class
  // case class MovieChanged(title: String, year: Int)
  // version 2 - added field
  // case class MovieChanged(title: String, year: Int, director: String = "unknown")
  // version 3 - renamed field year to release_year
  // case class MovieChanged(title: String, release_year: Int, director: String)
  // version 4 - removed field year
  // case class MovieChanged(title: String, director: String = "unknown", wonOscars: Int = 0)
  // version 5 - added releases as map country/year
  case class MovieChanged(title: String,
                          director: String = "unknown",
                          wonOscars: Int = 0,
                          releases: Map[Country, Year])

}
