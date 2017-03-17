Object.size = function(obj) {
    var size = 0, key;
    for (key in obj) {
        if (obj.hasOwnProperty(key)) size++;
    }
    return size;
};
var ip = '192.168.1.3';
var port1      = 8888;
console.log('started');
var port2 = 8888;
var WebSocketServer = require('ws').Server
var uuid = require('node-uuid');
var wss = new WebSocketServer({ path:'http://192.168.1.3:8888/Human-Web-/gencode',port: port1,server:ip });


var clients = {};
var dumCounter=0;
wss.on('connection', function connection(ws) {

  ws.on('message', function incoming(message) {
    console.log('received: %s', message);
  	var obj = JSON.parse(message);
  	if(obj.op == 'hello')
  	{
	  	var uuidToken = uuid.v1();
  		clients[uuidToken] = ws;
	  	var hello = { op:'hello',token:uuidToken};
		ws.send(JSON.stringify(hello),{mask:false});
  	}

  });

});


var http = require("http");

http.createServer(function(request, response) {

	response.writeHead(200, {"Content-Type": "text/plain","Access-Control-Allow-Origin":"*"});
	process.on('uncaughtException', function(err) {
			 response.end("Exception");
	});
	if(request.method == "POST")
	{
		var url = request.url;
		if(url == "/auth")
		{

		var body = '';
		request.on('data', function(chunk)
		{
			      body += chunk.toString();
		});

	    request.on('end', function () {
		var params = JSON.parse(body);
		console.log("Recived Params: "+JSON.stringify(params));
		 	var uuId = params.uuid;
		 	var accessToken = params.access_token;

		 	var msg = {'op':'authdone','accessToken':accessToken};
		 	if(clients[uuId] != undefined || clients[uuId] != null)
		 	{
		 		console.log("Before "+Object.size(clients));
			 	clients[uuId].send(JSON.stringify(msg),{mask:false});
			 	delete clients[uuId];
			 	console.log("After "+Object.size(clients));

			 	response.end('{"status":"OK"}');

		 	}
		 	else
		 	{
		 		response.end('{"status":"NOK"}');
		 	}

		 	});
		}
		else
		{
			  response.end('{"status":"NOK"}');
		}
	}
	else
	{
		 response.end("NOT Supported");
	}

}).listen(port2,ip);
