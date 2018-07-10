package br.ufg.inf.es.eir.presenter.remedy.list;;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import br.ufg.inf.es.eir.R;
import br.ufg.inf.es.eir.data.RemedyDAO;
import br.ufg.inf.es.eir.model.Remedy;
import br.ufg.inf.es.eir.presenter.BaseFragment;
import br.ufg.inf.es.eir.presenter.remedy.RemedyPage;
import br.ufg.inf.es.eir.web.WebRemedies;

/**
 * A simple {@link Fragment} subclass.
 */
public class RemedyListFragment extends BaseFragment {

    private List<Remedy> remedyList;
    private AdapterRemedyList adapter;

    public RemedyListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_remedy_list, container, false);
        remedyList = new LinkedList<>();
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
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.remedy_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AdapterRemedyList(remedyList, getActivity());
        recyclerView.setAdapter(adapter);
    }

    public void updateRecycler() {
        showDialogWithMessage(getString(R.string.load_remedies));

        List<Remedy> remediesFromDB = getRemediesFromDB();

        remedyList = remediesFromDB;

        updateAdapterDataSet(remediesFromDB);

        tryRemediesFromWeb();

        setupView();

        dismissDialog();
    }

    public void setupView() {
        if (adapter.getRemedies().size() > 0) {
            TextView text = (TextView) getView().findViewById(R.id.is_empty);
            text.setVisibility(View.GONE);

            RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.remedy_list);
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            TextView text = (TextView) getView().findViewById(R.id.is_empty);
            text.setVisibility(View.VISIBLE);

            RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.remedy_list);
            recyclerView.setVisibility(View.GONE);
        }



    }

    public void updateAdapterDataSet(List<Remedy> remedies) {
        adapter.setRemedies(remedies);
        adapter.notifyDataSetChanged();
    }

    public List<Remedy> getRemediesFromDB() {
        RemedyDAO dao = new RemedyDAO(getActivity());
        return dao.getAll();
    }

    private void tryRemediesFromWeb() {
        WebRemedies webRemedies = new WebRemedies();
        webRemedies.call();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Exception exception) {
        dismissDialog();
        showAlert(exception.getMessage());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(List<Remedy> remedies) {
        dismissDialog();

        updateRemediesDB(remedies);

        updateAdapterDataSet(getRemediesFromDB());

        setupView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Remedy remedy) {
        Intent intent = new Intent(getContext(), RemedyPage.class);
//        intent.putExtra("remedy", (Serializable) remedy);

        EventBus.getDefault().unregister(this);
        EventBus.getDefault().postSticky(remedy);

        startActivity(intent);
//        RemedyDAO dao = new RemedyDAO(getActivity());
//        adapter.setRemedies(dao.getAll());
//        adapter.notifyDataSetChanged();
    }

    public void updateRemediesDB(List<Remedy> remedies) {
        RemedyDAO dao = new RemedyDAO(getActivity());

        for (Remedy remedy : remedies) {
            dao.create(remedy);

        }
    }

    public List<Remedy> getRemedyList() {
        return remedyList;
    }

}
