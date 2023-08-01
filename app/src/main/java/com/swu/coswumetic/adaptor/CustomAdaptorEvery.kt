package com.swu.coswumetic.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.swu.coswumetic.EverythingActivity
import com.swu.coswumetic.R

class EveryListViewAdapter(val everyList: ArrayList<EverythingActivity.EveryList>) : RecyclerView.Adapter<EveryListViewAdapter.EveryListViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EveryListViewAdapter.EveryListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.everything, parent, false)
        return EveryListViewHolder(view)
    }

    override fun onBindViewHolder(holder: EveryListViewHolder, position: Int) {
        val currentItem = everyList.get(position)
        holder.recycler_expDate.text = ""
        holder.recycler_cosList.text = ""

        currentItem?.let {
            holder.recycler_expDate.text = it.recyclerExpDate
            holder.recycler_cosList.text = it.recyclerCosName

        }
    }

    override fun getItemCount(): Int {
        return everyList.count()
    }

    inner class EveryListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recycler_expDate = itemView.findViewById<TextView>(R.id.recycler_expDate)
        val recycler_cosList = itemView.findViewById<TextView>(R.id.recycler_cosName)
    }
}