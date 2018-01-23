package ui

import app.UiState
import react.RBuilder
import react.dom.div
import react.dom.h1
import react.dom.h2
import react.dom.h5

fun RBuilder.ui(state: UiState, eventHandler: EventHandler) {
    div("App-header") {
        h1("Text-Vertical") {
            +state.heading
        }
    }
    div{
        inputBlock(state, eventHandler)
    }
}