package denise.mendez.meli.modules

import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.jvm.isAccessible

/**
 * This method helps us to call private functions using reflection
 * @param name is the name of the private function
 * @param args are the arguments of the private function
 * @sample 'sut.callPrivateFun("privateFunctionName", param1, param2, ...param2)'
 */
inline fun <reified T> T.callPrivateFun(name: String, vararg args: Any?): Any? =
    T::class.declaredMemberFunctions
        .firstOrNull { it.name == name }
        ?.apply { isAccessible = true }
        ?.call(this, *args)

fun setPrivateField(target: Any, fieldName: String, newValue: Any) {
    val field = target.javaClass.getDeclaredField(fieldName)
    field.isAccessible = true
    field.set(target, newValue)
}
