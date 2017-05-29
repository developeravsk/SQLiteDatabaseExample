package silptech.developerabhi.databaseapp;

import android.app.Dialog;
import android.provider.Telephony;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView count;
    Button add;
    ListView lv;
ArrayList<Module> list=new ArrayList<Module>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //total count's textview
        count = (TextView) findViewById(R.id.textview);
        add = (Button) findViewById(R.id.add);
        lv = (ListView) findViewById(R.id.listview);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog);
                final EditText etRoll = (EditText) dialog.findViewById(R.id.entryid);
                final EditText etname = (EditText) dialog.findViewById(R.id.entryname);
                final EditText etphone = (EditText) dialog.findViewById(R.id.entryamt);
                Button save = (Button) dialog.findViewById(R.id.addentry);
                Button cancel = (Button) dialog.findViewById(R.id.cancelentry);
                dialog.show();
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseAssistant db12 = new DatabaseAssistant(MainActivity.this);
                        Module m = new Module();
                        m.setRoll(Integer.parseInt(etRoll.getText().toString()));
                        m.setName(etname.getText().toString());
                        m.setPhone(etphone.getText().toString());

                        db12.addData(m);
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "Data inserted successfully",
                                Toast.LENGTH_SHORT).show();
                        onResume();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        DatabaseAssistant dp=new DatabaseAssistant(MainActivity.this);
        list=dp.readData();
        count.setText("Total records="+list.size());
        lv.setAdapter(new MyAdapter(MainActivity.this,list));
    }
}
