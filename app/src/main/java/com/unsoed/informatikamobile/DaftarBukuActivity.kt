package com.unsoed.informatikamobile

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.unsoed.informatikamobile.data.model.BookDoc
import com.unsoed.informatikamobile.databinding.ActivityDaftarBukuBinding
import com.unsoed.informatikamobile.ui.adapter.BookAdapter
import com.unsoed.informatikamobile.ui.adapter.OnBookClickListener
import com.unsoed.informatikamobile.ui.fragment.BookDetailFragment
import com.unsoed.informatikamobile.viewmodel.MainViewModel

class DaftarBukuActivity : AppCompatActivity(), OnBookClickListener {
    private lateinit var binding: ActivityDaftarBukuBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarBukuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = BookAdapter(emptyList(), this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        viewModel.books.observe(this) {
            adapter.setData(it)
        }

        viewModel.fetchBooks("kotlin programming")
    }

    override fun onBookClick(book: BookDoc) {
        BookDetailFragment(
            book.title ?: "-",
            book.authorName?.joinToString(", ") ?: "Unknown Author",
            "${book.firstPublishYear}",
            book.coverId ?: 0
        ).show(supportFragmentManager, BookDetailFragment::class.java.simpleName)
    }
}