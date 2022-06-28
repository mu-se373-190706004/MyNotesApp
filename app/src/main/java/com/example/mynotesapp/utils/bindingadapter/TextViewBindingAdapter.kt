package com.example.mynotesapp.utils.bindingadapter

import android.graphics.Paint
import android.widget.TextView
import androidx.databinding.BindingAdapter

/**
 *Created by Mert Melih Aytemur on 21.06.2022.
 */

@BindingAdapter("setRentDeposit")
fun setRentDeposit(textView: TextView, deposit : String?){
    deposit?.let {
        textView.text = "Deposit: $deposit"
    }
}

@BindingAdapter("strikeThrough")
fun strikeThrough(textView: TextView,strikeTrough : Boolean){
    if(strikeTrough){
        textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }else{
        textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }
}
