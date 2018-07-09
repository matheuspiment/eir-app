package br.ufg.inf.es.eir.presenter.list;

import android.content.Context;
import android.net.Uri;
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

public class AdapterRemedyList extends RecyclerView.Adapter<AdapterRemedyList.RemedyViewHolder> {

    private List<Remedy> remedies;
    private Context context;

    public AdapterRemedyList(List<Remedy> remedies, Context context){
        this.remedies = remedies;
        this.context = context;
    }

    @Override
    public AdapterRemedyList.RemedyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_item, viewGroup, false);
        AdapterRemedyList.RemedyViewHolder viewHolder = new AdapterRemedyList.RemedyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RemedyViewHolder holder, int position) {
        final Remedy remedy = remedies.get(position);
        holder.nameView.setText(remedy.getName());
        Picasso.get().load(remedy.getImage()).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(remedy);
            }
        });
        holder.descriptionView.setText(remedy.getType());
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
        ImageView imageView;
        TextView nameView;
        TextView descriptionView;

        RemedyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.item_image);
            nameView = (TextView)itemView.findViewById(R.id.item_name);
            descriptionView = (TextView)itemView.findViewById(R.id.item_description);
        }
    }
}
