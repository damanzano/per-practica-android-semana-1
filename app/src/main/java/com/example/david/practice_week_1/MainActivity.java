package com.example.david.practice_week_1;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    TextView domainName;
    Button searchIP;
    TextView results;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        // Init GUI elements
        domainName = (TextView) findViewById(R.id.domainName);
        searchIP = (Button) findViewById(R.id.searchIP);
        results = (TextView) findViewById(R.id.results);

        // Add a Listener for click events to the button.
        searchIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save the domainName TextView content into a string variable for future use.
                String domainNameText = domainName.getText().toString();

                // Start a new AsyncTask in order execute network operation on a separated thread.
                new FindIPTask().execute(domainNameText);


            }
        });
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

    private class FindIPTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            // Process executed on background thread
            String result = "There is no IP to show";
            try{
                // search the IPs associated to the selected domain name
                InetAddress[] ips = InetAddress.getAllByName(params[0]);

                if(ips!=null && ips.length>0){
                    // Concatenate all IPs on a single string
                    result ="";
                    for (int i=0; i<ips.length;i++){
                        result += ips[i].getHostAddress()+"\n";
                    }
                }

            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            // Update the GUI with the operation results
            results.setText(s);
        }
    }
}
