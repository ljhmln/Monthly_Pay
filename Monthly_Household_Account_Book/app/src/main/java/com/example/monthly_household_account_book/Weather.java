package com.example.monthly_household_account_book;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Weather extends Fragment {

    private View view;

    private GpsTracker gpsTracker;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.weather, container, false);
        //MainActivity의 메소드 사용하여 액션바 타이틀 변경.
        ((MainActivity) getActivity()).setActionBarTitle("저축 현황");

        gpsTracker = new GpsTracker(container.getContext());
        GetAddress getAddress = new GetAddress();

        if(!getAddress.checkLocationServicesStatus()){
            getAddress.showDialogForLocationServiceSetting();
        }else{
            getAddress.checkRunTimePermission();
        }

        final TextView t1;
        TextView t2, t3;
        t1 = container.findViewById(R.id.t1);
        t2 = container.findViewById(R.id.t2);
        t3 = container.findViewById(R.id.t3);

        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();

        String address = getAddress.getCurrentAddress(latitude,longitude);
        t1.setText(address);

        return view;
    }

    class GetAddress extends AppCompatActivity {

        @Override
        public void onRequestPermissionsResult(int permsRequestCode,
                                               @NonNull String[] permissions,
                                               @NonNull int[] grandResults){
            if(permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length){
                // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면
                boolean check_Result = true;

                for(int result : grandResults){
                    if(result != PackageManager.PERMISSION_GRANTED){
                        check_Result = false;
                        break;
                    }
                }
                if(check_Result){
                    //위치 값을 가져올 수 있음
                }else{
                    //거부한 퍼미션이 있따면 앱을 사용할 숭 없는 이유를 설명해주고 앱을 종료함 2가지 경우가 있음
                    if(ActivityCompat.shouldShowRequestPermissionRationale(this,REQUIRED_PERMISSIONS[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(this,REQUIRED_PERMISSIONS[1])){
                        Toast.makeText(this,"퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요",Toast.LENGTH_LONG).show();
                        finish();
                    }else{
                        Toast.makeText(this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        void checkRunTimePermission(){

            //런타임 퍼미션 처리
            //1. 위치 퍼미션을 가지고 있는지 체크합니다.
            int hasFindLocationPermission = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION);
            int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION);

            if(hasFindLocationPermission == PackageManager.PERMISSION_GRANTED && hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED){
                //2. 이미 퍼미션을 가지고 있다면
                //( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식함)
                //3. 위치값을 가져올 수 있음
            }else{
                //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다 2가지 경우 (3-1, 4-1)가 있음
                //3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
                if(ActivityCompat.shouldShowRequestPermissionRationale(this,REQUIRED_PERMISSIONS[0])){
                    //3-2. 요청을 진행하기 전에 사용자에게 퍼미션이 필요한 이유를 설명해줌
                    Toast.makeText(this,"이 앱을 실행하려면 위치 접근 권한이 필요합니다",Toast.LENGTH_LONG).show();
                    //3-3. 사용자에게 퍼미션 요청을 함 요청 결과는 onRequestPermissionResult에서 수신됨
                    ActivityCompat.requestPermissions(this,REQUIRED_PERMISSIONS,PERMISSIONS_REQUEST_CODE);
                }else{
                    //4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 함
                    // 요청 결과는 onRequestPermissionResult에서 수신됨
                    ActivityCompat.requestPermissions(this,REQUIRED_PERMISSIONS,PERMISSIONS_REQUEST_CODE);
                }
            }
        }
        public String getCurrentAddress(double latitude, double longitude){
            //지오코더... GPS를 주소로 변환
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());

            List<Address> addresses;

            try{
                addresses = geocoder.getFromLocation(latitude,longitude,7);
            }catch(IOException e){
                Toast.makeText(this,"지오코더 서비스 사용불가",Toast.LENGTH_LONG).show();
                return "지오코더 서비스 사용불가";
            }catch(IllegalArgumentException e){
                Toast.makeText(this,"잘못된 GPS좌표",Toast.LENGTH_LONG).show();
                return "잘못된 GPS좌표";
            }
            if(addresses == null || addresses.size() == 0){
                Toast.makeText(this,"주소 미발견",Toast.LENGTH_LONG).show();
                return "주소 미발견";
            }
            Address address = addresses.get(0);
            return address.getAddressLine(0).toString()+"\n";
        }

        //여기서부터는 GPS 활성화를 위한 메소드들
        private void showDialogForLocationServiceSetting(){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("위치 서비스 비활성화");
            builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"+"위치 설정을 수정하실래요?");
            builder.setCancelable(true);
            builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    Intent callGPSSettingIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
                }
            });
            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            builder.create().show();
        }
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode,resultCode,data);

            switch (requestCode){

                case GPS_ENABLE_REQUEST_CODE:
                    //사용자가 GPS 활성 시켰는지 검사
                    if(checkLocationServicesStatus()){
                        if(checkLocationServicesStatus()){
                            Log.d("@@@","onActivityResult : GPS 활성화 됨");
                            checkRunTimePermission();
                            return;
                        }
                    }
                    break;
            }
        }
        public boolean checkLocationServicesStatus(){
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                    || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }
    }

}