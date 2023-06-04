package com.mazaady.portal.ui.adapter.screen2

import android.R
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.VideoView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mazaady.portal.data.model.screen2.GalleryItem
import com.mazaady.portal.databinding.ListItemImageGalleryBinding


/* The ImageGalleryAdapter class is a Kotlin implementation of a RecyclerView adapter that displays a
list of GalleryItem objects and uses Glide to load images. */
class ImageGalleryAdapter :
    ListAdapter<GalleryItem, ImageGalleryAdapter.ImagesGalleryViewHolder>(
        GalleryDiffCallback()
    ) {

    private var originalDataList: List<GalleryItem> = emptyList()

    /**
     * This function sets the original data list and submits it for display.
     *
     * @param dataList A list of GalleryItem objects that will be used to set the originalDataList and
     * submitted to the adapter using the submitList() method. If the dataList is null, then the
     * originalDataList will not be updated and nothing will be submitted to the adapter.
     */
    fun setOriginalDataList(dataList: List<GalleryItem>?) {
        if (dataList != null) {
            originalDataList = dataList
        }
        submitList(dataList)
    }

    /**
     * This function creates and returns a view holder for an image gallery item using a binding
     * layout.
     *
     * @param parent The parent parameter is the ViewGroup into which the created view will be inserted
     * after it is bound to an adapter position. It represents the parent view that will contain the
     * inflated view.
     * @param viewType The viewType parameter in the onCreateViewHolder method is an integer value that
     * represents the type of view that will be created by the adapter. This parameter is useful when
     * an adapter has multiple types of views to display in a single RecyclerView. By using different
     * view types, the adapter can create different layouts for different items in
     * @return An instance of the `ImagesGalleryViewHolder` class is being returned.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesGalleryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemImageGalleryBinding.inflate(inflater, parent, false)
        return ImagesGalleryViewHolder(binding)
    }

    /**
     * This function binds data to a view holder in an images gallery.
     *
     * @param holder `holder` is an instance of the `ImagesGalleryViewHolder` class, which holds the
     * views for a single item in a RecyclerView. It is used to bind data to the views in the
     * ViewHolder for a specific position in the RecyclerView.
     * @param position The `position` parameter in the `onBindViewHolder` method represents the
     * position of the item in the data set that is currently being bound to the view holder. It is
     * used to retrieve the corresponding data item from the data set and pass it to the `bind` method
     * of the view holder to
     */
    override fun onBindViewHolder(holder: ImagesGalleryViewHolder, position: Int) {
        val dataCategories = getItem(position)
        holder.bind(dataCategories)
    }

    /* `private var onItemClick: ((GalleryItem) -> Unit)? = null` is declaring a private nullable
    variable `onItemClick` of type function that takes a `GalleryItem` parameter and returns `Unit`.
    It is used to set a click listener on the items in the RecyclerView, where the click listener
    will execute the function passed to it with the clicked `GalleryItem` as a parameter. The `?`
    after the type indicates that the variable can be null. */
    private var onItemClick: ((GalleryItem) -> Unit)? = null
    /**
     * This function sets an item click listener for a gallery item in Kotlin.
     *
     * @param listener The parameter `listener` is a lambda function that takes a single argument of
     * type `GalleryItem` and returns nothing (`Unit`). This lambda function is used to set the
     * `onItemClick` property of an object, which is likely a custom view or adapter. The `onItemClick`
     * property is a
     */
    fun setOnItemClickListener(listener: (GalleryItem) -> Unit) {
        onItemClick = listener
    }

    inner class ImagesGalleryViewHolder(private val binding: ListItemImageGalleryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * The function binds an image to a view using Glide library in Kotlin.
         *
         * @param dataCategories dataCategories is an object of type GalleryItem that contains
         * information about an image to be displayed in a gallery. The function bind() uses the Glide
         * library to load the image from the URL specified in the GalleryItem object and display it in
         * the ImageView specified in the binding object.
         */
        fun bind(dataCategories: GalleryItem) {
            Glide.with(binding.image.context)
                .load(dataCategories.image)
                .into(binding.image)

        }
    }
}

/* The GalleryDiffCallback class is used to compare two GalleryItem objects and determine if they are
the same or have the same content. */
class GalleryDiffCallback : DiffUtil.ItemCallback<GalleryItem>() {
    /**
     * This function checks if the image of two GalleryItems are the same.
     *
     * @param oldItem The old item is an instance of the GalleryItem class representing the item that
     * was previously displayed in the list.
     * @param newItem `newItem` is a parameter of type `GalleryItem` which represents the new item
     * being compared in a list of items. It is used in the `areItemsTheSame` function to check if the
     * `image` property of the `newItem` is the same as the `image`
     * @return a boolean value that indicates whether the image of the old item is the same as the
     * image of the new item.
     */
    override fun areItemsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
        return oldItem.image == newItem.image
    }

    /**
     * The function checks if the contents of two GalleryItem objects are the same.
     *
     * @param oldItem The old item is the item that was previously displayed in the list or adapter
     * before any changes were made to the data set. It is usually compared to the new item to
     * determine if any changes have been made.
     * @param newItem `newItem` is a parameter of type `GalleryItem` which represents the new item in
     * the list being compared for changes.
     * @return a Boolean value that indicates whether the contents of the old and new GalleryItem
     * objects are the same. It is checking if the two objects are equal using the == operator.
     */
    override fun areContentsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
        return oldItem == newItem
    }
}
