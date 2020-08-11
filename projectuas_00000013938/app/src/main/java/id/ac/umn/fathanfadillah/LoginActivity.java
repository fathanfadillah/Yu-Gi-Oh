package id.ac.umn.fathanfadillah;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    public DBHelp databes;
    private List<Users> users;

    EditText edt_Username, edt_Password;
    Button btn_Login;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = getApplicationContext();

        databes = new DBHelp(context);

        edt_Username = (EditText) findViewById(R.id.edit_Username);
        edt_Password = (EditText) findViewById(R.id.edit_Password);
        btn_Login = (Button) findViewById(R.id.btnLogin);

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input_username = edt_Username.getText().toString();
                String input_password = edt_Password.getText().toString();

                Cursor myCursor = databes.getAllData();
                users = new ArrayList<>();
                while (myCursor.moveToNext()){
                    users.add(new Users(
                            myCursor.getString(0),
                            myCursor.getString(1)
                    ));
                }
                databes.close();

                for (Users user : users){
                    if (input_username.equals(user.getUsername()) && input_password.equals(user.getPassword())){
                        Toast.makeText(LoginActivity.this, "Login Succesfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(new Intent(LoginActivity.this, MainActivity.class));
                        startActivity(intent);
                        break;
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Username or Password is Invalid", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:
                Intent intent = new Intent(LoginActivity.this, About.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}