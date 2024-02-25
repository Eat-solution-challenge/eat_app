package com.example.eat.main.findAmount

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.eat.R

fun getBannerItemList(context: Context): List<BannerItem> {
    return listOf(
        BannerItem(R.drawable.banner_image1),
        BannerItem(R.drawable.banner_image2),
        BannerItem(R.drawable.banner_image_3),
        BannerItem(R.drawable.banner_image4),
        BannerItem(R.drawable.banner_image5)
    )
}
val GridItemList = listOf(
    GridItem(R.drawable.kor, "한식"),
    GridItem(R.drawable.jp, "일식"),
    GridItem(R.drawable.ch, "중식"),
    GridItem(R.drawable.pasta, "양식"),
    GridItem(R.drawable.korean_snack, "분식"),
    GridItem(R.drawable.asian, "아시안"),
    GridItem(R.drawable.snack, "간식"),
    GridItem(R.drawable.dessert, "디저트"),
    GridItem(R.drawable.etc, "기타")
)
