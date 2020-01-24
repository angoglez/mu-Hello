package com.fortysevendeg.muHello.server.app

import cats.effect.{ContextShift, ExitCode, IO, IOApp}



object ServerApp extends IOApp  {


  def run(args: List[String]): IO[ExitCode] = new ServerProgram[IO]().serverProgram()
}