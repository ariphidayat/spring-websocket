var stompClient = null;

function init() {
    var socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        stompClient.subscribe('/user/topic/messages', function(message) {
            showMessage(JSON.parse(message.body));
        });
    });
}

function sendMessage() {
    var to = document.getElementById('to').value;
    var content = document.getElementById('content').value;
    stompClient.send("/app/chat/" + to, {}, JSON.stringify({'content':content}));
}

function showMessage(message) {
    var response = document.getElementById('response');
    var p = document.createElement('p');
    p.appendChild(document.createTextNode(message.from + ": " + message.content));
    response.appendChild(p);
}