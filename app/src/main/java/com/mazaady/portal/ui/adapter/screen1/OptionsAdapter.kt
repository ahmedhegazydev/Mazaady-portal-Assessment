package com.mazaady.portal.ui.adapter.screen1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mazaady.portal.data.model.Options
import com.mazaady.portal.databinding.ItemOptionBinding
import java.util.*

/* The OptionsAdapter class is a Kotlin implementation of a RecyclerView adapter with filtering and
click listener functionality. */
class OptionsAdapter :
    ListAdapter<Options, OptionsAdapter.DataOptionsViewHolder>(
        DataOptionsDiffCallback()
    ), Filterable {

    /* `private var originalDataList: List<Options> = emptyList()` is declaring a private mutable
    variable `originalDataList` of type `List<Options>` and initializing it with an empty list. This
    variable is used to store the original unfiltered data list, which is used for filtering
    purposes in the `getFilter()` method of the `OptionsAdapter` class. When the adapter is
    initialized, the `originalDataList` is set to an empty list, and it is later updated with the
    actual data list using the `setOriginalDataList()` method. */
    private var originalDataList: List<Options> = emptyList()

    /**
     * This is a Kotlin function that returns a filter to perform filtering on a list of Options based
     * on a search query.
     *
     * @return A Filter object is being returned.
     */
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = mutableListOf<Options>()
                if (constraint.isNullOrBlank()) {
                    filteredList.addAll(originalDataList)
                } else {
                    val searchQuery = constraint.toString().trim().lowercase(Locale.getDefault())
                    for (item in originalDataList) {
                        if (item.name?.lowercase(Locale.getDefault())
                                ?.contains(searchQuery) == true
                        ) {
                            filteredList.add(item)
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                val filteredDataList = results?.values as? List<Options>
                submitList(filteredDataList)
            }
        }
    }

    /**
     * This function sets the original data list and submits it for display.
     *
     * @param dataList A nullable list of objects of type Options.
     */
    fun setOriginalDataList(dataList: List<Options>?) {
        if (dataList != null) {
            originalDataList = dataList
        }
        submitList(dataList)
    }

    /**
     * This function creates and returns a view holder for a data options item using a binding layout.
     *
     * @param parent The parent parameter is the ViewGroup into which the created view will be inserted
     * after it is bound to a data object. In other words, it is the parent layout that contains the
     * RecyclerView.
     * @param viewType The viewType parameter in the onCreateViewHolder method is an integer value that
     * represents the type of view that will be created by the adapter. This parameter is useful when
     * an adapter has multiple types of views to display in a single RecyclerView. By using different
     * view types, the adapter can create different layouts for different types of
     * @return An instance of the `DataOptionsViewHolder` class is being returned.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataOptionsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemOptionBinding.inflate(inflater, parent, false)
        return DataOptionsViewHolder(binding)
    }

    /**
     * This function binds data to a view holder in a RecyclerView.
     *
     * @param holder `holder` is an instance of the `DataOptionsViewHolder` class, which holds
     * references to the views that make up a single item in a RecyclerView. It is used to bind data to
     * the views in the ViewHolder for a specific position in the list.
     * @param position The position parameter in the onBindViewHolder method represents the position of
     * the item in the data set that is currently being bound to the view holder. It is an integer
     * value that starts from 0 and goes up to the total number of items in the data set minus 1.
     */
    override fun onBindViewHolder(holder: DataOptionsViewHolder, position: Int) {
        val dataCategories = getItem(position)
        holder.bind(dataCategories)
    }

    /* `private var onItemClick: ((Options?) -> Unit)? = null` is declaring a private mutable variable
    `onItemClick` of type `(Options?) -> Unit`, which is a lambda function that takes an optional
    parameter of type `Options` and returns `Unit`. It is initialized to `null`. This variable is
    used to set an item click listener for the adapter, which takes an optional parameter of type
    `Options` and returns `Unit`. The `onItemClick` property is set using the
    `setOnItemClickListener()` method of the adapter. When an item in the RecyclerView is clicked,
    the `onItemClick` property is invoked with the corresponding `Options` object (or `null` if the
    item is deselected). */
    private var onItemClick: ((Options?) -> Unit)? = null
    /**
     * This function sets an item click listener that takes an optional parameter of type Options and
     * returns Unit.
     *
     * @param listener The parameter `listener` is a lambda function that takes an argument of type
     * `Options` (or null) and returns nothing (`Unit`). This lambda function is used to set the
     * `onItemClick` property of an object, which is presumably an adapter or a view. The `onItemClick`
     * property
     */
    fun setOnItemClickListener(listener: (Options?) -> Unit) {
        onItemClick = listener
    }

    /* The DataOptionsViewHolder class is a Kotlin RecyclerView ViewHolder that binds data and handles
    click events for a list of options. */
    inner class DataOptionsViewHolder(private val binding: ItemOptionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val dataCategories = getItem(position)
                    if (dataCategories.isSelected == false) {
                        onItemClick?.invoke(dataCategories)
                        originalDataList.forEach {
                            it.isSelected = false
                        }
                        dataCategories.isSelected = true
                        notifyItemChanged(position)
                    }else{
                        onItemClick?.invoke(null)
                    }
                }
            }
        }

        /**
         * The function binds data to a view and updates the visibility of an image based on a boolean
         * value.
         *
         * @param dataCategories Options, which is likely a data class or a model class that contains
         * information about a category, such as its name and whether it is currently selected or not.
         * The function bind() takes in an instance of this class and sets the text of a TextView to
         * the category name, and shows or hides an
         */
        fun bind(dataCategories: Options) {
            binding.catName.text = dataCategories.name
            if (dataCategories.isSelected == true) {
                binding.imageCheck.visibility = View.VISIBLE
            } else {
                binding.imageCheck.visibility = View.GONE
            }
        }
    }
}

/* The class `DataOptionsDiffCallback` is a Kotlin implementation of `DiffUtil.ItemCallback` used for
comparing and updating `Options` objects in a RecyclerView. */
class DataOptionsDiffCallback : DiffUtil.ItemCallback<Options>() {
    override fun areItemsTheSame(oldItem: Options, newItem: Options): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Options, newItem: Options): Boolean {
        return oldItem == newItem
    }
}
