package com.carousel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.carousel.adapters.ListRvAdapter
import com.carousel.databinding.ActivityMainBinding
import com.carousel.models.RvItem

class MainActivity : AppCompatActivity() {
    private lateinit var sliderRunnable: Runnable
    private lateinit var sliderHandler: Handler
    private lateinit var binding: ActivityMainBinding

    private val SLIDE_DELAY = 5000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpAutoSliding()
        setUpDataRv()
    }

    private fun setUpDataRv() {
        binding.mainRv.adapter = ListRvAdapter(getRvData())
        binding.mainRv.layoutManager = LinearLayoutManager(applicationContext)
    }

    private fun getRvData(): List<RvItem> {
        val rvList = mutableListOf<RvItem>()
        rvList.add(RvItem(1,1))
        for (i in 2..10){
            rvList.add(RvItem(i,2))
        }
        return rvList
    }

    private fun setUpAutoSliding() {
//        View pager setup
        val imagesList = getImagesList()
        binding.mainVp.adapter = ViewPagerAdapter(imagesList)
        binding.mainVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.dotsIndicator.createIndicators(imagesList.size-2,0)
        binding.mainVp.setCurrentItem(1,false)
//        Sliding
        sliderHandler = Handler(Looper.getMainLooper())
        sliderRunnable = Runnable {
            val currentItem = binding.mainVp.currentItem
            if (currentItem == (binding.mainVp.adapter!!.itemCount - 2)) {
                binding.mainVp.currentItem = 1
            }else {
                binding.mainVp.currentItem = currentItem + 1
            }
            sliderHandler.postDelayed(sliderRunnable, SLIDE_DELAY)
        }

        binding.mainVp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position!=imagesList.size-1 && position!=0){
                    binding.dotsIndicator.animatePageSelected(position-1)
                }

            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                if (state==ViewPager2.SCROLL_STATE_IDLE){
                    sliderHandler.removeCallbacks(sliderRunnable)
                    sliderHandler.postDelayed(sliderRunnable,SLIDE_DELAY)
                    if (binding.mainVp.currentItem == binding.mainVp.adapter!!.itemCount-1){
                        binding.mainVp.setCurrentItem(1,false)
                        binding.dotsIndicator.animatePageSelected(0)
                    }
                    if (binding.mainVp.currentItem == 0){
                        binding.mainVp
                            .setCurrentItem(binding.mainVp.adapter!!.itemCount-2,
                                false)
                        binding.dotsIndicator.animatePageSelected(imagesList.size-2)
                    }
                }
            }
        })
    }

    private val TAG = "MainActivityTest"

    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(sliderRunnable)
    }

    override fun onResume() {
        super.onResume()
        sliderHandler.postDelayed(sliderRunnable, SLIDE_DELAY)
    }

    private fun getImagesList(): List<String> {
        val imagesList = mutableListOf<String>()
//        Last item duplicate
        imagesList.add("https://image.shutterstock.com/image-vector/brochure-template-layout-cover-design-600w-643120261.jpg")
        imagesList.add("https://image.shutterstock.com/image-vector/landscape-brochure-design-corporate-business-600w-611587748.jpg")
        imagesList.add("https://image.shutterstock.com/image-vector/brochure-template-layout-cover-design-600w-643120261.jpg")
        imagesList.add("https://image.shutterstock.com/image-vector/landscape-brochure-design-corporate-business-600w-611587748.jpg")
        imagesList.add("https://image.shutterstock.com/image-vector/brochure-template-layout-cover-design-600w-643120261.jpg")
//        First item duplicate
        imagesList.add("https://image.shutterstock.com/image-vector/landscape-brochure-design-corporate-business-600w-611587748.jpg")
        return imagesList
    }
}