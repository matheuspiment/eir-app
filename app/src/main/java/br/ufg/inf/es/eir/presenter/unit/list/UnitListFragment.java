package br.ufg.inf.es.eir.presenter.unit.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.LinkedList;
import java.util.List;

import br.ufg.inf.es.eir.R;
import br.ufg.inf.es.eir.data.UnitDAO;
import br.ufg.inf.es.eir.model.Unit;
import br.ufg.inf.es.eir.presenter.BaseFragment;
import br.ufg.inf.es.eir.presenter.unit.UnitPage;
import br.ufg.inf.es.eir.web.WebUnits;

public class UnitListFragment extends BaseFragment {

    private List<Unit> unitList;
    private AdapterUnitList adapter;

    public UnitListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_unit_list, container, false);
        unitList = new LinkedList<>();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        initRecycler();
        updateRecycler();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void initRecycler(){
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.unit_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AdapterUnitList(unitList, getActivity());
        recyclerView.setAdapter(adapter);
    }

    public void updateRecycler() {
        showDialogWithMessage(getString(R.string.load_remedies));

        List<Unit> unitsFromDB = getUnitsFromDB();

        unitList = unitsFromDB;

        updateAdapterDataSet(unitsFromDB);

        tryRemediesFromWeb();

        setupView();

        dismissDialog();
    }

    public void setupView() {
        if (adapter.getUnits().size() > 0) {
            TextView text = (TextView) getView().findViewById(R.id.units_is_empty);
            text.setVisibility(View.GONE);

            RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.unit_list);
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            TextView text = (TextView) getView().findViewById(R.id.units_is_empty);
            text.setVisibility(View.VISIBLE);

            RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.unit_list);
            recyclerView.setVisibility(View.GONE);
        }

    }

    public void updateAdapterDataSet(List<Unit> units) {
        adapter.setUnits(units);
        adapter.notifyDataSetChanged();
    }

    public List<Unit> getUnitsFromDB() {
        UnitDAO dao = new UnitDAO(getActivity());
        return dao.getAll();
    }

    private void tryRemediesFromWeb() {
        WebUnits webRemedies = new WebUnits();
        webRemedies.call();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Exception exception) {
        dismissDialog();
        showAlert(exception.getMessage());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(List<Unit> units) {
        dismissDialog();

        updateUnitsDB(units);

        updateAdapterDataSet(getUnitsFromDB());

        setupView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Unit unit) {
        Intent intent = new Intent(getContext(), UnitPage.class);
//        intent.putExtra("remedy", (Serializable) remedy);

        EventBus.getDefault().unregister(this);
        EventBus.getDefault().postSticky(unit);

        startActivity(intent);
//        RemedyDAO dao = new RemedyDAO(getActivity());
//        adapter.setRemedies(dao.getAll());
//        adapter.notifyDataSetChanged();
    }

    public void updateUnitsDB(List<Unit> units) {
        UnitDAO dao = new UnitDAO(getActivity());

        for (Unit remedy : units) {
            dao.create(remedy);

        }
    }

    public List<Unit> getUnitList() {
        return unitList;
    }

}
