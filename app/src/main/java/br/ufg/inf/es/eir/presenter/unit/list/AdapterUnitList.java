package br.ufg.inf.es.eir.presenter.unit.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import br.ufg.inf.es.eir.R;
import br.ufg.inf.es.eir.model.Remedy;
import br.ufg.inf.es.eir.model.Unit;

public class AdapterUnitList extends RecyclerView.Adapter<AdapterUnitList.UnitViewHolder> {

    private List<Unit> units;
    private Context context;

    public AdapterUnitList(List<Unit> units, Context context) {
        this.units = units;
        this.context = context;
    }

    @Override
    public AdapterUnitList.UnitViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_item, viewGroup, false);
        AdapterUnitList.UnitViewHolder viewHolder = new AdapterUnitList.UnitViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(UnitViewHolder holder, int position) {
        final Unit unit = units.get(position);

        holder.nameView.setText(unit.getName());

        Picasso.get().load(unit.getImage()).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(unit);
            }
        });

        holder.descriptionView.setText(unit.getPhone());
    }

    @Override
    public int getItemCount() {
        return units.size();
    }

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }

    public static class UnitViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameView;
        TextView descriptionView;

        UnitViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_image);
            nameView = (TextView) itemView.findViewById(R.id.item_name);
            descriptionView = (TextView) itemView.findViewById(R.id.item_description);
        }
    }
}
