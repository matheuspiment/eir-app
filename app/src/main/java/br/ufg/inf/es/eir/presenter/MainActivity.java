package br.ufg.inf.es.eir.presenter;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import br.ufg.inf.es.eir.R;
import br.ufg.inf.es.eir.model.Remedy;
import br.ufg.inf.es.eir.model.Unit;
import br.ufg.inf.es.eir.presenter.remedy.list.RemedyListFragment;
import br.ufg.inf.es.eir.presenter.unit.list.UnitListFragment;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String TAG = "MainActivity";
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    private RemedyListFragment remedyListFragment;
    private UnitListFragment unitListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the toolbar with menu
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);

        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());

        remedyListFragment = new RemedyListFragment();
        unitListFragment = new UnitListFragment();

        adapter.addFragment(remedyListFragment, "Remedios");
        adapter.addFragment(unitListFragment, "Unidades");

        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String userInput = newText.toLowerCase();

        List<Remedy> newRemedies = new ArrayList<>();
        List<Unit> newUnits = new ArrayList<>();

        List<Remedy> remedies = remedyListFragment.getRemedyList();
        List<Unit> units = unitListFragment.getUnitList();
        
        for (Remedy remedy : remedies) {
            if (remedy.getName().toLowerCase().contains(userInput)) {
                newRemedies.add(remedy);
            }
        }

        for (Unit unit : units) {
            if (unit.getName().toLowerCase().contains(userInput) ||
                    unit.getCity().toLowerCase().contains(userInput)) {
                newUnits.add(unit);
            }
        }

        remedyListFragment.updateAdapterDataSet(newRemedies);
        unitListFragment.updateAdapterDataSet(newUnits);

        remedyListFragment.setupView();
        unitListFragment.setupView();

        return true;
    }
}