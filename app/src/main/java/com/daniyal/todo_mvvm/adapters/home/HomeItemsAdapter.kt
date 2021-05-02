package com.daniyal.todo_mvvm.adapters.home

import android.content.Context
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.daniyal.todo_mvvm.R


class HomeItemsAdapter constructor(val items: List<ListItem> , val context: Context ,
                                   val clickListener: (ListItem, Int) -> Unit): RecyclerView.Adapter<HomeItemsAdapter.ViewHolder>() {


    private class HeaderViewHolder constructor(itemView: View) : ViewHolder(itemView) {
        var txt_header: TextView

        init {
            txt_header = itemView.findViewById<View>(R.id.txt_header) as TextView
        }
    }

    private class EventViewHolder internal constructor(itemView: View) :
        ViewHolder(itemView) {
        var txt_title: TextView
        var txt_desc: TextView
        var txt_time: TextView
        var txt_midday: TextView
        var IV_Priority: ImageView
        var CV_EventItem: CardView

        init {
            txt_title = itemView.findViewById<View>(R.id.TV_Title) as TextView
            txt_desc = itemView.findViewById<View>(R.id.TV_Desc) as TextView
            txt_time = itemView.findViewById<View>(R.id.TV_Time) as TextView
            txt_midday = itemView.findViewById<View>(R.id.TV_Midday) as TextView
            IV_Priority =
                itemView.findViewById<View>(R.id.IV_Priority) as ImageView
            CV_EventItem = itemView.findViewById<View>(R.id.CV_EventItem) as CardView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ListItem.TYPE_HEADER -> {
                val itemView = inflater.inflate(R.layout.view_list_item_header, parent, false)
                HeaderViewHolder(itemView)
            }
            ListItem.TYPE_EVENT -> {
                val itemView = inflater.inflate(R.layout.todo_item_layout, parent, false)
                EventViewHolder(itemView)
            }
            else -> throw IllegalStateException("unsupported item type")
        }
    }


    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].type
    }

    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        when (viewType) {
            ListItem.TYPE_HEADER -> {
                val header = items[position] as HeaderItem
                val holder = holder as HeaderViewHolder
            }
            ListItem.TYPE_EVENT -> {
                val event = items[position] as EventItem
                val holder = holder as EventViewHolder
                holder.txt_title.text = event.event
                holder.txt_desc.text = event.desc
                holder.txt_time.text = event.hour.split(" ").toTypedArray()[0]
                holder.txt_midday.text = event.hour.split(" ").toTypedArray()[1]
                holder.IV_Priority.setColorFilter(
                    ContextCompat.getColor(
                        context!!,
                        R.color.background
                    ), PorterDuff.Mode.SRC_IN
                )

                holder.CV_EventItem.setOnClickListener { clickListener(items[position], position) }
            }
            else -> throw java.lang.IllegalStateException("unsupported item type")

        }


    }



}