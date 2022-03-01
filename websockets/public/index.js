var ws = new WebSocket("ws://" + location.hostname + ":" + location.port + "/talk");
ws.onmessage = function(event) {
	var messages = document.getElementById("messages");
	if (messages.value.length > 0) {
		messages.value += "\n";
	}
	messages.value += event.data;
}
function send(event) {
	if (event.which === 13) {
		var message = document.getElementById("message");
		ws.send(message.value);
		message.value = "";
	}
}