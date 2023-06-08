package com.mazaady.portal.ui.tableview

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.mazaady.portal.R
import com.mazaady.portal.data.CollectionCatItem
import com.mazaady.portal.data.RandomDataCell
import com.mazaady.portal.databinding.FragmentTableViewShowSelectedDataBinding
import com.mazaady.portal.ui.adapter.RandomDataTableViewAdapter
import com.mazaady.portal.ui.list.ShowALlMainCategoriesListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.custom_dialog_layout.view.*
import kotlinx.android.synthetic.main.fragment_screen_one_categories.*
import ph.ingenuity.tableview.ITableView
import ph.ingenuity.tableview.TableView
import ph.ingenuity.tableview.feature.sort.SortState
import ph.ingenuity.tableview.listener.ITableViewListener
import java.util.*

private const val TAG = "TableShowSelectedFrag"

@AndroidEntryPoint
/* The TableViewShowSelectedFragment class is a Kotlin fragment that initializes data and fetches saved
arguments to display in a table view. */
class TableViewShowSelectedFragment : Fragment(R.layout.fragment_table_view_show_selected_data) {

    private lateinit var binding: FragmentTableViewShowSelectedDataBinding
    private val tableViewShowSelectedDataBinding: TableViewShowSelectedViewModel by viewModels()

    /* The above code is declaring a private property `args` that is initialized using the `navArgs()`
    delegate function. This function is used in Android Jetpack's Navigation component to retrieve
    the arguments passed to a destination fragment. The `TableViewShowSelectedFragmentArgs` class is
    the generated class that holds the arguments for the `TableViewShowSelectedFragment`. The `by`
    keyword is used to delegate the property access to the `navArgs()` function. */
    private val args by navArgs<TableViewShowSelectedFragmentArgs>()
    private var listSelectedItems = CollectionCatItem()

    /**
     * This function inflates a layout for a fragment and returns its root view.
     *
     * @param inflater The LayoutInflater is a class that is used to instantiate layout XML file into
     * its corresponding View objects. It takes an XML file as input and returns a View object. In the
     * code snippet provided, the LayoutInflater is used to inflate the layout for the
     * FragmentTableViewShowSelectedDataBinding.
     * @param container The `container` parameter is the parent view that the fragment's UI should be
     * attached to. It is a `ViewGroup` that contains the fragment's layout. If the fragment is not
     * attached to any parent view, this parameter will be null.
     * @param savedInstanceState The `savedInstanceState` parameter is a `Bundle` object that contains
     * the saved state of the fragment. This can be used to restore the state of the fragment when it
     * is recreated, for example, after a configuration change like a screen rotation.
     * @return The method is returning a View object, which is the root view of the inflated layout
     * specified by the FragmentTableViewShowSelectedDataBinding object.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTableViewShowSelectedDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    /**
     * The function initializes data and fetches saved arguments in a Kotlin fragment.
     *
     * @param view The view parameter is the root view of the fragment's layout. It is passed to the
     * onViewCreated() method as an argument. This view can be used to access and manipulate the UI
     * elements of the fragment.
     * @param savedInstanceState savedInstanceState is a parameter of the onViewCreated() method in
     * Android that represents the saved state of the fragment. It is a Bundle object that contains
     * data that was saved by the fragment in a previous state. This data can be used to restore the
     * state of the fragment when it is recreated. The savedInstanceState parameter
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchPassedSaveArgs()

        initializeData()
    }

    /**
     * This function fetches the passed save arguments and assigns them to a list of selected items.
     */
    private fun fetchPassedSaveArgs() {
        listSelectedItems = args.listCategories
    }

