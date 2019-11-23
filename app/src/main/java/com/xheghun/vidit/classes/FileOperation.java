package com.xheghun.vidit.classes;

import android.content.Context;
import android.graphics.Bitmap;

import com.xheghun.vidit.Util.BitmapUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileOperation {
    public static String copyFile(Context context, String path) {
        String fileName = System.currentTimeMillis() + ".png";
        String message = "file operation: ";
        String filesDir = context.getFilesDir() + "temp_img";
        File directory = new File(filesDir);
        if (!directory.exists()) {
            File newDirectory = new File(filesDir);
            if (newDirectory.mkdirs()) {
                message.concat(" directory created successfully");
            }
        }
        File file = new File(filesDir, fileName);
        if (file.exists()) {
            if (file.delete()) {
                message.concat(" File deleted successfully");
            }
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            Bitmap bit = BitmapUtil.getBitmap(path);
            bit.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return message;
    }

    public static void deleteRecursive(File fileOrDirectory) {

        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles()) {
                deleteRecursive(child);
            }
            fileOrDirectory.delete();
        }
    }

    public static boolean deleteAllFiles(String dir) {
        File file = new File(dir);

        if (file.isDirectory()) {
            String[] children = file.list();
            for (String child : children) {
                if (new File(file, child).delete()) {
                    return true;
                }
            }
        } else {
            return false;
        }
        return false;
    }
}
