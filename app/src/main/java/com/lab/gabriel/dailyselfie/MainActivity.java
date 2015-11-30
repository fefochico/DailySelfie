package com.lab.gabriel.dailyselfie;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final int CAPTURE_IMAGE_ACTIVITY_REQ = 100;
    private Uri fileUri;
    private InternalFileWriteRead ifwr;
    private String FILENAME = "photoList";
    private ListView lv;
    private Cursor mImageCursor;
    private ArrayList<String> list;
    private InfoViewAdapter mAdapter;
    private Notification mNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNotification= new Notification(getApplicationContext());

        ifwr= new InternalFileWriteRead(getApplicationContext(), FILENAME);
        list = ifwr.readName();

        lv= (ListView)findViewById(R.id.listView);
        mAdapter= new InfoViewAdapter(getApplicationContext(), mImageCursor);
        //adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        for(int i=0; i< list.size(); i++){
            if(i==(list.size()-1)) mAdapter.addNew(Uri.parse(list.get(i)));
            else mAdapter.add(Uri.parse(list.get(i)));
        }
        lv.setAdapter(mAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(list.get(position)), "image/*");
                startActivity(intent);
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void addElement(Uri uri){
        ifwr.writeLine(String.valueOf(uri));
        File file= new File(String.valueOf(uri));
        list.add(file.getPath());
        mAdapter.addNew(uri);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_photo) {
            Intent i= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            fileUri = Uri.fromFile(getOutputPhotoFile()); // create a file to save the image
            i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

            // start the image capture Intent
            startActivityForResult(i, CAPTURE_IMAGE_ACTIVITY_REQ);

        }

        return super.onOptionsItemSelected(item);
    }

    private File getOutputPhotoFile() {
        //String FolderPath= Environment.getExternalStorageDirectory().getAbsolutePath() + "/DailySelfie/";
        File directory;
        String name= "DailySelfieImages";
        directory = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), name);

        //directory= new File(MediaStore.Images+ File.separator + "DailySelfie");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss", Locale.US).format(new    Date());
        return new File(directory.getPath() + File.separator + "IMG_"
                + timeStamp + ".jpg");
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQ) {
            if (resultCode == RESULT_OK) {
                Uri photoUri = null;
                if (data == null) {
                    // A known bug here! The image should have saved in fileUri
                    Toast.makeText(this, "Image saved successfully",
                            Toast.LENGTH_LONG).show();
                    photoUri = fileUri;
                } else {
                    photoUri = data.getData();
                    Toast.makeText(this, "Image saved successfully in: " + data.getData(),
                            Toast.LENGTH_LONG).show();
                }
                addElement(photoUri);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Callout for image capture failed!",
                        Toast.LENGTH_LONG).show();
            }
        }
        mNotification.activate();
    }

}
