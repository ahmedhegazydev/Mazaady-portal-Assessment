package com.mazaady.portal.ui.static_screen

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.mazaady.portal.R
import com.mazaady.portal.data.model.screen2.*
import com.mazaady.portal.databinding.FragmentScreenTwoStaticBinding
import com.mazaady.portal.ui.adapter.screen2.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.list_item_image_gallery.*


@AndroidEntryPoint
/* This is a Kotlin class that sets up various RecyclerViews and adapters for a static screen in an
Android app. */
class ScreenTwoStaticFragment : Fragment(R.layout.fragment_screen_two_static) {

    private val viewModel: ScreenTwoStaticViewModel by viewModels()

    /* `private lateinit var binding: FragmentScreenTwoStaticBinding` is declaring a private property
    called `binding` of type `FragmentScreenTwoStaticBinding`. The `lateinit` keyword is used to
    indicate that the property will be initialized later, before it is used. This property is used
    to access views and other UI elements in the fragment's layout. It is initialized in the
    `onCreateView` method by inflating the layout using the `FragmentScreenTwoStaticBinding.inflate`
    method and assigning the resulting binding object to the `binding` property. */
    private lateinit var binding: FragmentScreenTwoStaticBinding

    /**
     * This function inflates a layout for a fragment and returns its root view.
     *
     * @param inflater An object that can be used to inflate any views in the fragment. It takes an XML
     * layout file and converts it into corresponding View objects.
     * @param container The `container` parameter is the parent view that the fragment's UI should be
     * attached to. It is a `ViewGroup` that contains the fragment's layout. If the fragment is not
     * attached to any parent view, this parameter will be null.
     * @param savedInstanceState The `savedInstanceState` parameter is a `Bundle` object that contains
     * the saved state of the fragment. This bundle is populated by the system when the fragment is
     * destroyed and recreated due to configuration changes (such as screen rotation) or when the
     * fragment is removed from the back stack and later re-added.
     * @return The method is returning a View object, which is the root view of the inflated layout
     * specified in the FragmentScreenTwoStaticBinding object.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScreenTwoStaticBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * The function sets up multiple RecyclerViews in a fragment's view.
     *
     * @param view The view parameter is the root view of the fragment's layout. It is passed to the
     * onViewCreated() method as an argument. This view can be used to access and manipulate the UI
     * elements of the fragment.
     * @param savedInstanceState savedInstanceState is a parameter of the onViewCreated() method in
     * Android that represents the saved state of the fragment. It is a Bundle object that contains
     * data that was saved by the fragment in a previous state, such as user input or other important
     * data. This parameter is used to restore the state of the fragment
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            setUpCurrentValuesRecyclerView()


            setUpBiddersRecyclerView()


            setUpSimilarProductsRecyclerView()


            setUpTopImageGalleryRecyclerView()


            setUpAvailablePricesRecyclerView()


        }

    }

    /**
     * This function sets up a horizontal RecyclerView with a list of AvailablePricesItems using an
     * AvailablePricesAdapter.
     */
    private fun FragmentScreenTwoStaticBinding.setUpAvailablePricesRecyclerView() {
        val availablePricesAdapter = AvailablePricesAdapter()
        recyclerViewAvailablePrices.layoutManager =
            LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
        recyclerViewAvailablePrices.adapter = availablePricesAdapter
        val listavailablePricesItems = mutableListOf<AvailablePricesItem>(
            AvailablePricesItem(
                value = "2000+"
            ),
            AvailablePricesItem(
                value = "2000+"
            ),
            AvailablePricesItem(
                value = "2000+"
            ),
            AvailablePricesItem(
                value = "2000+"
            ),
        )
        availablePricesAdapter.setOriginalDataList(listavailablePricesItems)
    }

