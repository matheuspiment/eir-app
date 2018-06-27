package br.ufg.inf.es.eir.presenter.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.ufg.inf.es.eir.R;

public class UnitListFragment extends Fragment {
    private static final String TAG = "Unidades";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_unit_list,container,false);

        return view;
    }
}
