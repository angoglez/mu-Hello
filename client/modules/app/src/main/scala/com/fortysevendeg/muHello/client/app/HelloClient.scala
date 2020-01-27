package com.fortysevendeg.muHello.client.app

import com.fortysevendeg.muHello.server.protocol._

trait HelloClient[F[_]] {

  def sayHelloTo(person: PersonRPC): F[HelloPersonStrRPC]

  def justSayHello(): F[HelloStr]

}
