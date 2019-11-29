package com.javadagger2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;

import androidx.room.Room;

import com.javadagger2.roomdb.AppDataBase;

import java.io.ByteArrayOutputStream;

public class Utils {

//    void  setImage(Bitmap img) {
//        dao = .instance.getImageTestDao()
//        val imageTest = ImageTest()
//        imageTest.data = getBytesFromImageMethod(image)//TODO
//        dao.updsertByReplacement(imageTest)
//    }
//
//        fun getImage():Bitmap?{
//                val dao = DatabaseHelper.instance.getImageTestDao()
//                val imageByteArray = dao.getAll()
//        return loadImageFromBytes(imageByteArray[0].data)
//        //change accordingly
//     }



    public  static AppDataBase getDbInstance(Context context){

        return Room.databaseBuilder(context,AppDataBase.class,"Movies2").allowMainThreadQueries().build();
    }

    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}
