package image_search;

public class test_openCV {
	private static void main(String path1, String path2) {
	    System.out.println(path1 + "-" + path2);

	    FeatureDetector detector = FeatureDetector.create(FeatureDetector.ORB);
	    DescriptorExtractor descriptor = DescriptorExtractor.create(DescriptorExtractor.ORB);

	    DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);

	    // first image
	    Mat img1 = Imgcodecs.imread(path1, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
	    Mat descriptors1 = new Mat();
	    MatOfKeyPoint keypoints1 = new MatOfKeyPoint();

	    detector.detect(img1, keypoints1);
	    descriptor.compute(img1, keypoints1, descriptors1);

	    // second image
	    Mat img2 = Imgcodecs.imread(path2, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
	    Mat descriptors2 = new Mat();
	    MatOfKeyPoint keypoints2 = new MatOfKeyPoint();

	    detector.detect(img2, keypoints2);
	    descriptor.compute(img2, keypoints2, descriptors2);

	    // match these two keypoints sets
	    MatOfDMatch matches = new MatOfDMatch();
	    matcher.match(descriptors1, descriptors2, matches);

	    for (DMatch m : matches.toArray()) {
	      // how to use these values to detect the similarity? They seem to be way off
	      // all of these values are in range 50-80 which seems wrong to me
	      System.out.println(m.distance);
	    }
	  }
}
