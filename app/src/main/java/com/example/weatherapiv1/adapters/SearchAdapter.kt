package com.example.weatherapiv1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapiv1.data.db.entities.geocoding.GeoCodingData
import com.example.weatherapiv1.databinding.SearchResultItemBinding
import com.example.weatherapiv1.ui.SearchItemOnClickListener

class SearchAdapter: RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    inner class SearchViewHolder (val binding: SearchResultItemBinding): RecyclerView.ViewHolder(binding.root)

    val differ = AsyncListDiffer(this, getDifferCallBack())
    private var searchItemClickListener: SearchItemOnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            SearchResultItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val currentItem = differ.currentList[position]!!

        holder.binding.apply {
            tvCityNameSearchItem.text = currentItem.name
            tvCountryNameSearchItem.text = currentItem.country

            if (position == differ.currentList.size-1) {
                divider2.visibility = View.INVISIBLE
            } else {
                divider2.visibility = View.VISIBLE
            }

            root.setOnClickListener {
                searchItemClickListener?.onItemClick(currentItem)
            }
        }
    }

    private fun getDifferCallBack (): DiffUtil.ItemCallback<GeoCodingData> {
        val differCallBack = object : DiffUtil.ItemCallback<GeoCodingData>() {
            override fun areItemsTheSame(oldItem: GeoCodingData, newItem: GeoCodingData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: GeoCodingData,
                newItem: GeoCodingData
            ): Boolean {
                return oldItem == newItem
            }

        }

        return differCallBack
    }

    fun setListener (listener: SearchItemOnClickListener) {
        searchItemClickListener = listener
    }
}