package com.lab.gabriel.dailyselfie;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by fefochico on 25/11/2015.
 */
public class InfoViewAdapter extends BaseAdapter {
    private ArrayList<Uri> list;
    private static LayoutInflater inflater = null;
    private Context mContext;
    private Cursor mImageCursor;

    public InfoViewAdapter(Context c, Cursor ic){
        mContext= c;
        mImageCursor=ic;
        list= new ArrayList<Uri>();
        inflater= LayoutInflater.from(mContext);
    }

    public void add(Uri dir) {
        list.add(dir);
    }

    public void addNew(Uri dir) {
        list.add(dir);
        notifyDataSetChanged();
    }

    // Clears the list adapter of all items.

    public void clear() {

        list.clear();
        notifyDataSetChanged();

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
        View itemLayout;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        itemLayout = inflater.inflate(R.layout.element, parent, false);

        File auxFile= new File(String.valueOf(list.get(position)));
        TextView tv= (TextView) itemLayout.findViewById(R.id.text);
        tv.setText(auxFile.getName());


        String dir= String.valueOf(list.get(position));
        ProgressBar pb= (ProgressBar) itemLayout.findViewById(R.id.progress);

        new Thread(new LoadIconTask(itemLayout,pb,R.id.picture,dir)).start();

        return itemLayout;

    }

}
