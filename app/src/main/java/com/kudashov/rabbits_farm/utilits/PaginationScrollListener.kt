package com.kudashov.rabbits_farm.utilits

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(var layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()


        if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
            && firstVisibleItemPosition >= 0
        ) {
            loadNextPage()
        }

    }

    protected abstract fun loadNextPage()
}