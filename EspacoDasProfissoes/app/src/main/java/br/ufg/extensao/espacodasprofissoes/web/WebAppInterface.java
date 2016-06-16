package br.ufg.extensao.espacodasprofissoes.web;

import android.content.Context;
import android.webkit.JavascriptInterface;

/**
 * Created by marceloquinta on 15/06/16.
 */
public class WebAppInterface {

    private Context mContext;

    public WebAppInterface(Context context){
        this.mContext = context;
    }

    @JavascriptInterface
    public void dummy() {

    }

    public Context getmContext() {
        return mContext;
    }
}
