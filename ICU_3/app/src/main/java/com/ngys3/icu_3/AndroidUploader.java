package com.ngys3.icu_3;

import java.io.*;
import java.net.*;
import android.util.Log;

public class AndroidUploader	{

    static String serviceDomain = "http://192.168.0.22:5002";

    static String postUrl = serviceDomain + "/upload_video";

    static String CRLF = "\r\n";

    static String twoHyphens = "--";

    static String boundary = "*****b*o*u*n*d*a*r*y*****";



    private String pictureFileName = null;

    private String name = null;

    private String password = null;

    private DataOutputStream dataStream = null;



    enum ReturnCode { noPicture, unknown, http201, http400, http401, http403, http404, http500};



    private String TAG = "멀티파트 테스트";



    public AndroidUploader() 	    {
    }



    public static void setServiceDomain(String domainName)	    {

        serviceDomain = domainName;

    }



    public static String getServiceDomain()	    {

        return serviceDomain;

    }



    public String uploadPicture(String pictureFileName)	    {

        this.pictureFileName = pictureFileName;

        File uploadFile = new File(pictureFileName);



        if (uploadFile.exists())

            try 	{

                FileInputStream fileInputStream = new FileInputStream(new File(pictureFileName));

                URL connectURL = new URL(postUrl);

                HttpURLConnection conn = (HttpURLConnection)connectURL.openConnection();



                conn.setDoInput(true);

                conn.setDoOutput(true);

                conn.setUseCaches(false);

                conn.setRequestMethod("POST");



                //conn.setRequestProperty("User-Agent", "myFileUploader");

                conn.setRequestProperty("Connection","Keep-Alive");

                conn.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);



                conn.connect();



                dataStream = new DataOutputStream(conn.getOutputStream());



                //writeFormField("login", name);

                //writeFormField("password", password);

                writeFileField("photo1", pictureFileName, "image/jpg", fileInputStream);



                // final closing boundary line

                dataStream.writeBytes(twoHyphens + boundary + twoHyphens + CRLF);



                fileInputStream.close();

                dataStream.flush();

                dataStream.close();

                dataStream = null;



                Log.d("업로드 테스트", "***********전송완료***********");



                String response = getResponse(conn);

                int responseCode = conn.getResponseCode();



                if (response.contains("uploaded successfully"))

                    return "http201";

                else

                    // for now assume bad name/password

                    return "ReturnCode.http401";

            }

            catch (MalformedURLException mue) {

                Log.e(TAG, "error: " + mue.getMessage(), mue);

                return "http400";

            }

            catch (IOException ioe) {

                Log.e(TAG, "error: " + ioe.getMessage(), ioe);

                return "http500";

            }

            catch (Exception e) {

                Log.e(TAG, "error: " + e.getMessage(), e);

                return "unknown";

            }    else    {

            return "noPicture";

        }

    }



    private String getResponse(HttpURLConnection conn)	    {

        try 	        {

            DataInputStream dis = new DataInputStream(conn.getInputStream());

            byte []        data = new byte[1024];

            int             len = dis.read(data, 0, 1024);



            dis.close();

            int responseCode = conn.getResponseCode();



            if (len > 0)

                return new String(data, 0, len);

            else

                return "";

        }

        catch(Exception e)     {

            //System.out.println("AndroidUploader: "+e);

            Log.e(TAG, "AndroidUploader: "+e);

            return "";

        }

    }



    /**

     *  this mode of reading response no good either

     */

    private String getResponseOrig(HttpURLConnection conn)	    {

        InputStream is = null;

        try   {

            is = conn.getInputStream();

            // scoop up the reply from the server

            int ch;

            StringBuffer sb = new StringBuffer();

            while( ( ch = is.read() ) != -1 ) {

                sb.append( (char)ch );

            }

            return sb.toString();   // TODO Auto-generated method stub

        }

        catch(Exception e)   {

            //System.out.println("GeoPictureUploader: biffed it getting HTTPResponse");

            Log.e(TAG, "AndroidUploader: "+e);

        }

        finally   {

            try {

                if (is != null)

                    is.close();

            } catch (Exception e) {}

        }



        return "";

    }



    /**

     * write one form field to dataSream

     * @param fieldName

     * @param fieldValue

     */

    private void writeFormField(String fieldName, String fieldValue)  {

        try  {

            dataStream.writeBytes(twoHyphens + boundary + CRLF);

            dataStream.writeBytes("Content-Disposition: form-data; name=\"" + fieldName + "\"" + CRLF);

            dataStream.writeBytes(CRLF);

            dataStream.writeBytes(fieldValue);

            dataStream.writeBytes(CRLF);

        }    catch(Exception e)   {

            //System.out.println("AndroidUploader.writeFormField: got: " + e.getMessage());

            Log.e(TAG, "AndroidUploader.writeFormField: " + e.getMessage());

        }

    }



    /**

     * write one file field to dataSream

     * @param fieldName - name of file field

     * @param fieldValue - file name

     * @param type - mime type

     * @param fileInputStream - stream of bytes that get sent up

     */

    private void writeFileField(

            String fieldName,

            String fieldValue,

            String type,

            FileInputStream fis)  {

        try {

            // opening boundary line

            dataStream.writeBytes(twoHyphens + boundary + CRLF);

            dataStream.writeBytes("Content-Disposition: form-data; name=\""

                    + fieldName

                    + "\";filename=\""

                    + fieldValue

                    + "\""

                    + CRLF);

            dataStream.writeBytes("Content-Type: " + type +  CRLF);

            dataStream.writeBytes(CRLF);



            // create a buffer of maximum size

            int bytesAvailable = fis.available();

            int maxBufferSize = 1024;

            int bufferSize = Math.min(bytesAvailable, maxBufferSize);

            byte[] buffer = new byte[bufferSize];

            // read file and write it into form...

            int bytesRead = fis.read(buffer, 0, bufferSize);

            while (bytesRead > 0)   {

                dataStream.write(buffer, 0, bufferSize);

                bytesAvailable = fis.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);

                bytesRead = fis.read(buffer, 0, bufferSize);

            }



            // closing CRLF

            dataStream.writeBytes(CRLF);

        }

        catch(Exception e)  {

            //System.out.println("GeoPictureUploader.writeFormField: got: " + e.getMessage());

            Log.e(TAG, "AndroidUploader.writeFormField: got: " + e.getMessage());

        }

    }


/*
    public static void main(String[] args)  {

        if (args.length >= 0)  {

            AndroidUploader gpu = new AndroidUploader("john", "notmyrealpassword");

            String picName = args[0];

            ReturnCode rc = gpu.uploadPicture(picName);

            //System.out.printf("done");

        }

    }
*/
}