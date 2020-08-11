package id.ac.umn.fathanfadillah;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class RecyclerHolder extends RecyclerView.ViewHolder {
    private List<Details> ditel;
    public TextView txtName;
    public ImageView imgSmall;

    public RecyclerHolder(final View itemView, final List<Details> ditel) {
        super(itemView);

        txtName = itemView.findViewById(R.id.txtName);
        imgSmall = itemView.findViewById(R.id.imageSmall);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                Intent intent = new Intent(itemView.getContext(),DetailActivity.class);
                intent.putExtra("image_url_small",ditel.get(position).getImageSmall());
                intent.putExtra("name",ditel.get(position).getName());
                intent.putExtra("type",ditel.get(position).getType());
                intent.putExtra("desc",ditel.get(position).getDesc());
                itemView.getContext().startActivity(intent);
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Toast.makeText(itemView.getContext(), "Notification", Toast.LENGTH_SHORT).show();

                new CountDownTimer(5000, 1000) {

                    public void onTick(long millisUntilFinished) {

                    }

                    public void onFinish() {
                        int position = getAdapterPosition();
                        NotificationCompat.Builder builder = new
                                NotificationCompat.Builder(itemView.getContext())
                                .setSmallIcon(R.drawable.ic_insert_emoticon_black_24dp)
                                .setContentTitle("Yu Gi Oh Deck")
                                .setContentText(ditel.get(position).getName());


                        Intent notificationtIntent = new Intent(itemView.getContext(), DetailActivity.class);

                        notificationtIntent.putExtra("image_url_small",ditel.get(position).getImageSmall());
                        notificationtIntent.putExtra("name",ditel.get(position).getName());
                        notificationtIntent.putExtra("type",ditel.get(position).getType());
                        notificationtIntent.putExtra("desc",ditel.get(position).getDesc());

                        PendingIntent contentIntent = PendingIntent.getActivity(itemView.getContext(), 0, notificationtIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                        builder.setContentIntent(contentIntent);

                        NotificationManager notificationManager = (NotificationManager) itemView.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(0, builder.build());
                    }
                }.start();

                return true;
            }
        });
    }
}