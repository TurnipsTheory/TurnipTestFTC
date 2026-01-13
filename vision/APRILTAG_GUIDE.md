# AprilTag Development Guide

## Quick Start

### Python Testing (on your laptop)
```bash
cd /path/to/TurnipTestFTC
python apriltag_test.py
```
Press 'q' to quit.

### FTC Robot Testing
Deploy `AprilTagTest.java` to your robot and run "AprilTag Detection Test" from TeleOp.

---

## How Python Concepts Map to FTC

### Camera Setup
| Python | FTC |
|--------|-----|
| `cv2.VideoCapture(0)` | `hardwareMap.get(WebcamName.class, "Webcam 1")` |
| `cap.set()` for resolution | Set in `VisionPortal.Builder()` |

### AprilTag Detection
| Python | FTC |
|--------|-----|
| `detector = apriltag.Detector()` | `AprilTagProcessor.Builder().build()` |
| `detector.detect(gray)` | `aprilTag.getDetections()` |
| `results[i].tag_id` | `detection.id` |
| `results[i].center` | `detection.center` |
| `results[i].corners` | Use `detection.corners` |

### Key Differences

**Python (for development):**
- Returns pixel coordinates
- You manually draw visualization
- Good for testing detection parameters
- Need to calculate pose yourself (or use additional libraries)

**FTC (for competition):**
- Returns 3D position automatically (X, Y, Z in inches)
- Returns orientation (Pitch, Roll, Yaw in degrees)
- Returns Range, Bearing, Elevation
- Displays camera feed on Driver Station automatically
- Much easier for navigation!

---

## Common Use Cases

### Just Detection (Is tag visible?)
Both systems detect tags. In FTC, check if `detections.size() > 0`.

### Get Position Relative to Tag
**Python:** You'd need to implement pose estimation manually
**FTC:** Built-in! Use `detection.ftcPose.x/y/z` for position

### Navigation (Drive to tag)
**FTC has everything you need:**
- `detection.ftcPose.range` = distance to tag
- `detection.ftcPose.bearing` = angle to tag (turn left/right)
- `detection.ftcPose.x/y/z` = 3D position

---

## Workflow Recommendation

1. **Test with Python first** - Use `apriltag_test.py` to:
   - Verify your camera works
   - Verify tags are detectable
   - Test lighting conditions
   - Experiment with camera angles

2. **Deploy to FTC** - Use `AprilTagTest.java` on the robot:
   - Already has all the pose estimation built-in
   - No porting of detection code needed!
   - Just use the detection data for navigation

---

## Need Custom Processing?

If you need to do custom image processing (color detection, line following, etc.) THEN use EasyOpenCV:
- Develop algorithm in Python (`opencv-python`)
- Port the processing logic to `EasyOpenCVTest.java` `processFrame()` method
- OpenCV functions are nearly identical (e.g., `cv2.Canny()` → `Imgproc.Canny()`)

But for AprilTags specifically, FTC's built-in support is superior!