    @Suppress("UNCHECKED_CAST")
    /**
     * The function initializes data for a table view adapter in Kotlin.
     */
    private fun initializeData() {

        val rowHeadersList = mutableListOf<RandomDataCell>()
        val cellsList = mutableListOf<MutableList<RandomDataCell>>()
        val columnHeadersList = mutableListOf<RandomDataCell>()

        val tableAdapter = RandomDataTableViewAdapter(requireContext())
        val hSpace = RandomDataCell("")

        cellsList.add(mutableListOf(RandomDataCell("Selected Main Cat")))
        cellsList.add(
            mutableListOf(
                hSpace,
                hSpace,
                RandomDataCell("name: ${listSelectedItems.mainCategoryItem?.name}")
            )
        )
        cellsList.add(
            mutableListOf(
                hSpace,
                hSpace,
                RandomDataCell("slug: ${listSelectedItems.mainCategoryItem?.slug}")
            )
        )

        cellsList.add(
            mutableListOf(
                RandomDataCell("Selected Sub Cat")
            )
        )
        cellsList.add(
            mutableListOf(
                hSpace, hSpace,
                RandomDataCell("name: ${listSelectedItems.children?.slug}")
            )
        )
        cellsList.add(
            mutableListOf(
                hSpace, hSpace,
                RandomDataCell("slug: ${listSelectedItems.children?.slug}")
            )
        )

        cellsList.add(
            mutableListOf(
                RandomDataCell("Selected Sub Properties")
            )
        )
        listSelectedItems.properties?.forEachIndexed { index, subCategoryItem ->
            val firstOption = listSelectedItems.options!![index]
            cellsList.add(
                mutableListOf(
                    hSpace, hSpace,
                    RandomDataCell("name: ${listSelectedItems.properties?.get(index)?.name}")
                )
            )
            if (listSelectedItems.options?.isNotEmpty() == true && index <= (listSelectedItems.options?.size?.minus(
                    1
                ) ?: 0)
            ) {
                if (firstOption.parent == -1) {
                    cellsList.add(
                        mutableListOf(
                            hSpace, hSpace,
                            RandomDataCell("Specify Here: ${firstOption.specifyHere}")
                        )
                    )
                } else {
                    cellsList.add(
                        mutableListOf(
                            hSpace, hSpace,
                            RandomDataCell("option: ${firstOption.name}")
                        )
                    )
                }
            }
            cellsList.add(
                mutableListOf(
                    hSpace, hSpace,
                    RandomDataCell("---------------------------------")
                )
            )
        }




        binding.tableView.adapter = tableAdapter
        binding.tableView.tableViewListener = TableViewListener(binding.tableView)
        tableAdapter.setAllItems(
            cellsList,
            columnHeadersList,
            rowHeadersList as List<Any>
        )
    }

}

/* The TableViewListener class is a listener for a TableView in Kotlin that handles various events such
as clicking and long pressing on cells, column headers, and row headers. */
class TableViewListener(private val tableView: TableView) : ITableViewListener {

    private var toast: Toast? = null

    private val context: Context = tableView.context

    override fun onCellClicked(cellView: RecyclerView.ViewHolder, column: Int, row: Int) {
        showToast("Cell $column $row has been clicked.")
    }

    override fun onCellLongPressed(cellView: RecyclerView.ViewHolder, column: Int, row: Int) {
        showToast("Cell $column, $row has been long pressed.")
    }

    override fun onColumnHeaderClicked(columnHeaderView: RecyclerView.ViewHolder, column: Int) {
        showToast("Column header $column has been clicked.")
    }

    override fun onColumnHeaderLongPressed(columnHeaderView: RecyclerView.ViewHolder, column: Int) {
        if (columnHeaderView is RandomDataTableViewAdapter.RandomDataColumnHeaderViewHolder) {
            val popup = ColumnHeaderLongPressPopup(columnHeaderView, tableView)
            popup.show()
        }
    }

    override fun onRowHeaderClicked(rowHeaderView: RecyclerView.ViewHolder, row: Int) {
        showToast("Row header $row has been clicked.")
    }

    override fun onRowHeaderLongPressed(rowHeaderView: RecyclerView.ViewHolder, row: Int) {
        if (rowHeaderView is RandomDataTableViewAdapter.RandomDataRowHeaderViewHolder) {
            val popup = RowHeaderLongPressPopup(rowHeaderView, tableView)
            popup.show()
        }
    }

    private fun showToast(message: String) {
        if (toast == null) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        }

        toast!!.setText(message)
        toast!!.show()
    }
}

