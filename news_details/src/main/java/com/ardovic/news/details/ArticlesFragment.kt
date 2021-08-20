package com.ardovic.news.details

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardovic.news.details.databinding.FragmentArticlesBinding
import dagger.Lazy
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class ArticlesFragment : Fragment(R.layout.fragment_articles) {

    @Inject
    internal lateinit var articlesViewModelFactory: Lazy<ArticlesViewModel.Factory>

    private val articlesViewModel: ArticlesViewModel by viewModels { articlesViewModelFactory.get() }
    private val newsDetailsComponentViewModel: NewsDetailsComponentViewModel by viewModels()
    private var adapter: ArticleAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        newsDetailsComponentViewModel.newsDetailsComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val articleAdapter = ArticleAdapter()
        this.adapter = articleAdapter

        lifecycleScope.launchWhenStarted {
            articlesViewModel.articles.collect { articles ->
                adapter?.submitList(articles)
            }
        }

        val binding = FragmentArticlesBinding.bind(view)

        binding.recycler.layoutManager = LinearLayoutManager(context)
        binding.recycler.adapter = articleAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }
}
