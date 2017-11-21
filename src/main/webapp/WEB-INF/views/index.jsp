<!DOCTYPE html>
<html>
<head>
    <title>Spring WebSocket</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.4/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    <script src="./js/app.js"></script>
</head>
<body onload="init()">
<div>You are loged in as <b>${pageContext.request.userPrincipal.name}</b> | <a href="/logout">Sign Out</a></div>
<div>
    <input id="to" type="text" placeholder="To..."/>
    <input id="content" type="text" placeholder="Message..."/>
    <button id="sendMessage" onclick="sendMessage();">Send</button>
</div>
<div>
    <p id="log"></p>
</div>
</body>
</html>