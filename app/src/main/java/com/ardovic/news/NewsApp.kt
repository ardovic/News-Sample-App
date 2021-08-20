package com.ardovic.news

import android.app.Application
import com.ardovic.news.details.NewsDetailsDependencies
import com.ardovic.news.details.NewsDetailsDependenciesProvider
import com.ardovic.news.di.AppComponent
import com.ardovic.news.di.DaggerAppComponent

class NewsApp : Application(), NewsDetailsDependenciesProvider {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .application(this)
            .apiKey(BuildConfig.NEWS_API_KEY)
            .build()
    }

    override val dependencies: NewsDetailsDependencies = appComponent

    override fun onCreate() {
        super.onCreate()
    }
}
