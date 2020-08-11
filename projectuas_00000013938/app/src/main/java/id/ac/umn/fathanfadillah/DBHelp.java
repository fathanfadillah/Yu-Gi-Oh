package id.ac.umn.fathanfadillah;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DBHelp extends SQLiteOpenHelper {

    private SQLiteDatabase myDB;
    private final Context myContext;
    public final static String DATABASE_PATH = "/data/data/id.ac.umn.fathanfadillah/databases/";
    private static final String DATABASE_NAME = "Credential.db";
    public static final String TABLE_NAME = "users";
    public static final String COL_1 = "user_name";
    public static final String COL_2 = "user_password";

    public DBHelp(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.myContext = context;
        openDatabase();
        myDB.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + "user_name TEXT NOT NULL," + "user_password TEXT NOT NULL" + ")");
    }

    //Open database
    public SQLiteDatabase openDatabase() {
        String myPath = DATABASE_PATH + DATABASE_NAME;
        if(myDB == null){
            createDatabase();
            myDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        }
        return myDB;
    }

    //Create a empty database on the system
    public void createDatabase(){
        boolean dbExist = checkDatabase();
        if(!dbExist){
            this.getReadableDatabase();
            try {
                copyDatabase();
            } catch (IOException e){
                throw new Error("Error copying database!");
            }
        }
    }

    //Check database already exist or not
    private boolean checkDatabase() {
        String myPath = DATABASE_PATH + DATABASE_NAME;
        File dbFile = new File(myPath);
        return dbFile.exists();
    }

    //Copies your database from your local assets-folder to the just created empty database in the system folder
    private void copyDatabase() throws IOException{
        InputStream externalDBStream = myContext.getAssets().open(DATABASE_NAME);

        OutputStream localDBStream = new FileOutputStream(DATABASE_PATH + DATABASE_NAME);

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = externalDBStream.read(buffer)) > 0){
            localDBStream.write(buffer, 0, bytesRead);
        }
        localDBStream.flush();

        localDBStream.close();
        externalDBStream.close();
    }

    @Override
    public synchronized void close() {
        if(myDB != null){
            myDB.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return cursor;
    }
}