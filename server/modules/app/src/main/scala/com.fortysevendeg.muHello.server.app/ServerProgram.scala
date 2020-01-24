package com.fortysevendeg.muHello.server.app

import cats.effect.{ConcurrentEffect, ContextShift, Effect, ExitCode, IO, Sync, Timer}
import cats.implicits._
import com.fortysevendeg.muHello.server.process.HelloPersonServiceImpl
import com.fortysevendeg.muHello.server.protocol._
import higherkindness.mu.rpc.server.{AddService, GrpcServer}

import scala.concurrent.ExecutionContext

class ServerProgram[F[_]: ConcurrentEffect: ContextShift: Timer] {

  def serverProgram(): F[ExitCode] = {

    implicit val helloPersonServiceHandler: HelloPersonServiceImpl[F] =
      new HelloPersonServiceImpl[F]

    val serverName = "HelloServer"
    val configPort = 19683

    for {
      helloService <- HelloPersonService.bindService[F]
      server <- GrpcServer
        .default[F](configPort, List(AddService(helloService)))
      exitCode <- GrpcServer.server(server).as(ExitCode.Success)
    } yield exitCode

  }

}