/* The RowHeaderLongPressPopup class creates a popup menu with options to scroll to a specific column
position and show/hide a column in a table view. */
class RowHeaderLongPressPopup(
    viewHolder: RandomDataTableViewAdapter.RandomDataRowHeaderViewHolder,
    private val tableView: ITableView
) : PopupMenu(
    viewHolder.itemView.context,
    viewHolder.itemView
), PopupMenu.OnMenuItemClickListener {

    private val context = viewHolder.itemView.context

    init {
        createMenuItems()
        setOnMenuItemClickListener(this)
    }

    /**
     * This function handles menu item clicks and performs actions based on the clicked item's ID.
     *
     * @param item MenuItem object that represents the menu item that was clicked by the user. It
     * contains information such as the ID of the item, its title, and any other attributes that were
     * set for it.
     * @return a boolean value of `true`.
     */
    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            SCROLL_COLUMN -> tableView.scrollToColumn(50)
            SHOW_HIDE_COLUMN -> {
                if (tableView.isColumnVisible(SAMPLE_SHOW_HIDE_COLUMN)) {
                    tableView.hideColumn(SAMPLE_SHOW_HIDE_COLUMN)
                } else {
                    tableView.showColumn(SAMPLE_SHOW_HIDE_COLUMN)
                }
            }
        }

        return true
    }

    /**
     * This function creates two menu items with specific IDs and labels.
     */
    private fun createMenuItems() {
        menu.add(Menu.NONE, SCROLL_COLUMN, 0, context.getString(R.string.scroll_to_column_position))
        menu.add(Menu.NONE, SHOW_HIDE_COLUMN, 1, context.getString(R.string.show_hide_column))
    }

    /* The above code is defining a Kotlin class with a companion object that contains three constant
    properties: `SCROLL_COLUMN`, `SHOW_HIDE_COLUMN`, and `SAMPLE_SHOW_HIDE_COLUMN`. These properties
    are used to represent column indices in a table or grid layout. */
    companion object {
        const val SCROLL_COLUMN = 0
        const val SHOW_HIDE_COLUMN = 1
        const val SAMPLE_SHOW_HIDE_COLUMN = 5
    }
}

/* The ColumnHeaderLongPressPopup class is a Kotlin implementation of a popup menu that allows sorting,
hiding, showing, and scrolling rows in a TableView. */
class ColumnHeaderLongPressPopup(
    viewHolder: RandomDataTableViewAdapter.RandomDataColumnHeaderViewHolder,
    private val tableView: TableView
) : PopupMenu(
    viewHolder.itemView.context,
    viewHolder.itemView
), PopupMenu.OnMenuItemClickListener {

    private val context = viewHolder.itemView.context

    private val xPosition = viewHolder.adapterPosition

    init {
        createMenuItems()
        changeMenuItemsVisibility()
        setOnMenuItemClickListener(this)
    }

    /**
     * This function handles menu item clicks and performs corresponding actions on a table view.
     *
     * @param item A MenuItem object representing the menu item that was clicked by the user. It
     * contains information such as the ID of the item and its title.
     * @return a boolean value of `true`.
     */
    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            ASCENDING -> tableView.sortColumn(xPosition, SortState.ASCENDING)
            DESCENDING -> tableView.sortColumn(xPosition, SortState.DESCENDING)
            HIDE_ROW -> tableView.hideRow(3)
            SHOW_ROW -> tableView.showRow(3)
            SCROLL_ROW -> tableView.scrollToRow(100)
        }

        return true
    }

    /**
     * The function creates menu items with specific IDs and labels for sorting, hiding, showing, and
     * scrolling to a row position.
     */
    private fun createMenuItems() {
        menu.add(Menu.NONE, ASCENDING, 0, context.getString(R.string.sort_ascending))
        menu.add(Menu.NONE, DESCENDING, 1, context.getString(R.string.sort_descending))
        menu.add(Menu.NONE, HIDE_ROW, 2, context.getString(R.string.hide_row))
        menu.add(Menu.NONE, SHOW_ROW, 3, context.getString(R.string.show_row))
        menu.add(Menu.NONE, SCROLL_ROW, 4, context.getString(R.string.scroll_to_row_position))
    }

    /**
     * The function changes the visibility of menu items based on the sort state of a table column.
     */
    private fun changeMenuItemsVisibility() {
        val sortState = tableView.getColumnSortState(xPosition)
        when (sortState) {
            SortState.DESCENDING -> menu.getItem(1).isVisible = false
            SortState.ASCENDING -> menu.getItem(0).isVisible = false
            SortState.UNSORTED -> {
            }
        }
    }

    companion object {
        const val ASCENDING = 0
        const val DESCENDING = 1
        const val HIDE_ROW = 2
        const val SHOW_ROW = 3
        const val SCROLL_ROW = 4
    }
}