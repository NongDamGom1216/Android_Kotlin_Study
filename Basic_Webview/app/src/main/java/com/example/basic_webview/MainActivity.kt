package com.example.basic_webview

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ContextMenu
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.webkit.WebViewClient
import android.widget.Toast
import com.example.basic_webview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.content.webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
            loadUrl("http://nongdamgom1216.github.io/Portfolio/")
        }

        binding.content.urlEditText.setOnEditorActionListener { _, actionId, _ ->
            // 표현식(람다 함수는 마지막 줄이 리턴)
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                var url = binding.content.urlEditText.text.toString()
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "https://$url"
                    binding.content.urlEditText.setText(url)
                }
                binding.content.webView.loadUrl(url)
                true
            } else {
                false
            }
        }
        registerForContextMenu(binding.content.webView)
    }

    override fun onBackPressed() {
        if (binding.content.webView.canGoBack()) {
            binding.content.webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.action_google, R.id.action_home -> {
                binding.content.webView.loadUrl("http://www.google.com")
                return true
            }
            R.id.action_naver -> {
                binding.content.webView.loadUrl("http://www.naver.com")
                return true
            }
            R.id.action_mypage -> {
                binding.content.webView.loadUrl("http://nongdamgom1216.github.io/Portfolio/")
                return true
            }
            R.id.action_call -> {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:031-123-4567")
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
                return true
            }
            R.id.action_send_text -> {
                Toast.makeText(this,"문자 보내기",Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.action_email -> {
                Toast.makeText(this,"이메일 보내기",Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_context, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.action_share -> {
                Toast.makeText(this,"페이지 공유 클릭!",Toast.LENGTH_SHORT).show()
            }
            R.id.action_broswer -> {
                Toast.makeText(this,"기본 브라우저에서 열기 클릭!",Toast.LENGTH_SHORT).show()
            }
        }

        return super.onContextItemSelected(item)
    }
}