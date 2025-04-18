package com.example.moviepage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class ImageSliderAdapter(
    private val context: Context, // we need context to access resources like animations
    private val imageList: List<Int> // list of images to display in the slider
) : RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder>() {

    // this method creates the view holder for each image in the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        // inflate the layout for each image
        val view = LayoutInflater.from(context).inflate(R.layout.image_slide, parent, false)
        return ImageViewHolder(view) // return the ViewHolder with the inflated view
    }

    // this method is called to set the data (image) to each view holder
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        // set the image for the current position
        holder.imageView.setImageResource(imageList[position])

        // load the zoom animation from the XML file
        val comboAnim = AnimationUtils.loadAnimation(context, R.anim.zoom)

        // start the animation on the image
        holder.imageView.startAnimation(comboAnim)
    }

    // return the number of items in the list (how many images we have)
    override fun getItemCount(): Int {
        return imageList.size
    }

    // view holder class to hold the reference to the image view
    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // image view where each image will be displayed
        val imageView: ImageView = itemView.findViewById(R.id.sliderImage)
    }
}
