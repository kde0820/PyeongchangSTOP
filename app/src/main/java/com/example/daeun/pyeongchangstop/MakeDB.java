package com.example.daeun.pyeongchangstop;

import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by daeun on 2017-08-22.
 */

public class MakeDB {
    private static final String PATH = "/data/data/com.example.daeun.pyeongchangstop/";

    public void createDB(String name, Resources res){
        File folder = new File(PATH + "databases");
        folder.mkdirs();
        File outFile = new File(PATH + "databases/" + name);

        if (outFile.length() <= 0) {
            AssetManager am = res.getAssets();
            InputStream is = null;
            try {
                is = am.open(name, AssetManager.ACCESS_BUFFER);
                long filesize = is.available();
                byte[] tempdata = new byte[(int) filesize];
                is.read(tempdata);
                is.close();
                outFile.createNewFile();
                FileOutputStream fo = new FileOutputStream(outFile);
                fo.write(tempdata);
                fo.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
