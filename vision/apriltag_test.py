import cv2
import apriltag
import numpy as np

# Open the webcam (try 0, 1, or 2 if one doesn't work)
cap = cv2.VideoCapture(0)

# Set resolution to match FTC (640x480)
cap.set(cv2.CAP_PROP_FRAME_WIDTH, 640)
cap.set(cv2.CAP_PROP_FRAME_HEIGHT, 480)

if not cap.isOpened():
    print("Error: Cannot open webcam")
    exit()

# Create AprilTag detector
# Options: TAG_36h11 (FTC default), TAG_25h9, TAG_16h5
options = apriltag.DetectorOptions(families="tag36h11")
detector = apriltag.Detector(options)

print("AprilTag detector initialized")
print("Press 'q' to quit")

while True:
    ret, frame = cap.read()

    if not ret:
        print("Error: Can't receive frame")
        break

    # Convert to grayscale (AprilTag detection needs grayscale)
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

    # Detect AprilTags
    results = detector.detect(gray)

    # Draw detected tags on the frame
    for r in results:
        # Get the corner points
        corners = r.corners.astype(int)

        # Draw the bounding box
        cv2.polylines(frame, [corners], isClosed=True, color=(0, 255, 0), thickness=2)

        # Draw the tag ID at the center
        center = r.center.astype(int)
        cv2.putText(frame, f"ID: {r.tag_id}",
                    (center[0] - 20, center[1] - 20),
                    cv2.FONT_HERSHEY_SIMPLEX, 0.6, (0, 255, 0), 2)

        # Draw center point
        cv2.circle(frame, tuple(center), 5, (0, 0, 255), -1)

        # Print detection info
        print(f"Detected tag ID {r.tag_id} at center ({center[0]}, {center[1]})")
        print(f"  Distance estimate (pixels): {r.decision_margin}")

    # Display tag count
    cv2.putText(frame, f"Tags detected: {len(results)}",
                (10, 30), cv2.FONT_HERSHEY_SIMPLEX, 0.7, (255, 255, 0), 2)

    # Show the frame
    cv2.imshow('AprilTag Detection', frame)

    # Press 'q' to quit
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

cap.release()
cv2.destroyAllWindows()
