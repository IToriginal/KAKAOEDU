//id 찾아오는 함수
function getId(id){
    return document.getElementById(id);
}

let data = {};//전송 데이터(JSON)

let ws ;
let mid = getId('mid');
let btnLogin = getId('btnLogin');
let btnSend = getId('btnSend');
let talk = getId('talk');
let msg = getId('msg');

//로그인 처리 - 웹 소켓 연결
btnLogin.addEventListener("click", (e)=> {
    //연결
    ws = new WebSocket("ws://" + "192.168.0.8:9999" + "/chat");
    //메시지가 온 경우
    ws.addEventListener("message",(msg) => {
        //JSON 파싱
        let data = JSON.parse(msg.data);

        //전송한 유저가 자신인지 아닌지에 따라 클래스를 다르게 적용
        let css;
        if (data.mid === mid.value) {
            css = 'class=me';
        } else {
            css = 'class=other';
        }
        //출력할 메시지 생성
        let item = `<div ${css}><span><b>${data.mid}</b></span>[${data.date}]<br/><span>${data.msg}</span></div>`;

        //출력
        talk.innerHTML += item;

        //스크롤 바 이동
        talk.scrollTop = talk.scrollHeight;
    })
});

//데이터를 전송하는 함수
function send() {
    if (msg.value.trim() !== '') {
        //전송할 데이터를 생성
        data.mid = getId('mid').value;
        data.msg = msg.value;
        data.date = new Date().toLocaleString();
        //서버에 전송할 때는 객체를 JSON 문자열로 변환하여 전송
        let temp = JSON.stringify(data);
        ws.send(temp);
    }
    msg.value = '';
}

btnSend.addEventListener("click", (e) => {
    send();
});

msg.addEventListener("keyup", (e) => {
    if (e.keyCode === 13) {
        send();
    }
});