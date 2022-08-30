package com.tongue.dandelioncourier.ui.views

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.tongue.dandelioncourier.R

class TextFieldView(context: Context, attributeSet: AttributeSet)
    : ConstraintLayout(context,attributeSet){

    private var imageView: ImageView
    var textInputEditText: TextInputEditText
    private var textInputLayout: TextInputLayout
    private var validator: ((String) -> Pair<Boolean, String>)? = null

    init {
        inflate(context, R.layout.custom_textfield_view, this)
        imageView = findViewById(R.id.textField_imageView)
        textInputEditText = findViewById(R.id.textField_editText)
        textInputLayout = findViewById(R.id.textField_inputLayout)
        textInputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                validate()
            }

            override fun afterTextChanged(p0: Editable?) {}

        })
    }

    fun getText(): String{
        return textInputEditText.text.toString()
    }

    fun setText(text: String){
        textInputEditText.setText(text)
    }

    fun setInputType(inputType: Int){
        textInputEditText.inputType = inputType
    }

    fun setUpAsPasswordInput(){
        textInputLayout.isPasswordVisibilityToggleEnabled = true
    }

    fun setUpResources(image: Int, hint: Int){
        imageView.setImageResource(image)
        textInputLayout.setHint(hint)
    }

    fun setUpResources(image: Int, hint: Int, inputType: Int){
        imageView.setImageResource(image)
        textInputLayout.setHint(hint)
        textInputEditText.inputType = inputType
    }

    fun validate(): Boolean{
        textInputLayout.isErrorEnabled=false
        if (validator==null)
            return true
        var content = textInputEditText.text
        var result = validator!!.invoke(content.toString())
        return if (result.first)
            true
        else{
            textInputLayout.error = result.second
            false
        }
    }

    fun setErrorMessage(msg: String){
        textInputLayout.error = msg
    }

    fun setValidationRoutine(v:(content:String)-> (Pair<Boolean,String>)){
        validator = v
    }

}