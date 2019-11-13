package com.xheghun.vidit.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BitmapUtil {
    public static Bitmap getBitmap(String filePath) {
        try {
            Bitmap bitmap;
            File file = new File(filePath);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = BitmapFactory.decodeStream(new FileInputStream(file), null, options);
            return bitmap;
        } catch  (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap getBitmap(int imageRes, Context res) {
        return BitmapFactory.decodeResource(res.getResources(),imageRes);
    }
}
