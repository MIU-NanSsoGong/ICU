package com.ngys3.icu_3;

import android.content.ContentValues;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class RequestHttpURLConnection {

    public String request(String _url, String json) {

        HttpURLConnection urlConn = null;

        // URL 뒤에 붙여서 보낼 파라미터.
        StringBuffer sbParams = new StringBuffer();

        /**
         * 1. StringBuffer에 파라미터 연결
         * */

        sbParams.append(json);

        // 파라미터가 2개 이상이면 isAnd를 true로 바꾸고 다음 루프부터 &를 붙인다.

        /**
         * 2. HttpURLConnection을 통해 web의 데이터를 가져온다.
         * */
        try {
            URL url = new URL(_url);
            urlConn = (HttpURLConnection) url.openConnection();

            // [2-1]. urlConn 설정.
            urlConn.setReadTimeout(10000);
            urlConn.setConnectTimeout(15000);
            urlConn.setRequestMethod("POST"); // URL 요청에 대한 메소드 설정 : GET/POST.
            urlConn.setDoOutput(true);
            urlConn.setDoInput(true);
            urlConn.setRequestProperty("Accept-Charset", "utf-8"); // Accept-Charset 설정.
            //urlConn.setRequestProperty("Context_Type", "application/x-www-form-urlencoded");
            urlConn.setRequestProperty("Accept", "application/json");
            urlConn.setRequestProperty("Content-type", "application/json");


            // [2-2]. parameter 전달 및 데이터 읽어오기.
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(urlConn.getOutputStream()));
            pw.write(sbParams.toString());
            pw.flush(); // 출력 스트림을 flush. 버퍼링 된 모든 출력 바이트를 강제 실행.
            pw.close(); // 출력 스트림을 닫고 모든 시스템 자원을 해제.

            // [2-3]. 연결 요청 확인.
            // 실패 시 null을 리턴하고 메서드를 종료.
            if (urlConn.getResponseCode() != HttpURLConnection.HTTP_OK)
                return null;

            // [2-4]. 읽어온 결과물 리턴.
            // 요청한 URL의 출력물을 BufferedReader로 받는다.
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"));

            // 출력물의 라인과 그 합에 대한 변수.
            String line;
            String page = "";

            // 라인을 받아와 합친다.
            while ((line = reader.readLine()) != null) {
                page += line;
            }
            return page;

        } catch (MalformedURLException e) { // for URL.
            e.printStackTrace();
        } catch (IOException e) { // for openConnection().
            e.printStackTrace();
        } finally {
            if (urlConn != null)
                urlConn.disconnect();
        }
        return null;
    }
}
