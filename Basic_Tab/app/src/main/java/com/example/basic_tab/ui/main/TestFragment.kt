package com.example.basic_tab.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.webkit.WebViewClient
import com.example.basic_tab.R
import com.example.basic_tab.databinding.FragmentMainBinding
import com.example.basic_tab.databinding.FragmentTestBinding


class TestFragment : Fragment() {

    private var _binding: FragmentTestBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

        binding.webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
            loadUrl("http://nongdamgom1216.github.io/Portfolio/")
        }

        binding.urlEditText.setOnEditorActionListener { _, actionId, _ ->
            // 표현식(람다 함수는 마지막 줄이 리턴)
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                var url = binding.urlEditText.text.toString()
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "https://$url"
                    binding.urlEditText.setText(url)
                }
                binding.webView.loadUrl(url)
                true
            } else {
                false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}