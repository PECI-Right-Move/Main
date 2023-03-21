//
// This file is auto-generated. Please don't modify it!
//
package org.opencv.objdetect;

import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Algorithm;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.objdetect.Board;
import org.opencv.objdetect.DetectorParameters;
import org.opencv.objdetect.Dictionary;
import org.opencv.objdetect.RefineParameters;
import org.opencv.utils.Converters;

// C++: class ArucoDetector
/**
 * The main functionality of ArucoDetector class is detection of markers in an image with detectMarkers() method.
 *
 * After detecting some markers in the image, you can try to find undetected markers from this dictionary with
 * refineDetectedMarkers() method.
 *
 * SEE: DetectorParameters, RefineParameters
 */
public class ArucoDetector extends Algorithm {

    protected ArucoDetector(long addr) { super(addr); }
    public static final int
            CORNER_REFINE_NONE = 0,
            CORNER_REFINE_SUBPIX = 1,
            CORNER_REFINE_CONTOUR = 2,
            CORNER_REFINE_APRILTAG = 3,
            DICT_4X4_50 = 0,
            DICT_4X4_100 = 0+1,
            DICT_4X4_250 = 0+2,
            DICT_4X4_1000 = 0+3,
            DICT_5X5_50 = 0+4,
            DICT_5X5_100 = 0+5,
            DICT_5X5_250 = 0+6,
            DICT_5X5_1000 = 0+7,
            DICT_6X6_50 = 0+8,
            DICT_6X6_100 = 0+9,
            DICT_6X6_250 = 0+10,
            DICT_6X6_1000 = 0+11,
            DICT_7X7_50 = 0+12,
            DICT_7X7_100 = 0+13,
            DICT_7X7_250 = 0+14,
            DICT_7X7_1000 = 0+15,
            DICT_ARUCO_ORIGINAL = 0+16,
            DICT_APRILTAG_16h5 = 0+17,
            DICT_APRILTAG_25h9 = 0+18,
            DICT_APRILTAG_36h10 = 0+19,
            DICT_APRILTAG_36h11 = 0+20;
    // internal usage only
    public static ArucoDetector __fromPtr__(long addr) { return new ArucoDetector(addr); }

    //
    // C++:   cv::aruco::ArucoDetector::ArucoDetector(Dictionary dictionary = getPredefinedDictionary(cv::aruco::DICT_4X4_50), DetectorParameters detectorParams = DetectorParameters(), RefineParameters refineParams = RefineParameters())
    //

    /**
     * Basic ArucoDetector constructor
     *
     * @param dictionary indicates the type of markers that will be searched
     * @param detectorParams marker detection parameters
     * @param refineParams marker refine detection parameters
     */
    public ArucoDetector(Dictionary dictionary, DetectorParameters detectorParams, RefineParameters refineParams) {
        super(ArucoDetector_0(dictionary.nativeObj, detectorParams.nativeObj, refineParams.nativeObj));
    }

    /**
     * Basic ArucoDetector constructor
     *
     * @param dictionary indicates the type of markers that will be searched
     * @param detectorParams marker detection parameters
     */
    public ArucoDetector(Dictionary dictionary, DetectorParameters detectorParams) {
        super(ArucoDetector_1(dictionary.nativeObj, detectorParams.nativeObj));
    }

    /**
     * Basic ArucoDetector constructor
     *
     * @param dictionary indicates the type of markers that will be searched
     */
    public ArucoDetector(Dictionary dictionary) {
        super(ArucoDetector_2(dictionary.nativeObj));
    }

    /**
     * Basic ArucoDetector constructor
     *
     */
    public ArucoDetector() {
        super(ArucoDetector_3());
    }


    //
    // C++:  void cv::aruco::ArucoDetector::detectMarkers(Mat image, vector_Mat& corners, Mat& ids, vector_Mat& rejectedImgPoints = vector_Mat())
    //

