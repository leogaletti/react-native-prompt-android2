package br.net.lgm.promptandroid

import android.app.Activity
import android.content.DialogInterface
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.facebook.react.bridge.*

class PromptModule(reactContext: ReactApplicationContext) :
    ReactContextBaseJavaModule(reactContext) {

    override fun getName(): String {
        return "PromptModule"
    }

    @ReactMethod
    fun prompt(title: String, message: String?, placeholder: String?, promise: Promise) {
        val activity: Activity? = currentActivity
        if (activity == null) {
            promise.reject("ACTIVITY_NOT_FOUND", "Activity não encontrada")
            return
        }

        activity.runOnUiThread {
            val editText = EditText(activity).apply {
                hint = placeholder ?: "Digite um valor"
            }

            val builder = AlertDialog.Builder(activity)
            builder.setTitle(title)

            if (!message.isNullOrEmpty()) {
                builder.setMessage(message)
            }

            builder.setView(editText)

            // Botão de confirmação
            builder.setPositiveButton("Ok") { dialogInterface: DialogInterface, which: Int ->
                val input = editText.text.toString()
                promise.resolve(input)
            }

            // Botão de cancelamento
            builder.setNegativeButton("Cancelar") { dialogInterface: DialogInterface, which: Int ->
                promise.reject("PROMPT_CANCELLED", "Prompt cancelado pelo usuário")
                dialogInterface.cancel()
            }

            // Não deixar cancelar tocando fora
            builder.setCancelable(false)

            val dialog = builder.create()
            dialog.show()
        }
    }
}

