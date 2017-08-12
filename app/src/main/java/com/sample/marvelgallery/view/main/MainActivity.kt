package com.sample.marvelgallery.view.main

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.Window
import com.sample.marvelgallery.R
import com.sample.marvelgallery.data.MarvelRepository
import com.sample.marvelgallery.model.MarvelCharacter
import com.sample.marvelgallery.presenter.MainPresenter
import com.sample.marvelgallery.view.common.BaseActivityWithPresenter
import com.sample.marvelgallery.view.common.bindToSwipeRefresh
import com.sample.marvelgallery.view.common.toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivityWithPresenter(), MainView { // 1

    override var refresh by bindToSwipeRefresh(R.id.swipeRefreshView) // 2
    override val presenter by lazy { MainPresenter(this, MarvelRepository.get()) } // 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        swipeRefreshView.setOnRefreshListener { presenter.onRefresh() } // 4
        presenter.onViewCreated() // 4
    }

    override fun show(items: List<MarvelCharacter>) {
        val categoryItemAdapters = items.map(::CharacterItemAdapter)
        recyclerView.adapter = MainListAdapter(categoryItemAdapters)
    }

    override fun showError(error: Throwable) {
        toast("Error: ${error.message}") // 2
        error.printStackTrace()
    }
}