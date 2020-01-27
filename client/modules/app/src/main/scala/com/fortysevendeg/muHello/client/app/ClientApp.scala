package com.fortysevendeg.muHello.client.app

import cats.effect._
import cats.implicits._

object ClientApp extends IOApp {
  def run(args: List[String]): IO[ExitCode] =
    ClientProgram[IO].use(_.myProgram).as(ExitCode.Success)

}
