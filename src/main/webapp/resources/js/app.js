var stompClient = null;

function init() {
    var socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, stompSuccess);
}

function stompSuccess() {
    stompClient.subscribe('/topic/messages', showMessage);
    stompClient.subscribe('/user/queue/messages', showMessage);
}

function sendMessage() {
    var to = document.getElementById('to').value;
    var content = document.getElementById('content').value;
    stompClient.send("/app/chat/" + to, {}, JSON.stringify({'content':content}));
}

function showMessage(res) {
    var message = JSON.parse(res.body);
    var log = document.getElementById('log');
    var p = document.createElement('p');
    p.appendChild(document.createTextNode(message.from + ": " + message.content));
    log.appendChild(p);
}