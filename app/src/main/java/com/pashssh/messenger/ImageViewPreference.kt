package com.pashssh.messenger

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.preference.Preference
import androidx.preference.PreferenceViewHolder
import com.squareup.picasso.Picasso

class ImageViewPreference(context: Context, attributeSet: AttributeSet) :
    Preference(context, attributeSet) {

    private lateinit var imageView: ImageView
    lateinit var textView: TextView
    private var imageClickListener: View.OnClickListener = View.OnClickListener { }

    override fun onBindViewHolder(holder: PreferenceViewHolder?) {
        super.onBindViewHolder(holder)
        imageView = holder?.findViewById(R.id.pref_profile_image) as ImageView
        textView = holder.findViewById(R.id.pref_fullname) as TextView
        imageView.setOnClickListener(imageClickListener)
    }

    fun setImageClickListener(clickListener: View.OnClickListener) {
        imageClickListener = clickListener
    }

    fun setImage(path: String) {
        Picasso.get()
            .load(path)
            .into(imageView)
    }

    fun setText(str: String) {
        textView.text = str
    }



}