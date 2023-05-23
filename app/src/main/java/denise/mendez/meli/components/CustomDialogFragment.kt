package denise.mendez.meli.components

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.NO_ID
import android.view.ViewGroup
import android.widget.Button
import android.widget.CompoundButton
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import denise.mendez.meli.R
import denise.mendez.meli.components.CustomDialogFragment.Builder.Companion.ARGUMENT_CANCELABLE
import denise.mendez.meli.components.CustomDialogFragment.Builder.Companion.ARGUMENT_ICON
import denise.mendez.meli.components.CustomDialogFragment.Builder.Companion.ARGUMENT_ICON_COLOR
import denise.mendez.meli.components.CustomDialogFragment.Builder.Companion.ARGUMENT_MESSAGE
import denise.mendez.meli.components.CustomDialogFragment.Builder.Companion.ARGUMENT_MESSAGE_PARAMS
import denise.mendez.meli.components.CustomDialogFragment.Builder.Companion.ARGUMENT_NEGATIVE_BUTTON
import denise.mendez.meli.components.CustomDialogFragment.Builder.Companion.ARGUMENT_POSITIVE_BUTTON
import denise.mendez.meli.components.CustomDialogFragment.Builder.Companion.ARGUMENT_TITLE
import denise.mendez.meli.components.CustomDialogFragment.Builder.Companion.ARGUMENT_TITLE_PARAMS
import denise.mendez.meli.databinding.DialogCustomBinding

class CustomDialogFragment : AppCompatDialogFragment() {

    class Builder(
        private val contextLocal: Context
    ) {

        companion object {
            const val ARGUMENT_ICON = "ARGUMENT_ICON"
            const val ARGUMENT_ICON_COLOR = "ARGUMENT_ICON_COLOR"
            const val ARGUMENT_TITLE = "ARGUMENT_TITLE"
            const val ARGUMENT_TITLE_PARAMS = "ARGUMENT_TITLE_PARAMS"
            const val ARGUMENT_MESSAGE = "ARGUMENT_MESSAGE"
            const val ARGUMENT_MESSAGE_PARAMS = "ARGUMENT_MESSAGE_PARAMS"
            const val ARGUMENT_CHECKBOX = "ARGUMENT_CHECKBOX"
            const val ARGUMENT_CHECKBOX_PARAMS = "ARGUMENT_CHECKBOX_PARAMS"
            const val ARGUMENT_POSITIVE_BUTTON = "ARGUMENT_POSITIVE_BUTTON"
            const val ARGUMENT_NEGATIVE_BUTTON = "ARGUMENT_NEGATIVE_BUTTON"
            const val ARGUMENT_CANCELABLE = "ARGUMENT_CANCELABLE"
        }

        private val arguments = Bundle()
        private var onPositiveClickListener: View.OnClickListener? = null
        private var onNegativeClickListener: View.OnClickListener? = null
        private var onCheckedChangedListener: CompoundButton.OnCheckedChangeListener? = null

        init {
            setCancelable(true)
        }

        fun setIcon(@DrawableRes drawableId: Int): Builder {
            arguments.putInt(ARGUMENT_ICON, drawableId)
            return this
        }

        fun setIcon(@DrawableRes drawableId: Int, @ColorRes color: Int): Builder {
            arguments.putInt(ARGUMENT_ICON, drawableId)
            arguments.putInt(ARGUMENT_ICON_COLOR, color)
            return this
        }

        fun setTitle(@StringRes titleId: Int, vararg params: String): Builder {
            arguments.putString(ARGUMENT_TITLE, contextLocal.getString(titleId))
            arguments.putStringArray(ARGUMENT_TITLE_PARAMS, params)
            return this
        }

        fun setTitle(message: String, vararg params: String): Builder {
            arguments.putString(ARGUMENT_TITLE, message)
            arguments.putStringArray(ARGUMENT_TITLE_PARAMS, params)
            return this
        }

        fun setMessage(@StringRes messageId: Int, vararg params: String): Builder {
            arguments.putString(ARGUMENT_MESSAGE, contextLocal.getString(messageId))
            arguments.putStringArray(ARGUMENT_MESSAGE_PARAMS, params)
            return this
        }

        fun setMessage(message: String, vararg params: String): Builder {
            arguments.putString(ARGUMENT_MESSAGE, message)
            arguments.putStringArray(ARGUMENT_MESSAGE_PARAMS, params)
            return this
        }

        fun setCheckboxAdditionalOption(
            @StringRes checkboxAdditionalOptionId: Int,
            vararg params: String
        ): Builder {
            arguments.putString(
                ARGUMENT_CHECKBOX,
                contextLocal.getString(checkboxAdditionalOptionId)
            )
            arguments.putStringArray(ARGUMENT_CHECKBOX_PARAMS, params)
            return this
        }

        fun setCheckboxAdditionalOptionListener(listener: CompoundButton.OnCheckedChangeListener? = null): Builder {
            onCheckedChangedListener = listener
            return this
        }

        fun setPositiveButton(
            @StringRes positiveButtonId: Int,
            listener: View.OnClickListener? = null
        ):
            Builder {
            arguments.putString(ARGUMENT_POSITIVE_BUTTON, contextLocal.getString(positiveButtonId))
            onPositiveClickListener = listener
            return this
        }

        fun setNegativeButton(
            @StringRes positiveButtonId: Int,
            listener: View.OnClickListener? = null
        ):
            Builder {
            arguments.putString(ARGUMENT_NEGATIVE_BUTTON, contextLocal.getString(positiveButtonId))
            onNegativeClickListener = listener
            return this
        }

        fun setCancelable(cancelable: Boolean): Builder {
            arguments.putBoolean(ARGUMENT_CANCELABLE, cancelable)
            return this
        }

        fun create(): CustomDialogFragment {
            val customDialog = CustomDialogFragment()
            customDialog.arguments = arguments
            customDialog.onPositiveClickListener = onPositiveClickListener
            customDialog.onNegativeClickListener = onNegativeClickListener
            customDialog.onCheckedChangedListener = onCheckedChangedListener
            return customDialog
        }
    }

