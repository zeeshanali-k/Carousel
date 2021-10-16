package com.carousel.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.carousel.databinding.HeaderItemBinding
import com.carousel.databinding.ItemViewPagerBinding
import com.carousel.databinding.RvItemBinding
import com.carousel.models.RvItem

//TODO: change list of string to model class
class ListRvAdapter(val data: List<RvItem>) : RecyclerView.Adapter<ListRvAdapter.VPHolder>() {
    class VPHolder(val rvBinding: ViewDataBinding) : RecyclerView.ViewHolder(rvBinding.root)

    override fun getItemViewType(position: Int): Int {
        return data[position].itemType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VPHolder {
        if (viewType == 1) {
            return VPHolder(
                HeaderItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )
        } else {
            return VPHolder(
                RvItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: VPHolder, position: Int) {
        holder.rvBinding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return data.size
    }
}