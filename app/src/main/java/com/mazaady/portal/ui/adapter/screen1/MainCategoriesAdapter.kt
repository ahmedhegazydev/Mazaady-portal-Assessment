package com.mazaady.portal.ui.adapter.screen1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mazaady.portal.data.model.screen1.MainCategoryItem
import com.mazaady.portal.databinding.ItemMainCategoriesBinding
import java.util.*

/* The MainCategoriesAdapter class is a Kotlin implementation of a RecyclerView adapter with filterable
functionality and click listeners for a list of MainCategoryItem objects. */
class MainCategoriesAdapter :
    ListAdapter<MainCategoryItem, MainCategoriesAdapter.DataCategoriesViewHolder>(
        DataCategoriesDiffCallback()
    ), Filterable {


    /* `private var originalDataList: List<MainCategoryItem> = emptyList()` is declaring a private
    mutable variable `originalDataList` of type `List<MainCategoryItem>` and initializing it with an
    empty list. This variable is used to store the original unfiltered list of `MainCategoryItem`
    objects, which is used by the `Filter` implementation to filter the list based on user input.
    The `setOriginalDataList` function is used to set the original data list and submit it to the
    adapter. */
    private var originalDataList: List<MainCategoryItem> = emptyList()

    /**
     * This function returns a filter that performs filtering on a list of MainCategoryItems based on a
     * search query.
     *
     * @return A Filter object is being returned.
     */
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = mutableListOf<MainCategoryItem>()
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
                val filteredDataList = results?.values as? List<MainCategoryItem>
                submitList(filteredDataList)
            }
        }
    }

    /**
     * This function sets the original data list and submits it for display.
     *
     * @param dataList A nullable List of MainCategoryItem objects that contains the original data to
     * be displayed in a RecyclerView.
     */
    fun setOriginalDataList(dataList: List<MainCategoryItem>?) {
        if (dataList != null) {
            originalDataList = dataList
        }
        submitList(dataList)
    }

    /**
     * This function creates and returns a view holder for a specific type of data category.
     *
     * @param parent The parent parameter is the ViewGroup into which the created view will be inserted
     * after it is bound to an adapter position. It represents the parent view of the RecyclerView.
     * @param viewType The viewType parameter in the onCreateViewHolder method is an integer value that
     * represents the type of view that will be created by the adapter. This parameter is useful when
     * an adapter has multiple types of views to display in a single RecyclerView. By using different
     * view types, the adapter can create different layouts for different types of
     * @return An instance of the `DataCategoriesViewHolder` class is being returned.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataCategoriesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMainCategoriesBinding.inflate(inflater, parent, false)
        return DataCategoriesViewHolder(binding)
    }

    /**
     * This function binds data to a view holder in a RecyclerView adapter.
     *
     * @param holder `holder` is an instance of the `DataCategoriesViewHolder` class, which holds the
     * views for a single item in a RecyclerView. It is used to bind data to the views in the
     * ViewHolder for a specific position in the list.
     * @param position The position parameter in the onBindViewHolder method is the position of the
     * item in the data set that the RecyclerView is currently binding to the ViewHolder. It is an
     * integer value that starts from 0 and goes up to the total number of items in the data set minus
     * 1.
     */
    override fun onBindViewHolder(holder: DataCategoriesViewHolder, position: Int) {
        val dataCategories = getItem(position)
        holder.bind(dataCategories)
    }

    /* `private var onItemClick: ((MainCategoryItem?) -> Unit)? = null` is declaring a private mutable
    variable `onItemClick` of type `(MainCategoryItem?) -> Unit`, which is a lambda function that
    takes a single argument of type `MainCategoryItem?` (an optional `MainCategoryItem` object) and
    returns nothing (`Unit`). This variable is initialized with a null value. It is used to set an
    item click listener for a main category item in Kotlin. The `setOnItemClickListener` function is
    used to set the `onItemClick` variable to a lambda function that will handle item click events
    in a list or grid view. When an item is clicked, the lambda function is invoked with the clicked
    `MainCategoryItem` object as its argument. If no item is clicked, the lambda function is invoked
    with a null argument. */
    private var onItemClick: ((MainCategoryItem?) -> Unit)? = null
    /**
     * This function sets an item click listener for a main category item in Kotlin.
     *
     * @param listener The `listener` parameter is a lambda function that takes a single argument of
     * type `MainCategoryItem?` (an optional `MainCategoryItem` object) and returns nothing (`Unit`).
     * This lambda function is used to handle item click events in a list or grid view. When an item is
     * clicked
     */
    fun setOnItemClickListener(listener: (MainCategoryItem?) -> Unit) {
        onItemClick = listener
    }

    /* The class is a ViewHolder for a RecyclerView that handles the binding and click events for a
    list of main categories. */
    inner class DataCategoriesViewHolder(private val binding: ItemMainCategoriesBinding) :
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
         * The function sets the name and visibility of a category item based on its selected status.
         *
         * @param dataCategories The parameter `dataCategories` is of type `MainCategoryItem`, which is
         * likely a data class or a model class that represents a category item in an app or system.
         * The function `bind` takes this parameter and sets the name of the category item to a
         * TextView and shows or hides a check
         */
        fun bind(dataCategories: MainCategoryItem) {
            binding.catName.text = dataCategories.name
            if (dataCategories.isSelected == true) {
                binding.imageCheck.visibility = View.VISIBLE
            } else {
                binding.imageCheck.visibility = View.GONE
            }

        }
    }
}

/* The class `DataCategoriesDiffCallback` is a Kotlin implementation of `DiffUtil.ItemCallback` used
for comparing and updating lists of `MainCategoryItem` objects. */
class DataCategoriesDiffCallback : DiffUtil.ItemCallback<MainCategoryItem>() {
    override fun areItemsTheSame(oldItem: MainCategoryItem, newItem: MainCategoryItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MainCategoryItem, newItem: MainCategoryItem): Boolean {
        return oldItem == newItem
    }
}
