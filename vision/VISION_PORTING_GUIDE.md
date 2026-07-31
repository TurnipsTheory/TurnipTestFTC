# Vision Processing: Python to FTC Porting Guide

## Workflow

1. **Develop on laptop** - Use `vision_processing_test.py` to test your algorithm
2. **Port to FTC** - Copy the processing logic to `EasyOpenCVTest.java`
3. **Test on robot** - Deploy and verify it works

---

## Python to Java OpenCV Mapping

### Color Conversions
```python
# PYTHON
hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)
gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
```

```java
// JAVA (FTC)
Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV);
Imgproc.cvtColor(input, gray, Imgproc.COLOR_RGB2GRAY);
```

### Color Range Filtering
```python
# PYTHON
lower = np.array([0, 120, 70])
upper = np.array([10, 255, 255])
mask = cv2.inRange(hsv, lower, upper)
```

```java
// JAVA (FTC)
Scalar lower = new Scalar(0, 120, 70);
Scalar upper = new Scalar(10, 255, 255);
Core.inRange(hsv, lower, upper, mask);
```

### Edge Detection
```python
# PYTHON
edges = cv2.Canny(gray, 50, 150)
```

```java
// JAVA (FTC)
Imgproc.Canny(gray, edges, 50, 150);
```

### Blur/Smoothing
```python
# PYTHON
blurred = cv2.GaussianBlur(frame, (15, 15), 0)
```

```java
// JAVA (FTC)
Imgproc.GaussianBlur(input, blurred, new Size(15, 15), 0);
```

### Find Contours
```python
# PYTHON
contours, _ = cv2.findContours(mask, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
```

```java
// JAVA (FTC)
List<MatOfPoint> contours = new ArrayList<>();
Mat hierarchy = new Mat();
Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
```

### Draw Shapes
```python
# PYTHON
cv2.rectangle(frame, (x, y), (x+w, y+h), (0, 255, 0), 2)
cv2.circle(frame, (cx, cy), 5, (255, 0, 0), -1)
cv2.putText(frame, "Text", (x, y), cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 255, 255), 2)
```

```java
// JAVA (FTC)
Imgproc.rectangle(input, new Point(x, y), new Point(x+w, y+h), new Scalar(0, 255, 0), 2);
Imgproc.circle(input, new Point(cx, cy), 5, new Scalar(255, 0, 0), -1);
Imgproc.putText(input, "Text", new Point(x, y), Imgproc.FONT_HERSHEY_SIMPLEX, 1, new Scalar(255, 255, 255), 2);
```

### Bitwise Operations
```python
# PYTHON
result = cv2.bitwise_and(img1, img2)
result = cv2.bitwise_or(mask1, mask2)
```

```java
// JAVA (FTC)
Core.bitwise_and(img1, img2, result);
Core.bitwise_or(mask1, mask2, result);
```

---

## Example: Complete Porting

### Python Version
```python
def process_frame(frame):
    hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)
    lower_red = np.array([0, 120, 70])
    upper_red = np.array([10, 255, 255])
    mask = cv2.inRange(hsv, lower_red, upper_red)

    contours, _ = cv2.findContours(mask, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

    for contour in contours:
        area = cv2.contourArea(contour)
        if area > 500:
            x, y, w, h = cv2.boundingRect(contour)
            cv2.rectangle(frame, (x, y), (x+w, y+h), (0, 255, 0), 2)

    return frame
```

### FTC Java Version
```java
@Override
public Mat processFrame(Mat input) {
    Mat hsv = new Mat();
    Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV);

    Scalar lowerRed = new Scalar(0, 120, 70);
    Scalar upperRed = new Scalar(10, 255, 255);
    Mat mask = new Mat();
    Core.inRange(hsv, lowerRed, upperRed, mask);

    List<MatOfPoint> contours = new ArrayList<>();
    Mat hierarchy = new Mat();
    Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

    for (MatOfPoint contour : contours) {
        double area = Imgproc.contourArea(contour);
        if (area > 500) {
            Rect rect = Imgproc.boundingRect(contour);
            Imgproc.rectangle(input, rect.tl(), rect.br(), new Scalar(0, 255, 0), 2);
        }
    }

    // Release temporary Mats to avoid memory leaks
    hsv.release();
    mask.release();
    hierarchy.release();

    return input;
}
```

---

## Key Differences to Remember

1. **Color Order**: Python uses BGR, FTC uses RGB
2. **Objects**: Python uses tuples/arrays, Java uses objects (`Scalar`, `Point`, `Size`)
3. **Memory**: Java requires `.release()` on temporary Mats
4. **Syntax**: Python uses `cv2.function()`, Java uses `Imgproc.function()` or `Core.function()`

---

## Testing Workflow

1. **Python** - Run `vision_processing_test.py`:
   ```bash
   python vision_processing_test.py
   ```

2. **Java** - Update `EasyOpenCVTest.java` processFrame() method (lines 30-32)

3. **Deploy** - Build and deploy to Robot Controller

4. **Iterate** - If something doesn't work, debug in Python first!
