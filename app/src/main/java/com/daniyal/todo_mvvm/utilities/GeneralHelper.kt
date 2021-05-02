package com.daniyal.todo_mvvm.utilities

import android.app.Activity
import android.content.Context
import android.text.format.DateUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.daniyal.todo_mvvm.R
import com.daniyal.todo_mvvm.ui.HomeActivity
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.json.JSONObject
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class GeneralHelper {

    companion object {

        fun parseFailureJson(jsonObject: JSONObject): String {
            val message = jsonObject.getString("message")
            val errors = jsonObject.getString("success")
            return message.trim()
        }


        fun loadImage(context: Context, url: String, view: ImageView) {
            Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(view)
        }

        fun dateParse(date: String): String? {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSS")
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"))
            try {
                val time: Long = sdf.parse(date).getTime()
                val now = System.currentTimeMillis()
                return DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS)
                    .toString()
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return null
        }


        fun isDateParse(date: String): Boolean {
            return try {
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSS")
                sdf.setTimeZone(TimeZone.getTimeZone("GMT"))

                val time: Long = sdf.parse(date).getTime()
                val now = System.currentTimeMillis()
                true
            } catch (e: ParseException) {
                false
            }
            return false
        }

        fun hideKeyboardFrom(context: Context, view: View) {
            val imm: InputMethodManager =
                context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
        }

        fun dateTimeFormatter(int: Int) : String{
            var str: String = ""
            str = if (int < 10) {
                "0$int"
            } else {
                int.toString()
            }

            return str
        }

        fun convertDateIntoTimeStamp(dateTime: String) : Long{
//            try {
//                val formatter: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm")
//                val date = formatter.parse(dateTime) as Date
//                val output = date.time / 1000L
//                val str = java.lang.Long.toString(output)
//               return  str.toLong() * 1000
//            } catch (e: Exception) {
//            }
//                return 0
            val formatter: DateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm")
            val date = formatter.parse(dateTime) as Date
            return date.getTime()
        }



    }
}