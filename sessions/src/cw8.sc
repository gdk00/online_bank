import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object helper {
    object container
    trait Docker {
        def run(container: _)
    }

    object Get{
        def apply(uri: String): Seq[String] = ???
    }
    trait Git{
        def commit: Git
        def push: Git
        def git: Git
        def & (git: Git): Git
    }

    //trait Scala
    object Scala
    object Akka
    object Http
    object Postgres
    object Slick
    object JavaScript
    implicit def akka2str(obj: Akka.type): String = ???
    implicit def http2str(obj: Http.type): String = ???
    implicit def pg2str(obj: Postgres.type): String = ???
}

import helper._

trait Monad[_] {
  def flatMap()
}

val monads = {
    case x :: Nil => "Done"
    case Some(value) => "https://github.com/anton-k/ru-neophyte-guide-to-scala/blob/master/src/p05-option.md"
    case Left(value) => Right("https://github.com/anton-k/ru-neophyte-guide-to-scala/blob/master/src/p07-either.md")
    case f: Future[_] => f.map(_ => "https://github.com/anton-k/ru-neophyte-guide-to-scala/blob/master/src/p08-future.md")
}


val Technologies = {
    case Scala  => "Done" + monads
    case "akka" => Seq("https://akka.io/", "https://ru.wikipedia.org/wiki/Модель_акторов")
    case "http" => Get("https://ru.wikipedia.org/wiki/HTTP").map {
        case "methods" => Seq("GET", "PUT", "POST", "DELETE")
        case "URI" => true
        case "ContentType" => "application/json"
        case "status" => 200
    }
    case docker: Docker => docker run container
    case git: Git => (git commit) & (git push)
    case "sbt" => "https://www.scala-sbt.org/download.html"
}

val gettingStart ="""
    sbt new scala/scala-seed.g8
"""
