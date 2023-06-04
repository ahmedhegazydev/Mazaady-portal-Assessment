package com.mazaady.portal.ui.adapter.screen1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mazaady.portal.data.model.Children
import com.mazaady.portal.databinding.ItemSubCategoriesBinding
import java.util.*

class SubCategoriesAdapter :
    ListAdapter<Children, SubCategoriesAdapter.DataSubCategoriesViewHolder>(
        DataSubCategoriesDiffCallback()
    ),
    Filterable {


    private var originalDataList: List<Children> = emptyList()
    /**
     * This is a Kotlin function that returns a filter object to perform filtering on a list of data
     * based on a search query.
     *
     * @return A Filter object is being returned.
     */
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = mutableListOf<Children>()
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
                val filteredDataList = results?.values as? List<Children>
                submitList(filteredDataList)
            }
        }
    }

    /**
     * This function sets the original data list and submits it for display.
     *
     * @param dataList A nullable list of objects of type Children.
     */
    fun setOriginalDataList(dataList: List<Children>?) {
        if (dataList != null) {
            originalDataList = dataList
        }
        submitList(dataList)
    }

    /* `private var onItemClick: ((Children?) -> Unit)? = null` is declaring a private nullable
    variable `onItemClick` of type lambda function that takes a nullable `Children` object as input
    and returns nothing (`Unit`). This variable is used to set an `onItemClickListener` for a list
    of `Children` objects in the `SubCategoriesAdapter` class. When an item in the list is clicked,
    the `onItemClick` function is invoked with the clicked `Children` object as input. If the
    clicked item is already selected, the function is invoked with a null input. */
    private var onItemClick: ((Children?) -> Unit)? = null
    /**
     * This function sets an onItemClickListener for a list of Children objects.
     *
     * @param listener The parameter `listener` is a lambda function that takes a nullable `Children`
     * object as input and returns nothing (`Unit`). This function is used to set the `onItemClick`
     * property of an object, which is likely a listener for a click event on a list item. When the
     * item is clicked
     */
    fun setOnItemClickListener(listener: (Children?) -> Unit) {
        onItemClick = listener
    }

    /**
     * This function creates and returns a view holder for a subcategory item in a recycler view.
     *
     * @param parent The parent parameter is the ViewGroup into which the created view will be inserted
     * after it is bound to an adapter position. It represents the parent view that will contain the
     * inflated view.
     * @param viewType The viewType parameter in the onCreateViewHolder method is an integer value that
     * represents the type of view that will be created by the adapter. This parameter is useful when
     * an adapter has multiple view types, and it helps the adapter to determine which type of view to
     * create based on the position of the item in the data
     * @return An instance of the `DataSubCategoriesViewHolder` class is being returned.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataSubCategoriesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSubCategoriesBinding.inflate(inflater, parent, false)
        return DataSubCategoriesViewHolder(binding)
    }

    /**
     * This function binds data to a view holder in a RecyclerView adapter.
     *
     * @param holder DataSubCategoriesViewHolder is the ViewHolder object that holds the views for a
     * single item in the RecyclerView.
     * @param position The position parameter in the onBindViewHolder method is the position of the
     * item in the data set that the RecyclerView is currently binding. It is an integer value that
     * represents the position of the item in the adapter's data set. The position parameter is used to
     * retrieve the data for the item at that position from the
     */
    override fun onBindViewHolder(holder: DataSubCategoriesViewHolder, position: Int) {
        val dataCategories = getItem(position)
        holder.bind(dataCategories)
    }

    /* The DataSubCategoriesViewHolder class is responsible for binding data to a view and updating the
    UI based on the selected state of a category. */
    inner class DataSubCategoriesViewHolder(private val binding: ItemSubCategoriesBinding) :
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
                    } else {
                        onItemClick?.invoke(null)
                    }
                }
            }
        }

        /**
         * The function binds data to a view and sets the visibility of an image based on a boolean
         * value.
         *
         * @param dataCategories dataCategories is a variable of type Children that is being passed as
         * a parameter to the function. It contains information about a category, including its name
         * and whether it is selected or not. The function uses this information to update the UI by
         * setting the text of a TextView to the category name and showing or
         */
        fun bind(dataCategories: Children) {
            binding.catName.text = dataCategories.name
            if (dataCategories.isSelected == true) {
                binding.imageCheck.visibility = View.VISIBLE
            } else {
                binding.imageCheck.visibility = View.GONE
            }
        }
    }
}

/* This is a Kotlin class that implements the DiffUtil.ItemCallback interface to compare two lists of
Children objects based on their IDs and contents. */
class DataSubCategoriesDiffCallback : DiffUtil.ItemCallback<Children>() {
    override fun areItemsTheSame(oldItem: Children, newItem: Children): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Children, newItem: Children): Boolean {
        return oldItem == newItem
    }
}
