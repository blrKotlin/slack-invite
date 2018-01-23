package ui

import app.UiState
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import kotlinx.html.style
import org.w3c.dom.HTMLInputElement
import react.RBuilder
import react.dom.*

interface EventHandler {
    fun submitClicked()
    fun emailChanged(email: String)
}

fun RBuilder.inputBlock(state: UiState, eventHandler: EventHandler) {

    div("Input-Container") {
        form {
            input(type = InputType.email, name = "name", classes = "question") {
                attrs {
                    required = true
                    id = "nme"
                    onChangeFunction = {
                        val target = it.target as HTMLInputElement
                        eventHandler.emailChanged(target.value)
                    }
                }
            }
            label {
                span {
                    +state.placeholder
                }
            }
        }
        div("container") {
            a(classes = "btn") {
                +state.buttonText
                attrs.onClickFunction = {
                    eventHandler.submitClicked()
                }
            }
        }

    }
}


