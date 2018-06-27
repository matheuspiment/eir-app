package br.ufg.inf.es.eir.presenter.list;;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;

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
import br.ufg.inf.es.eir.web.WebRemedies;

/**
 * A simple {@link Fragment} subclass.
 */
public class RemedyListFragment extends BaseFragment {

    private List<Remedy> remedyList;
    private AdapterRemedy adapter;

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
//        getRemedies();
        tryRemedies();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void initRecycler(){
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.remedy_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AdapterRemedy(remedyList, getActivity());
        recyclerView.setAdapter(adapter);
    }


    public void getRemedies(){
        List<Remedy> remedyList = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            Remedy remedy = new Remedy();
            remedy.setId(i);
            remedy.setName("remedio" + i);
            remedyList.add(remedy);
        }

        showDialogWithMessage(getString(R.string.load_remedies));
        RemedyDAO dao = new RemedyDAO(getActivity());
        adapter.setRemedies(remedyList);
        adapter.notifyDataSetChanged();
        dismissDialog();
    }

    private void tryRemedies() {
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
        adapter.setRemedies(remedies);
        adapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Remedy remedy) {
        RemedyDAO dao = new RemedyDAO(getActivity());
        adapter.setRemedies(dao.getAll());
        adapter.notifyDataSetChanged();
    }

    public void add(){
        Remedy remedy = new Remedy();
        remedy.setName("NEW");
        RemedyDAO dao = new RemedyDAO(getActivity());
        dao.create(remedy);

        adapter.setRemedies(dao.getAll());
        adapter.notifyDataSetChanged();
    }

}