    /**
     * Basic marker detection
     *
     * @param image input image
     * @param corners vector of detected marker corners. For each marker, its four corners
     * are provided, (e.g std::vector&lt;std::vector&lt;cv::Point2f&gt; &gt; ). For N detected markers,
     * the dimensions of this array is Nx4. The order of the corners is clockwise.
     * @param ids vector of identifiers of the detected markers. The identifier is of type int
     * (e.g. std::vector&lt;int&gt;). For N detected markers, the size of ids is also N.
     * The identifiers have the same order than the markers in the imgPoints array.
     * @param rejectedImgPoints contains the imgPoints of those squares whose inner code has not a
     * correct codification. Useful for debugging purposes.
     *
     * Performs marker detection in the input image. Only markers included in the specific dictionary
     * are searched. For each detected marker, it returns the 2D position of its corner in the image
     * and its corresponding identifier.
     * Note that this function does not perform pose estimation.
     * <b>Note:</b> The function does not correct lens distortion or takes it into account. It's recommended to undistort
     * input image with corresponging camera model, if camera parameters are known
     * SEE: undistort, estimatePoseSingleMarkers,  estimatePoseBoard
     */
    public void detectMarkers(Mat image, List<Mat> corners, Mat ids, List<Mat> rejectedImgPoints) {
        Mat corners_mat = new Mat();
        Mat rejectedImgPoints_mat = new Mat();
        detectMarkers_0(nativeObj, image.nativeObj, corners_mat.nativeObj, ids.nativeObj, rejectedImgPoints_mat.nativeObj);
        Converters.Mat_to_vector_Mat(corners_mat, corners);
        corners_mat.release();
        Converters.Mat_to_vector_Mat(rejectedImgPoints_mat, rejectedImgPoints);
        rejectedImgPoints_mat.release();
    }

    /**
     * Basic marker detection
     *
     * @param image input image
     * @param corners vector of detected marker corners. For each marker, its four corners
     * are provided, (e.g std::vector&lt;std::vector&lt;cv::Point2f&gt; &gt; ). For N detected markers,
     * the dimensions of this array is Nx4. The order of the corners is clockwise.
     * @param ids vector of identifiers of the detected markers. The identifier is of type int
     * (e.g. std::vector&lt;int&gt;). For N detected markers, the size of ids is also N.
     * The identifiers have the same order than the markers in the imgPoints array.
     * correct codification. Useful for debugging purposes.
     *
     * Performs marker detection in the input image. Only markers included in the specific dictionary
     * are searched. For each detected marker, it returns the 2D position of its corner in the image
     * and its corresponding identifier.
     * Note that this function does not perform pose estimation.
     * <b>Note:</b> The function does not correct lens distortion or takes it into account. It's recommended to undistort
     * input image with corresponging camera model, if camera parameters are known
     * SEE: undistort, estimatePoseSingleMarkers,  estimatePoseBoard
     */
    public void detectMarkers(Mat image, List<Mat> corners, Mat ids) {
        Mat corners_mat = new Mat();
        detectMarkers_1(nativeObj, image.nativeObj, corners_mat.nativeObj, ids.nativeObj);
        Converters.Mat_to_vector_Mat(corners_mat, corners);
        corners_mat.release();
    }


    //
    // C++:  void cv::aruco::ArucoDetector::refineDetectedMarkers(Mat image, Board board, vector_Mat& detectedCorners, Mat& detectedIds, vector_Mat& rejectedCorners, Mat cameraMatrix = Mat(), Mat distCoeffs = Mat(), Mat& recoveredIdxs = Mat())
    //

    /**
     * Refind not detected markers based on the already detected and the board layout
     *
     * @param image input image
     * @param board layout of markers in the board.
     * @param detectedCorners vector of already detected marker corners.
     * @param detectedIds vector of already detected marker identifiers.
     * @param rejectedCorners vector of rejected candidates during the marker detection process.
     * @param cameraMatrix optional input 3x3 floating-point camera matrix
     * \(A = \vecthreethree{f_x}{0}{c_x}{0}{f_y}{c_y}{0}{0}{1}\)
     * @param distCoeffs optional vector of distortion coefficients
     * \((k_1, k_2, p_1, p_2[, k_3[, k_4, k_5, k_6],[s_1, s_2, s_3, s_4]])\) of 4, 5, 8 or 12 elements
     * @param recoveredIdxs Optional array to returns the indexes of the recovered candidates in the
     * original rejectedCorners array.
     *
     * This function tries to find markers that were not detected in the basic detecMarkers function.
     * First, based on the current detected marker and the board layout, the function interpolates
     * the position of the missing markers. Then it tries to find correspondence between the reprojected
     * markers and the rejected candidates based on the minRepDistance and errorCorrectionRate parameters.
     * If camera parameters and distortion coefficients are provided, missing markers are reprojected
     * using projectPoint function. If not, missing marker projections are interpolated using global
     * homography, and all the marker corners in the board must have the same Z coordinate.
     */
    public void refineDetectedMarkers(Mat image, Board board, List<Mat> detectedCorners, Mat detectedIds, List<Mat> rejectedCorners, Mat cameraMatrix, Mat distCoeffs, Mat recoveredIdxs) {
        Mat detectedCorners_mat = Converters.vector_Mat_to_Mat(detectedCorners);
        Mat rejectedCorners_mat = Converters.vector_Mat_to_Mat(rejectedCorners);
        refineDetectedMarkers_0(nativeObj, image.nativeObj, board.nativeObj, detectedCorners_mat.nativeObj, detectedIds.nativeObj, rejectedCorners_mat.nativeObj, cameraMatrix.nativeObj, distCoeffs.nativeObj, recoveredIdxs.nativeObj);
        Converters.Mat_to_vector_Mat(detectedCorners_mat, detectedCorners);
        detectedCorners_mat.release();
        Converters.Mat_to_vector_Mat(rejectedCorners_mat, rejectedCorners);
        rejectedCorners_mat.release();
    }

