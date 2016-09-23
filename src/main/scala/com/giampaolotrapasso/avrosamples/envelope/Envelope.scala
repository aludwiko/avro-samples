package com.giampaolotrapasso.avrosamples.envelope

import com.giampaolotrapasso.avrosamples.serializers.EventSerializer


trait Message

case class Order(id: Int, product: String, quantity: Int) extends Message

case class Payment(id: Int, orderId: Int, value: Int)  extends Message

case class Envelope[T <: Message](id: String,
                                  envType: String,
                                  version: Int,
                                  occurredOn: Long = System.currentTimeMillis(),
                                  message: T) extends EventSerializer[T]

object Envelopes {
  type OrderWithEnvelope = Envelope[Order]
}


