package id.ac.umn.fathanfadillah;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerMain recyclerMain;
    private ArrayList<Details> allDetials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureReceiver();
        allDetials = new ArrayList<>();
        MainAsyncTask runner = new MainAsyncTask();
        runner.execute();
        recyclerView = findViewById(R.id.list_deck);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerMain = new RecyclerMain(this,R.layout.list_deck,allDetials);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerMain);
    }

    private void configureReceiver(){
        IntentFilter filter = new IntentFilter();
        filter.addAction("id.ac.umn.fathanfadillah;");
    }
    class MainAsyncTask extends AsyncTask<String,Void,String>
    {
        private String result = "Failed Import Data";

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection urlConnection = null;
            BufferedReader bufferedReader = null;

            String jsonString = null;
            try{
                String urlString = "https://db.ygoprodeck.com/api/v4/cardinfo.php?atk=4000&sort=name";
                URL url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer stringBuffer = new StringBuffer();

                if(inputStream == null){
                    return null;
                }
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while((line = bufferedReader.readLine())!=null){
                    stringBuffer.append(line + "\n");
                }
                if(stringBuffer.length() == 0 ){
                    return null;
                }
                jsonString = stringBuffer.toString();

                Log.d("FETCHDATA",jsonString);
                JSONArray jsonObject = new JSONArray(jsonString);
                    JSONArray jsonObject1 = jsonObject.getJSONArray(0);//index
                    for(int i = 0 ; i< jsonObject1.length() ; i++){
                        JSONObject data = jsonObject1.getJSONObject(i);

                        Details ditel = new Details();

                        ditel.setId(data.getString("id"));
                        ditel.setName(data.getString("name"));
                        ditel.setType(data.getString("type"));
                        ditel.setDesc(data.getString("desc"));
                        ditel.setImageSmall(data.getString("image_url_small"));

                        allDetials.add(ditel);
                        Log.d("FETCHDATA",ditel.getId());
                    }
                //}
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally{
                if(urlConnection != null){
                    urlConnection.disconnect();
                }
                if(bufferedReader != null){
                    try{
                        bufferedReader.close();
                    }catch(IOException e){
                        Log.e("BUFFEREDIOEXCEPTION","IOException" + e.getMessage());
                    }
                }
            }
            return jsonString;
        }
        @Override
        protected void onPostExecute(String result){
            recyclerMain.notifyDataSetChanged();
        }

    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.custom_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
