package com.fortysevendeg.muHello.server.protocol

import higherkindness.mu.rpc.internal.encoders.avro.bigDecimalTagged._
import higherkindness.mu.rpc.internal.encoders.avro.javatime._
import higherkindness.mu.rpc.protocol._

sealed trait Person extends Product with Serializable

@message final case class PersonRPC(name: String, category: String) extends Person

@message final case class HelloPersonStrRPC(message: String) extends Person

@message final case class HelloStr(message: String) extends Person

