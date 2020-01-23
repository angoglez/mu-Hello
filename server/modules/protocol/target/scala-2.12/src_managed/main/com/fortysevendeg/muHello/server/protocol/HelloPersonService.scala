package com.fortysevendeg.muHello.server.protocol

import higherkindness.mu.rpc.internal.encoders.avro.bigDecimalTagged._
import higherkindness.mu.rpc.internal.encoders.avro.javatime._
import higherkindness.mu.rpc.protocol._


@service(AvroWithSchema,compressionType = Identity) trait HelloPersonService[F[_]] {

  def sayHelloTo(person: com.fortysevendeg.muHello.server.protocol.PersonRPC): F[com.fortysevendeg.muHello.server.protocol.HelloPersonStrRPC]

  def justSayHello(arg: Empty.type): F[com.fortysevendeg.muHello.server.protocol.HelloStr]

}
