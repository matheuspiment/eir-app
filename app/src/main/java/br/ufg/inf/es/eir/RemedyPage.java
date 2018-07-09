package br.ufg.inf.es.eir;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import br.ufg.inf.es.eir.model.Remedy;

public class RemedyPage extends AppCompatActivity {
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

        TextView name = (TextView) findViewById(R.id.name_remedy);
        name.setText(remedy.getName());

        TextView amount = (TextView) findViewById(R.id.type_remedy);
        amount.setText(remedy.getType());

        TextView content = (TextView) findViewById(R.id.content_remedy);
        content.setText(remedy.getContent());

        TextView lab = (TextView) findViewById(R.id.lab_remedy);
        lab.setText(remedy.getLab());

        TextView code = (TextView) findViewById(R.id.code_remedy);
        code.setText(remedy.getCode());

        ImageView img = (ImageView) findViewById(R.id.img_remedy);
        //img.setImageBitmap(remedy.getImage());      app:srcCompat="@mipmap/ic_launcher"
        //img.setImageURI(remedy.getImage ());

        TextView composition = (TextView) findViewById(R.id.composition_remedy);
        composition.setText(String.valueOf(remedy.getComposition()));

    }
}