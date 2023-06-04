package com.mazaady.portal.ui.adapter.screen2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mazaady.portal.data.model.screen2.MazadCurrentValueItem
import com.mazaady.portal.databinding.ItemCurrentValueBinding

/* The `CurrentValuesAdapter` class is a Kotlin implementation of a `ListAdapter` used to display a
list of `MazadCurrentValueItem` objects in a `RecyclerView`. */
class CurrentValuesAdapter :
    ListAdapter<MazadCurrentValueItem, CurrentValuesAdapter.CurrentValuesViewHolder>(
        CurrentValuesDiffCallback()
    ) {


    /* `private var originalDataList: List<MazadCurrentValueItem> = emptyList()` is declaring a private
    mutable variable `originalDataList` of type `List<MazadCurrentValueItem>` and initializing it
    with an empty list. This variable is used to keep a copy of the original data list passed to the
    adapter, which can be used later for comparison or filtering purposes. The `setOriginalDataList`
    function updates this variable with the new data list and submits the new list to the adapter
    for display. */
    private var originalDataList: List<MazadCurrentValueItem> = emptyList()

    /**
     * This function sets the original data list and submits it for display.
     *
     * @param dataList It is a nullable list of objects of type MazadCurrentValueItem.
     */
    fun setOriginalDataList(dataList: List<MazadCurrentValueItem>?) {
        if (dataList != null) {
            originalDataList = dataList
        }
        submitList(dataList)
    }

    /**
     * This function creates and returns a view holder for the current values item in a recycler view.
     *
     * @param parent The parent parameter is the ViewGroup into which the created view will be inserted
     * after it is bound to an adapter position. In other words, it is the layout that contains the
     * RecyclerView that this adapter is attached to.
     * @param viewType The viewType parameter is an integer value that represents the type of view that
     * will be created by the adapter. It is used when an adapter has multiple view types and needs to
     * create different views based on the type. In this case, since there is only one view type, the
     * value of viewType is not
     * @return The function `onCreateViewHolder` is returning an instance of `CurrentValuesViewHolder`.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrentValuesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCurrentValueBinding.inflate(inflater, parent, false)
        return CurrentValuesViewHolder(binding)
    }

    /**
     * This function binds data to a view holder in a RecyclerView.
     *
     * @param holder CurrentValuesViewHolder is the ViewHolder object that holds the views for each
     * item in the RecyclerView.
     * @param position The position parameter in the onBindViewHolder method represents the position of
     * the item in the data set that is currently being bound to the view holder. It is an integer
     * value that starts from 0 and goes up to the total number of items in the data set minus 1.
     */
    override fun onBindViewHolder(holder: CurrentValuesViewHolder, position: Int) {
        val dataCategories = getItem(position)
        holder.bind(dataCategories)
    }

    /* `private var onItemClick: ((MazadCurrentValueItem) -> Unit)? = null` is declaring a private
    mutable variable `onItemClick` of type `(MazadCurrentValueItem) -> Unit`, which is a lambda
    function that takes a single argument of type `MazadCurrentValueItem` and returns nothing
    (`Unit`). This variable is initialized with a null value. It is used to store a click listener
    function that will be called when an item in the RecyclerView is clicked. The
    `setOnItemClickListener` function sets the `onItemClick` variable to the provided lambda
    function, which will be called when an item is clicked. */
    private var onItemClick: ((MazadCurrentValueItem) -> Unit)? = null
    /**
     * The function sets an item click listener for a MazadCurrentValueItem object.
     *
     * @param listener The `listener` parameter is a lambda function that takes a single argument of
     * type `MazadCurrentValueItem` and returns nothing (`Unit`). This lambda function is used to
     * handle the click events on an item in a list or grid view. When an item is clicked, the
     * `onItemClick`
     */
    fun setOnItemClickListener(listener: (MazadCurrentValueItem) -> Unit) {
        onItemClick = listener
    }

    inner class CurrentValuesViewHolder(private val binding: ItemCurrentValueBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * The function "bind" sets the title and value of a MazadCurrentValueItem object to
         * corresponding TextViews in the layout.
         *
         * @param dataCategories MazadCurrentValueItem, which is a data class or a model class that
         * contains information about a current value item in an auction or bidding system. It likely
         * has properties such as title and value that are being set in the bind() function.
         */
        fun bind(dataCategories: MazadCurrentValueItem) {
            binding.tvValueTitle.text = dataCategories.title
            binding.tvValue.text = dataCategories.value
        }
    }
}

/* The class CurrentValuesDiffCallback is a DiffUtil ItemCallback used to compare and determine if two
MazadCurrentValueItem objects are the same or have the same content. */
class CurrentValuesDiffCallback : DiffUtil.ItemCallback<MazadCurrentValueItem>() {
    /**
     * This function checks if the title of two MazadCurrentValueItem objects are the same.
     *
     * @param oldItem MazadCurrentValueItem object representing the old item in the list being
     * compared.
     * @param newItem `newItem` is a parameter of type `MazadCurrentValueItem` which represents the new
     * item being compared in a `DiffUtil` callback method called `areItemsTheSame()`. It is used to
     * check if the new item is the same as the old item based on some criteria.
     * @return a boolean value that indicates whether the "title" property of the "oldItem" and
     * "newItem" parameters are the same.
     */
    override fun areItemsTheSame(
        oldItem: MazadCurrentValueItem,
        newItem: MazadCurrentValueItem
    ): Boolean {
        return oldItem.title == newItem.title
    }

    /**
     * The function checks if the contents of two MazadCurrentValueItem objects are the same.
     *
     * @param oldItem MazadCurrentValueItem object representing the old item in the list being compared
     * for changes.
     * @param newItem newItem is a parameter of type MazadCurrentValueItem which represents the new
     * item in a list that is being compared to the old item.
     * @return a Boolean value that indicates whether the contents of the old and new items are the
     * same. It is checking if the oldItem and newItem are equal using the == operator.
     */
    override fun areContentsTheSame(
        oldItem: MazadCurrentValueItem,
        newItem: MazadCurrentValueItem
    ): Boolean {
        return oldItem == newItem
    }
}
