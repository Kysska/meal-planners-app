package com.example.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.example.ui.databinding.SearchToolbarBinding
import timber.log.Timber

class SearchBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    lateinit var binding: SearchToolbarBinding
    private var hint: String = ""
    private var clearButtonClickListener: OnClearSearchClickListener? = null
    private var searchActionListener: OnSearchActionListener? = null

    interface OnSearchActionListener {
        fun onSearchSubmitted(query: String)
    }

    interface OnClearSearchClickListener {
        fun onClearButtonClicked()
    }

    fun setText(text: String?) {
        binding.searchEditText.setText(text)
    }

    fun getText(): String {
        return binding.searchEditText.text.toString()
    }

    fun clear() {
        binding.searchEditText.setText("")
    }

    private fun clearBackButton() {
        clear()
        clearButtonClickListener?.onClearButtonClicked()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        binding = SearchToolbarBinding.inflate(LayoutInflater.from(context), this, true)
        binding.searchEditText.hint = hint
        binding.deleteTextButton.setOnClickListener {
            clearBackButton()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        observeActionListener()
        binding.searchEditText.doAfterTextChanged { text ->
            if (!text.isNullOrEmpty() && !binding.deleteTextButton.isVisible) {
                binding.deleteTextButton.visibility = View.VISIBLE
            }
            if (text.isNullOrEmpty() && binding.deleteTextButton.isVisible) {
                binding.deleteTextButton.visibility = View.GONE
            }
        }
    }

    private fun observeActionListener() {
        binding.searchEditText.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                actionId == EditorInfo.IME_ACTION_DONE ||
                (event?.keyCode == KeyEvent.KEYCODE_ENTER)) {

                val query = binding.searchEditText.text.toString().trim()
                Timber.tag("search").d(query)
                if (query.length > MIN_LENGTH) {
                    searchActionListener?.onSearchSubmitted(query)
                }

                return@setOnEditorActionListener true
            }
            false
        }
    }

    fun setOnSearchActionListener(listener: OnSearchActionListener) {
        searchActionListener = listener
    }

    fun setOnClearButtonClickListener(listener: OnClearSearchClickListener) {
        clearButtonClickListener = listener
    }

    companion object {
        const val MIN_LENGTH = 3
    }
}
