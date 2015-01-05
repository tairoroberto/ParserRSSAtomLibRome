package br.com.tairoroberto.parserrssatomlibrome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;

import java.util.List;

/**
 * Created by tairo on 04/01/15.
 */
public class MyAdapter extends BaseAdapter {
    Context context;
    List<SyndEntry> list;

    public MyAdapter(Context context, List<SyndEntry> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.rss,null);

        TextView textView1 = (TextView)convertView.findViewById(R.id.textView1);
        TextView textView2 = (TextView)convertView.findViewById(R.id.textView2);
        TextView textView3 = (TextView)convertView.findViewById(R.id.textView3);

        textView1.setText(list.get(position).getTitle());
        textView2.setText(list.get(position).getAuthor());
        textView3.setText(list.get(position).getPublishedDate().toString());

        return convertView;
    }
}
