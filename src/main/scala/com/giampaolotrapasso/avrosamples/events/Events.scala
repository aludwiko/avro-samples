package com.giampaolotrapasso.avrosamples.events

import com.giampaolotrapasso.avrosamples.Movie.{Country, Year}

trait Event
final case class MovieChangedV1(title: String, year: Int)                                   extends Event
final case class MovieChangedV2(title: String, year: Int, director: String = "Burton")      extends Event
final case class MovieChangedV3(title: String, released_year: Int = 1980, director: String) extends Event
final case class MovieChangedV4(title: String, director: String, wonOscars: Int = 0)        extends Event
final case class MovieChangedV5(title: String, director: String, wonOscars: Int = 0, releases: Map[Country, Year])
    extends Event