    /**
     * Refind not detected markers based on the already detected and the board layout
     *
     * @param image input image
     * @param board layout of markers in the board.
     * @param detectedCorners vector of already detected marker corners.
     * @param detectedIds vector of already detected marker identifiers.
     * @param rejectedCorners vector of rejected candidates during the marker detection process.
     * @param cameraMatrix optional input 3x3 floating-point camera matrix
     * \(A = \vecthreethree{f_x}{0}{c_x}{0}{f_y}{c_y}{0}{0}{1}\)
     * @param distCoeffs optional vector of distortion coefficients
     * \((k_1, k_2, p_1, p_2[, k_3[, k_4, k_5, k_6],[s_1, s_2, s_3, s_4]])\) of 4, 5, 8 or 12 elements
     * original rejectedCorners array.
     *
     * This function tries to find markers that were not detected in the basic detecMarkers function.
     * First, based on the current detected marker and the board layout, the function interpolates
     * the position of the missing markers. Then it tries to find correspondence between the reprojected
     * markers and the rejected candidates based on the minRepDistance and errorCorrectionRate parameters.
     * If camera parameters and distortion coefficients are provided, missing markers are reprojected
     * using projectPoint function. If not, missing marker projections are interpolated using global
     * homography, and all the marker corners in the board must have the same Z coordinate.
     */
    public void refineDetectedMarkers(Mat image, Board board, List<Mat> detectedCorners, Mat detectedIds, List<Mat> rejectedCorners, Mat cameraMatrix, Mat distCoeffs) {
        Mat detectedCorners_mat = Converters.vector_Mat_to_Mat(detectedCorners);
        Mat rejectedCorners_mat = Converters.vector_Mat_to_Mat(rejectedCorners);
        refineDetectedMarkers_1(nativeObj, image.nativeObj, board.nativeObj, detectedCorners_mat.nativeObj, detectedIds.nativeObj, rejectedCorners_mat.nativeObj, cameraMatrix.nativeObj, distCoeffs.nativeObj);
        Converters.Mat_to_vector_Mat(detectedCorners_mat, detectedCorners);
        detectedCorners_mat.release();
        Converters.Mat_to_vector_Mat(rejectedCorners_mat, rejectedCorners);
        rejectedCorners_mat.release();
    }

