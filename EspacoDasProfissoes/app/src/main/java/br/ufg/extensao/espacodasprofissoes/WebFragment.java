package br.ufg.extensao.espacodasprofissoes;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * A simple {@link Fragment} subclass.
 */
public class WebFragment extends Fragment {

    private WebView webview;


    public WebFragment() {
        // Required empty public constructor
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
        webview.addJavascriptInterface(new WebAppInterface(getActivity()), "Android");
        String folderPath = "file:android_asset/";
        String fileName = "index.html";
        String file = folderPath + fileName;
        webview.loadUrl(file);
    }


    private class SpecialWebViewClient extends WebViewClient {
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

}
