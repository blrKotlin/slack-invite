package app

import kotlinx.html.InputType
import kotlinx.html.hidden
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import org.w3c.dom.url.URLSearchParams
import org.w3c.fetch.RequestInit
import react.*
import react.dom.*
import ui.EventHandler
import ui.ui
import kotlin.browser.window


interface AppUiState : RState {
    var uiState: UiState
}


class App : RComponent<RProps, AppUiState>() {

    override fun AppUiState.init() {
        uiState = UiState(
                "Request Invite to BlrKotlin Slack Channel",
                "email@yourdomain.com",
                "Request Invite",
                false,
                "")
    }
    override fun RBuilder.render() {
        ui(state.uiState, object : EventHandler{
            override fun emailChanged(email: String) {
                setState {
                    state.uiState.email = email
                }
            }

            override fun submitClicked() {
                if(state.uiState.email.isEmpty()){
                    /**
                     *  Show an error message maybe.
                     */
                    return
                }

                val url = "https://blr-kotlin.herokuapp.com/invite_post/"
                window.fetch(url, object : RequestInit {
                    override var method: String? = "POST"
                    override var body: dynamic = URLSearchParams().apply {
                        append("email", "${state.uiState.email}")
                    }
                }).then { response ->
                    response.json().then({ resolve ->
                        when (parse(resolve)) {
                            1 -> {
                                setState {
                                    state.uiState.isInviteSent = true
                                    state.uiState.buttonText = "Check your Email"
                                }
                            }
                            2 -> {
                                setState {

                                    state.uiState.isInviteSent = true
                                    state.uiState.buttonText = "You are already Invited, Check your Email"
                                }
                            }

                            3 -> {
                                state.uiState.isInviteSent = true
                                state.uiState.buttonText = "You are already In team"
                            }
                            else -> {
                                console.log("Something went wrong :(")
                            }
                        }
                    }, { t ->
                        console.log(t)
                    })
                }

            }

        })

    }

    private fun parse(json: dynamic): Int {
        return json.status
    }
}

fun RBuilder.app() = child(App::class) {}