    /**
     * Refind not detected markers based on the already detected and the board layout
     *
     * @param image input image
     * @param board layout of markers in the board.
     * @param detectedCorners vector of already detected marker corners.
     * @param detectedIds vector of already detected marker identifiers.
     * @param rejectedCorners vector of rejected candidates during the marker detection process.
     * @param cameraMatrix optional input 3x3 floating-point camera matrix
     * \(A = \vecthreethree{f_x}{0}{c_x}{0}{f_y}{c_y}{0}{0}{1}\)
     * \((k_1, k_2, p_1, p_2[, k_3[, k_4, k_5, k_6],[s_1, s_2, s_3, s_4]])\) of 4, 5, 8 or 12 elements
     * original rejectedCorners array.
     *
     * This function tries to find markers that were not detected in the basic detecMarkers function.
     * First, based on the current detected marker and the board layout, the function interpolates
     * the position of the missing markers. Then it tries to find correspondence between the reprojected
     * markers and the rejected candidates based on the minRepDistance and errorCorrectionRate parameters.
     * If camera parameters and distortion coefficients are provided, missing markers are reprojected
     * using projectPoint function. If not, missing marker projections are interpolated using global
     * homography, and all the marker corners in the board must have the same Z coordinate.
     */
    public void refineDetectedMarkers(Mat image, Board board, List<Mat> detectedCorners, Mat detectedIds, List<Mat> rejectedCorners, Mat cameraMatrix) {
        Mat detectedCorners_mat = Converters.vector_Mat_to_Mat(detectedCorners);
        Mat rejectedCorners_mat = Converters.vector_Mat_to_Mat(rejectedCorners);
        refineDetectedMarkers_2(nativeObj, image.nativeObj, board.nativeObj, detectedCorners_mat.nativeObj, detectedIds.nativeObj, rejectedCorners_mat.nativeObj, cameraMatrix.nativeObj);
        Converters.Mat_to_vector_Mat(detectedCorners_mat, detectedCorners);
        detectedCorners_mat.release();
        Converters.Mat_to_vector_Mat(rejectedCorners_mat, rejectedCorners);
        rejectedCorners_mat.release();
    }

    /**
     * Refind not detected markers based on the already detected and the board layout
     *
     * @param image input image
     * @param board layout of markers in the board.
     * @param detectedCorners vector of already detected marker corners.
     * @param detectedIds vector of already detected marker identifiers.
     * @param rejectedCorners vector of rejected candidates during the marker detection process.
     * \(A = \vecthreethree{f_x}{0}{c_x}{0}{f_y}{c_y}{0}{0}{1}\)
     * \((k_1, k_2, p_1, p_2[, k_3[, k_4, k_5, k_6],[s_1, s_2, s_3, s_4]])\) of 4, 5, 8 or 12 elements
     * original rejectedCorners array.
     *
     * This function tries to find markers that were not detected in the basic detecMarkers function.
     * First, based on the current detected marker and the board layout, the function interpolates
     * the position of the missing markers. Then it tries to find correspondence between the reprojected
     * markers and the rejected candidates based on the minRepDistance and errorCorrectionRate parameters.
     * If camera parameters and distortion coefficients are provided, missing markers are reprojected
     * using projectPoint function. If not, missing marker projections are interpolated using global
     * homography, and all the marker corners in the board must have the same Z coordinate.
     */
    public void refineDetectedMarkers(Mat image, Board board, List<Mat> detectedCorners, Mat detectedIds, List<Mat> rejectedCorners) {
        Mat detectedCorners_mat = Converters.vector_Mat_to_Mat(detectedCorners);
        Mat rejectedCorners_mat = Converters.vector_Mat_to_Mat(rejectedCorners);
        refineDetectedMarkers_3(nativeObj, image.nativeObj, board.nativeObj, detectedCorners_mat.nativeObj, detectedIds.nativeObj, rejectedCorners_mat.nativeObj);
        Converters.Mat_to_vector_Mat(detectedCorners_mat, detectedCorners);
        detectedCorners_mat.release();
        Converters.Mat_to_vector_Mat(rejectedCorners_mat, rejectedCorners);
        rejectedCorners_mat.release();
    }


    //
    // C++:  Dictionary cv::aruco::ArucoDetector::getDictionary()
    //

    public Dictionary getDictionary() {
        return new Dictionary(getDictionary_0(nativeObj));
    }


    //
    // C++:  void cv::aruco::ArucoDetector::setDictionary(Dictionary dictionary)
    //

    public void setDictionary(Dictionary dictionary) {
        setDictionary_0(nativeObj, dictionary.nativeObj);
    }


    //
    // C++:  DetectorParameters cv::aruco::ArucoDetector::getDetectorParameters()
    //

    public DetectorParameters getDetectorParameters() {
        return new DetectorParameters(getDetectorParameters_0(nativeObj));
    }


    //
    // C++:  void cv::aruco::ArucoDetector::setDetectorParameters(DetectorParameters detectorParameters)
    //

    public void setDetectorParameters(DetectorParameters detectorParameters) {
        setDetectorParameters_0(nativeObj, detectorParameters.nativeObj);
    }


    //
    // C++:  RefineParameters cv::aruco::ArucoDetector::getRefineParameters()
    //

