package com.swu.coswumetic.adaptor

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.swu.coswumetic.DetailActivity
import com.swu.coswumetic.EverythingActivity
import com.swu.coswumetic.HomeActivity
import com.swu.coswumetic.R

class ExpiryListViewAdapter(val expiryList: ArrayList<HomeActivity.ExpiryList>) : RecyclerView.Adapter<ExpiryListViewAdapter.ExpiryListViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExpiryListViewAdapter.ExpiryListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list, parent, false)
        return ExpiryListViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ExpiryListViewAdapter.ExpiryListViewHolder,
        position: Int
    ) {
        val currentItem = expiryList.get(position)
        holder.recycler_expDate.text = ""
        holder.recycler_cosList.text = ""

        currentItem?.let {
            holder.recycler_expDate.text = it.recycler_expDate
            holder.recycler_cosList.text = it.recycler_cosName
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView?.context, DetailActivity::class.java)
            intent.putExtra("cosName", holder.recycler_cosList.text.toString())
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return expiryList.count()
    }

    inner class ExpiryListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recycler_expDate = itemView.findViewById<TextView>(R.id.recyclerExpDate)
        val recycler_cosList = itemView.findViewById<TextView>(R.id.recyclerCosName)

    }
}