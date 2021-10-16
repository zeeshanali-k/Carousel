package com.carousel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.carousel.databinding.ItemViewPagerBinding

//TODO: change list of string to model class
class ViewPagerAdapter(val imagesList: List<String>) :
    RecyclerView.Adapter<ViewPagerAdapter.VPHolder>() {
    class VPHolder(val rvBinding: ItemViewPagerBinding) : RecyclerView.ViewHolder(rvBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VPHolder {
        return VPHolder(
            ItemViewPagerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: VPHolder, position: Int) {
        holder.rvBinding.imageUrl = imagesList[position]
        holder.rvBinding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }
}