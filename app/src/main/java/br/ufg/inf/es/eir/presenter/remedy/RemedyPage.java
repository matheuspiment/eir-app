package br.ufg.inf.es.eir.presenter.remedy;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import br.ufg.inf.es.eir.R;
import br.ufg.inf.es.eir.data.UnitDAO;
import br.ufg.inf.es.eir.model.Remedy;
import br.ufg.inf.es.eir.model.Unit;
import br.ufg.inf.es.eir.presenter.BaseActivity;
import br.ufg.inf.es.eir.presenter.unit.UnitPage;
import br.ufg.inf.es.eir.presenter.unit.list.AdapterUnitList;

public class RemedyPage extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remedy_page);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        Remedy remedy = EventBus.getDefault().removeStickyEvent(Remedy.class);
        setupView(remedy);

        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Exception exception) {
        dismissDialog();
        showAlert(exception.getMessage());
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Unit unit) {
        Intent intent = new Intent(this, UnitPage.class);

        EventBus.getDefault().unregister(this);
        EventBus.getDefault().postSticky(unit);

        startActivity(intent);
    }

    public void setupView(Remedy remedy) {
        setTitle(remedy.getName());

        TextView name = (TextView) findViewById(R.id.name_remedy);
        name.setText(remedy.getName());

        TextView type = (TextView) findViewById(R.id.presentation_remedy);
        type.setText(Integer.toString(remedy.getContent()) + " - " + remedy.getType());

        TextView lab = (TextView) findViewById(R.id.lab_remedy);
        lab.setText(remedy.getLab());

        TextView code = (TextView) findViewById(R.id.code_remedy);
        code.setText("Código: " + Integer.toString(remedy.getCode()));

        ImageView img = (ImageView) findViewById(R.id.img_remedy);

        Picasso.get().load(remedy.getImage()).into(img);

        setupList(remedy);
    }

    public void setupList(Remedy remedy) {
        UnitDAO dao = new UnitDAO(this);
        List<Unit> unitList = new LinkedList<>();
        AdapterUnitList adapter;
        ArrayList unitIds = (ArrayList) remedy.getUnits();

        for (int i = 0; i < unitIds.size(); i++) {
            Unit unit = dao.retrieve((Integer) unitIds.get(i));
            unitList.add(unit);
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.remedy_unit_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterUnitList(unitList, this);
        recyclerView.setAdapter(adapter);
    }
}
