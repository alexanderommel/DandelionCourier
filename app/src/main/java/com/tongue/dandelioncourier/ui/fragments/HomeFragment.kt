package com.tongue.dandelioncourier.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.tongue.dandelioncourier.R
import com.tongue.dandelioncourier.data.model.ShippingNotification
import com.tongue.dandelioncourier.databinding.FragmentHomeBinding
import com.tongue.dandelioncourier.ui.viewmodels.ActivityViewModel
import com.tongue.dandelioncourier.utils.StompInstance

class HomeFragment: Fragment(), StompInstance.CourierSubscriptionsCallBack {

    private val activityViewModel: ActivityViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var mapsFragment: GoogleMapsFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)

        bottomSheetBehavior = BottomSheetBehavior.from(binding.standardBottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.saveFlags = BottomSheetBehavior.SAVE_ALL

        setUpBottomSheetPeekHeight()
        setUpBottomSheetHeader()
        loadMap()
        activityViewModel.connectStomp(activityViewModel.authentication.jwt,this)


        binding.navigationHeaderViewHeader.setTitle(getString(R.string.home_title))

        return binding.root
    }

    private fun loadMap(){
        val origin = LatLng(
            activityViewModel.position.latitude.toDouble(),
            activityViewModel.position.longitude.toDouble()
        )


        mapsFragment = GoogleMapsFragment(origin,object : GoogleMapsFragment.GoogleMapReadyCallBack{
            override fun onMapReady(mapsFragment: GoogleMapsFragment) {
                mapsFragment.enableMyLocation()
            }
        })

        binding.navigationHeaderViewHeader.hideSecondaryButton()

        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.add(R.id.maps_container,mapsFragment)
            ?.commit()
    }

    override fun onStart() {
        super.onStart()
        binding.layoutShippingStatus.root.visibility = View.GONE
    }

    private fun setUpBottomSheetPeekHeight(){
        binding.layoutSheetHeader.root.doOnLayout {
            bottomSheetBehavior.setPeekHeight(it.height,false)
        }
    }

    private fun setUpBottomSheetHeader(){
        bottomSheetBehavior.addBottomSheetCallback(object:BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState==BottomSheetBehavior.STATE_EXPANDED){
                    binding.layoutSheetHeader.imageViewToggle.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                }
                if (newState==BottomSheetBehavior.STATE_COLLAPSED){
                    binding.layoutSheetHeader.imageViewToggle.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {}

        })
    }

    override fun onShippingNotificationReceived(shippingNotification: ShippingNotification) {
        binding.layoutShippingStatus.root.visibility = View.VISIBLE
        binding.layoutShippingStatus.textViewStoreName.text = shippingNotification.origin.owner
        binding.layoutSheetHeader.textViewStatus.setText(R.string.home_order_received)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        val driverPosition = LatLng(
            activityViewModel.position.latitude.toDouble(),
            activityViewModel.position.longitude.toDouble()
        )

        val customerPosition = LatLng(
            shippingNotification.origin.latitude.toDouble(),
            shippingNotification.origin.longitude.toDouble()
        )

        mapsFragment.drawPolylineBetweenTwoPoints(driverPosition,customerPosition,"Tú",shippingNotification.origin.owner)
    }

}