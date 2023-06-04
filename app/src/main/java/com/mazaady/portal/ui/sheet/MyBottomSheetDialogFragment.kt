package com.mazaady.portal.ui.sheet;

import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mazaady.portal.databinding.FragmentMyBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mazaady.portal.R
import com.mazaady.portal.data.model.Children
import com.mazaady.portal.data.model.screen1.MainCategoryItem
import com.mazaady.portal.data.model.Options
import com.mazaady.portal.ui.adapter.screen1.MainCategoriesAdapter
import com.mazaady.portal.ui.adapter.screen1.OptionsAdapter
import com.mazaady.portal.ui.adapter.screen1.SubCategoriesAdapter
import com.mazaady.portal.util.DataCategoriesItemDecoration
import com.mazaady.portal.util.afterTextChanged
import com.mazaady.portal.util.serializable
import dagger.hilt.android.AndroidEntryPoint

typealias Callback = (Any) -> Unit

@AndroidEntryPoint
/**
 * This is a Kotlin class for a Bottom Sheet Dialog Fragment that displays a list of items and allows
 * filtering and selection of those items.
 *
 * @param inflater An instance of the LayoutInflater class, which is used to inflate views from XML
 * layout resources.
 * @param container The ViewGroup that the fragment's UI should be attached to.
 * @param savedInstanceState `savedInstanceState` is a Bundle object that contains the saved state of
 * the fragment. It is used to restore the state of the fragment in case it is destroyed and recreated
 * by the system. The saved state can include information such as the current scroll position of a
 * RecyclerView or the text entered in an EditText
 * @return The `onCreateView` method is returning the root view of the inflated
 * `FragmentMyBottomSheetBinding` layout.
 */
class MyBottomSheetDialogFragment : BottomSheetDialogFragment() {
    /**
     * This function sets a callback for a given object.
     *
     * @param callback The parameter "callback" is of type "Callback", which is likely an interface or
     * a class that defines a set of methods that can be called by the class or function that is using
     * it. The purpose of this parameter is to allow the caller to provide an implementation of the
     * Callback interface or class,
     */
    fun setCallback(callback: Callback) {
        this.callback = callback
    }

    private var callback: Callback? = null


    private lateinit var binding: FragmentMyBottomSheetBinding
    private lateinit var recyclerView: RecyclerView

    private lateinit var adapterMainCategories: MainCategoriesAdapter
    private lateinit var adapterSubCategories: SubCategoriesAdapter
    private lateinit var adapterOptions: OptionsAdapter
    private var mainCategoryItems: MutableList<MainCategoryItem>? = null
    private var title: String? = null
    private var subCategoryItems: MutableList<Children>? = null
    private var optionsItems: MutableList<Options>? = null

    /* The `companion object` is a singleton object that is associated with the class in which it is
    defined. In this case, it is defined within the `MyBottomSheetDialogFragment` class. */
    companion object {
        const val ARG_LIST_KEY_MAIN_CATEGORIES = "ARG_LIST_KEY_MAIN_CATEGORIES"
        const val ARG_LIST_KEY_TITLE = "ARG_LIST_KEY_TITLE"
        const val ARG_LIST_KEY_SUB_CATEGORIES = "ARG_LIST_KEY_SUB_CATEGORIES"
        const val ARG_LIST_KEY_PROPERTIES = "ARG_LIST_KEY_PROPERTIES"
        fun <T : Parcelable> newInstance(
            key: String,
            list: List<T>
        ): MyBottomSheetDialogFragment {
            val fragment = MyBottomSheetDialogFragment()
            val args = Bundle().apply {
                putParcelableArrayList(key, ArrayList(list))
            }
            fragment.arguments = args
            return fragment
        }
    }

