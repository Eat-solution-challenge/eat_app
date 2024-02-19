package com.example.eat.main.findAmount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FIndAmountViewModel : ViewModel() {
    private val _bannerItemList=MutableLiveData<List<BannerItem>>()
    private val _gridItemList= MutableLiveData<List<GridItem>>()
    private val _currentPosition=MutableLiveData<Int>()


    val bannerItemList: LiveData<List<BannerItem>>
        get() = _bannerItemList

    val gridItemList: LiveData<List<GridItem>>
        get() = _gridItemList

    val currentPosition: LiveData<Int>
        get() =_currentPosition

    // fragment 특성상 화면 전환간에 view가 늘 새롭게 생성되기 되기 때문에 이미지 배너의 현재 position을 알고 있지 않으면 position은 늘 0이라 화면 전환할 때마다 이미지 배너는 0번째 부터 swipe를 시작할 것

    init {
        // 이미지 목록을 초기화한다
        //_bannerItemList.value = listOf(
        //    BannerItem(R.drawable.banner_image1),
        //    BannerItem(R.drawable.banner_image2),
        //    BannerItem(R.drawable.banner_image_3),
        //    BannerItem(R.drawable.banner_image4)
        // )
    }

    init {
        _currentPosition.value=0
    }

    fun setBannerItems(list: List<BannerItem>){
        _bannerItemList.value=list
    }

    fun setGridItems(list: List<GridItem>)
    {
        _gridItemList.value=list
    }

    fun setCurrentPosition(position: Int){
        _currentPosition.value=position
    }

    fun getCurrentPosition() = _currentPosition.value
}