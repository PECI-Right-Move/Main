
package com.example.sample;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraActivity;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.calib3d.Calib3d;
import org.opencv.core.CvType;
import org.opencv.core.MatOfDouble;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.*;
import org.opencv.core.Mat;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.objdetect.ArucoDetector;
import org.opencv.objdetect.DetectorParameters;
import org.opencv.objdetect.Dictionary;
import org.opencv.objdetect.RefineParameters;
import  java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import android.view.ViewGroup;

import com.example.sample.MainActivity;
import com.example.sample.Pino;
import com.example.sample.Placa;
import com.example.sample.R;

public class colorVerification extends CameraActivity implements CvCameraViewListener2 {
    private static final String TAG = "OCVSample::Activity";
    private CameraBridgeViewBase mOpenCvCameraView;
    private boolean              mIsJavaCamera = true;
    private MenuItem             mItemSwitchCamera = null;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    public colorVerification() {
        Log.i(TAG, "Instantiated new " + this.getClass());
    }

    static{ System.loadLibrary("opencv_java4"); }
    /** Called when the activity is first created. */


    @Override
    public void onCreate(Bundle savedInstanceState) {

        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.color_verification);

        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.tutorial1_activity_java_surface_view);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);

        View rootView = findViewById(android.R.id.content).getRootView();
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rootView.setLayoutParams(params);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    @Override
    protected List<? extends CameraBridgeViewBase> getCameraViewList() {
        return Collections.singletonList(mOpenCvCameraView);
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    public void onCameraViewStarted(int width, int height) {
    }

    public void onCameraViewStopped() {
    }

    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        Mat img = inputFrame.gray();
        Mat rgba =inputFrame.rgba();
        // Apply Hough transform to detect circles
        Mat circles = new Mat();
        Imgproc.HoughCircles(img, circles, Imgproc.HOUGH_GRADIENT, 1, 20, 30, 30, 15, 40);
        if (circles.total()!=0) {
            // Convert the (x, y) coordinates and radius of the circles to integers
            int numCircles = (int) circles.total();
            Point[] circleCenters = new Point[numCircles];
            float[] circleRadii = new float[numCircles];
            for (int i = 0; i < numCircles; i++) {
                double[] circleData = circles.get(0, i);
                circleCenters[i] = new Point(Math.round(circleData[0]), Math.round(circleData[1]));
                circleRadii[i] = (float) Math.round(circleData[2]);
            }

            // Filter out smaller circles contained within larger ones
            List<Integer> sortedIndices = new ArrayList<>();
            for (int i = 0; i < numCircles; i++) {
                sortedIndices.add(i);
            }
            Collections.sort(sortedIndices, (a, b) -> Float.compare(circleRadii[b], circleRadii[a]));
            List<Point> finalCenters = new ArrayList<>();
            List<Float> finalRadii = new ArrayList<>();
            for (int i = 0; i < numCircles; i++) {
                boolean isContained = false;
                for (int j = i + 1; j < numCircles; j++) {
                    if (doCirclesCollide(circleCenters[i], circleRadii[i], circleCenters[j], circleRadii[j])) {
                        isContained = true;
                        break;
                    }
                }
                if (!isContained) {
                    finalCenters.add(circleCenters[i]);
                    finalRadii.add(circleRadii[i]);
                }
            }
            System.out.println(finalCenters.size());
            if (finalCenters.size()== 128) {
                for (int i = 0; i < finalCenters.size(); i++) {
                    Imgproc.circle(rgba, finalCenters.get(i), Math.round(finalRadii.get(i)), new Scalar(0, 255, 0), 2);
                }
                Placa placa = new Placa(finalCenters);

                Pino[][] matrix = placa.getMatrix();

                int pinoY = -1;
                int pinoX = -1;
                while (pinoX == -1 || pinoY == -1) {
                    if (pinoX == -1) {
                        pinoX = getPieceX();
                    }
                    if (pinoY == -1) {
                        pinoY = getPieceY();
                    }
                }
                double x = matrix[pinoX][pinoY].x;
                double y = matrix[pinoX][pinoY].y;
                // Define a Scalar object to store the RGB values of the pixel
                Scalar pixelRgb = new Scalar(0, 0, 0);
                // Check if the pixel coordinates are within the bounds of the image
                if (x >= 0 && x < rgba.width() && y >= 0 && y < rgba.height()) {
                    // Get the pixel value at the specified location as a 1x1 matrix
                    Mat pixelMat = rgba.submat((int) y, (int) y + 1, (int) x, (int) x + 1);

                    // Convert the 1x1 matrix to a Scalar object representing the RGB values of the pixel
                    pixelRgb = new Scalar(pixelMat.get(0, 0));
                }

                double red = pixelRgb.val[0];
                double green = pixelRgb.val[1];
                double blue = pixelRgb.val[2];

                for (int i = 0; i < matrix.length; i++) {
                    Imgproc.circle(rgba, new Point(matrix[i][0].x, matrix[i][0].y), 5, new Scalar(255, 0, 0), -1);
                }
                for (int i = 0; i < matrix.length; i++) {
                    Imgproc.circle(rgba, new Point(matrix[i][1].x, matrix[i][1].y), 5, new Scalar(255, 255, 0), -1);
                }
                for (int i = 0; i < matrix.length; i++) {
                    Imgproc.circle(rgba, new Point(matrix[i][2].x, matrix[i][2].y), 5, new Scalar(255, 0, 255), -1);
                }
                for (int i = 0; i < matrix.length; i++) {
                    Imgproc.circle(rgba, new Point(matrix[i][3].x, matrix[i][3].y), 5, new Scalar(255, 255, 255), -1);
                }
                for (int i = 0; i < matrix.length; i++) {
                    Imgproc.circle(rgba, new Point(matrix[i][4].x, matrix[i][4].y), 5, new Scalar(0, 255, 0), -1);
                }
                    /*
                    List<Point> leftmost= placa.getLeftmost();
                    List<Point> rightmost= placa.getRightmost();
                    for (Point h:rightmost) {
                        Imgproc.circle(rgba, h, 5, new Scalar(255, 0, 0), -1);
                    }
                    for( Point h:leftmost){
                        Imgproc.circle(rgba, h, 5, new Scalar(255, 255, 255), -1);
                    }
                    */
                // Define the color space to compare the pixel's RGB values against
                Scalar[] colors = new Scalar[]{
                        new Scalar(255, 0, 0), // Red
                        new Scalar(255, 255, 0), // Yellow
                        new Scalar(0, 255, 0), // Green
                        new Scalar(0, 0, 255), // Blue
                        new Scalar(255, 255, 255), // White
                };

                // Calculate the Euclidean distance between the pixel's RGB values and each color in the color space
                double[] distances = new double[colors.length];
                for (int i = 0; i < colors.length; i++) {
                    distances[i] = Math.sqrt(Math.pow(red - colors[i].val[0], 2)
                            + Math.pow(green - colors[i].val[1], 2)
                            + Math.pow(blue - colors[i].val[2], 2));
                }
                // Find the index of the closest color in the color space
                int closestColorIndex = 0;
                double closestColorDistance = distances[0];
                for (int i = 1; i < distances.length; i++) {
                    if (distances[i] < closestColorDistance) {
                        closestColorIndex = i;
                        closestColorDistance = distances[i];
                    }
                }

                // Get the name of the closest color
                String closestColorName;
                switch (closestColorIndex) {
                    case 0:
                        closestColorName = "Red";
                        break;
                    case 1:
                        closestColorName = "Yellow";
                        break;
                    case 2:
                        closestColorName = "Green";
                        break;
                    case 3:
                        closestColorName = "Blue";
                        break;
                    case 4:
                        closestColorName = "White";
                        break;
                    default:
                        closestColorName = "Unknown";
                        break;
                }

                // Print the name of the closest color to the log
                Log.e("MyApp", "The color is " + closestColorName);

                //setResultColor(closestColorName);
                // Display the resulting image
                System.out.println("Circles found: " + finalCenters.size());
            }
        }



        return rgba;
    }

    private static boolean doCirclesCollide(Point center1, float radius1, Point center2, float radius2) {
        double distance = Math.sqrt(Math.pow(center2.x - center1.x, 2) + Math.pow(center2.y - center1.y, 2));
        if (distance<radius1+radius2) return  true;
        else return false  ;
    }

    private int getPieceX() {
        Intent intent = getIntent();
        int pieceX = intent.getIntExtra("pieceX", -1);
        return pieceX;
    }

    private int getPieceY() {
        Intent intent = getIntent();
        int pieceY = intent.getIntExtra("pieceY", -1);
        return pieceY;
    }

    private void setResultColor(String color) {
        Intent intent = new Intent();
        intent.putExtra("color", color);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

}
