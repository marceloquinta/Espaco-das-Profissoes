package br.ufg.extensao.espacodasprofissoes;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import org.greenrobot.eventbus.EventBus;

import br.ufg.extensao.espacodasprofissoes.model.PageContent;
import br.ufg.extensao.espacodasprofissoes.model.Route;
import br.ufg.extensao.espacodasprofissoes.web.RouteWebInterface;
import br.ufg.extensao.espacodasprofissoes.web.SpecialWebViewClient;

public class RouteDetailActivity extends AppCompatActivity {

    private Route route;

    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_detail);

        route = EventBus.getDefault().removeStickyEvent(Route.class);
        setupToolbar();
        setupFragment();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(route.getName());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void setupFragment(){
        WebFragment rotasFragment = new WebFragment();
        rotasFragment.setPageContent(PageContent.ROTAS_CARD);
        RouteWebInterface interfaceWeb = new RouteWebInterface(this);
        interfaceWeb.setRota(route.getId(), route.getName());
        rotasFragment.setWebAppInterface(interfaceWeb);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, rotasFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);

    }
}
