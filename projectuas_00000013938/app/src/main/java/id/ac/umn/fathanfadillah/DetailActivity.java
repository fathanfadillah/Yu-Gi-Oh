package id.ac.umn.fathanfadillah;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class DetailActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView textName,textType,textDesc;
    private FloatingActionButton floatingActionButton;
    private String name,type,desc,image;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        context = getApplicationContext();

        textName = findViewById(R.id.textName);
        textType = findViewById(R.id.textType);
        textDesc = findViewById(R.id.textDesc);
        imageView = findViewById(R.id.imageSmall);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            name = bundle.getString("name");
            type = bundle.getString("type");
            desc = bundle.getString("desc");
            image = bundle.getString("image_url_small");
            Glide.with(this)
                    .load(image)
                    .override(720,1080)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);

            textName.setText(name);
            textType.setText(type);
            textDesc.setText(desc);
        }
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Notification", Toast.LENGTH_SHORT).show();

                new CountDownTimer(50, 10) {

                    public void onTick(long millisUntilFinished) {

                    }

                    public void onFinish() {
                        NotificationCompat.Builder builder = new
                                NotificationCompat.Builder(context)
                                .setSmallIcon(R.drawable.ic_insert_emoticon_black_24dp)
                                .setContentTitle("Yu Gi Oh Deck")
                                .setContentText(name);


                        Intent notificationtIntent = new Intent(context, DetailActivity.class);

                        notificationtIntent.putExtra("image_url_small",image);
                        notificationtIntent.putExtra("name",name);
                        notificationtIntent.putExtra("type",type);
                        notificationtIntent.putExtra("desc",desc);

                        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationtIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                        builder.setContentIntent(contentIntent);

                        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(0, builder.build());
                    }
                }.start();
            }
        });
    }
}
