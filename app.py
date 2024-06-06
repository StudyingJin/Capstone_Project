from flask import Flask, render_template, Response, request, redirect, url_for, jsonify
import cv2
import mediapipe as mp
import numpy as np
import os
from tensorflow.keras.models import load_model

app = Flask(__name__)

mp_drawing = mp.solutions.drawing_utils
mp_pose = mp.solutions.pose

# 인공지능 모델 로드
model = load_model('/mnt/data/Capstone_evaluate_action.h5')

# 기본 각도 계산 함수
def calculate_angle(a, b, c):
    a = np.array(a)  # 시작
    b = np.array(b)  # 중간
    c = np.array(c)  # 끝
    
    radians = np.arctan2(c[1] - b[1], c[0] - b[0]) - np.arctan2(a[1] - b[1], a[0] - b[0])
    angle = np.abs(radians * 180.0 / np.pi)

    if angle > 180.0:
        angle = 360 - angle

    return angle

# 초기화
counter = 0
stage = None
sets = 0
risk_message = ""

def generate_frames(exercise_type):
    global counter, stage, sets, risk_message
    counter = 0
    sets = 0
    stage = None
    risk_message = ""

    cap = cv2.VideoCapture(0)
    with mp_pose.Pose(min_detection_confidence=0.5, min_tracking_confidence=0.5) as pose:
        while True:
            success, frame = cap.read()
            if not success:
                break

            image = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
            image.flags.writeable = False

            results = pose.process(image)

            image.flags.writeable = True
            image = cv2.cvtColor(image, cv2.COLOR_RGB2BGR)

            try:
                landmarks = results.pose_landmarks.landmark

                if exercise_type == "squat":
                    left_hip = [landmarks[mp_pose.PoseLandmark.LEFT_HIP.value].x,
                                landmarks[mp_pose.PoseLandmark.LEFT_HIP.value].y]
                    left_knee = [landmarks[mp_pose.PoseLandmark.LEFT_KNEE.value].x,
                                 landmarks[mp_pose.PoseLandmark.LEFT_KNEE.value].y]
                    left_ankle = [landmarks[mp_pose.PoseLandmark.LEFT_ANKLE.value].x,
                                  landmarks[mp_pose.PoseLandmark.LEFT_ANKLE.value].y]
                    angle = calculate_angle(left_hip, left_knee, left_ankle)

                    if angle > 160:
                        stage = "up"
                    if angle < 90 and stage == "up":
                        stage = "down"
                        counter += 1
                        if counter % 10 == 0:
                            sets += 1

                    cv2.putText(image, str(angle),
                                tuple(np.multiply(left_knee, [640, 480]).astype(int)),
                                cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255, 255, 255), 2, cv2.LINE_AA)

                elif exercise_type == "dumbbell_curl":
                    right_shoulder = [landmarks[mp_pose.PoseLandmark.RIGHT_SHOULDER.value].x,
                                      landmarks[mp_pose.PoseLandmark.RIGHT_SHOULDER.value].y]
                    right_elbow = [landmarks[mp_pose.PoseLandmark.RIGHT_ELBOW.value].x,
                                   landmarks[mp_pose.PoseLandmark.RIGHT_ELBOW.value].y]
                    right_wrist = [landmarks[mp_pose.PoseLandmark.RIGHT_WRIST.value].x,
                                   landmarks[mp_pose.PoseLandmark.RIGHT_WRIST.value].y]
                    angle = calculate_angle(right_shoulder, right_elbow, right_wrist)

                    if angle > 160:
                        stage = "down"
                    if angle < 30 and stage == "down":
                        stage = "up"
                        counter += 1
                        if counter % 10 == 0:
                            sets += 1

                    cv2.putText(image, str(angle),
                                tuple(np.multiply(right_elbow, [640, 480]).astype(int)),
                                cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255, 255, 255), 2, cv2.LINE_AA)

                elif exercise_type == "pushup":
                    right_shoulder = [landmarks[mp_pose.PoseLandmark.RIGHT_SHOULDER.value].x,
                                      landmarks[mp_pose.PoseLandmark.RIGHT_SHOULDER.value].y]
                    right_elbow = [landmarks[mp_pose.PoseLandmark.RIGHT_ELBOW.value].x,
                                   landmarks[mp_pose.PoseLandmark.RIGHT_ELBOW.value].y]
                    right_wrist = [landmarks[mp_pose.PoseLandmark.RIGHT_WRIST.value].x,
                                   landmarks[mp_pose.PoseLandmark.RIGHT_WRIST.value].y]
                    angle = calculate_angle(right_shoulder, right_elbow, right_wrist)

                    if angle > 160:
                        stage = "up"
                    if angle < 90 and stage == "up":
                        stage = "down"
                        counter += 1
                        if counter % 10 == 0:
                            sets += 1

                    cv2.putText(image, str(angle),
                                tuple(np.multiply(right_elbow, [640, 480]).astype(int)),
                                cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255, 255, 255), 2, cv2.LINE_AA)

                # 위험 문구 로직
                if angle < 30 or angle > 160:
                    risk_message = "Warning: Improper form detected!"

                mp_drawing.draw_landmarks(
                    image, results.pose_landmarks, mp_pose.POSE_CONNECTIONS)

                output_path = os.path.join('/home/pi/MagicMirror', 'pose_landmarks.png')
                cv2.imwrite(output_path, image)

            except Exception as e:
                print(e)

            ret, buffer = cv2.imencode('.jpg', image)
            frame = buffer.tobytes()
            yield (b'--frame\r\n'
                   b'Content-Type: image/jpeg\r\n\r\n' + frame + b'\r\n')

@app.route('/')
def index():
    return render_template('index.html')

@app.route('/video_feed/<exercise_type>')
def video_feed(exercise_type):
    return Response(generate_frames(exercise_type), mimetype='multipart/x-mixed-replace; boundary=frame')

@app.route('/satisfaction', methods=['GET', 'POST'])
def satisfaction():
    if request.method == 'POST':
        rating = request.form['rating']
        # 여기에서 모델을 사용하여 만족도 평가를 수행하고 결과를 저장하거나 처리할 수 있습니다.
        # 예시:
        # model.predict(np.array([rating]))

        return redirect(url_for('index'))
    return render_template('satisfaction.html')

@app.route('/exercise_status')
def exercise_status():
    global counter, sets, risk_message
    return jsonify(counter=counter, sets=sets, risk_message=risk_message)

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)

