package com.mazaady.portal.ui.list

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.mazaady.portal.R
import com.mazaady.portal.data.model.Children
import com.mazaady.portal.data.model.Options
import com.mazaady.portal.data.model.screen1.MainCategoryItem
import com.mazaady.portal.data.model.screen1.SubCategoryItem
import com.mazaady.portal.databinding.FragmentScreenOneCategoriesBinding
import com.mazaady.portal.state.NetworkState
import com.mazaady.portal.ui.adapter.screen1.PropertiesAdapter
import com.mazaady.portal.ui.sheet.Callback
import com.mazaady.portal.ui.sheet.MyBottomSheetDialogFragment
import com.mazaady.portal.ui.sheet.MyBottomSheetDialogFragment.Companion.ARG_LIST_KEY_MAIN_CATEGORIES
import com.mazaady.portal.ui.sheet.MyBottomSheetDialogFragment.Companion.ARG_LIST_KEY_SUB_CATEGORIES
import com.mazaady.portal.util.DataCategoriesItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.custom_dialog_layout.view.*
import kotlinx.android.synthetic.main.fragment_screen_one_categories.*
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

private const val TAG = "ScreenOneCatFragment"

@AndroidEntryPoint
/* The above code is a Kotlin class that represents a fragment in an Android app. It displays a list of
main categories and subcategories, and allows the user to select properties and options for a
selected subcategory. The selected data is displayed in a table format when the user clicks the
"Submit" button. The code sets up UI elements, observes changes in data, and handles user
interactions with the UI. */
class ScreenOneAllCatsListFragment : Fragment(R.layout.fragment_screen_one_categories) {

    private val viewModelCategories: ShowALlMainCategoriesListViewModel by viewModels()
    private lateinit var propertiesAdapter: PropertiesAdapter


    private var dataSubCategories: MutableList<Children> = mutableListOf()
    private var categoryList: MutableList<MainCategoryItem> = mutableListOf()
    private var dataSubCategoriesOld: MutableList<Children> = mutableListOf()
    private var categoryListOld: MutableList<MainCategoryItem> = mutableListOf()

    private var isLoading = false
    private lateinit var binding: FragmentScreenOneCategoriesBinding
    private var editTextMainCat: EditText? = null
    private var editTextSubCat: EditText? = null
    private var indexToAddSubList = RecyclerView.NO_POSITION
    private var currentProperties = mutableListOf<SubCategoryItem>()
    private var tempCurrentPropertiesForReset = mutableListOf<SubCategoryItem>()
    private lateinit var selectedMainCat: MainCategoryItem
    private lateinit var selectedSubCat: Children
    private var selectedProperties = mutableListOf<SubCategoryItem>()
    private var selectedOptions = mutableListOf<Options>()
    private var resetSubPropertiesRecyclerView = true

    /* The above code is defining a global variable `globalCallback` which is an implementation of the
    `Callback` interface. The `Callback` interface has a single function `invoke` which takes an
    argument of type `Any`. The `invoke` function is overridden in the implementation of
    `globalCallback` to perform different actions based on the type of the `selectedItem` argument.
    If `selectedItem` is an instance of `MainCategoryItem`, it sets the `selectedMainCat` variable
    to the selected item, sets the text of `editTextMainCat` to the name of the selected item */
    private var globalCallback = object : Callback {
        override fun invoke(selectedItem: Any) {
            when (selectedItem) {
                is MainCategoryItem -> {
                    selectedMainCat = selectedItem
                    editTextMainCat?.setText(selectedItem.name)
                    selectedItem.children.forEach {
                        dataSubCategories.add(Children().apply {
                            name = it.name
                            id = it.id
                            description = it.description
                            image = it.image
                            slug = it.slug
                            circleIcon = it.circleIcon
                            disableShipping = it.disableShipping
                        })
                    }
                    if (dataSubCategories != dataSubCategoriesOld) {
                        sheetSubCategories = null
                    }
                    dataSubCategoriesOld = dataSubCategories
                }
                is Children -> {
                    selectedSubCat = selectedItem
                    editTextSubCat?.setText(selectedItem.name)
                    viewModelCategories.getAllSubCategoriesListShowing(selectedItem.id)
                }
            }
        }

    }
    val hashMapParentIdsAndOptions: HashMap<String, Options> = HashMap()
    private var sheetMainCategories: MyBottomSheetDialogFragment? = null
    private var sheetSubCategories: MyBottomSheetDialogFragment? = null