    /**
     * This function sets up a horizontal image gallery RecyclerView with a PagerSnapHelper and an
     * indicator.
     */
    private fun FragmentScreenTwoStaticBinding.setUpTopImageGalleryRecyclerView() {
        val imageGalleryAdapter = ImageGalleryAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext()).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        recyclerView.adapter = imageGalleryAdapter

        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(recyclerView)
        binding.indicator.attachToRecyclerView(recyclerView, pagerSnapHelper)
        imageGalleryAdapter.registerAdapterDataObserver(indicator.adapterDataObserver)
        val listGalleryItems = mutableListOf<GalleryItem>(
            GalleryItem(
                image = "https://imageio.forbes.com/specials-images/imageserve/5d3703b3090f4300070d570d/2020-Cadillac-CT5/0x0.jpg?format=jpg&crop=4842,2723,x288,y538,safe&width=960",
            ),
            GalleryItem(
                image = "https://imageio.forbes.com/specials-images/imageserve/5d3703b3090f4300070d570d/2020-Cadillac-CT5/0x0.jpg?format=jpg&crop=4842,2723,x288,y538,safe&width=960",
            ),
            GalleryItem(
                image = "https://imageio.forbes.com/specials-images/imageserve/5d3703b3090f4300070d570d/2020-Cadillac-CT5/0x0.jpg?format=jpg&crop=4842,2723,x288,y538,safe&width=960",
            ),
            GalleryItem(
                image = "https://imageio.forbes.com/specials-images/imageserve/5d3703b3090f4300070d570d/2020-Cadillac-CT5/0x0.jpg?format=jpg&crop=4842,2723,x288,y538,safe&width=960",
            ),
            GalleryItem(
                image = "https://imageio.forbes.com/specials-images/imageserve/5d3703b3090f4300070d570d/2020-Cadillac-CT5/0x0.jpg?format=jpg&crop=4842,2723,x288,y538,safe&width=960",
            ),
        )
        imageGalleryAdapter.setOriginalDataList(listGalleryItems)
    }

    /**
     * This function sets up a horizontal RecyclerView with a list of similar product items and
     * attaches a GravitySnapHelper to it.
     */
    private fun FragmentScreenTwoStaticBinding.setUpSimilarProductsRecyclerView() {
        val similarProductsAdapter = SimilarProductsAdapter()
        recyclerViewSimilar.layoutManager = LinearLayoutManager(requireContext()).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        recyclerViewSimilar.adapter = similarProductsAdapter
        val listSimilarProductItems = mutableListOf<SimilarProductItem>(
            SimilarProductItem(
                image = "https://imageio.forbes.com/specials-images/imageserve/5d3703b3090f4300070d570d/2020-Cadillac-CT5/0x0.jpg?format=jpg&crop=4842,2723,x288,y538,safe&width=960",
                title = "سيارة سريعة بى ام دابليو تصنيع 2021 حالته جديدة",
                startAfter = "3 :22:55",
                startPrice = "20000"
            ),
            SimilarProductItem(
                image = "https://imageio.forbes.com/specials-images/imageserve/5d3703b3090f4300070d570d/2020-Cadillac-CT5/0x0.jpg?format=jpg&crop=4842,2723,x288,y538,safe&width=960",
                title = "سيارة سريعة بى ام دابليو تصنيع 2021 حالته جديدة",
                startAfter = "3 :22:55",
                startPrice = "20000"
            ),
            SimilarProductItem(
                image = "https://imageio.forbes.com/specials-images/imageserve/5d3703b3090f4300070d570d/2020-Cadillac-CT5/0x0.jpg?format=jpg&crop=4842,2723,x288,y538,safe&width=960",
                title = "سيارة سريعة بى ام دابليو تصنيع 2021 حالته جديدة",
                startAfter = "3 :22:55",
                startPrice = "20000"
            ),
            SimilarProductItem(
                image = "https://imageio.forbes.com/specials-images/imageserve/5d3703b3090f4300070d570d/2020-Cadillac-CT5/0x0.jpg?format=jpg&crop=4842,2723,x288,y538,safe&width=960",
                title = "سيارة سريعة بى ام دابليو تصنيع 2021 حالته جديدة",
                startAfter = "3 :22:55",
                startPrice = "20000"
            ),
            SimilarProductItem(
                image = "https://imageio.forbes.com/specials-images/imageserve/5d3703b3090f4300070d570d/2020-Cadillac-CT5/0x0.jpg?format=jpg&crop=4842,2723,x288,y538,safe&width=960",
                title = "سيارة سريعة بى ام دابليو تصنيع 2021 حالته جديدة",
                startAfter = "3 :22:55",
                startPrice = "20000"
            ),
        )
        similarProductsAdapter.setOriginalDataList(listSimilarProductItems)
        val snapHelper = GravitySnapHelper(Gravity.START)
        snapHelper.attachToRecyclerView(recyclerViewSimilar)
    }

    /**
     * This function sets up a RecyclerView with a list of bidders using a custom adapter and a list of
     * BidderItem objects.
     */
    private fun FragmentScreenTwoStaticBinding.setUpBiddersRecyclerView() {
        val biddersAdapter = BiddersAdapter()
        recyclerViewBidders.adapter = biddersAdapter
        val listBidderItems = mutableListOf<BidderItem>(
            BidderItem(
                profileImage = "https://imageio.forbes.com/specials-images/imageserve/5d3703b3090f4300070d570d/2020-Cadillac-CT5/0x0.jpg?format=jpg&crop=4842,2723,x288,y538,safe&width=960",
                name = "Name Here",
                counter = "20+",
                time = "20:30:50"
            ),
            BidderItem(
                profileImage = "https://imageio.forbes.com/specials-images/imageserve/5d3703b3090f4300070d570d/2020-Cadillac-CT5/0x0.jpg?format=jpg&crop=4842,2723,x288,y538,safe&width=960",
                name = "Name Here",
                counter = "20+",
                time = "20:30:50"
            ),
            BidderItem(
                profileImage = "https://imageio.forbes.com/specials-images/imageserve/5d3703b3090f4300070d570d/2020-Cadillac-CT5/0x0.jpg?format=jpg&crop=4842,2723,x288,y538,safe&width=960",
                name = "Name Here",
                counter = "20+",
                time = "20:30:50"
            ),
        )
        biddersAdapter.setOriginalDataList(listBidderItems)
    }

    /**
     * The function sets up a RecyclerView with a list of current values for a bidding auction.
     */
    private fun FragmentScreenTwoStaticBinding.setUpCurrentValuesRecyclerView() {
        val currentValuesAdapter = CurrentValuesAdapter()
        recyclerViewCurrentValues.adapter = currentValuesAdapter
        val listCurrentValues = mutableListOf<MazadCurrentValueItem>(
            MazadCurrentValueItem(title = "القيمه الحاليه للمزاد", value = "2000 $"),
            MazadCurrentValueItem(title = "القيمه الحاليه للمزاد", value = "6000 $"),
            MazadCurrentValueItem(title = "القيمه الحاليه بعد الضريبه", value = "4000 $"),
            MazadCurrentValueItem(title = "القيمه الحاليه للمزاد", value = "1000 $"),
        )
        currentValuesAdapter.setOriginalDataList(listCurrentValues)
    }
}