package com.fortysevendeg.muHello.server.process

import cats.effect.Sync
import com.fortysevendeg.muHello.server.protocol._
import higherkindness.mu.rpc.protocol.Empty

class HelloPersonServiceImpl[F[_]: Sync] extends HelloPersonService[F] {

  val serviceName = "HelloPersonService"

  override def sayHelloTo(person: PersonRPC): F[HelloPersonStrRPC] =
    Sync[F].pure(
      HelloPersonStrRPC(s"Hello,${person.name}!!\n You're a fantastic ${person.category}"))

  override def justSayHello(arg: Empty.type): F[HelloStr] =
    Sync[F].pure(HelloStr("Hey... just wanted to say hello!!"))
}
