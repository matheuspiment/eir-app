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
        
        TextView number = (TextView) findViewById(R.id.number_pharmacy);
        number.setText(pharmacy.getNumber());
        
        TextView complement = (TextView) findViewById(R.id.complement_pharmacy);
        complement.setText(pharmacy.getComplement());
        
        TextView region = (TextView) findViewById(R.id.region_pharmacy);
        region.setText(pharmacy.getRegion());
        
        TextView zipcode = (TextView) findViewById(R.id.zipcode_pharmacy);
        zipcode.setText(pharmacy.getZipcode());
        
        TextView city = (TextView) findViewById(R.id.city_pharmacy);
        city.setText(pharmacy.getCity());
        
        TextView state = (TextView) findViewById(R.id.state_pharmacy);
        state.setText(pharmacy.getState());
        
        TextView country = (TextView) findViewById(R.id.country_pharmacy);
        country.setText(pharmacy.getCountry());
        
        TextView lat = (TextView) findViewById(R.id.lat_pharmacy);
        lat.setText(pharmacy.getLat());
        
        TextView lgn = (TextView) findViewById(R.id.lgn_pharmacy);
        lgn.setText(pharmacy.getLgn());

    }
}

