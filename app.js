var express = require("express");
var app = express();
var server = require("http").createServer(app);
var io = require("socket.io").listen(server);
var fs = require("fs");
server.listen(process.env.PORT || 6969);

var listTemperature = [];
var listHumidity = [];

//random
var id;
var id_room;
var lamp_1;
var lamp_2;
var lamp_3;
var lampOne;
var lampTwo;
var lampThree;
var humid;
var temp;

io.sockets.on('connection', function(socket){
    
    console.log('One user connected');

    socket.on('ANDROID_SEND_IP_ADDRESS', function(ipAddress){
        console.log('ip address : ' + ipAddress);
    });

    // socket.on('Arduino', function(dataEsp){
    //     console.log('esp connected');
    //     console.log('data from esp : ' + dataEsp);
    // });

    socket.on('Arduino', function(data) {
	    console.log("Module esp ket noi");
        var t = JSON.stringify(data);
	    var obj = JSON.parse(t);
        console.log(obj);
    });

    socket.on('ESP_SEND_REQUEST', function(dataEsp){
        console.log('esp connected');
        console.log('data from esp duc : ' + dataEsp);
    });

    socket.on('ANDROID_SEND_REQUEST', function(dataAndroid){
        console.log('has data : ' + dataAndroid);
        listTemperature.push(dataAndroid);
        listHumidity.push(dataAndroid);

        io.sockets.emit("SERVER_SEND_DATA", {
            temperature : listTemperature[listTemperature.length-1],
            humidity : listHumidity[listHumidity.length-1]
        });
    });

    socket.on('ANDROID_SEND_REQUEST_TEST', function(dataAndroid){
        var data = JSON.parse(dataAndroid);
        console.log('has data : ' + data);
        
        io.sockets.emit("SERVER_SEND_DATA_TEST", {
            result : 'success'
        });
    });

    socket.on('ANDROID_SEND_ADJUSTMENT', function(data){
        // var obj = JSON.stringify(data);
        console.log('has data: ' + data);

        id = Math.floor((Math.random()*1000) + 1);

        humid = Math.floor((Math.random()*100) + 1);

        temp = Math.floor((Math.random()*45) + 1);

        lampOne = Math.floor((Math.random())*10 + 1);

        lampTwo = Math.floor((Math.random())*10 + 1);

        lampThree = Math.floor((Math.random())*10 + 1);

        if(lampOne % 2 == 0){
            lamp_1 = true;
        }else lamp_1 = false;

        if (lampTwo % 2 == 0){
            lamp_2 = true;
        }else lamp_2 = false;
        if(lampThree % 2 == 0){
            lamp_3 = true;
        }else lamp_3 = false;

        if(id % 2 == 0){
            id_room = 1;
        }else id_room = 2;

        var response = {
                id_room : id_room,
                lamp_1 : lamp_1,
                lamp_2 : lamp_2,
                lamp_3 : lamp_3,
                humidity : humid,
                temperature : temp
        }

        console.log('wtf : ' + JSON.stringify(response));

        io.sockets.emit('SERVER_RESPONSE', response);
    });
});
