package br.com.tairoroberto.parserrssatomlibrome;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndContent;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndFeed;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.FeedException;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.SyndFeedInput;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.XmlReader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener{

    private ListView listView;
    private MyAdapter adapter;
    private List<SyndEntry> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Implementa a lista eo listView
        list = new ArrayList<SyndEntry>();
        adapter = new MyAdapter(this,list);
        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        Button btnBuscarRss = (Button)findViewById(R.id.btnBuscarFeed);
        btnBuscarRss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readFeed("http://exampleusername.livejournal.com/data/atom");
            }
        });
    }

    private void readFeed(final String urlFeed){

       new Thread(){
           public void run(){
               URL url;
               try{
                       //Url do feed
                       url = new URL(urlFeed);

                       //Inicia o SyndEntry
                       SyndFeedInput input = new SyndFeedInput();
                       //Buscar as entradas de feed
                       SyndFeed feed = input.build(new XmlReader(url));
                       //Inicialisa a lista com as entradas
                       List entradas = feed.getEntries();

                       //coloca as entrados em um iterator para ser percorrido
                       Iterator iterator = entradas.iterator();

                       while (iterator.hasNext()){
                           //pega cada entrada e add a lista
                           SyndEntry aux = (SyndEntry) iterator.next();
                           list.add(aux);
                       }

                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                            adapter.notifyDataSetChanged();
                       }
                   });



                   } catch (MalformedURLException e) {
                       e.printStackTrace();
                   } catch (FeedException e) {
                       e.printStackTrace();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }

       }.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, RSSContentActivity.class);
        intent.putExtra("rss",getContetFeed(list.get(position)));
        startActivity(intent);
    }



    private String getContetFeed(SyndEntry syndEntry){
        StringBuilder builder = new StringBuilder();

        if (syndEntry.getContents() != null){
            //Inicialisa a lista com os dados
            List contents = syndEntry.getContents();

            //coloca os dados em um iterator para ser percorrido
            Iterator iterator = contents.iterator();

            while (iterator.hasNext()){
                //pega cada entrada e add no stringBuilder
                SyndContent aux = (SyndContent) iterator.next();
                builder.append(aux.getValue());
            }
        }
        return (builder.toString());
    }
}
