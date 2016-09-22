package com.giampaolotrapasso.avrosamples.test

import org.scalatest.concurrent.{Eventually, ScalaFutures}
import org.scalatest.prop.PropertyChecks
import org.scalatest.{BeforeAndAfterAll, FlatSpec, GivenWhenThen, Matchers}

class TestSpec
    extends FlatSpec
    with Matchers
    with GivenWhenThen
    with ScalaFutures
    with BeforeAndAfterAll
    with Eventually
    with PropertyChecks