    public RefineParameters getRefineParameters() {
        return new RefineParameters(getRefineParameters_0(nativeObj));
    }

    //javadoc: getPredefinedDictionary(dict)

    //
    // C++:  void cv::aruco::ArucoDetector::setRefineParameters(RefineParameters refineParameters)
    //

    public void setRefineParameters(RefineParameters refineParameters) {
        setRefineParameters_0(nativeObj, refineParameters.nativeObj);
    }

    //javadoc: estimatePoseSingleMarkers(corners, markerLength, cameraMatrix, distCoeffs, rvecs, tvecs, _objPoints)
    public static void estimatePoseSingleMarkers(List<Mat> corners, float markerLength, Mat cameraMatrix, Mat distCoeffs, Mat rvecs, Mat tvecs, Mat _objPoints)
    {
        Mat corners_mat = Converters.vector_Mat_to_Mat(corners);
        estimatePoseSingleMarkers_0(corners_mat.nativeObj, markerLength, cameraMatrix.nativeObj, distCoeffs.nativeObj, rvecs.nativeObj, tvecs.nativeObj, _objPoints.nativeObj);

        return;
    }

    //javadoc: estimatePoseSingleMarkers(corners, markerLength, cameraMatrix, distCoeffs, rvecs, tvecs)
    public static void estimatePoseSingleMarkers(List<Mat> corners, float markerLength, Mat cameraMatrix, Mat distCoeffs, Mat rvecs, Mat tvecs)
    {
        Mat corners_mat = Converters.vector_Mat_to_Mat(corners);
        estimatePoseSingleMarkers_1(corners_mat.nativeObj, markerLength, cameraMatrix.nativeObj, distCoeffs.nativeObj, rvecs.nativeObj, tvecs.nativeObj);

        return;
    }
    //
    // C++:  void cv::aruco::drawDetectedMarkers(Mat& image, vector_Mat corners, Mat ids = Mat(), Scalar borderColor = Scalar(0, 255, 0))
    //

    //javadoc: drawDetectedMarkers(image, corners, ids, borderColor)
    public static void drawDetectedMarkers(Mat image, List<Mat> corners, Mat ids, Scalar borderColor)
    {
        Mat corners_mat = Converters.vector_Mat_to_Mat(corners);
        drawDetectedMarkers_0(image.nativeObj, corners_mat.nativeObj, ids.nativeObj, borderColor.val[0], borderColor.val[1], borderColor.val[2], borderColor.val[3]);

        return;
    }

    //javadoc: drawDetectedMarkers(image, corners, ids)
    public static void drawDetectedMarkers(Mat image, List<Mat> corners, Mat ids)
    {
        Mat corners_mat = Converters.vector_Mat_to_Mat(corners);
        drawDetectedMarkers_1(image.nativeObj, corners_mat.nativeObj, ids.nativeObj);

        return;
    }

    //javadoc: drawDetectedMarkers(image, corners)
    public static void drawDetectedMarkers(Mat image, List<Mat> corners)
    {
        Mat corners_mat = Converters.vector_Mat_to_Mat(corners);
        drawDetectedMarkers_2(image.nativeObj, corners_mat.nativeObj);

        return;
    }
    //
    // C++:  void cv::aruco::ArucoDetector::write(FileStorage fs, String name)
    //

    // Unknown type 'FileStorage' (I), skipping the function


    //
    // C++:  void cv::aruco::ArucoDetector::read(FileNode fn)
    //

    // Unknown type 'FileNode' (I), skipping the function


    @Override
    protected void finalize() throws Throwable {
        delete(nativeObj);
    }



    // C++:   cv::aruco::ArucoDetector::ArucoDetector(Dictionary dictionary = getPredefinedDictionary(cv::aruco::DICT_4X4_50), DetectorParameters detectorParams = DetectorParameters(), RefineParameters refineParams = RefineParameters())
    private static native long ArucoDetector_0(long dictionary_nativeObj, long detectorParams_nativeObj, long refineParams_nativeObj);
    private static native long ArucoDetector_1(long dictionary_nativeObj, long detectorParams_nativeObj);
    private static native long ArucoDetector_2(long dictionary_nativeObj);
    private static native long ArucoDetector_3();

