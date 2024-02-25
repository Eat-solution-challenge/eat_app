package com.example.eat.main.findAmount

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eat.R


class GridRecyclerViewAdapter : RecyclerView.Adapter<GridRecyclerViewAdapter.GridItemViewHolder>() {
    private var gridItemList: List<GridItem>? = null
    private var onItemClickListener: OnItemClickListener? = null
    // 아이템 클릭 리스너 인터페이스 정의
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun submitList(list: List<GridItem>?) {
        gridItemList = list
        notifyDataSetChanged()
    }

    // 아이템 클릭 리스너 설정 메서드
    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridItemViewHolder {
        return GridItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout_grid, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return gridItemList?.size ?: 0
    }

    override fun onBindViewHolder(holder: GridItemViewHolder, position: Int) {
        gridItemList?.let {
            holder.bind(it[position])
        }
    }

    inner class GridItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val imageView: ImageView = itemView.findViewById(R.id.iv_grid_image)
        private val textView: TextView = itemView.findViewById(R.id.tv_grid_title)

        init {
            // 아이템 뷰에 클릭 리스너 설정
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            // 클릭 이벤트가 발생하면 등록된 리스너에게 해당 아이템의 위치를 전달
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onItemClickListener?.onItemClick(position)
            }
        }

        fun bind(gridItem: GridItem) {
            imageView.setImageResource(gridItem.image)
            textView.text = gridItem.title
        }
    }

    class GridSpacingItemDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)
            val position = parent.getChildAdapterPosition(view)
            val spanCount = (parent.layoutManager as GridLayoutManager).spanCount
            val column = position % spanCount
            outRect.left = spacing - column * spacing / spanCount
            outRect.right = (column + 1) * spacing / spanCount
            if (position >= spanCount) {
                outRect.top = spacing
            }
        }
    }

    // RecyclerView에 아이템 간격 설정 적용
    fun applyItemSpacing(recyclerView: RecyclerView, spacing: Int) {
        recyclerView.addItemDecoration(GridSpacingItemDecoration(spacing))
    }

}
