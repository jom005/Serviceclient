package com.example.ghost005.serviceclient.services;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

/**
 * Service for controlling the cameras flashlight.
 */
public class CameraService extends Service
{
    private final IBinder binder = new CameraBinder();

    private boolean isFlashlightOn;
    private CameraManager mCameraManager;
    private String mCameraId;


    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return binder;
    }


    public void onCreate() {
        super.onCreate();
        isFlashlightOn = false;
        Boolean isFlashAvailable = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!isFlashAvailable) {

            AlertDialog alert = new AlertDialog.Builder(CameraService.this)
                    .create();
            alert.setTitle("Error !!");
            alert.setMessage("Your device doesn't support flash light!");
            alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alert.show();
            return;
        }
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            mCameraId = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        disableFlashlight();}

    public boolean isFlashlightOn()
    {
        return isFlashlightOn;
    }

    public class CameraBinder extends Binder
    {
        public CameraService getService()
        {
            return CameraService.this;
        }
    }

        /**
         * Enables the flashlight as a torch.
         */
        public void enableFlashlight() {

            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mCameraManager.setTorchMode(mCameraId, true);
                    isFlashlightOn = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * Disables the flashlight.
         */
        public void disableFlashlight() {

            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mCameraManager.setTorchMode(mCameraId, false);
                    isFlashlightOn = false;

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}