    // C++:  void cv::aruco::ArucoDetector::detectMarkers(Mat image, vector_Mat& corners, Mat& ids, vector_Mat& rejectedImgPoints = vector_Mat())
    private static native void detectMarkers_0(long nativeObj, long image_nativeObj, long corners_mat_nativeObj, long ids_nativeObj, long rejectedImgPoints_mat_nativeObj);
    private static native void detectMarkers_1(long nativeObj, long image_nativeObj, long corners_mat_nativeObj, long ids_nativeObj);

    // C++:  void cv::aruco::ArucoDetector::refineDetectedMarkers(Mat image, Board board, vector_Mat& detectedCorners, Mat& detectedIds, vector_Mat& rejectedCorners, Mat cameraMatrix = Mat(), Mat distCoeffs = Mat(), Mat& recoveredIdxs = Mat())
    private static native void refineDetectedMarkers_0(long nativeObj, long image_nativeObj, long board_nativeObj, long detectedCorners_mat_nativeObj, long detectedIds_nativeObj, long rejectedCorners_mat_nativeObj, long cameraMatrix_nativeObj, long distCoeffs_nativeObj, long recoveredIdxs_nativeObj);
    private static native void refineDetectedMarkers_1(long nativeObj, long image_nativeObj, long board_nativeObj, long detectedCorners_mat_nativeObj, long detectedIds_nativeObj, long rejectedCorners_mat_nativeObj, long cameraMatrix_nativeObj, long distCoeffs_nativeObj);
    private static native void refineDetectedMarkers_2(long nativeObj, long image_nativeObj, long board_nativeObj, long detectedCorners_mat_nativeObj, long detectedIds_nativeObj, long rejectedCorners_mat_nativeObj, long cameraMatrix_nativeObj);
    private static native void refineDetectedMarkers_3(long nativeObj, long image_nativeObj, long board_nativeObj, long detectedCorners_mat_nativeObj, long detectedIds_nativeObj, long rejectedCorners_mat_nativeObj);

    // C++:  Dictionary cv::aruco::ArucoDetector::getDictionary()
    private static native long getDictionary_0(long nativeObj);

    // C++:  void cv::aruco::ArucoDetector::setDictionary(Dictionary dictionary)
    private static native void setDictionary_0(long nativeObj, long dictionary_nativeObj);

    // C++:  DetectorParameters cv::aruco::ArucoDetector::getDetectorParameters()
    private static native long getDetectorParameters_0(long nativeObj);

    // C++:  void cv::aruco::ArucoDetector::setDetectorParameters(DetectorParameters detectorParameters)
    private static native void setDetectorParameters_0(long nativeObj, long detectorParameters_nativeObj);

    // C++:  RefineParameters cv::aruco::ArucoDetector::getRefineParameters()
    private static native long getRefineParameters_0(long nativeObj);

    // C++:  void cv::aruco::ArucoDetector::setRefineParameters(RefineParameters refineParameters)
    private static native void setRefineParameters_0(long nativeObj, long refineParameters_nativeObj);

    // native support for java finalize()
    private static native void delete(long nativeObj);

    // C++:  void cv::aruco::estimatePoseSingleMarkers(vector_Mat corners, float markerLength, Mat cameraMatrix, Mat distCoeffs, Mat& rvecs, Mat& tvecs, Mat& _objPoints = Mat())
    private static native void estimatePoseSingleMarkers_0(long corners_mat_nativeObj, float markerLength, long cameraMatrix_nativeObj, long distCoeffs_nativeObj, long rvecs_nativeObj, long tvecs_nativeObj, long _objPoints_nativeObj);
    private static native void estimatePoseSingleMarkers_1(long corners_mat_nativeObj, float markerLength, long cameraMatrix_nativeObj, long distCoeffs_nativeObj, long rvecs_nativeObj, long tvecs_nativeObj);

    // C++:  void cv::aruco::drawDetectedMarkers(Mat& image, vector_Mat corners, Mat ids = Mat(), Scalar borderColor = Scalar(0, 255, 0))
    private static native void drawDetectedMarkers_0(long image_nativeObj, long corners_mat_nativeObj, long ids_nativeObj, double borderColor_val0, double borderColor_val1, double borderColor_val2, double borderColor_val3);
    private static native void drawDetectedMarkers_1(long image_nativeObj, long corners_mat_nativeObj, long ids_nativeObj);
    private static native void drawDetectedMarkers_2(long image_nativeObj, long corners_mat_nativeObj);
}
