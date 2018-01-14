package com.example.chris.cakeslicer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class AndroidSurfaceViewExample extends Activity implements SurfaceHolder.Callback {
    TextView testView;

    Camera camera;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;

    PictureCallback rawCallback;
    ShutterCallback shutterCallback;
    PictureCallback jpegCallback;

    int image_width = 100;
    int image_height = 100;
    public int number_slices = 2;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        number_slices = Integer.parseInt(getIntent().getStringExtra("NUMSLICES"));
        Log.d("NUMBERSSS", number_slices+"");
        setContentView(R.layout.activity_main);

        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        surfaceHolder.addCallback(this);

        // deprecated setting, but required on Android versions prior to 3.0
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        jpegCallback = new PictureCallback() {
            @SuppressLint("WrongConstant")
            public void onPictureTaken(byte[] data, Camera camera) {
                FileOutputStream outStream = null;
                try {
                    outStream = new FileOutputStream(String.format("/sdcard/%d.jpg", System.currentTimeMillis()));
                    outStream.write(data);
                    outStream.close();
                    Log.d("Log", "onPictureTaken - wrote bytes: " + data.length);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                }
                Toast.makeText(getApplicationContext(), "Picture Saved", 2000).show();
                refreshCamera();
            }
        };

        final SeekBar size_slider = (SeekBar) findViewById(R.id.size_slider);
        size_slider.setMax(500);
        size_slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                // TODO Auto-generated method stub

                ImageView img = (ImageView) findViewById(R.id.guidelines);
                img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(progress,progress);

                image_height = 3*(500-progress);
                image_width = 3*(500-progress);
                img.getLayoutParams().height = image_height;
                img.getLayoutParams().width = image_width;
                img.requestLayout();
                Log.d("Size = ", progress + "");

                //Play sound effect





            }
        });

//        final SeekBar number_slices_slider = (SeekBar) findViewById(R.id.slice_slider);
//        number_slices_slider.setMax(3); // Up to 9 slices
//        number_slices_slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

//            public void onStopTrackingTouch(SeekBar seekBar) {
//                // TODO Auto-generated method stub
//            }
//
//            public void onStartTrackingTouch(SeekBar seekBar) {
//                // TODO Auto-generated method stub
//
//            }
//
//            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
//                // TODO Auto-generated method stub
//                ImageView img = (ImageView) findViewById(R.id.guidelines);
//                if (progress == 0){
//                    img.setImageResource(R.drawable.guidelines_0);
//                } else if (progress == 1){
//                    img.setImageResource(R.drawable.guidelines_1);
//
//                } else if (progress == 2){
//                    img.setImageResource(R.drawable.guidelines_2);
//
//                } else if (progress == 3){
//                    img.setImageResource(R.drawable.guidelines_3);
//
//                }
//
//
//                image_height = 3*progress;
//                image_width = 3*progress;
//                img.getLayoutParams().height = image_height;
//                img.getLayoutParams().width = image_width;
//
//                img = (ImageView) findViewById(R.id.guidelines);
//                img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(progress,progress);
//
//
//                img.getLayoutParams().height = image_height;
//                img.getLayoutParams().width = image_width;
//                img.requestLayout();
//                Log.d("Size = ", progress + "");
//
//
//                img.requestLayout();
//
//                ImageView img2 = (ImageView) findViewById(R.id.num_slides_counter);
//                if (progress == 0){
//                    img2.setImageResource(R.drawable.guidelines_0);
//                } else if (progress == 1){
//                    img2.setImageResource(R.drawable.guidelines_1);
//
//                } else if (progress == 2){
//                    img2.setImageResource(R.drawable.guidelines_2);
//
//                } else if (progress == 3){
//                    img2.setImageResource(R.drawable.guidelines_3);
//
//                }
//                img2.requestLayout();
//                //img2.getLayoutParams().height = image_height;
//                //img2.getLayoutParams().width = image_width;
//
//                //Play sound effect
//            }
//        });

   }

    public void captureImage(View v) throws IOException {
        //take the picture
        camera.takePicture(null, null, jpegCallback);
    }

    public void refreshCamera() {
        if (surfaceHolder.getSurface() == null) {
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            camera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here
        // start preview with new settings
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (Exception e) {

        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // Now that the size is known, set up the camera parameters and begin
        // the preview.
        refreshCamera();
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            // open the camera
            camera = Camera.open();
            camera.setDisplayOrientation(90);
        } catch (RuntimeException e) {
            // check for exceptions
            System.err.println(e);
            return;
        }
        Camera.Parameters param;
        param = camera.getParameters();

        // modify parameter
        param.setPreviewSize(352, 288);
        camera.setParameters(param);
        try {
            // The Surface has been created, now tell the camera where to draw
            // the preview.
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (Exception e) {
            // check for exceptions
            System.err.println(e);
            return;
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // stop preview and release camera
        camera.stopPreview();
        camera.release();
        camera = null;
    }

}