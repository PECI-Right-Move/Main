package com.example.sample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.opencv.android.Utils;
import org.opencv.calib3d.Calib3d;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.MatOfPoint3;
import org.opencv.core.MatOfPoint3f;
import org.opencv.core.Point;
import org.opencv.core.Point3;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Vector;
import java.util.List;

public class Calibration extends AppCompatActivity {
    static{ System.loadLibrary("opencv_java4"); }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getApplicationContext();
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pattern);
        // Convert the bitmap to a grayscale Mat object
        Mat grayscaleMat = new Mat(bitmap.getWidth(), bitmap.getHeight(), CvType.CV_8UC1);
        Utils.bitmapToMat(bitmap, grayscaleMat);
        Imgproc.cvtColor(grayscaleMat, grayscaleMat, Imgproc.COLOR_BGR2GRAY);

        Size patternSize = new Size(9,6);
        Size imageSize = new Size(2000,1500);

        int nframes=1;

        //fazer os vetores
        MatOfPoint3f objpt = new MatOfPoint3f();
        MatOfPoint2f imgpt1 =new MatOfPoint2f();

        double npoints = patternSize.width*patternSize.height;

        Calib3d.findChessboardCorners(grayscaleMat, patternSize,imgpt1);


        for (int j = 0; j < 9*6; j++)
            objpt.push_back(new MatOfPoint3f(new Point3(j % 9, j /6, 0.0f)));

        List<Mat> rvecs=new ArrayList<>();
        List<Mat> tvecs=new ArrayList<>();
        Mat A =new Mat();
        Mat coeficientes = new Mat();

        // turn MatOfPoint2f to List of Mat
        List<Mat> cornersList = new ArrayList<>();
        cornersList.add(imgpt1);

        // turn objpt to a list of mat
        List<Mat> objptList = new ArrayList<>();
        objptList.add(objpt);

        Calib3d.calibrateCamera(objptList,cornersList,imageSize,A,coeficientes,rvecs,tvecs);

        // tvecs e rvecs to MAt
        Mat tvecsList = new Mat();
        Core.vconcat(tvecs, tvecsList);
        Mat rvecsList = new Mat();
        Core.vconcat(rvecs,rvecsList);

        MatOfDouble mat = new MatOfDouble();
        mat.create(1, 4, CvType.CV_64F);
        mat.setTo(new Scalar(0));


        MatOfPoint3f pontos3 =  new MatOfPoint3f();
        pontos3.push_back( new MatOfPoint3f( new Point3(-1,0,0)) );

        MatOfPoint2f pontos2 = new MatOfPoint2f();

        Calib3d.projectPoints(pontos3,rvecsList,tvecsList,A,mat,pontos2);
        List<Point> points = pontos2.toList();
        for (int i = 0; i < points.size(); i++) {
            Point point = points.get(i);
            System.out.println("X: " + point.x + ", Y: " + point.y);
        };
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

    }
}
