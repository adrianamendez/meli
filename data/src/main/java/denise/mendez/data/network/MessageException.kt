package denise.mendez.data.network


interface MessageException {
    fun process(error: Throwable, params: HashMap<String, Any>? = null): MessageExceptionInfo
}