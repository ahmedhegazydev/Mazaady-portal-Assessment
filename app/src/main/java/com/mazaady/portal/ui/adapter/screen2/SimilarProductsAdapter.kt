package com.mazaady.portal.ui.adapter.screen2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mazaady.portal.data.model.screen2.SimilarProductItem
import com.mazaady.portal.databinding.ListItemSimilarProductsBinding

/* The `SimilarProductsAdapter` class is a RecyclerView adapter that binds data to views for displaying
a list of similar products and allows for item click listeners to be set. */
class SimilarProductsAdapter :
    ListAdapter<SimilarProductItem, SimilarProductsAdapter.SimilarViewHolder>(
        SimilarProductsDiffCallback()
    ) {


    /* `private var originalDataList: List<SimilarProductItem> = emptyList()` is declaring a private
    mutable variable `originalDataList` of type `List<SimilarProductItem>` and initializing it with
    an empty list. This variable is used to keep track of the original data list passed to the
    adapter, which can be used later for filtering or sorting purposes. The `setOriginalDataList`
    function updates this variable with the new data list and submits the new list to the adapter
    using the `submitList` function. */
    private var originalDataList: List<SimilarProductItem> = emptyList()


    /**
     * This function sets the original data list and submits it for display.
     *
     * @param dataList A list of SimilarProductItem objects that will be used to set the
     * originalDataList and submitted to the adapter.
     */
    fun setOriginalDataList(dataList: List<SimilarProductItem>?) {
        if (dataList != null) {
            originalDataList = dataList
        }
        submitList(dataList)
    }

    /**
     * This function creates and returns a view holder for a list item in a RecyclerView.
     *
     * @param parent The parent parameter is the ViewGroup into which the new View will be added after
     * it is bound to an adapter position. In other words, it is the layout that contains the
     * RecyclerView that this adapter is attached to.
     * @param viewType The viewType parameter in the onCreateViewHolder method is an integer value that
     * represents the type of view that will be created by the adapter. This parameter is useful when
     * an adapter has multiple types of views to display in a single RecyclerView. By using different
     * view types, the adapter can create different layouts for different items in
     * @return An instance of the class `SimilarViewHolder` is being returned.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemSimilarProductsBinding.inflate(inflater, parent, false)
        return SimilarViewHolder(binding)
    }

    /**
     * This function binds data to a view holder in a RecyclerView for a list of similar items.
     *
     * @param holder `holder` is an instance of the `SimilarViewHolder` class, which holds the views
     * for a single item in a RecyclerView. It is used to bind data to the views in the ViewHolder for
     * a specific position in the list.
     * @param position The position parameter in the onBindViewHolder method represents the position of
     * the item in the data set that is currently being bound to the view holder. It is an integer
     * value that starts from 0 and goes up to the total number of items in the data set minus 1.
     */
    override fun onBindViewHolder(holder: SimilarViewHolder, position: Int) {
        val dataCategories = getItem(position)
        holder.bind(dataCategories)
    }

    /* `private var onItemClick: ((SimilarProductItem) -> Unit)? = null` is declaring a private mutable
    variable `onItemClick` of type `(SimilarProductItem) -> Unit`, which is a function that takes a
    `SimilarProductItem` object as a parameter and returns `Unit`. This variable is used to store a
    listener function that will be called when an item in the RecyclerView is clicked. The
    `setOnItemClickListener` function sets the `onItemClick` variable to the listener function
    passed as a parameter. */
    private var onItemClick: ((SimilarProductItem) -> Unit)? = null
    /**
     * This function sets an item click listener for a list of similar products.
     *
     * @param listener The parameter `listener` is a lambda function that takes a single argument of
     * type `SimilarProductItem` and returns nothing (`Unit`). This lambda function is used to set the
     * `onItemClick` property of an object, which is likely a RecyclerView adapter or a custom view.
     * The `onItemClick`
     */
    fun setOnItemClickListener(listener: (SimilarProductItem) -> Unit) {
        onItemClick = listener
    }

    /* The SimilarViewHolder class is used to bind data to the views of a RecyclerView item for
    displaying similar products. */
    inner class SimilarViewHolder(private val binding: ListItemSimilarProductsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * The function binds data to views in a layout using Glide to load an image and sets text for
         * other views.
         *
         * @param dataCategories dataCategories is an object of type SimilarProductItem, which contains
         * information about a similar product. This function binds the data from the
         * SimilarProductItem object to the corresponding views in the layout using the Android Data
         * Binding library. The function uses Glide library to load the image from the URL specified in
         * the Similar
         */
        fun bind(dataCategories: SimilarProductItem) {
            Glide.with(binding.image.context)
                .load(dataCategories.image)
                .into(binding.image)
            binding.mazadName.text = dataCategories.title
            binding.tvStartMins.text = dataCategories.startAfter
            binding.price.text = dataCategories.startPrice
        }
    }
}

/* The `SimilarProductsDiffCallback` class is a Kotlin implementation of the `DiffUtil.ItemCallback`
interface used to compare and determine if two `SimilarProductItem` objects have the same content or
not. */
class SimilarProductsDiffCallback : DiffUtil.ItemCallback<SimilarProductItem>() {
    /**
     * This function checks if two SimilarProductItems have the same image, title, startAfter, and
     * startPrice.
     *
     * @param oldItem The old item is an instance of the SimilarProductItem class that represents the
     * item in the list before any changes were made to it.
     * @param newItem `newItem` is a parameter of type `SimilarProductItem` which represents the new
     * item being compared in the `areItemsTheSame` function. It contains properties such as `image`,
     * `title`, `startAfter`, and `startPrice`. These properties are used to determine if the new
     * @return a boolean value that indicates whether the old and new items are the same based on their
     * image, title, startAfter, and startPrice properties.
     */
    override fun areItemsTheSame(
        oldItem: SimilarProductItem,
        newItem: SimilarProductItem
    ): Boolean {
        return oldItem.image == newItem.image &&
                oldItem.title == newItem.title &&
                oldItem.startAfter == newItem.startAfter &&
                oldItem.startPrice == newItem.startPrice
    }

    /**
     * The function checks if the content of two SimilarProductItem objects are the same.
     *
     * @param oldItem The old item is an instance of the SimilarProductItem class that represents the
     * item before any changes were made to it. It is used as a reference to compare with the new item
     * to determine if any changes have been made.
     * @param newItem `newItem` is a parameter of type `SimilarProductItem` which represents the new
     * item being compared in the `areContentsTheSame` function. It is used to check if the content of
     * the new item is the same as the old item.
     * @return a Boolean value that indicates whether the contents of the old and new items are the
     * same. It is checking if the old item is equal to the new item using the `==` operator.
     */
    override fun areContentsTheSame(
        oldItem: SimilarProductItem,
        newItem: SimilarProductItem
    ): Boolean {
        return oldItem == newItem
    }
}
