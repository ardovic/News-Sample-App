package com.ardovic.news.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel

internal class NewsDetailsComponentViewModel(application: Application): AndroidViewModel(application) {

    val newsDetailsComponent: NewsDetailsComponent by lazy {
        DaggerNewsDetailsComponent.builder()
            .dependencies(application.newsDetailsDependenciesProvider.dependencies)
            .build()
    }
}