    /**
     * This function inflates a layout for a bottom sheet fragment and returns its root view.
     *
     * @param inflater An object that can be used to inflate any views in the fragment. It takes a
     * layout resource file and returns a View object that represents the layout.
     * @param container The `container` parameter is the parent view that the fragment's UI should be
     * attached to, or `null` if there is no parent view. It is typically used in conjunction with the
     * `attachToRoot` parameter of the `inflate` method to determine whether the inflated view should
     * be attached to
     * @param savedInstanceState The `savedInstanceState` parameter is a `Bundle` object that contains
     * the data that was saved in the `onSaveInstanceState()` method of the fragment, if any. This data
     * can be used to restore the state of the fragment when it is recreated.
     * @return The method is returning a View object, which is the root view of the inflated layout
     * specified in the FragmentMyBottomSheetBinding object.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }


    /**
     * This function sets up the UI, adapters, options recycler view, and retrieves passed arguments
     * data in a Kotlin Android app.
     *
     * @param view The view parameter is the root view of the fragment's layout. It is passed to the
     * onViewCreated() method as an argument. This view can be used to access and manipulate the UI
     * elements of the fragment.
     * @param savedInstanceState savedInstanceState is a parameter of the onViewCreated() method in
     * Android development. It is a Bundle object that contains the saved state of the fragment when it
     * was last destroyed. This bundle can be used to restore the state of the fragment when it is
     * recreated. The savedInstanceState parameter is null if the fragment is being
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpUiAndSearchView()

        setUpAdapters()

        setUpOptionsRecyclerView()

        getPassedArgsData()

    }

    /**
     * This function sets up the UI and search view for filtering items based on user input.
     */
    private fun setUpUiAndSearchView() {
        binding.close.setOnClickListener {
            dismiss()
        }
        binding.search.afterTextChanged { s ->
            when {
                mainCategoryItems != null -> adapterMainCategories.filter.filter(s)
                subCategoryItems != null -> adapterSubCategories.filter.filter(s)
                optionsItems != null -> adapterOptions.filter.filter(s)
                else -> callback?.invoke(s)
            }
        }
    }

    /**
     * The function retrieves data from arguments and sets the appropriate adapter and title based on
     * the type of data received.
     */
    private fun getPassedArgsData() {
        title = arguments?.getString(ARG_LIST_KEY_TITLE)
        mainCategoryItems =
            arguments?.serializable(ARG_LIST_KEY_MAIN_CATEGORIES)
        subCategoryItems =
            arguments?.serializable(ARG_LIST_KEY_SUB_CATEGORIES)
        optionsItems =
            arguments?.serializable(ARG_LIST_KEY_PROPERTIES)

        if (mainCategoryItems != null) {
            recyclerView.adapter = adapterMainCategories
            adapterMainCategories.setOriginalDataList(mainCategoryItems)
            binding.title.text = getString(R.string.select_main_cat)
        }
        if (subCategoryItems != null) {
            recyclerView.adapter = adapterSubCategories
            adapterSubCategories.setOriginalDataList(subCategoryItems)
            binding.title.text = getString(R.string.select_sub_cat)
        }
        if (optionsItems != null) {
            recyclerView.adapter = adapterOptions
            adapterOptions.setOriginalDataList(optionsItems)
            binding.title.text = title
        }
    }

    /**
     * This function sets up adapters for main categories, subcategories, and options and assigns click
     * listeners to them.
     */
    private fun setUpAdapters() {
        adapterMainCategories = MainCategoriesAdapter()
        adapterSubCategories = SubCategoriesAdapter()
        adapterOptions = OptionsAdapter()

        adapterMainCategories.setOnItemClickListener {
            dismiss()
            if (it != null) {
                callback?.invoke(it)
            }
        }
        adapterSubCategories.setOnItemClickListener {
            if (it != null) {
                callback?.invoke(it)
            }
            dismiss()
        }
        adapterOptions.setOnItemClickListener {
            if (it != null) {
                callback?.invoke(it)
            }
            dismiss()
        }
    }

    /**
     * This function sets up a RecyclerView with a LinearLayoutManager and adds an item decoration.
     */
    private fun setUpOptionsRecyclerView() {
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(DataCategoriesItemDecoration(requireContext()))
    }

}
