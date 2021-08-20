package com.ardovic.news.details

import android.app.Application
import android.content.Context
import com.ardovic.news.api.NewsService
import dagger.Component
import dagger.Module
import dagger.Subcomponent
import javax.inject.Scope

@Scope
internal annotation class NewsDetailsScope

@[NewsDetailsScope Component(
    dependencies = [NewsDetailsDependencies::class],
    modules = [NewsDetailsModule::class]
)]
interface NewsDetailsComponent {

    fun inject(fragment: ArticlesFragment)

    @Component.Builder
    interface Builder {

        fun dependencies(newsDetailsDependencies: NewsDetailsDependencies): Builder

        fun build(): NewsDetailsComponent
    }
}

@Module
class NewsDetailsModule {

}

interface NewsDetailsDependencies {

    val newsService: NewsService
}

interface NewsDetailsDependenciesProvider {

    val dependencies: NewsDetailsDependencies
}

val Context.newsDetailsDependenciesProvider: NewsDetailsDependenciesProvider
    get() = when (this) {
        is NewsDetailsDependenciesProvider -> this
        is Application -> error("Application class must implement NewsDetailsDependenciesProvider interface")
        else -> applicationContext.newsDetailsDependenciesProvider
    }
