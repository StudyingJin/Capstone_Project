Module.register("MMM-CustomButtons", {
    defaults: {
        // 기본 설정은 여기에 추가
    },

    getDom: function() {
        var self = this;
        var wrapper = document.createElement("div");
        wrapper.className = "button-wrapper";

        // Push-up 버튼
        var button1 = document.createElement("button");
        button1.innerHTML = "Push-up";
        button1.className = "custom-button";
        button1.addEventListener("click", function() {
            self.startExercise("pushup");
        });

        // Dumbbell Curl 버튼
        var button2 = document.createElement("button");
        button2.innerHTML = "Dumbbell Curl";
        button2.className = "custom-button";
        button2.addEventListener("click", function() {
            self.startExercise("dumbbell_curl");
        });

        // Squat 버튼
        var button3 = document.createElement("button");
        button3.innerHTML = "Squat";
        button3.className = "custom-button";
        button3.addEventListener("click", function() {
            self.startExercise("squat");
        });

        // Quit 버튼
        var button4 = document.createElement("button");
        button4.innerHTML = "Quit";
        button4.className = "custom-button";
        button4.addEventListener("click", function() {
            self.endExercise();
        });

        wrapper.appendChild(button1);
        wrapper.appendChild(button2);
        wrapper.appendChild(button3);
        wrapper.appendChild(button4);

        return wrapper;
    },

    startExercise: function(exerciseType) {
        // 새로운 창 열기 (전체 화면 모드로 열기)
        var exerciseWindow = window.open("http://127.0.0.1:5000/video_feed/" + exerciseType, exerciseType, "width=800,height=600");
    },

    endExercise: function() {
        // 운동 세션 종료 후 만족도 조사 페이지로 이동
        window.location.href = "http://127.0.0.1:5000/satisfaction";
    },

    getStyles: function() {
        return ["MMM-CustomButtons.css"];
    },
});


