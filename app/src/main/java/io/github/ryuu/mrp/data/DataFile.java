package io.github.ryuu.mrp.data;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import androidx.core.app.ActivityCompat;

public class DataFile {
    public static boolean save() {
        String dbpath = Environment.getExternalStorageDirectory()+"/Android/data/io.github.ryuu.mrp/database/"
                +"YourAccount.db";
        String dbpath2 = Environment.getExternalStorageDirectory()+"/Android/data/io.github.ryuu.mrp/database/"
                +"YourAccount.db-shm";
        String dbpath3 = Environment.getExternalStorageDirectory()+"/Android/data/io.github.ryuu.mrp/database/"
                +"YourAccount.db-wal";

        boolean success=copyFile(dbpath, Environment.getExternalStorageDirectory() + "/Mrp/YourAccount.db");
        success=copyFile(dbpath2, Environment.getExternalStorageDirectory() + "/Mrp/YourAccount.db-shm");
        success=copyFile(dbpath3, Environment.getExternalStorageDirectory() + "/Mrp/YourAccount.db-wal");
        Log.d("路径：",dbpath);
        return success;
    }

    public static boolean input() {
        String dbpath = Environment.getExternalStorageDirectory()+"/Android/data/io.github.ryuu.mrp/database/"
                +"YourAccount.db";
        String dbpath2 = Environment.getExternalStorageDirectory()+"/Android/data/io.github.ryuu.mrp/database/"
                +"YourAccount.db-shm";
        String dbpath3 = Environment.getExternalStorageDirectory()+"/Android/data/io.github.ryuu.mrp/database/"
                +"YourAccount.db-wal";
        //deleteFile(Environment.getExternalStorageDirectory()+"/Android/data/io.github.ryuu.mrp/database/YourAccount.db-shm");
//        deleteFile(Environment.getExternalStorageDirectory()+"/Android/data/io.github.ryuu.mrp/database/YourAccount.db-wal");
        boolean success=copyFile(Environment.getExternalStorageDirectory() + "/Mrp/YourAccount.db", dbpath);
        success=copyFile(Environment.getExternalStorageDirectory() + "/Mrp/YourAccount.db-shm", dbpath2);
        success=copyFile(Environment.getExternalStorageDirectory() + "/Mrp/YourAccount.db-wal", dbpath3);
        return success;
    }

    public static boolean copyFile(String source, String dest) {
        try {
            File f1 = new File(source);
            File f2 = new File(dest);
            File folder = new File(Environment.getExternalStorageDirectory() + "/Mrp");
            folder.mkdirs();
            InputStream in = new FileInputStream(f1);

            OutputStream out = new FileOutputStream(f2);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0)
                out.write(buf, 0, len);


            out.close();
            in.close();
            Log.d("拷贝完成","完成");
        } catch (FileNotFoundException ex) {
            Log.d("拷贝失败",ex.getMessage());
            return false;
        } catch (IOException e) {
            Log.d("拷贝失败","IO错误");
            return false;
        }

        return true;

    }
//    public static boolean deleteFile(String source) {
//        try {
//            File file = new File(source);
//            if (file.delete()) {
//                return true;
//            } else {
//                return false;
//            }
//        } catch (Exception e) {
//            Log.d("删除数据库失败：",e.getMessage());
//            return false;
//        }
//
//    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {"android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    public static boolean verifyStoragePermissions(Activity activity) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                        REQUEST_EXTERNAL_STORAGE);
                return false;
            }else {
                Log.d("存储权限：","有");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
