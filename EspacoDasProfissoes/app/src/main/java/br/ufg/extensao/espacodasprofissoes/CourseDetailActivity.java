package br.ufg.extensao.espacodasprofissoes;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import br.ufg.extensao.espacodasprofissoes.model.Course;
import br.ufg.extensao.espacodasprofissoes.model.PageContent;
import br.ufg.extensao.espacodasprofissoes.web.CourseWebInterface;
import br.ufg.extensao.espacodasprofissoes.web.RouteWebInterface;

public class CourseDetailActivity extends AppCompatActivity {

    private ViewPager viewPager;

    private Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        course = EventBus.getDefault().removeStickyEvent(Course.class);

        setupToolbar();
        setupViewPager();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(course.getName());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setupViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        WebFragment cursos = new WebFragment();
        cursos.setPageContent(PageContent.CURSOS_INFO);

        WebFragment programacao = new WebFragment();
        programacao.setPageContent(PageContent.CURSOS_PROGRAMACAO);

        CourseWebInterface interfaceWeb = new CourseWebInterface(this);
        interfaceWeb.setCurso(course.getName(),course.getId(), course.getRegionalId());
        programacao.setWebAppInterface(interfaceWeb);
        cursos.setWebAppInterface(interfaceWeb);

        viewPagerAdapter.addFragment(cursos, getString(R.string.tab_info));
        viewPagerAdapter.addFragment(programacao, getString(R.string.tab_agenda));
        viewPager.setAdapter(viewPagerAdapter);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);

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
}
