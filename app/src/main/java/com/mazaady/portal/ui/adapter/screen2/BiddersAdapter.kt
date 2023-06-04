package com.mazaady.portal.ui.adapter.screen2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mazaady.portal.data.model.screen2.BidderItem
import com.mazaady.portal.databinding.ListItemBiddersBinding

class BiddersAdapter :
    ListAdapter<BidderItem, BiddersAdapter.BiddingViewHolder>(
        BiddingDiffCallback()
    ) {


    /* `private var originalDataList: List<BidderItem> = emptyList()` is declaring a private mutable
    variable `originalDataList` of type `List<BidderItem>` and initializing it with an empty list.
    This variable is used to store the original data list passed to the adapter, which can be used
    later for comparison purposes. The `setOriginalDataList` function updates this variable with the
    new data list and submits the new list to the adapter. */
    private var originalDataList: List<BidderItem> = emptyList()


    /**
     * This function sets the original data list and submits it for display.
     *
     * @param dataList A nullable list of BidderItem objects that will be used to set the
     * originalDataList and submitList. If the dataList is not null, it will be assigned to the
     * originalDataList and submitted using the submitList method.
     */
    fun setOriginalDataList(dataList: List<BidderItem>?) {
        if (dataList != null) {
            originalDataList = dataList
        }
        submitList(dataList)
    }

    /**
     * This function creates and returns a BiddingViewHolder object using a layout inflater and a
     * binding object.
     *
     * @param parent The parent parameter is the ViewGroup into which the newly created View will be
     * added after it is bound to an adapter position. In other words, it is the parent layout that
     * contains the RecyclerView.
     * @param viewType The viewType parameter in the onCreateViewHolder method is an integer value that
     * represents the type of view that will be created by the adapter. This parameter is useful when
     * an adapter has multiple view types, and it helps the adapter to determine which type of view to
     * create based on the viewType value.
     * @return A `BiddingViewHolder` object is being returned.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BiddingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBiddersBinding.inflate(inflater, parent, false)
        return BiddingViewHolder(binding)
    }

    /**
     * This function binds data to a view holder in a RecyclerView.
     *
     * @param holder BiddingViewHolder is the type of the ViewHolder that holds the views for each item
     * in the RecyclerView.
     * @param position The position parameter in the onBindViewHolder method represents the position of
     * the item in the data set that is currently being bound to the view holder. It is an integer
     * value that starts from 0 and goes up to the total number of items in the data set minus 1.
     */
    override fun onBindViewHolder(holder: BiddingViewHolder, position: Int) {
        val dataCategories = getItem(position)
        holder.bind(dataCategories)
    }

    /* `private var onItemClick: ((BidderItem) -> Unit)? = null` is declaring a private mutable
    variable `onItemClick` of type `(BidderItem) -> Unit`, which is a lambda function that takes a
    single argument of type `BidderItem` and returns nothing (`Unit`). This variable is used to
    store an on-click listener for a bidder item. It is initialized with a null value, indicating
    that no listener has been set yet. The `setOnItemClickListener` function sets the `onItemClick`
    property to a given lambda function, which will be executed when a bidder item is clicked. */
    private var onItemClick: ((BidderItem) -> Unit)? = null
    /**
     * This function sets an on-click listener for a bidder item and executes a given function when the
     * item is clicked.
     *
     * @param listener The parameter `listener` is a lambda function that takes a single argument of
     * type `BidderItem` and returns nothing (`Unit`). This lambda function is used to set the
     * `onItemClick` property of an object, which is likely a custom view or adapter. The `onItemClick`
     * property is
     */
    fun setOnItemClickListener(listener: (BidderItem) -> Unit) {
        /**
         * The function binds data to views using Glide library in Kotlin.
         *
         * @param dataCategories dataCategories is an object of type BidderItem, which contains
         * information about a bidder such as their profile image, name, counter, and time. The
         * function bind() takes this object as a parameter and uses it to populate the UI elements in
         * the layout file associated with the binding object. Specifically,
         */
        onItemClick = listener
    }

    /* The BiddingViewHolder class binds data to views using Glide library in Kotlin. */
    inner class BiddingViewHolder(private val binding: ListItemBiddersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * The function binds data to views using Glide library in Kotlin.
         *
         * @param dataCategories dataCategories is an object of type BidderItem, which contains
         * information about a bidder such as their profile image, name, counter, and time. The
         * function bind() takes this object as a parameter and uses it to populate the UI elements in
         * the layout file associated with the binding object. Specifically,
         */
        fun bind(dataCategories: BidderItem) {
            Glide.with(binding.profileImage.context)
                .load(dataCategories.profileImage)
                .into(binding.profileImage)
            binding.personeName.text = dataCategories.name
            binding.counter.text = dataCategories.counter
            binding.time.text = dataCategories.time
        }
    }
}

/* This is a Kotlin class that implements the DiffUtil.ItemCallback interface to compare two BidderItem
objects and determine if they represent the same item and have the same content. */
class BiddingDiffCallback : DiffUtil.ItemCallback<BidderItem>() {
    /**
     * This function checks if two BidderItem objects have the same profile image, name, time, and
     * counter values.
     *
     * @param oldItem The old item is an instance of the BidderItem class representing the item before
     * any changes were made to it.
     * @param newItem `newItem` is a parameter of type `BidderItem` which represents the new item being
     * compared in the `areItemsTheSame` function. It contains properties such as `profileImage`,
     * `name`, `time`, and `counter`.
     * @return A boolean value indicating whether the old and new BidderItem objects have the same
     * values for their profileImage, name, time, and counter properties.
     */
    override fun areItemsTheSame(oldItem: BidderItem, newItem: BidderItem): Boolean {
        return oldItem.profileImage == newItem.profileImage && oldItem.name == newItem.name && oldItem.time == newItem.time && oldItem.counter == newItem.counter
    }

    /**
     * The function checks if the contents of two BidderItem objects are the same.
     *
     * @param oldItem `oldItem` is a variable of type `BidderItem` that represents the old item in a
     * list of items being compared for changes.
     * @param newItem `newItem` is a parameter of type `BidderItem` which represents the new item being
     * compared in a `DiffUtil.Callback` method. It is used to check if the contents of the new item
     * are the same as the old item.
     * @return a Boolean value, which indicates whether the contents of the old and new BidderItem
     * objects are the same. The function compares the two objects using the == operator, which checks
     * for equality between the objects. If the objects have the same values for all their properties,
     * the function returns true, indicating that the contents are the same. Otherwise, it returns
     * false.
     */
    override fun areContentsTheSame(oldItem: BidderItem, newItem: BidderItem): Boolean {
        return oldItem == newItem
    }
}
