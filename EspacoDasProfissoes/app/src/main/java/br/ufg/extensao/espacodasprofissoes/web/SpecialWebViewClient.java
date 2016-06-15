package br.ufg.extensao.espacodasprofissoes.web;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class SpecialWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.getSettings().setJavaScriptEnabled(true);
        view.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        view.loadUrl(url);
        view.setScrollbarFadingEnabled(true);
        view.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);
        return true;
    }
}
