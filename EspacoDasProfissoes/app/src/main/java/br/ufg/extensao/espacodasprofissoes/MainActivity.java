package br.ufg.extensao.espacodasprofissoes;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.PorterDuff;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import br.ufg.extensao.espacodasprofissoes.model.Course;
import br.ufg.extensao.espacodasprofissoes.model.PageContent;
import br.ufg.extensao.espacodasprofissoes.model.Route;
import br.ufg.extensao.espacodasprofissoes.web.CourseWebInterface;
import br.ufg.extensao.espacodasprofissoes.web.RouteWebInterface;


public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();
        setupViewPager();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        super.onResume();
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        WebFragment cursos = new WebFragment();
        cursos.setPageContent(PageContent.CURSOS);
        cursos.setWebAppInterface(new CourseWebInterface(this));

        WebFragment programacao = new WebFragment();
        programacao.setPageContent(PageContent.PROGRAMA);

        WebFragment rotas = new WebFragment();
        rotas.setPageContent(PageContent.ROTAS);
        rotas.setWebAppInterface(new RouteWebInterface(this));

        viewPagerAdapter.addFragment(cursos, getString(R.string.tab_courses));
        viewPagerAdapter.addFragment(programacao, getString(R.string.tab_agenda));
        viewPagerAdapter.addFragment(rotas, getString(R.string.tab_routes));
        viewPagerAdapter.addFragment(new MapFragment(), getString(R.string.tab_places));
        viewPager.setAdapter(viewPagerAdapter);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        setupTabIcons(tabs);
    }

    private void setupTabIcons(TabLayout tabLayout) {
        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText(getString(R.string.tab_courses));
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_school_black_24dp, 0, 0);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText(getString(R.string.tab_agenda));
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_event_note_black_24dp, 0, 0);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText(getString(R.string.tab_routes));
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_timeline_black_24dp, 0, 0);

        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFour.setText(getString(R.string.tab_places));
        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_place_black_24dp, 0, 0);

        tabLayout.getTabAt(0).setCustomView(tabOne);
        tabLayout.getTabAt(1).setCustomView(tabTwo);
        tabLayout.getTabAt(2).setCustomView(tabThree);
        tabLayout.getTabAt(3).setCustomView(tabFour);
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    public void hideKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Subscribe
    public void onEvent(Course course){
        Intent intent = new Intent(this, CourseDetailActivity.class);
        EventBus.getDefault().unregister(this);
        EventBus.getDefault().postSticky(course);
        startActivity(intent);
    }

    @Subscribe
    public void onEvent(Route route){
        Intent intent = new Intent(this, RouteDetailActivity.class);
        EventBus.getDefault().unregister(this);
        EventBus.getDefault().postSticky(route);
        startActivity(intent);
    }

}