    companion object {
        const val CUSTOM_DIALOG_TAG = "CUSTOM_DIALOG_TAG"
    }

    internal var onPositiveClickListener: View.OnClickListener? = null
    internal var onNegativeClickListener: View.OnClickListener? = null
    internal var onCheckedChangedListener: CompoundButton.OnCheckedChangeListener? = null
    private lateinit var binding: DialogCustomBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AppCompatDialog(this.requireContext(), R.style.CustomDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_custom,
            null,
            false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.apply {
            if (containsKey(ARGUMENT_ICON_COLOR)) {
                setIcon(getInt(ARGUMENT_ICON), getInt(ARGUMENT_ICON_COLOR))
            } else {
                setIcon(getInt(ARGUMENT_ICON))
            }
            setLabelText(binding.textViewTitle, ARGUMENT_TITLE, ARGUMENT_TITLE_PARAMS)
            setLabelText(binding.textViewMessage, ARGUMENT_MESSAGE, ARGUMENT_MESSAGE_PARAMS)
            setButtonText(binding.buttonNegative, ARGUMENT_NEGATIVE_BUTTON)
            setButtonText(binding.buttonPositive, ARGUMENT_POSITIVE_BUTTON)
            isCancelable = getBoolean(ARGUMENT_CANCELABLE)
        }

        binding.buttonNegative.setOnClickListener {
            dismiss()
            onNegativeClickListener?.onClick(it)
        }

        binding.buttonPositive.setOnClickListener {
            dismiss()
            onPositiveClickListener?.onClick(it)
        }
    }

    override fun onResume() {
        super.onResume()
        val width = ConstraintLayout.LayoutParams.WRAP_CONTENT
        val height = ConstraintLayout.LayoutParams.WRAP_CONTENT
        dialog?.window?.apply {
            setLayout(width, height)
            setBackgroundDrawable(
                InsetDrawable(
                    context.getDrawable(R.drawable.bg_custom_dialog),
                    16
                )
            )
        }
    }

    private fun setIcon(@DrawableRes drawableId: Int) {
        with(binding.imageViewDialogIcon) {
            if (drawableId == 0 || context == null) {
                visibility = GONE
            } else {
                setImageDrawable(context?.getDrawable(drawableId))
            }
        }
    }

    private fun setIcon(@DrawableRes drawableId: Int, @ColorRes color: Int) {
        with(binding.imageViewDialogIcon) {
            if (drawableId == NO_ID || context == null) {
                visibility = GONE
            } else {
                setImageDrawable(context?.getDrawable(drawableId))
            }
        }
    }

    private fun setLabelText(textView: TextView, key: String, keyParams: String) {
        val text = arguments?.getString(key)
        val params = arguments?.getStringArray(keyParams) as Array<*>
        if (text?.isNotEmpty() == true) {
            textView.text = text.format(*params)
        } else {
            textView.visibility = View.GONE
        }
    }

    private fun setButtonText(button: Button, key: String) {
        val text = arguments?.getString(key)
        if (text?.isNotEmpty() == true) {
            button.text = text
        } else {
            button.visibility = View.GONE
        }
    }

    override fun show(manager: FragmentManager, tag: String?) {
        val ft = manager.beginTransaction()
        ft.add(this, tag)
        ft.commitAllowingStateLoss()
    }
}
