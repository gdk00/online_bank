package misis

import akka.actor.ActorSystem
import akka.stream.scaladsl.{Flow, Sink, Source}

import scala.util.Success

object AkkaStreamsDemo extends App {
    implicit val system: ActorSystem = ActorSystem("App")
    implicit val ec = system.dispatcher

    // 0. Функции высших порядков
    println("== 0 ==")
    private val sum1000: Int = (1 to 1000).map(x => x * 2).foldLeft(0)(_ + _)
    println(sum1000)


    def factorial(x: Int): Int = ???
    /*
            private val future: Future[IndexedSeq[Int]] = Future.sequence((1 to 10000).map(x => Future {
                factorial(x)
            }))
        */

    // 1. Graph = Source -> Flow -> Sink. Stream = Graph.run()
    println("== 1 ==")
    val source = Source(1 to 100)
    val flow = Flow[Int].map(_ * 2)
    val sink = Sink.foreach(println)

    val graph = source.via(flow).to(sink)
    val graphLine = Source(1 to 100).map(_ * 2).map { x =>
        println(x)
        x
    }.to(Sink.ignore)

    //graph.run()

    //2. Backpressure (обратное давление)
    println("== 2 ==")
    val flowFactorial = Flow[Int].map(factorial)
    val printFlow = Flow[Int].map { x =>
        println(s"flow: ${x}")
        x
    }
    val slowSink = Sink.foreach { x: Int =>
        Thread.sleep(1000)
        println(s"sink: ${x}")
    }

    // source.via(flowFactorial).to(sink)
    val pullGraph = source.via(printFlow).to(slowSink)
    // pullGraph.run()


    val asyncGraph = source.via(printFlow).async.to(slowSink)
    // asyncGraph.run()

    //3. Mat
    println("== 3 ==")
    val sinkFold = Sink.fold[Int, Int](0)(_ + _)
    val graphFold = source.via(flow).toMat(sinkFold)((left, right) => right)
    val foldF = graphFold.run()
    foldF.onComplete {
        case Success(value) => println(s"Result fold: ${value}")
    }
}
