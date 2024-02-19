package com.example.eat.main.findAmount

import android.view.View

interface Interaction: View.OnClickListener {

    fun onBannerItemClicked(bannerItem: BannerItem)

}