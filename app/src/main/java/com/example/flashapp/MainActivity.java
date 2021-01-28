package com.example.flashapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {
ImageButton off;
boolean state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        off = findViewById(R.id.off);
        Dexter.withContext(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                runFlashlight();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Toast.makeText(getApplicationContext(),"Camera Permission Required",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

            }
        }).check();


    }
    private void runFlashlight(){
        off.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                if (!state){
                    CameraManager cm = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                    try {
                            String cameraid = cm.getCameraIdList()[0];
                            cm.setTorchMode(cameraid,true);
                            state = true;
                            off.setImageResource(R.drawable.torch_on);
                    }catch (Exception e){

                    }
                }
                else
                {
                    CameraManager cm = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                    try {
                        String cameraid = cm.getCameraIdList()[0];
                        cm.setTorchMode(cameraid,false);
                        state = false;
                        off.setImageResource(R.drawable.torch_off);
                    }catch (Exception e){

                    }
                }
            }
        });
    }
}