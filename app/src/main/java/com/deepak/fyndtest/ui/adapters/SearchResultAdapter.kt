package com.deepak.fyndtest.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.UiThread
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.deepak.fyndtest.R
import com.deepak.fyndtest.databinding.SearchresultitemBinding
import com.deepak.fyndtest.ui.models.SearchResultsItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.searchresultitem.view.*

/**
 * Created by Deepak Kumar Verma on 10-01-2021.
 * Company Shantiinfotech.
 */
class SearchResultAdapter(
    private val mContext: Context,
    private var mSearchResultsItemList: MutableList<SearchResultsItem>
) : RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null
    var currentPos = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: SearchresultitemBinding = DataBindingUtil.inflate(inflater, R.layout.searchresultitem, parent, false)
        return ViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mSearchResultsItem = mSearchResultsItemList[position]
        holder.bind(mSearchResultsItem)

        val imageUrl = mSearchResultsItem.getFormattedPosterPath()
        Picasso.get().load(imageUrl).into(holder.itemView.iviamge)

        if (position == currentPos) {
            // selected true
        } else {
            // selected false
        }
    }

    override fun getItemCount(): Int {
        return mSearchResultsItemList.size
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
    }

    val searchResultsItem: List<SearchResultsItem>
        get() = mSearchResultsItemList

    fun addChatMassgeModel(mSearchResultsItem: SearchResultsItem) {
        try {
            mSearchResultsItemList.add(mSearchResultsItem)
            notifyItemInserted(itemCount)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setSearchResultsItemList(mSearchResultsItemList: MutableList<SearchResultsItem>) {
        this.mSearchResultsItemList = mSearchResultsItemList
        notifyDataSetChanged()
    }

    private fun dataSetChanged() {
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

    inner class ViewHolder(view: View, private val binding: SearchresultitemBinding) : RecyclerView.ViewHolder(view), View.OnClickListener {
        override fun onClick(v: View) {
            try {
                if (onItemClickListener != null) {
//                    currentPos = adapterPosition
//                    notifyItemRangeChanged(0, mSearchResultsItemList.size)
                    onItemClickListener!!.onItemClick(v, adapterPosition)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        @UiThread
        fun bind(mSearchResultsItem: SearchResultsItem?) {
            binding.searchResultsItem = mSearchResultsItem
        }

        init {
            view.setOnClickListener(this)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

    fun deleteitem(position: Int) {
        mSearchResultsItemList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, mSearchResultsItemList.size)
    }

    fun clearAdapter() {
        val size = mSearchResultsItemList.size
        mSearchResultsItemList.clear()
        notifyItemRangeRemoved(0, size)
    }
}