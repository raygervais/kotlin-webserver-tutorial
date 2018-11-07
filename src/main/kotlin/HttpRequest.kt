import com.sun.javafx.scene.control.skin.TableHeaderRow
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import java.math.BigDecimal

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
            get(path="/calculation/{operation}/") {
                val operator: String? = call.parameters["operation"]
                System.out.println(operator)

                val queryParams: Set<Map.Entry<String, List<String>>> = call.request.queryParameters.entries()
                System.out.println(queryParams.toString())

                val primaryNumeric: BigDecimal = call.request.queryParameters.get("primary")!!.toBigDecimal()
                val secondaryNumeric: BigDecimal = call.request.queryParameters.get("secondary")!!.toBigDecimal()

                try {
                    val result: BigDecimal = when (operator) {
                        "add" -> (primaryNumeric + secondaryNumeric)
                        "subtract" -> (primaryNumeric - secondaryNumeric)
                        "divide" -> (primaryNumeric / secondaryNumeric)
                        "multiply" -> (primaryNumeric * secondaryNumeric)
                        else -> throw error("Invalid Operator Provided!")
                    }
                    call.respondText(result.toString(), ContentType.Text.JavaScript )
                } catch (e: ArithmeticException) {
                    call.response.status(HttpStatusCode(500, "Invalid Calculation Attempted"))
                    call.respondText("Error! Invalid Calculation! <br /> Attempt "  + e.message!!, ContentType.Text.Html)
                }

            }
        }
    }
    server.start(wait = true)
}
