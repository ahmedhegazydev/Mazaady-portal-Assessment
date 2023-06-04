package com.mazaady.portal.ui.adapter.screen1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.mazaady.portal.R
import com.mazaady.portal.data.model.Options
import com.mazaady.portal.data.model.screen1.SubCategoryItem
import com.mazaady.portal.databinding.LayoutOutlinedEditTextContainerBinding
import com.mazaady.portal.ui.sheet.MyBottomSheetDialogFragment
import com.mazaady.portal.ui.sheet.MyBottomSheetDialogFragment.Companion.ARG_LIST_KEY_TITLE
import java.util.*

/* This is a Kotlin class called `PropertiesAdapter` that extends `ListAdapter` and implements
`Filterable`. It is used to display a list of `SubCategoryItem` objects in a `RecyclerView`. */
class PropertiesAdapter(
    private val childFragmentManager: FragmentManager
) :
    ListAdapter<SubCategoryItem, PropertiesAdapter.DataPropertiesViewHolder>(PropertiesDiffCallback()),
    Filterable {


    private var sheetProperties: MyBottomSheetDialogFragment? = null
    private var originalDataList: List<SubCategoryItem> = emptyList()
    private var dataOptionsOld = mutableListOf<Options>()
    /**
     * This is a Kotlin function that returns a filter to perform filtering on a list of
     * SubCategoryItem objects based on a search query.
     *
     * @return A Filter object is being returned.
     */
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = mutableListOf<SubCategoryItem>()
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
                val filteredDataList = results?.values as? List<SubCategoryItem>
                submitList(filteredDataList)
            }
        }
    }

    /**
     * This function sets the original data list and submits it to be displayed.
     *
     * @param dataList The parameter `dataList` is a list of `SubCategoryItem` objects that is passed
     * as an argument to the function `setOriginalDataList()`. This function sets the
     * `originalDataList` variable to the same list and submits it to the adapter using the
     * `submitList()` method.
     */
    fun setOriginalDataList(dataList: List<SubCategoryItem>) {
        originalDataList = dataList
        submitList(dataList)
    }

    private var onItemOptionsSelected: ((SubCategoryItem, Options, Int) -> Unit)? = null
    /**
     * This function sets a listener for when an item option is selected and passes the selected item,
     * option, and index to the listener.
     *
     * @param listener A function that takes in three parameters - a SubCategoryItem object, an Options
     * object, and an integer - and returns Unit (i.e., no return value). This function is used to
     * handle the event when an option is selected for a particular item in a subcategory.
     */
    fun setOnItemOptionSelectListener(listener: (SubCategoryItem, Options, Int) -> Unit) {
        onItemOptionsSelected = listener
    }

    /**
     * This function creates and returns a view holder for a data properties view using a layout
     * inflater and binding.
     *
     * @param parent The parent parameter is the ViewGroup into which the created view will be inserted
     * after it is bound to an adapter position. It represents the parent view that will contain the
     * inflated layout.
     * @param viewType The viewType parameter in the onCreateViewHolder method is an integer value that
     * represents the type of view that will be created by the adapter. This parameter is useful when
     * an adapter has multiple types of views to display in a single RecyclerView. By using different
     * view types, the adapter can create different layouts for different types of
     * @return An instance of the `DataPropertiesViewHolder` class is being returned.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataPropertiesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutOutlinedEditTextContainerBinding.inflate(inflater, parent, false)
        return DataPropertiesViewHolder(binding)
    }

    /**
     * This function binds data to a view holder in a RecyclerView.
     *
     * @param holder `holder` is an instance of the `DataPropertiesViewHolder` class, which holds
     * references to the views that make up a single item in a RecyclerView. It is used to bind data to
     * the views in the ViewHolder for a specific position in the list.
     * @param position The `position` parameter in the `onBindViewHolder` method is the position of the
     * item in the data set that is currently being bound to the view holder. It is used to retrieve
     * the corresponding data object from the data set and pass it to the `bind` method of the view
     * holder to
     */
    override fun onBindViewHolder(holder: DataPropertiesViewHolder, position: Int) {
        val dataCategories = getItem(position)
        holder.bind(dataCategories)
    }

    inner class DataPropertiesViewHolder(private val binding: LayoutOutlinedEditTextContainerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        /**
         * The function binds data to a view and sets up a click listener to display a bottom sheet
         * dialog fragment with options.
         *
         * @param dataCategories The parameter `dataCategories` is of type `SubCategoryItem`, which is
         * an object containing information about a subcategory. It is used to set the hint for a
         * `TextInputLayout` and to display a list of options in a bottom sheet dialog.
         */
        fun bind(dataCategories: SubCategoryItem) {
            binding.apply {
                til.root.findViewById<TextInputLayout>(R.id.til).hint = dataCategories.name
                viewClickable.root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        if (sheetProperties == null || dataOptionsOld != dataCategories.options) {
                            sheetProperties =
                                MyBottomSheetDialogFragment.newInstance(
                                    MyBottomSheetDialogFragment.ARG_LIST_KEY_PROPERTIES,
                                    dataCategories.options
                                )
                            sheetProperties?.setCallback {
                                when (it) {
                                    is Options -> {
                                        til.root.findViewById<TextInputLayout>(R.id.til).editText?.setText(
                                            it.name ?: ""
                                        )
                                        if (it.name == "اخر") {
                                            tilSepcifyHere.visibility = View.VISIBLE
                                        } else {
                                            tilSepcifyHere.visibility = View.GONE
                                        }
                                        onItemOptionsSelected?.invoke(dataCategories, it, position)
                                    }
                                }
                            }
                        }
                        dataOptionsOld = dataCategories.options
                        val args = Bundle().apply {
                            putString(ARG_LIST_KEY_TITLE, "Select ${dataCategories.name}")
                        }
                        sheetProperties?.arguments?.apply {
                            putAll(args)
                        }
                        if (sheetProperties?.isBottomSheetDialogVisible?.not() == true) {
                            sheetProperties?.show(
                                childFragmentManager,
                                "MyBottomSheetDialogFragment"
                            )
                        }
                    }
                }

            }
        }
    }
}

/* `PropertiesDiffCallback` is a class that extends `DiffUtil.ItemCallback` and is used to determine if
two `SubCategoryItem` objects are the same or have the same contents. It has two methods:
`areItemsTheSame` and `areContentsTheSame`. */
class PropertiesDiffCallback : DiffUtil.ItemCallback<SubCategoryItem>() {
    override fun areItemsTheSame(oldItem: SubCategoryItem, newItem: SubCategoryItem): Boolean {
        return (oldItem.id == newItem.id &&
                oldItem.name == newItem.name &&
                oldItem.description == newItem.description &&
                oldItem.options == newItem.options &&
                oldItem.slug == newItem.slug &&
                oldItem.parent == newItem.parent &&
                oldItem.type == newItem.type &&
                oldItem.otherValue == newItem.otherValue &&
                oldItem.list == newItem.list &&
                oldItem.value == newItem.value

                )
    }

    override fun areContentsTheSame(oldItem: SubCategoryItem, newItem: SubCategoryItem): Boolean {
        return oldItem == newItem
    }
}
