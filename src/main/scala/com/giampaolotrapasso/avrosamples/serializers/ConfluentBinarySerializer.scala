package com.giampaolotrapasso.avrosamples.serializers

import java.io.ByteArrayOutputStream

import com.giampaolotrapasso.avrosamples.events._
import com.sksamuel.avro4s.AvroOutputStream

object ConfluentBinarySerializer {

  def serializeV1(o: AnyRef): Array[Byte] = {
    val output = new ByteArrayOutputStream
    output.write(1)
    val avro = AvroOutputStream.binary[MovieChangedV1](output)
    avro.write(o.asInstanceOf[MovieChangedV1])
    avro.close()
    output.toByteArray
  }

  def serializeV2(o: AnyRef): Array[Byte] = {
    val output = new ByteArrayOutputStream
    output.write(2)
    val avro = AvroOutputStream.binary[MovieChangedV2](output)
    avro.write(o.asInstanceOf[MovieChangedV2])
    avro.close()
    output.toByteArray
  }

  def serializeV3(o: AnyRef): Array[Byte] = {
    val output = new ByteArrayOutputStream
    output.write(3)
    val avro = AvroOutputStream.binary[MovieChangedV3](output)
    avro.write(o.asInstanceOf[MovieChangedV3])
    avro.close()
    output.toByteArray
  }

  def serializeV4(o: AnyRef): Array[Byte] = {
    val output = new ByteArrayOutputStream
    output.write(4)
    val avro = AvroOutputStream.binary[MovieChangedV4](output)
    avro.write(o.asInstanceOf[MovieChangedV4])
    avro.close()
    output.toByteArray
  }

  def serializeV5(o: AnyRef): Array[Byte] = {
    val output = new ByteArrayOutputStream
    output.write(5)
    val avro = AvroOutputStream.binary[MovieChangedV5](output)
    avro.write(o.asInstanceOf[MovieChangedV5])
    avro.close()
    output.toByteArray
  }

}