    /**
     * This function inflates a layout for a fragment and returns its root view.
     *
     * @param inflater An object that can be used to inflate any views in the fragment. It takes an XML
     * layout file and converts it into corresponding View objects.
     * @param container The `container` parameter is the parent view that the fragment's UI should be
     * attached to. It is a `ViewGroup` that contains the fragment's layout. If the fragment is not
     * attached to any parent view, this parameter will be null.
     * @param savedInstanceState The `savedInstanceState` parameter is a `Bundle` object that contains
     * the previous state of the fragment, which can be used to restore the state of the fragment if it
     * was previously destroyed and recreated by the system. It can be null if there is no previous
     * state.
     * @return The method is returning a View object, which is the root view of the inflated layout
     * specified by the FragmentScreenOneCategoriesBinding object.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScreenOneCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    /**
     * This function sets up observers, UI, main and sub categories, and a properties recycler view in
     * a Kotlin fragment.
     *
     * @param view The root view of the fragment's layout. It is the main container that holds all the
     * UI elements of the fragment.
     * @param savedInstanceState savedInstanceState is a parameter of type Bundle that is used to save
     * and restore the state of the fragment in case it is destroyed and recreated by the system. It
     * can be used to store and retrieve data such as user input, scroll position, or any other
     * relevant information that needs to be preserved across configuration
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sheetMainCategories = initBottomSheetForMainCategories()

        setupObservers()

        setupUI()

        setUpMainAdnSubCategories()

        setUpPropertiesRecyclerView()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    /**
     * The function displays a table of all selected data in a custom dialog.
     */
    private fun showAllSelectedDataAsTable() {
        for (i in 0 until recyclerViewProperties.childCount) {
            val childView: View = recyclerViewProperties.getChildAt(i)
            val specifyTil: TextInputLayout = childView.findViewById(R.id.til_sepcify_here)
            if (specifyTil.visibility == View.VISIBLE) {
                val specifyHereValue =
                    specifyTil.editText?.text.toString().trim()
                selectedOptions[i].apply {
                    specifyHere = specifyHereValue
                }
            }
            break
        }

        val inflater = LayoutInflater.from(context)
        val customView = inflater.inflate(R.layout.custom_dialog_layout, null)
        val textView = customView.textView

        textView.text = generateAllSelectedInfoTable()
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle("Title")
        alertDialogBuilder.setMessage("Message")
        alertDialogBuilder.setView(customView)
        alertDialogBuilder.setCancelable(true)
        alertDialogBuilder.setPositiveButton("OK") { dialog, which ->

        }
        alertDialogBuilder.setNegativeButton("Cancel") { dialog, which ->

        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    /**
     * This function generates a table of information about selected options and properties.
     *
     * @return a string that contains information about the selected main category, subcategory,
     * properties, and options. The string is generated by appending various properties and values to a
     * StringBuilder object and then converting it to a string.
     */
    private fun generateAllSelectedInfoTable(): String {
        val uniqueOptions = HashSet<Options>()
        selectedProperties.forEach { subCategoryItem ->
            val parentId: String = subCategoryItem.id.toString() ?: return@forEach
            val matchingOptions = hashMapParentIdsAndOptions.entries
                .filter { it.key == parentId }
                .map { it.value }
            uniqueOptions.addAll(matchingOptions)
        }
        selectedOptions.clear()
        selectedOptions.addAll(uniqueOptions)
        selectedOptions =
            selectedOptions.sortedWith(compareBy({ it.parent }, { it.id })).toMutableList()

        val stringBuilder = StringBuilder()
        stringBuilder.append("|-------------------------\n")
        stringBuilder.append("selected MainCat:\n")
        stringBuilder.append("    name = ${selectedMainCat.name}\n")
        stringBuilder.append("    description = ${selectedMainCat.description}\n")
        stringBuilder.append("    slug = ${selectedMainCat.slug}\n")
        stringBuilder.append("|-------------------------\n")
        stringBuilder.append("selected SubCat:\n")
        stringBuilder.append("    name = ${selectedSubCat.name}\n")
        stringBuilder.append("    description = ${selectedSubCat.description}\n")
        stringBuilder.append("    slug = ${selectedSubCat.slug}\n")
        stringBuilder.append("|-------------------------\n")
        stringBuilder.append("selected Properties:\n")

        selectedProperties.forEachIndexed { index, subCategoryItem ->
            stringBuilder.append("|    name = ${subCategoryItem.name}\n")
            stringBuilder.append("|    description = ${subCategoryItem.description}\n")
            stringBuilder.append("|    slug = ${subCategoryItem.slug}\n")

            if (selectedOptions.isNotEmpty() && index <= selectedOptions.size - 1) {
                val firstOption = selectedOptions[index]
                stringBuilder.append("|           selected Options:\n")
                stringBuilder.append("|                 name = ${firstOption.name}\n")
                stringBuilder.append("|                 slug = ${firstOption.slug}\n")
                if (firstOption.parent == -1) {
                    stringBuilder.append("|                 specify Here = ${firstOption.specifyHere}\n")
                }
            } else {
                stringBuilder.append("|    No options selected\n")
            }
            stringBuilder.append("|-------------------------\n")
        }

        return stringBuilder.toString()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    /**
     * The function sets up a click listener for a button that triggers the display of selected data in
     * a table format.
     */
    private fun setupUI() {
        binding.btnSubmit.setOnClickListener {
            showAllSelectedDataAsTable()
        }
    }

    /**
     * This function sets up the main and sub categories for a view and adds click listeners to show a
     * bottom sheet dialog fragment.
     */
    private fun setUpMainAdnSubCategories() {
        binding.viewMainCat.root.findViewById<TextInputLayout>(R.id.til).apply {
            hint = "Main Category"
            editTextMainCat = editText
        }
        binding.viewSubCat.root.findViewById<TextInputLayout>(R.id.til).apply {
            hint = "Sub Category"
            editTextSubCat = editText
        }

        binding.viewMainCat.root.findViewById<View>(R.id.view_clickable).setOnClickListener {
            if (sheetMainCategories == null) {
                sheetMainCategories =
                    MyBottomSheetDialogFragment.newInstance(
                        ARG_LIST_KEY_MAIN_CATEGORIES,
                        categoryList
                    )
                sheetMainCategories?.setCallback(callback = globalCallback)
            }

            if (sheetMainCategories?.isBottomSheetDialogVisible?.not() == true) {
                sheetMainCategories?.show(
                    childFragmentManager,
                    "sheetMainCategories"
                )
            }
        }
        binding.viewSubCat.root.findViewById<View>(R.id.view_clickable).setOnClickListener {
            showSubCategoriesBottomSheet()
        }
    }

    /**
     * The function initializes a bottom sheet dialog fragment for main categories with a callback.
     *
     * @return The function `initBottomSheetForMainCategories()` returns an instance of
     * `MyBottomSheetDialogFragment` with a callback set to `globalCallback`.
     */
    private fun initBottomSheetForMainCategories(): MyBottomSheetDialogFragment? {
        val sheetMainCategories =
            MyBottomSheetDialogFragment.newInstance(ARG_LIST_KEY_MAIN_CATEGORIES, categoryList)
        sheetMainCategories.setCallback(callback = globalCallback)
        return sheetMainCategories
    }

    /**
     * The function initializes a bottom sheet dialog fragment for subcategories with a callback
     * function.
     *
     * @return The function `initBottomSheetForSubCategories()` is returning an instance of
     * `MyBottomSheetDialogFragment` with a list of sub-categories data and a callback function set to
     * `globalCallback`.
     */
    private fun initBottomSheetForSubCategories(): MyBottomSheetDialogFragment? {
        val sheetSubCategories = MyBottomSheetDialogFragment.newInstance(
            ARG_LIST_KEY_SUB_CATEGORIES,
            dataSubCategories
        )
        sheetSubCategories.setCallback(callback = globalCallback)
        return sheetSubCategories
    }

    /**
     * This function sets up a RecyclerView with a custom adapter and handles item selection and
     * addition to a list.
     */
    private fun setUpPropertiesRecyclerView() {
        propertiesAdapter = PropertiesAdapter(childFragmentManager)
        propertiesAdapter.setOnItemOptionSelectListener { item, option, adapterPosition ->

            val isReset = tempCurrentPropertiesForReset.any { item.name?.trim().equals(it.name?.trim(), ignoreCase = true) }
            if (isReset) {
                currentProperties.removeAll {
                    it.isTempForRestingRecycler == true
                }
                propertiesAdapter.setOriginalDataList(currentProperties)
            }

            indexToAddSubList = adapterPosition

            viewModelCategories.getAllOptionsListShowing(option.id)

            hashMapParentIdsAndOptions[item.id.toString()] = option

            val itemExists =
                selectedProperties.any { it.id == item.id }
            val itemExistsOptions =
                selectedOptions.any { it.id == option.id }
            if (!itemExistsOptions) {
                selectedOptions.add(option)
            }
            if (!itemExists) {
                selectedProperties.add(item)
            }
            selectedProperties.forEachIndexed { index, subCategoryItem ->
                if (subCategoryItem.id == option.parent || -1 == option.parent) {
                    selectedOptions.removeAt(index)
                    selectedOptions.add(index, option)
                }
            }
            val removeDuplicated = selectedProperties.filter {
                it.name?.trim().equals(item.name?.trim(), ignoreCase = true)
            }.toMutableList().takeIf { it.isNotEmpty() && it.size >= 2 }?.removeFirst()
            selectedProperties.removeAll {
                it.name == removeDuplicated?.name &&
                        it.id == removeDuplicated?.id &&
                        it.description == removeDuplicated?.description &&
                        it.options == removeDuplicated?.options &&
                        it.otherValue == removeDuplicated.otherValue &&
                        it.slug == removeDuplicated.slug &&
                        it.list == removeDuplicated.list
            }


        }
        binding.recyclerViewProperties.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewProperties.addItemDecoration(DataCategoriesItemDecoration(requireContext()))
        binding.recyclerViewProperties.adapter = propertiesAdapter
    }

    /**
     * The function shows a bottom sheet dialog fragment with a list of sub-categories and sets a
     * callback for it.
     */
    private fun showSubCategoriesBottomSheet() {
        if (sheetSubCategories == null) {
            sheetSubCategories = MyBottomSheetDialogFragment.newInstance(
                ARG_LIST_KEY_SUB_CATEGORIES,
                dataSubCategories
            )
            sheetSubCategories?.setCallback(callback = globalCallback)
        }
        if (sheetSubCategories?.isBottomSheetDialogVisible?.not() == true) {
            sheetSubCategories?.show(
                childFragmentManager,
                "sheetSubCategories"
            )
        }
    }

    /**
     * This function sets up observers to collect data from different network states and updates the UI
     * accordingly.
     */
    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModelCategories.mainCatResponse.collect { response ->
                    when (response) {
                        is NetworkState.Success -> {
                            hideProgressBar()
                            response.data?.let {
                                categoryList = it.data?.categories ?: mutableListOf()
                                if (categoryList != categoryListOld) {
                                    sheetMainCategories = null
                                }
                                categoryListOld = categoryList

                            }
                        }

                        is NetworkState.Loading -> {
                            showProgressBar()
                        }

                        is NetworkState.Error -> {
                            hideProgressBar()
                            response.message?.let {
                                showErrorMessage(response.message)
                            }
                        }

                        else -> {}
                    }
                }

            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModelCategories.errorMessage.collect { value ->
                    if (value.isNotEmpty()) {
                        Toast.makeText(activity, value, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModelCategories.subPropertiesResponse.collect { response ->
                    when (response) {
                        is NetworkState.Success -> {
                            hideProgressBar()
                            response.data?.let { data ->
                                data.data?.forEach { item ->
                                    item.options.add(0, Options().apply {
                                        id = -1
                                        name = "اخر"
                                        slug = "اخر"
                                        parent = -1
                                        child = true
                                    })
                                }
                                propertiesAdapter.setOriginalDataList(data.data ?: mutableListOf())
                                currentProperties = data.data ?: mutableListOf()

                            }
                            if (resetSubPropertiesRecyclerView){
                                tempCurrentPropertiesForReset.addAll(currentProperties)
                                resetSubPropertiesRecyclerView = false
                            }
                        }

                        is NetworkState.Loading -> {
                            showProgressBar()
                        }

                        is NetworkState.Error -> {
                            hideProgressBar()
                            response.message?.let {
                                showErrorMessage(response.message)
                            }
                        }

                        else -> {}
                    }
                }

            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModelCategories.optionsResponse.collect { response ->
                    when (response) {
                        is NetworkState.Success -> {
                            hideProgressBar()
                            response.data?.let { data ->
                                val addNewOptions: MutableList<SubCategoryItem> = mutableListOf()
                                data.data.forEachIndexed { index, options ->
                                    addNewOptions.add(SubCategoryItem().apply {
                                        id = options.id
                                        name = options.name
                                        slug = options.slug
                                        parent = options.parent.toString()
                                        this.options = options.options
                                        isTempForRestingRecycler = true
                                    })
                                }
                                if (addNewOptions.isNotEmpty()) {
                                    val firstOption = addNewOptions.firstOrNull()
                                    val filterations = currentProperties.filter { item ->
                                        item.name?.trim()
                                            .equals(firstOption?.name?.trim(), ignoreCase = true) &&
                                                item.slug?.trim()
                                                    .equals(
                                                        firstOption?.slug?.trim(),
                                                        ignoreCase = true
                                                    )
                                    }
                                    currentProperties.removeAll(filterations)
                                    currentProperties.addAll(indexToAddSubList + 1, addNewOptions)
                                    propertiesAdapter.setOriginalDataList(currentProperties)
                                }
                                resetSubPropertiesRecyclerView = true
                            }
                        }

                        is NetworkState.Loading -> {
                            showProgressBar()
                        }

                        is NetworkState.Error -> {
                            hideProgressBar()
                            response.message?.let {
                                showErrorMessage(response.message)
                            }
                        }

                        else -> {}
                    }
                }

            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModelCategories.errorMessage.collect { value ->
                    if (value.isNotEmpty()) {
                        Toast.makeText(activity, value, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    /**
     * The function hides a progress bar, displays an error message as a toast, logs the error message,
     * and sets a boolean flag to indicate that the app is no longer loading.
     *
     * @param message The error message that will be displayed to the user and logged in the console.
     */
    private fun showErrorMessage(message: String) {
        paginationProgressBar.visibility = View.INVISIBLE
        isLoading = true
        message.let { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            Log.e(TAG, "Error: $message")
        }
    }

    /**
     * The function sets a progress bar to be visible and sets a boolean flag to indicate that loading
     * is in progress.
     */
    private fun showProgressBar() {
        isLoading = true
        paginationProgressBar.visibility = View.VISIBLE
    }

    /**
     * This function hides a progress bar and sets a boolean flag to false.
     */
    private fun hideProgressBar() {
        isLoading = false
        paginationProgressBar.visibility = View.GONE
    }

}