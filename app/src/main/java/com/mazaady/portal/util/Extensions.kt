package com.mazaady.portal.util

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.io.Serializable

/* This is an extension function for the EditText class in Kotlin. It adds a TextWatcher to the
EditText and calls the provided `action` function after the text has been changed. The `action`
function takes a single parameter of type String, which is the new text value of the EditText. This
function can be used to perform some action whenever the text in the EditText is changed, without
having to write the boilerplate code for adding a TextWatcher every time. */
fun EditText.afterTextChanged(action: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // Do nothing
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // Do nothing
        }

        override fun afterTextChanged(s: Editable?) {
            action(s.toString())
        }
    })
}

/* This is an extension function for the `Bundle` class in Kotlin. It adds a function called
`serializable` that takes a `key` parameter of type `String` and returns an object of type `T`,
which must implement the `Serializable` interface. The function uses the `when` expression to check
if the device's Android version is equal to or greater than `Build.VERSION_CODES.TIRAMISU`. If it
is, the function calls the `getSerializable` method of the `Bundle` class with the `key` and
`T::class.java` parameters to retrieve the serializable object. If the device's Android version is
lower than `Build.VERSION_CODES.TIRAMISU`, the function calls the deprecated `getSerializable`
method of the `Bundle` class and casts the result to type `T`. The `reified` keyword is used to
allow the type `T` to be used at runtime, which is necessary for the `getSerializable` method to
work properly. */
inline fun <reified T : Serializable> Bundle.serializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getSerializable(key) as? T
}

