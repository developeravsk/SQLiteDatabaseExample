package silptech.developerabhi.databaseapp;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by abhi on 5/26/2017.
 */

class MyAdapter extends BaseAdapter {
    Context c;
    ArrayList<Module> list1 = new ArrayList<>();
    LayoutInflater inflater;

    public MyAdapter(MainActivity mainActivity, ArrayList<Module> list) {
        c = mainActivity;
        list1 = list;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list1.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listrecords, null);
            holder = new ViewHolder();
            holder.ROLL = (TextView) convertView.findViewById(R.id.idd);
            holder.NAME = (TextView) convertView.findViewById(R.id.name);
            holder.PHONE = (TextView) convertView.findViewById(R.id.paid);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.ROLL.setText(String.valueOf(list1.get(position).getRoll()));
        holder.NAME.setText(list1.get(position).getName());
        holder.PHONE.setText(list1.get(position).getPhone());
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final Dialog dialog = new Dialog(c);
                dialog.setContentView(R.layout.update);
                final EditText eName = (EditText) dialog.findViewById(R.id.updatename);
                final EditText ePhone = (EditText) dialog.findViewById(R.id.updateamt);
                Button bUpdate = (Button) dialog.findViewById(R.id.update1);
                Button bDelete = (Button) dialog.findViewById(R.id.delete1);
                Button bCancel = (Button) dialog.findViewById(R.id.cancel1);
                dialog.show();
                bUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Module m = new Module();
                        m.setName(eName.getText().toString());
                        m.setPhone(ePhone.getText().toString());
                        m.setRoll(list1.get(position).getRoll());
                        DatabaseAssistant dp = new DatabaseAssistant(c);
                        dp.updateData(m);
                        dialog.dismiss();
                        MainActivity mainActivity = (MainActivity) c;
                        mainActivity.onResume();
                    }
                });
                bDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseAssistant dp = new DatabaseAssistant(c);
                        dp.deleteData(list1.get(position).getRoll());
                        dialog.dismiss();
                        MainActivity mainActivity = (MainActivity) c;
                        mainActivity.onResume();

                    }
                });
                bCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
dialog.dismiss();
                    }
                });

                return true;
            }
        });
        return convertView;
    }
}
