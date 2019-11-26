package com.ijbh.wcfinder;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class WaterClosetManager {
    public static WaterClosetManager instance;
    private Context context;
    private ArrayList<WaterCloset> wcs = new ArrayList<>();

    /*
    manager.addWaterCloset(new WaterCloset("Hit", "Building 8", "HIT, Holon", "1/1/19", true, R.drawable.wc_img_1, 2, 2, 2, 2));
        manager.addWaterCloset(new WaterCloset("Microsoft", "At the end of the hall","Hertzeliya", "2/3/19",  true, R.drawable.wc_img_2, 1, 2, 3, 4));
        manager.addWaterCloset(new WaterCloset("Apple", "Building 11", "Tel Aviv", "8/8/18", true, R.drawable.wc_img_1, 3,4,5,3));

*/
    static final String FILE_NAME = "waterclosets.dat";

    private WaterClosetManager(Context context){
        this.context = context;

        try {
            FileInputStream fis = context.openFileInput(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            wcs = (ArrayList<WaterCloset>) ois.readObject();

            ois.close();
            //fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static WaterClosetManager getInstance(Context context){
        if(instance == null)
            instance = new WaterClosetManager(context);

        return instance;
    }

    public WaterCloset getWaterCloset(int position){
        if(position < wcs.size()){
            return wcs.get(position);
        }
        return null;
    }

    public void addWaterCloset(WaterCloset waterCloset){
        wcs.add(waterCloset);
        saveWaterClosets();
    }

    public void removeWaterCloset(int position){
        if(position < wcs.size())
            wcs.remove(position);

        saveWaterClosets();
    }

    public void saveWaterClosets(){
        try {
            FileOutputStream fos = context.openFileOutput(FILE_NAME, context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(wcs);

            oos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<WaterCloset> getWcs(){
        return wcs;
    }
}
