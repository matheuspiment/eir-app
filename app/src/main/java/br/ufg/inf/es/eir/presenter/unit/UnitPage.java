package br.ufg.inf.es.eir.presenter.unit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import br.ufg.inf.es.eir.R;
import br.ufg.inf.es.eir.model.Remedy;

public class UnitPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remedy_page);

//        Intent i = getIntent();
//        Remedy remedy = (Remedy) i.getSerializableExtra("remedy");
//        setupView(remedy);

        Remedy remedy = EventBus.getDefault().removeStickyEvent(Remedy.class);
        setupView(remedy);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void setupView(Remedy remedy) {
        setTitle(remedy.getName());

        TextView name = (TextView) findViewById(R.id.name);
        name.setText(remedy.getName());
    }
}
