package com.giampaolotrapasso.avrosamples.test

import com.giampaolotrapasso.avrosamples.envelope.Envelope.Payload
import com.giampaolotrapasso.avrosamples.envelope.{Envelope, EnvelopeSerializer, Order}
import shapeless.Coproduct

class EnvelopeEncodingTest extends TestSpec {

  "EnvelopeEncodingTest" should "deserialize an enveloped order " in {

    val order    = Order(1, "book", 2)
    val envelope = Envelope(id = "1", envType = "Order", version = 1, message = Coproduct[Payload](order))

    val ashes: Array[Byte] = EnvelopeSerializer.serialize(envelope)

    info(envelope.toString)

    val phoenix = EnvelopeSerializer.deserialize(ashes)
    info(s"${phoenix} raised up from the avro ashes")
    envelope shouldEqual phoenix

  }

}
