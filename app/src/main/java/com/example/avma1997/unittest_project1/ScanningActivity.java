package com.example.avma1997.unittest_project1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import dalvik.annotation.TestTarget;

public class ScanningActivity extends AppCompatActivity {
    SurfaceView cameraView;
    TextView barcodeInfo;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ContextCompat.checkSelfPermission(ScanningActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(ScanningActivity.this,
                    new String[]{Manifest.permission.CAMERA},
                    5);


        } else {
            loadScanningActivity();
        }

    }


    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 5: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {         // If request is cancelled, the result arrays are empty.
                loadScanningActivity();}
                else
                {finish();}



                // other 'case' lines to check for other
                // permissions this app might request
            }
        }
    }

    public void loadScanningActivity() {

            setContentView(R.layout.activity_scanning);
            cameraView = (SurfaceView) findViewById(R.id.camera_view);
            barcodeInfo = (TextView) findViewById(R.id.code_info);

            barcodeDetector =
                    new BarcodeDetector.Builder(this)
                            .setBarcodeFormats(Barcode.QR_CODE)
                            .build();

            cameraSource = new CameraSource
                    .Builder(this, barcodeDetector)
                    .setRequestedPreviewSize(640, 480)
                    .build();
            cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException ie) {
                        Log.e("CAMERA SOURCE", ie.getMessage());
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    cameraSource.stop();
                }
            });
            barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
                @Override
                public void release() {
                }

                public void receiveDetections(Detector.Detections<Barcode> detections) {
                    final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                    if (barcodes.size() != 0) {
                        barcodeInfo.post(new Runnable() {    // Use the post method of the TextView
                            public void run() {
                                barcodeInfo.setText(    // Update the TextView
                                        barcodes.valueAt(0).displayValue
                                );
                            }
                        });
                    }
                }
            });


        }
    }
