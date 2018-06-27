package br.ufg.inf.es.eir.presenter.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.github.matheuspiment.eir.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import br.ufg.inf.es.eir.model.Remedy;

public class AdapterRemedy extends RecyclerView.Adapter<AdapterRemedy.RemedyViewHolder> {

    private List<Remedy> remedies;
    private Context context;

    public AdapterRemedy(List<Remedy> remedies, Context context){
        this.remedies = remedies;
        this.context = context;
    }

    @Override
    public AdapterRemedy.RemedyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_item, viewGroup, false);
        AdapterRemedy.RemedyViewHolder viewHolder = new AdapterRemedy.RemedyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RemedyViewHolder holder, int position) {
        final Remedy remedy = remedies.get(position);
        holder.idView.setText("" + remedy.getId() + "");
        holder.nameView.setText(remedy.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(remedy);
            }
        });
    }

    @Override
    public int getItemCount() {
        return remedies.size();
    }

    public List<Remedy> getRemedies() {
        return remedies;
    }

    public void setRemedies(List<Remedy> remedies) {
        this.remedies = remedies;
    }

    public static class RemedyViewHolder extends RecyclerView.ViewHolder {
        TextView idView;
        TextView nameView;

        RemedyViewHolder(View itemView) {
            super(itemView);
            idView = (TextView)itemView.findViewById(R.id.item_id);
            nameView = (TextView)itemView.findViewById(R.id.item_name);
        }
    }
}
