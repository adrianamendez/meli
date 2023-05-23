package denise.mendez.meli.common

import denise.mendez.data.network.MessageExceptionInfo


interface ErrorView {
    fun showError(messageExceptionInfo: Int)
}