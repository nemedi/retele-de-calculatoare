var webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/screen");
webSocket.binaryType = "arraybuffer";
webSocket.onopen = function() {
	setInterval('webSocket.send(document.body.clientWidth + "," + document.body.clientHeight)', 50);
}
webSocket.onmessage = function(event) {
	var bytes = new Uint8Array(event.data);
	var data = "";
	for (var i = 0; i < bytes.byteLength; ++i) {
		data += String.fromCharCode(bytes[i]);
	}
	document.getElementById("screen").src = "data:image/jpeg;base64," + window.btoa(data);
}