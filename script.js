var express = require('express')
var http = require('http')
var net = require('net');
var child = require('child_process');
 
var app = express();
var httpServer = http.createServer(app);
 
app.get('/', function(req, res) {
  var date = new Date();
 
  res.writeHead(200, {
    'Date':date.toUTCString(),
    'Connection':'close',
    'Cache-Control':'private',
    'Content-Type':'video/webm',
    'Server':'CustomStreamer/0.0.1',
  });
 
  var tcpServer = net.createServer(function (socket) {
    socket.on('data', function (data) {
      res.write(data);
    });
    socket.on('close', function(had_error) {
      res.end();
    });
  });
 
  tcpServer.maxConnections = 1;
  
  tcpServer.listen(function() {
    var gstMuxer = child.spawn("/home/pi/gst-server-raspicam.sh.BACKUP");
 
    gstMuxer.stderr.on('data', onSpawnError);
    gstMuxer.on('exit', onSpawnExit);
 
    res.connection.on('close', function() {
      gstMuxer.kill();
    });
  });
});
 
httpServer.listen(9001);
 
function onSpawnError(data) {
  console.log(data.toString());
}
 
function onSpawnExit(code) {
  if (code != null) {
    console.error('GStreamer error, exit code ' + code);
  }
}
 
process.on('uncaughtException', function(err) {
  console.debug(err);
});
