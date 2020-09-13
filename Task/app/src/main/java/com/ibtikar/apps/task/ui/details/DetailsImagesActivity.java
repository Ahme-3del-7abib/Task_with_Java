package com.ibtikar.apps.task.ui.details;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ibtikar.apps.task.R;
import com.ibtikar.apps.task.Utils.ConstantsUtils;
import com.ibtikar.apps.task.databinding.ActivityDetailsImagesBinding;
import com.ibtikar.apps.task.ui.main.MainActivity;
import com.ibtikar.apps.task.ui.profile.ProfileActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class DetailsImagesActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private static final int WRITE_EXTERNAL = 122;
    private ActivityDetailsImagesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_images);

        getPermission();
        setView();

        binding.actorFullImgID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });
    }

    private void setView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details_images);
        String Img = getIntent().getExtras().getString("image");
        Picasso.get().load(ConstantsUtils.BASE_IMAGE_SOURCE + Img).into(binding.actorFullImgID);
    }

    private void showAlertDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final View customLayout = LayoutInflater.from(this).inflate(R.layout.custom_alert_dialog, null);
        builder.setView(customLayout);

        Button YES = customLayout.findViewById(R.id.yesBtn);
        YES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToGallery();
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void saveToGallery() {

        BitmapDrawable draw = (BitmapDrawable) binding.actorFullImgID.getDrawable();
        Bitmap bitmap = draw.getBitmap();

        FileOutputStream outStream = null;
        File sdCard = Environment.getExternalStorageDirectory();

        File dir = new File(sdCard.getAbsolutePath() + "/Demo");
        dir.mkdirs();
        String fileName = String.format("%d.jpg", System.currentTimeMillis());
        File outFile = new File(dir, fileName);

        try {
            outStream = new FileOutputStream(outFile);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            Toast.makeText(this, "Image Saved Successfully", Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }

        try {
            outStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(outFile));
        sendBroadcast(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(WRITE_EXTERNAL)
    private void getPermission() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // do nothing
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_sms),
                    WRITE_EXTERNAL, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(this, "Now you can download any image you needed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(this, "You must accept permission access if you want to download image !!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DetailsImagesActivity.this, MainActivity.class));
    }
}
