package br.ufg.inf.es.eir;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import br.ufg.inf.es.eir.model.Pharmacy;
import br.ufg.inf.es.eir.model.Remedy;

public class PharmacyPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_page);

        Intent i = getIntent();
        Pharmacy pharmacy = (Pharmacy) i.getSerializableExtra("pharmacy");
        setupView(pharmacy);

        Pharmacy pharmacy = EventBus.getDefault().removeStickyEvent(pharmacy.class);
        setupView(pharmacy);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void setupView(Pharmacy pharmacy) {
        setTitle(pharmacy.getName());

        TextView name = (TextView) findViewById(R.id.name_pharmacy);
        name.setText(pharmacy.getName());

        TextView phone = (TextView) findViewById(R.id.phone_pharmacy);
        phone.setText(pharmacy.getPhone());

        ImageView img = (ImageView) findViewById(R.id.img_pharmacy);
//        img.setImageURI(pharmacy.getImage ());

        TextView street = (TextView) findViewById(R.id.street_pharmacy);
        street.setText(pharmacy.getStreet());

    }
}

