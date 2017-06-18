//Code này truy cập vào mạng WIFI sau đó truy cập vào địa chỉ IP server nodejs trên máy tính với vai trò là client
//Đọc nhiệt độ vào độ ẩm tử từ DHT liên tục và in ra Serial với chu kỳ 1000ms
//Sau mỗi chu kỳ 5s thì gừi 1 jon lên server với các hàm trong đó là ID của phòng, trạng thái 3 đèn led và nhiệt độ và độ ẩm hiện tại
//Lắng nghe từ Server gửi về 1 json với các tham số ID của phòng, điều chỉnh 3 đèn led và nhiệt độ và độ ẩm max
//Nếu nhiệt độ hiện tại lớn hơn nhiệt độ max gửi về từ android thì sé sáng đèn cảnh bảo nhiệt độ và ngược lại
//Tương tư với độ ẩm



#include <ESP8266WiFi.h>
#include <SocketIOClient.h>
#include <ArduinoJson.h>
#include <DHT.h>

     
SocketIOClient client;
const char* ssid = "12345678";          //Tên mạng Wifi mà Socket server đang kết nối
const char* password = "123456789";  //Pass mạng wifi 
 
char host[] = "192.168.43.66";  //Địa chỉ IP dịch vụ, hãy thay đổi nó theo địa chỉ IP Socket server của bạn.
int port = 6969;                  //Cổng dịch vụ socket server do chúng ta tạo!
 

// RID: Tên hàm (tên sự kiện)
// Rfull: Danh sách biến (được đóng gói lại là chuối JSON)
extern String RID;
extern String Rfull;

//// DHT11
const int DHTPIN = 5;//Lấy dữ liệu chân D1
const int DHTTYPE = DHT11;//Sử dụng DHT11
DHT dht (DHTPIN, DHTTYPE);
    
     
//Bien cac tham so Socket
float h,t,t_max,h_max;
int id_room = 1;
int id1_room;
const int led1  = 16; //D0
const int led2  = 4;  //D2
const int led3  = 0;  //D3
const int led_t = 2;  //D4
const int led_h = 14;  //D5

boolean led1Status,led2Status ,led3Status;

//Một số biến dùng cho việc tạo một task
unsigned long displayMillis = 0;
unsigned long sendJsonMillis = 0;
    
void setup()
{
    //Bật baudrate ở mức 115200 để giao tiếp với máy tính qua Serial
    Serial.begin(115200);
    dht.begin();  //Khởi động DHT
    delay(10);
    
    pinMode(led1, OUTPUT);
    pinMode(led2, OUTPUT);
    pinMode(led3, OUTPUT);
    pinMode(led_t,OUTPUT);
    pinMode(led_h,OUTPUT);

 
    //Việc đầu tiên cần làm là kết nối vào mạng Wifi
    Serial.print("Ket noi vao mang ");
    Serial.println(ssid); 

    //Kết nối vào mạng Wifi
    WiFi.begin(ssid, password);
 
    //Chờ đến khi đã được kết nối
    while (WiFi.status() != WL_CONNECTED) { //Thoát ra khỏi vòng 
        delay(500);
        Serial.print('.');
    }
 
    Serial.println();
    Serial.println(F("Da ket noi WiFi"));
    Serial.println(F("Di chi IP cua ESP8266 (Socket Client ESP8266): "));
    Serial.println(WiFi.localIP());
 
    if (!client.connect(host, port)) {
        Serial.println(F("Ket noi den socket server that bai!"));
        return;
    }
 
    //Khi đã kết nối thành công
    if (client.connected()) {
        //Thì gửi sự kiện ("connection") đến Socket server ahihi.
        client.send("connection", "message", "Connected !!!!");
    }

}

void loop()
{
  readDHT();
  if(millis()-displayMillis > 1000)
    {
      displayMillis = millis();
      Serial.print("Nhiet do: ");
      Serial.println(t);
      Serial.print("Do am: ");
      Serial.println(h);  
    }
  delay(10);

  if ((millis() - sendJsonMillis > 5000)) 
  {
      sendJsonMillis = millis();
  
     if(client.connected())
    {
      StaticJsonBuffer<200> jsonSend;
      JsonObject& objJson = jsonSend.createObject();
      objJson["id_room"] = (int) id_room;
      objJson["lamp_1"]  = (boolean)led1Status;
      objJson["lamp_2"]  = (boolean)led2Status;
      objJson["lamp_3"]  = (boolean)led3Status;
      objJson["humidity"]   = (float)h;
      objJson["temperature"]    = (float)t;
      
      
      String dataSend;
      objJson.printTo(dataSend);
      client.sendJSON("Arduino", dataSend); 
      Serial.println(dataSend);
      delay(10);
    }
  }
  
  if (client.monitor())
  {
    Serial.print("Da nhan goi Json voi ten: ");
      Serial.println(RID);
      Serial.println(Rfull);
      StaticJsonBuffer<200> jsonReceive;
      JsonObject& root = jsonReceive.parseObject(Rfull);
      id1_room    = root["id_room"];
      led1Status = root["lamp_1"];
      led2Status = root["lamp_2"];
      led3Status = root["lamp_3"];
      t_max = root["max_temperature"];
      h_max = root["max_humidity"];     
      digitalWrite(led1, led1Status);
      digitalWrite(led2, led2Status);
//      Serial.println(led1Status);
//      Serial.println(led2Status);
//      Serial.println(led3Status);
      digitalWrite(led3, led3Status);
//      Serial.println(t_max);
//      Serial.println(h_max);
//      Serial.println(Rfull);
      if(t > t_max) digitalWrite(led_t, HIGH);
        
      if(t <= t_max) digitalWrite(led_t, LOW);
    
      if(h > h_max) digitalWrite(led_h, HIGH);
    
      if(h <= h_max) digitalWrite(led_h, LOW);
      delay(10);  
  }

  if (!client.connected())
  {
    client.reconnect(host, port);
  }
}
//DHT11
void readDHT()
{
  h = dht.readHumidity(); //Đọc độ ẩm
  t = dht.readTemperature(); //Đọc nhiệt độ
}
