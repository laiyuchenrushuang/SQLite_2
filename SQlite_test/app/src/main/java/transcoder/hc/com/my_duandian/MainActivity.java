package transcoder.hc.com.my_duandian;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button chaxun;
    Button zengjia;
    Button shanchu;
    Button genggai;
    SQLiteDatabase db;
    TextView show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initSQLite();
    }

    private void initSQLite() {
        DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this, "test_db_2",null,1);
         db = dbHelper.getWritableDatabase();
    }

    private void initView() {
        chaxun = findViewById(R.id.chaxun);
        zengjia = findViewById(R.id.zengjia);
        shanchu = findViewById(R.id.shanchu);
        genggai = findViewById(R.id.xiugai);
        show = findViewById(R.id.show);
        chaxun.setOnClickListener(this);
        zengjia.setOnClickListener(this);
        shanchu.setOnClickListener(this);
        genggai.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chaxun:
                Cursor cursor = db.query("user", new String[]{"id","name","age"}, null, null, null, null, null, null);
                //利用游标遍历所有数据对象
                //为了显示全部，把所有对象连接起来，放到TextView中

                String textview_data = "";
                while(cursor.moveToNext()){
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    int age = cursor.getInt(cursor.getColumnIndex("age"));
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    textview_data = textview_data + "\n" +id+"     "+name+"     "+ age;
                }
                show.setText(textview_data);

                break;
            case R.id.shanchu:
                String delete_data_l = "laiyu";
                String delete_data_c = "chenrushuang";
                db.delete("user", "name=?", new String[]{delete_data_l});
                db.delete("user", "name=?", new String[]{delete_data_c});//一条一条的删除。。。
                getshowDB();
                break;
            case R.id.xiugai:
                ContentValues values2 = new ContentValues();

                String update_after_data = "chenrushuang";
                values2.put("name", update_after_data);
                db.update("user", values2, "name = ?", new String[]{"laiyu"});
                getshowDB();
                break;
            case R.id.zengjia:
                ContentValues values = new ContentValues();
                String insert_data = "laiyu";
                values.put("age",29);
                values.put("name",insert_data);
                //数据库执行插入命令
                db.insert("user", null, values);
                getshowDB();
                break;
        }
    }

    private void getshowDB() {
        Cursor cursor = db.query("user", new String[]{"name"}, null, null, null, null, null);
        String textview_data = "";
        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("name"));
//            int age = cursor.getInt(cursor.getColumnIndex("age"));
            textview_data = textview_data + "\n" + name;
        }
        show.setText(textview_data);

    }
}
