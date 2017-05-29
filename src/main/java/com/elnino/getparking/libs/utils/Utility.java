package com.elnino.getparking.libs.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by jeetupal on 28/05/17.
 */

public class Utility {
  /**
   *  Check network connection.
   * @param context
   */
  public static boolean isNetworkAvailable(Context context) {
    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
  }

  /**
   *  Check if null or empty string.
   * @param str
   */
  public static boolean isNullOrEmpty(String str) {
    return (str.equals(null) || str.equals("") || str.isEmpty());
  }

  /**
   * @param array
   */
  public static int getMinValue( int [] array){
    int minValue = array[0];
    for (int i = 0; i < array.length; i++) {
      if ( array[0] < minValue){
        minValue = array[i];
      }
    }
    return minValue;
  }

  /**
   *  @param unixTimestampInMili
   */
  public static String convertUnixTimeStampToDate( long unixTimestampInMili){
    String result = null;
    Date date = new Date( unixTimestampInMili );
//    yyyy-MM-dd HH:mm:ss z
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT-4"));
    result = simpleDateFormat.format(date);
    return result;
  }

  /**
   * write to same file.
   * @param fileUri
   * @param rawData
   */
  private static void writeToFile(Uri fileUri, byte[] rawData) {
    FileOutputStream fos = null;
    try {
      File file = new File(fileUri.getPath());
      fos = new FileOutputStream(file);
      fos.write(rawData);
    } catch(Exception ex) {
      ex.printStackTrace();
    } finally {
      Utility.close(fos);
    }
  }
  /**
   *
   * @param closeable
   */
  private static void close(Closeable closeable) {
    try {
      if(closeable != null) {
        closeable.close();
      }
    } catch (Exception ex) {

    }
  }
}
