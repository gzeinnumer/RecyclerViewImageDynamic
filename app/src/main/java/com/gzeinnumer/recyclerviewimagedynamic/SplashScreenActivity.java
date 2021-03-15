package com.gzeinnumer.recyclerviewimagedynamic;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.gzeinnumer.eeda.helper.FGPermission;

import rebus.permissionutils.PermissionEnum;
import rebus.permissionutils.PermissionManager;

public class SplashScreenActivity extends AppCompatActivity {

    private PermissionEnum[] permissions = new PermissionEnum[]{
            PermissionEnum.READ_EXTERNAL_STORAGE,
            PermissionEnum.WRITE_EXTERNAL_STORAGE,
            PermissionEnum.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        FGPermission.checkPermissions(this, permissions);

        checkPermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handleResult(this, requestCode, permissions, grantResults);

        checkPermissions();
    }

    private void onSuccessCheckPermitions() {

        startActivity(new Intent(getApplicationContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));

    }

    private void checkPermissions() {
        boolean isAllGranted = FGPermission.getPermissionResult(this, permissions);

        if (isAllGranted){
            onSuccessCheckPermitions();
        } else {
            Toast.makeText(this, "Permission Required", Toast.LENGTH_SHORT).show();
        }
    }
}