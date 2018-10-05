import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

// Following this tutorial: https://ktor.io/quickstart/index.html

fun main(args: Array<String>) {
    val server = embeddedServer(Netty, port = 8000) {
        routing {
            get("/") {
                call.respondText("Hello World!", ContentType.Text.Plain)
            }
            get(path="/demo") {
                call.respondText("<h1>Welcome to KTOR!</h1>", ContentType.Text.Html)
            }
        }
    }
    server.start(wait = true)
}
