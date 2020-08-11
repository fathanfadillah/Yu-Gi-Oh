package id.ac.umn.fathanfadillah;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class RecyclerMain extends RecyclerView.Adapter<RecyclerHolder> {
    private List<Details> ditel;
    private Context recyclerContext;
    private int rowLayout;
    public RecyclerMain(Context context, int rowLayout, List<Details> ditel){
        this.recyclerContext = context;
        this.rowLayout = rowLayout;
        this.ditel = ditel;
    }
    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.recyclerContext);
        View view = inflater.inflate(rowLayout,parent,false);
        return new RecyclerHolder(view,ditel);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        Details showDetails = ditel.get(position);

        holder.txtName.setText(showDetails.getName());
        Glide.with(recyclerContext)
                .load(showDetails.getImageSmall())
                .override(300,600)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imgSmall);
    }

    @Override
    public int getItemCount() {
        return (ditel != null)?ditel.size():0;
    }
}
