package com.fortysevendeg.muHello.client.app

import cats.effect._
import higherkindness.mu.rpc._
import com.fortysevendeg.muHello.server.protocol._
import cats.implicits._
import higherkindness.mu.rpc.protocol.Empty

class ClientProgram[F[_]: ConcurrentEffect: ContextShift](client: HelloPersonService[F])
    extends HelloClient[F] {

  val p1 = PersonRPC("Anita", "Junior Scala Software Engineer")
  val p2 = PersonRPC("Enrique", "Super Compendium Maintainer")

  def sayHelloTo(person: PersonRPC): F[HelloPersonStrRPC] = client.sayHelloTo(person)

  def justSayHello(): F[HelloStr] = client.justSayHello(Empty)

  def myProgram: F[HelloPersonStrRPC] =
    sayHelloTo(p2).flatTap({ result =>
      Sync[F].delay(println(s"Result = $result"))
    })

}

object ClientProgram {

  def apply[F[_]: ConcurrentEffect: ContextShift] = {

    val channelFor: ChannelForAddress =
      ChannelForAddress("localhost", 19683)

    implicit val serviceClient: Resource[F, HelloPersonService[F]] =
      HelloPersonService.client[F](channelFor)

    serviceClient.map(new ClientProgram[F](_))
  }

}
