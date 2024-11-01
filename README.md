<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>스마트미러 홈트레이닝 시스템</title>
</head>
<body>

<h2 align="center">스마트미러 홈트레이닝 시스템</h2>
<p align="center">
    이 프로젝트는 COVID-19 팬데믹 상황 속에서 증가한 홈트레이닝 수요에 맞추어 자세 탐지 모델과 라즈베리파이4를 활용하여 
    <strong>안전하고 효율적인 스마트미러 기반 홈트레이닝 시스템</strong>을 구축하는 것을 목표로 합니다.
</p>

<h3 align="center">프로젝트 개요</h3>
<p align="center">
    스마트미러를 통해 사용자가 자신의 운동 자세를 실시간으로 모니터링하고, 잘못된 자세를 즉시 교정할 수 있도록 지원합니다. 자세 탐지 모델로는 MediaPipe를 사용하였으며, 라즈베리파이4가 중앙 제어 장치 역할을 수행합니다.
</p>

<h4>주요 기능</h4>
<ul>
    <li><strong>실시간 자세 추적</strong>: MediaPipe 모델을 사용하여 사용자의 운동 자세를 감지하고 실시간으로 화면에 표시</li>
    <li><strong>자세 교정 피드백</strong>: 잘못된 자세 탐지 시 화면에 경고 문구를 표시하여 사용자가 바로잡을 수 있도록 도움</li>
    <li><strong>운동 기록 및 평가</strong>: 운동 후 만족도를 '만족', '보통', '불만족'으로 기록하여 개인의 운동 습관을 관리</li>
    <li><strong>연동 앱 제공</strong>: Android 앱을 통해 운동량 및 식단 관리를 지원하고 스마트미러와 연동하여 사용자의 건강 상태 데이터를 관리</li>
</ul>

<h3>시스템 구성 요소</h3>
<ul>
    <li><strong>MediaPipe</strong>: 구글의 머신러닝 프레임워크로 실시간 자세 추적을 수행합니다. 총 33개의 랜드마크를 통해 사용자의 자세를 인식합니다.</li>
    <li><strong>라즈베리파이4</strong>: 중앙 제어 장치로 사용되며, MediaPipe 모델을 통해 자세 추적과 사용자 인터페이스 관리를 담당합니다.</li>
    <li><strong>Android 앱</strong>: 사용자의 운동 기록과 건강 데이터를 관리하는 앱으로, Google Assistant와 연동하여 다양한 기능을 제공합니다.</li>
    <li><strong>Flask 웹 프레임워크</strong>: 웹 기반 인터페이스 구축을 위해 사용되어 사용자와 스마트미러 간의 상호작용을 지원합니다.</li>
</ul>

<h3>시스템 구조</h3>
<p>스마트미러는 사용자가 화면을 보며 운동 종목을 선택하고, 선택한 운동에 따라 자세 추적 및 교정이 이루어집니다. 운동이 끝난 후에는 만족도 평가를 통해 운동 기록을 저장하고, 이는 연동된 앱에서 확인할 수 있습니다.</p>

<h4>주요 기능 흐름</h4>
<ol>
    <li><strong>스마트미러와의 상호작용</strong>: 사용자는 스마트미러를 통해 운동 종목을 선택하고 자세 교정 피드백을 받습니다.</li>
    <li><strong>데이터 연동</strong>: Android 앱을 통해 운동 기록 및 식단 데이터를 관리하며, 스마트미러와 데이터가 동기화됩니다.</li>
    <li><strong>운동 결과 평가</strong>: 만족도 평가를 위해 손동작을 인식하여 '만족', '보통', '불만족'으로 기록합니다.</li>
</ol>

<h3>시스템 구현 결과</h3>
<ul>
    <li><strong>자세 교정 효과</strong>: 사용자는 스마트미러를 통해 실시간 자세 교정을 받아 보다 안전한 홈트레이닝을 할 수 있습니다.</li>
    <li><strong>LSTM 신경망</strong>을 사용하여 손동작 인식을 통해 운동 만족도를 기록하고, epoch 80 기준으로 약 99%의 정확도를 달성하였습니다.</li>
</ul>

<h3>참고 문헌</h3>
<ul>
    <li>Kwon J Y, Nam S B, “How Has the Home Training Trend Changed before and after the COVID-19 Pandemic?”, The Korean Society of Sport and Leisure Studies, vol. 90, October 2022.</li>
    <li>Min L C, Myung L D, “Design and Implementation of Exercise Posture Correction System using Behavior Detection“, Korea Institute of Communication Sciences, February 2022.</li>
</ul>

</body>
</html>
