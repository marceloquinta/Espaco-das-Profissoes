package br.ufg.extensao.espacodasprofissoes.web;

import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class SpecialWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url != null && url.startsWith("http://") ) {
            view.getContext().startActivity(
                    new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            return true;
        }else{
            view.getSettings().setJavaScriptEnabled(true);
            view.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            view.loadUrl(url);
            view.setScrollbarFadingEnabled(true);
            view.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);
            return true;
        }


    }
}
