package br.ufg.extensao.espacodasprofissoes.web;

import android.content.Context;
import android.content.res.AssetManager;
import android.webkit.JavascriptInterface;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.InputStream;

import br.ufg.extensao.espacodasprofissoes.model.Route;

/**
 * Created by marceloquinta on 15/06/16.
 */
public class RouteWebInterface extends WebAppInterface {

    private Route route;

    /** Instantiate the interface and set the context */
    public RouteWebInterface(Context c) {
        super(c);
    }

    @JavascriptInterface
    public int getRotaId() {
        return 0;
    }

    @JavascriptInterface
    public void setRota(int rotaId, String routeName)
    {
        if(route == null){
            route = new Route();
        }

        route.setId(rotaId);
        route.setName(routeName);
        EventBus.getDefault().post(route);
    }
}
