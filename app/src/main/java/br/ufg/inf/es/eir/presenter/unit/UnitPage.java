package br.ufg.inf.es.eir.presenter.unit;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import br.ufg.inf.es.eir.R;
import br.ufg.inf.es.eir.model.Remedy;
import br.ufg.inf.es.eir.model.Unit;

public class UnitPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_page);

        Unit unit = EventBus.getDefault().removeStickyEvent(Unit.class);
        setupView(unit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void setupView(Unit unit) {
        setTitle(unit.getName());

        TextView name = (TextView) findViewById(R.id.name_pharmacy);
        name.setText(unit.getName());

        TextView phone = (TextView) findViewById(R.id.phone_pharmacy);
        phone.setText(unit.getPhone());

        ImageView img = (ImageView) findViewById(R.id.img_pharmacy);
        Picasso.get().load(unit.getImage()).into(img);

        TextView street = (TextView) findViewById(R.id.street_pharmacy);
        street.setText(unit.getStreet());

        TextView number = (TextView) findViewById(R.id.number_pharmacy);
        number.setText(Integer.toString(unit.getNumber()));

        TextView complement = (TextView) findViewById(R.id.complement_pharmacy);
        complement.setText(unit.getComplement());

        TextView region = (TextView) findViewById(R.id.region_pharmacy);
        region.setText(unit.getRegion());

        TextView zipcode = (TextView) findViewById(R.id.zipcode_pharmacy);
        zipcode.setText(unit.getZipcode());

        TextView city = (TextView) findViewById(R.id.city_pharmacy);
        city.setText(unit.getCity());

        TextView state = (TextView) findViewById(R.id.state_pharmacy);
        state.setText(unit.getState());

        TextView country = (TextView) findViewById(R.id.country_pharmacy);
        country.setText(unit.getCountry());

    }
}
