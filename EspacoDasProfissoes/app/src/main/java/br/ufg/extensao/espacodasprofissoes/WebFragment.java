package br.ufg.extensao.espacodasprofissoes;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import br.ufg.extensao.espacodasprofissoes.model.PageContent;
import br.ufg.extensao.espacodasprofissoes.web.SpecialWebViewClient;
import br.ufg.extensao.espacodasprofissoes.web.WebAppInterface;


/**
 * A simple {@link Fragment} subclass.
 */
public class WebFragment extends Fragment {

    private WebView webview;

    private PageContent pageContent;

    private WebAppInterface webAppInterface;

    public WebFragment() {
        // Required empty public constructor
    }

    public PageContent getPageContent() {
        return pageContent;
    }

    public void setPageContent(PageContent pageContent) {
        this.pageContent = pageContent;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v =  inflater.inflate(R.layout.fragment_web, container, false);

        webview = (WebView) v.findViewById(R.id.webview);
        loadWebView();

        return v;
    }

    public void loadWebView(){
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new SpecialWebViewClient());
        webview.setWebChromeClient(new WebChromeClient());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setAllowContentAccess(true);
        webview.getSettings().setAllowFileAccess(true);
        webview.getSettings().setAllowUniversalAccessFromFileURLs(true);

        webview.addJavascriptInterface(webAppInterface, "Android");
        String folderPath = "file:android_asset/material_html/";
        String fileName = pageContent.getPageName();
        String file = folderPath + fileName;
        webview.loadUrl(file);
    }

    public void setWebAppInterface(WebAppInterface webInterface){
        this.webAppInterface = webInterface;
    }



}
