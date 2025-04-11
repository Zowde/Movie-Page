package com.example.moviepage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class ImageSliderAdapter(
    private val context: Context,
    private val imageList: List<Int>
) : RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder>() {

    //creating the visual box that’ll hold each image.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        // Inflate the layout for the individual image slide
        val view = LayoutInflater.from(context).inflate(R.layout.image_slide, parent, false)
        return ImageViewHolder(view)
    }
    //take the correct image from the list and show it in the box
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.imageView.setImageResource(imageList[position])
        val animation = AnimationUtils.loadAnimation(context, R.anim.fade)
        holder.imageView.startAnimation(animation)
        val slideInRight = AnimationUtils.loadAnimation(context, R.anim.slide)
        holder.imageView.startAnimation(slideInRight)
        val zoomIn = AnimationUtils.loadAnimation(context, R.anim.zoom)
        holder.imageView.startAnimation(zoomIn)
        val bounce = AnimationUtils.loadAnimation(context, R.anim.bounce)
        holder.imageView.startAnimation(bounce)

    }

    // return the total number of images in the list
    override fun getItemCount(): Int {
        return imageList.size
    }

    // viewHolder class to hold references to views
    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.sliderImage)
    }
}