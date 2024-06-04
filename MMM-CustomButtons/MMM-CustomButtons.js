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

        // Dumb-bell Curl 버튼
        var button2 = document.createElement("button");
        button2.innerHTML = "Dumb-bell Curl";
        button2.className = "custom-button";
        button2.addEventListener("click", function() {
            self.startExercise("dumbbell_curl");
        });

        // 스쿼트 버튼
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
            self.quitExercise();
        });

        wrapper.appendChild(button1);
        wrapper.appendChild(button2);
        wrapper.appendChild(button3);
        wrapper.appendChild(button4);

        return wrapper;
    },

    startExercise: function(exerciseType) {
        // 새로운 창 열기 (전체 화면 모드로 열기)
        var exerciseWindow = window.open("http://127.0.0.1:5000", exerciseType, "width=800,height=600");

        exerciseWindow.onload = function() {
            exerciseWindow.document.body.style.margin = '0';
            exerciseWindow.document.body.style.overflow = 'hidden';

            // CSS 추가: iframe을 창 크기에 맞게 조정
            var style = document.createElement('style');
            style.innerHTML = `
                body, html {
                    width: 100%;
                    height: 100%;
                    margin: 0;
                    overflow: hidden;
                }
                iframe {
                    width: 100%;
                    height: 100%;
                    border: none;
                }
            `;
            exerciseWindow.document.head.appendChild(style);

            console.log(exerciseType + " started in new window.");
        };
    },

    quitExercise: function() {
        // 홈 화면 복귀
        alert("Exercise Session Ended. Returning to Home Screen.");
    },

    getStyles: function() {
        return ["MMM-CustomButtons.css"];
    },
});


