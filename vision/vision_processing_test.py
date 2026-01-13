import cv2
import numpy as np

# Open the webcam
cap = cv2.VideoCapture(0)
cap.set(cv2.CAP_PROP_FRAME_WIDTH, 640)
cap.set(cv2.CAP_PROP_FRAME_HEIGHT, 480)

if not cap.isOpened():
    print("Error: Cannot open webcam")
    exit()

print("Vision processing test started")
print("Press 'q' to quit")
print("\n" + "="*50)
print("DEVELOP YOUR PROCESSING LOGIC BELOW")
print("="*50 + "\n")

while True:
    ret, frame = cap.read()

    if not ret:
        print("Error: Can't receive frame")
        break

    # ========================================================
    # YOUR PROCESSING CODE GOES HERE
    # Everything you write here can be ported to FTC!
    # ========================================================

    # Example 1: Color detection (find red objects)
    hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)

    # Red color range in HSV
    lower_red1 = np.array([0, 120, 70])
    upper_red1 = np.array([10, 255, 255])
    lower_red2 = np.array([170, 120, 70])
    upper_red2 = np.array([180, 255, 255])

    mask1 = cv2.inRange(hsv, lower_red1, upper_red1)
    mask2 = cv2.inRange(hsv, lower_red2, upper_red2)
    red_mask = cv2.bitwise_or(mask1, mask2)

    # Find contours of red objects
    contours, _ = cv2.findContours(red_mask, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

    # Draw bounding boxes around detected objects
    for contour in contours:
        area = cv2.contourArea(contour)
        if area > 500:  # Filter small noise
            x, y, w, h = cv2.boundingRect(contour)
            cv2.rectangle(frame, (x, y), (x+w, y+h), (0, 255, 0), 2)
            cv2.putText(frame, f"Area: {int(area)}", (x, y-10),
                       cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 255, 0), 2)

    # Example 2: Edge detection
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    edges = cv2.Canny(gray, 50, 150)

    # Example 3: Blur/smoothing
    blurred = cv2.GaussianBlur(frame, (15, 15), 0)

    # ========================================================
    # END OF YOUR PROCESSING CODE
    # ========================================================

    # Display windows
    cv2.imshow('Original', frame)
    cv2.imshow('Red Mask', red_mask)
    cv2.imshow('Edges', edges)
    cv2.imshow('Blurred', blurred)

    # Press 'q' to quit
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

cap.release()
cv2.destroyAllWindows()
