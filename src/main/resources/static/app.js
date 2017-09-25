var stompClient = null;

function setConnected(connected) {
    document.getElementById('connect').disabled = connected;
    document.getElementById('disconnect').disabled = !connected;
    document.getElementById('log').style.visibility = connected ? 'visible' : 'hidden';
    document.getElementById('response').innerHTML = '';
}

function connect() {
    var socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        setConnected(true);
        stompClient.subscribe('/topic/messages', function(message) {
            showMessage(JSON.parse(message.body));
        });
    });
}

function disconnect() {
    if(stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
}

function sendMessage() {
    var from = document.getElementById('from').value;
    var content = document.getElementById('content').value;
    stompClient.send("/app/chat", {}, JSON.stringify({'from':from, 'content':content}));
}

function showMessage(message) {
    var response = document.getElementById('response');
    var p = document.createElement('p');
    p.appendChild(document.createTextNode(message.from + ": " + message.content));
    response.appendChild(p);
}