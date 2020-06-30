package com.example.monthly_household_account_book;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Weather_Api {
    String x;
    String y;

    public Weather_Api(String x , String y){
        this.x = x;
        this.y = y;
    }
    String getsky(){

        Run run = new Run();
        Thread thread = new Thread(run);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return run.getSky();
    }
    String gett1h(){

        Run run = new Run();
        Thread thread = new Thread(run);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return run.getT1h();
    }
    String getpty(){

        Run run = new Run();
        Thread thread = new Thread(run);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return run.getPty();
    }
    class Run implements Runnable{
        String sky ;
        String t1h ;
        String pty;
        @Override
        public void run(){
            // 현재 시간 날짜 불러오기
            TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");
            Date now = new Date();
            SimpleDateFormat hour = new SimpleDateFormat("HH");
            hour.setTimeZone(tz);
            String nowHour = hour.format(now);
            SimpleDateFormat min = new SimpleDateFormat("mm");
            min.setTimeZone(tz);
            String nowMin = min.format(now);
            SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
            date.setTimeZone(tz);
            String nowDate = date.format(now);

            String min30 = "30";
            String min45 = "45";
            int a = Integer.parseInt(nowHour);
            int b = a-1;
            int c = a+1;
            String nextHour = Integer.toString(c);
            String prevHour = Integer.toString(b);

            String min00 = "00";
            String fcstNow = nowHour + min00;
            String nowTime = nowHour + nowMin;
            if (nextHour.equals("25")) {
                nextHour = "01";
            }
            try {
                Date minute = min.parse(nowMin);
                Date minStr = min.parse(min45);
                if(minute.getTime()<minStr.getTime()){
                    nowTime = prevHour+min30;
                } else {
                    nowTime = nowHour+min30;
                    fcstNow = nextHour+min00;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //공공데이터 url
            String read = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getUltraSrtFcst?" +
                    "serviceKey=hoZXx3GxdkQ%2Bg0P4SEQXPcIokCmUDUdJeQ5OFJHlrUm%2B10hdiDydDWbgEslYa9XpmfKGO" +
                    "r5j86sqZ%2BXqYvsf6w%3D%3D&pageNo=1&numOfRows=100&dataType=JSON&base_date="+nowDate+"&base_time="+nowTime+"&nx="+x+"&ny="+y;
            try {
                URL url = new URL(read);
                BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                String result  = "";
                String line ;
                while((line= br.readLine()) != null){
                    result += line;
                }

                JSONObject root = new JSONObject(result);
                JSONObject response = root.getJSONObject("response");
                JSONObject body = response.getJSONObject("body");
                JSONObject items = body.getJSONObject("items");
                JSONArray item = items.getJSONArray("item");
                for(int i=0; i<item.length(); i++){
                    JSONObject sun = item.getJSONObject(i);
                    String category = sun.getString("category");
                    String fcstTime = sun.getString("fcstTime");

                    if(category.equals("SKY")){
                        if(fcstTime.equals(fcstNow)){
                            sky = sun.getString("fcstValue");
                            if(sky.equals("1")){
                                sky = "맑음";
                            } else if(sky.equals("3")){
                                sky = "구름 많음";
                            } else if(sky.equals("4")){
                                sky = "흐림";
                            }System.out.println(sky);
                        }
                    }
                    if(category.equals("T1H")){
                        if(fcstTime.equals(fcstNow)){
                            t1h = sun.getString("fcstValue");
                            System.out.println(t1h);
                        }
                    }
                    if(category.equals("PTY")){
                        if(fcstTime.equals(fcstNow)){
                            pty = sun.getString("fcstValue");
                            if(pty.equals("0")){
                                pty = "비안옴";
                            } else if(pty.equals("1")){
                                pty = "비";
                            } else if(pty.equals("2")){
                                pty = "비/눈";
                            } else if(pty.equals("3")){
                                pty = "눈";
                            } else if(pty.equals("4")){
                                pty = "소나기";
                            }System.out.println(pty);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String getSky(){
            return sky;
        }
        String getT1h(){
            return t1h;
        }
        String getPty(){
            return pty;
        }
    }
}


