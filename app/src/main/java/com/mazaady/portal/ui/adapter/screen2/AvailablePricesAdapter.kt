package com.mazaady.portal.ui.adapter.screen2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mazaady.portal.data.model.screen2.AvailablePricesItem
import com.mazaady.portal.databinding.ListItemAvailablePricesBinding

/* The AvailablePricesAdapter class is a RecyclerView adapter that binds data to a view holder and
handles click events on the items in the RecyclerView. */
class AvailablePricesAdapter :
    ListAdapter<AvailablePricesItem, AvailablePricesAdapter.AvailablePricesViewHolder>(
        AvailablePricesDiffCallback()
    ) {


    /* `private var originalDataList: List<AvailablePricesItem> = emptyList()` is declaring a private
    mutable variable `originalDataList` of type `List<AvailablePricesItem>` and initializing it with
    an empty list. This variable is used to keep track of the original data list passed to the
    adapter, which can be used later for filtering or sorting purposes. The `setOriginalDataList`
    function updates this variable with the new data list and submits the new list to the adapter
    using the `submitList` function. */
    private var originalDataList: List<AvailablePricesItem> = emptyList()


    /**
     * This function sets the original data list and submits it for display.
     *
     * @param dataList A nullable list of AvailablePricesItem objects that will be used to set the
     * originalDataList and submitList. If the dataList is not null, it will be assigned to the
     * originalDataList and submitted using the submitList method.
     */
    fun setOriginalDataList(dataList: List<AvailablePricesItem>?) {
        if (dataList != null) {
            originalDataList = dataList
        }
        submitList(dataList)
    }

    /**
     * This function creates and returns a new instance of the AvailablePricesViewHolder class with the
     * specified layout and binding.
     *
     * @param parent The parent parameter is the ViewGroup into which the newly created View will be
     * added after it is bound to an adapter position. It represents the parent view of the
     * RecyclerView.
     * @param viewType The viewType parameter in the onCreateViewHolder method is an integer value that
     * represents the type of view that will be created by the adapter. This parameter is useful when
     * an adapter has multiple types of views to display in a single RecyclerView. By using different
     * view types, the adapter can create different layouts for different items in
     * @return An instance of the `AvailablePricesViewHolder` class is being returned.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvailablePricesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemAvailablePricesBinding.inflate(inflater, parent, false)
        return AvailablePricesViewHolder(binding)
    }

    /**
     * This function binds data to a view holder in a RecyclerView.
     *
     * @param holder AvailablePricesViewHolder is the ViewHolder object that holds the views for each
     * item in the RecyclerView.
     * @param position The `position` parameter in the `onBindViewHolder` method refers to the position
     * of the item in the data set that is currently being bound to the view holder. It is an integer
     * value that starts from 0 and goes up to the total number of items in the data set minus 1
     */
    override fun onBindViewHolder(holder: AvailablePricesViewHolder, position: Int) {
        val dataCategories = getItem(position)
        holder.bind(dataCategories)
    }

    /* `private var onItemClick: ((AvailablePricesItem) -> Unit)? = null` is declaring a private
    mutable variable `onItemClick` of type `(AvailablePricesItem) -> Unit`, which is a lambda
    function that takes an argument of type `AvailablePricesItem` and returns nothing (`Unit`). The
    `?` after the type indicates that the variable can be null. It is initialized to null. This
    variable is used to set an item click listener that takes an `AvailablePricesItem` object as a
    parameter. The lambda function is used to set the `onItemClick` property of an object, which is
    likely a RecyclerView adapter or a custom view. The `onItemClick` property is used to handle
    click events on the items in the RecyclerView. */
    private var onItemClick: ((AvailablePricesItem) -> Unit)? = null
    /**
     * This function sets an item click listener that takes an AvailablePricesItem object as a
     * parameter.
     *
     * @param listener The parameter `listener` is a lambda function that takes an argument of type
     * `AvailablePricesItem` and returns nothing (`Unit`). This lambda function is used to set the
     * `onItemClick` property of an object, which is likely a RecyclerView adapter or a custom view.
     * The `onItemClick` property
     */
    fun setOnItemClickListener(listener: (AvailablePricesItem) -> Unit) {
        onItemClick = listener
    }

    /* The AvailablePricesViewHolder class binds the value of a data category to a TextView in a
    layout. */
    inner class AvailablePricesViewHolder(private val binding: ListItemAvailablePricesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * The function sets the value of a TextView in a layout with the value of a data category.
         *
         * @param dataCategories AvailablePricesItem object, which contains information about a
         * specific price category.
         */
        fun bind(dataCategories: AvailablePricesItem) {
            binding.tvPrice.text = dataCategories.value
        }
    }
}

/* The class `AvailablePricesDiffCallback` is a Kotlin implementation of the `DiffUtil.ItemCallback`
interface used for comparing and updating lists of `AvailablePricesItem` objects. */
class AvailablePricesDiffCallback : DiffUtil.ItemCallback<AvailablePricesItem>() {
    override fun areItemsTheSame(
        oldItem: AvailablePricesItem,
        newItem: AvailablePricesItem
    ): Boolean {
        return oldItem.value == newItem.value
    }

    override fun areContentsTheSame(
        oldItem: AvailablePricesItem,
        newItem: AvailablePricesItem
    ): Boolean {
        return oldItem == newItem
    }
}
