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


    /* `private var sheetProperties: MyBottomSheetDialogFragment? = null` is declaring a nullable
    variable `sheetProperties` of type `MyBottomSheetDialogFragment`. It is initialized to `null`.
    This variable is used to hold an instance of `MyBottomSheetDialogFragment`, which is a bottom
    sheet dialog fragment that displays a list of options for a particular item in a subcategory.
    The variable is used to check if the bottom sheet dialog fragment has already been created or
    not, and to show or hide the fragment accordingly. */
    private var sheetProperties: MyBottomSheetDialogFragment? = null
    /* The above code is declaring a private mutable variable named `originalDataList` of type
    `List<SubCategoryItem>` and initializing it with an empty list. The `List` is a collection
    interface in Kotlin that represents an ordered collection of elements. `SubCategoryItem` is a
    custom data class or type that is not defined in the given code snippet. */
    private var originalDataList: List<SubCategoryItem> = emptyList()
    /* The above code is declaring a private mutable list variable named `dataOptionsOld` of type
    `Options`. The `mutableListOf()` function is used to create an empty mutable list of `Options`
    type and assign it to the `dataOptionsOld` variable. */
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
     * This function sets the original data list, submits it, and notifies the adapter of the changes.
     *
     * @param dataList A list of SubCategoryItem objects that will be used to set the originalDataList
     * and submitList for a RecyclerView adapter. The notifyDataSetChanged() method is called to update
     * the UI with the new data.
     */
    fun setOriginalDataList(dataList: List<SubCategoryItem>) {
        originalDataList = dataList
        submitList(dataList)
        notifyDataSetChanged()
    }

    /* `private var onItemOptionsSelected: ((SubCategoryItem, Options, Int) -> Unit)? = null` is
    declaring a nullable variable `onItemOptionsSelected` of type function that takes in three
    parameters - a `SubCategoryItem` object, an `Options` object, and an integer - and returns
    `Unit` (i.e., no return value). This variable is used to set a listener for when an option is
    selected for a particular item in a subcategory, and the selected item, option, and index are
    passed to the listener. If no listener is set, the variable is initialized to `null`. */
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

    /* The `DataPropertiesViewHolder` class binds data to a view and sets up a click listener to
    display a bottom sheet dialog fragment with options. */
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
                val tilView: TextInputLayout = binding.til.root.findViewById(R.id.til)
                tilView.hint = "تحديد " + dataCategories.name
                if (dataCategories.selectedOption?.trim()?.isNotEmpty() == true) {
                    tilView.editText?.setText(
                        dataCategories.selectedOption
                    )
                }else{
                    tilView.editText?.setText("")
                }
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
                                        dataCategories.selectedOption = it.name
                                        tilView.editText?.setText(
                                            dataCategories.selectedOption
                                        )
                                        tilSepcifyHere.visibility =
                                            if (it.name == root.context.getString(R.string.other)) {
                                                View.VISIBLE
                                            } else {
                                                View.GONE
                                            }
                                        if (it.name != root.context.getString(R.string.other)) {
                                            onItemOptionsSelected?.invoke(
                                                dataCategories,
                                                it,
                                                position
                                            )
                                        }
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
                oldItem.value == newItem.value)
    }

    override fun areContentsTheSame(oldItem: SubCategoryItem, newItem: SubCategoryItem): Boolean {
        return oldItem == newItem
    }
}
