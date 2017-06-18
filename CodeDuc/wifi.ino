//Code này dùng đề kết nối WIFI và đọc các module thời gian thực DS1307, module SDcard, module DHT11
//KHi khởi động thì ESP kiểm tra EEPROM xem đã lưu ID và Pass WIFI chưa nếu đã lưu thì kết nối
//Nếu chưa lưu hoặc không kết nối được thì ESP chuyển sang chế độ AP
//Ở chế độ AP thì ESP là server và điện thoại truy cập vào WIFI của ESP
//Điện thoại vào trình và đến địa chỉ 192.168.4.1 để gửi tên WIFI , Pass và địa chỉ host
//ESP nhận được và khởi động lại là OK
//Khi ESP dùng chế độ client thì mới hiện thị thời gian, và nhiệt độ, độ ẩm.
//ESP tạo ra các file để lưu dữ liệu nhiệt độ vào file mỗi ngày có 1 file cho tchawsc


//WIFI
#include<ESP8266WiFi.h>
#include<ESP8266WebServer.h>
#include"EEPROM.h"


//SocketIO
#include <SocketIOClient.h>
#include <ArduinoJson.h>

//Ngoai vi
#include <DHT.h>
#include<Wire.h>
#include<SPI.h>
#include <SD.h>


//SocketIO
const int port = 6969;
char host[20];


//WIFI
ESP8266WebServer server(80);
const char* ssidESP   =    "DoAn2";
const char* passESP   =    "12345678";
String st;
String content;
int statusCode;
int n = 1;


//Bien luu du lieu lay tu HTML
String hsid;
String hpass;
String hhost;


////Bien luu ten wifi mat khau
String ssid;
String pass;
String iphost;

// DHT11
const int DHTPIN = 5;//Lấy dữ liệu chân D1
const int DHTTYPE = DHT11;//Sử dụng DHT11
DHT dht (DHTPIN, DHTTYPE);


// DS1307
const byte DS1307 = 0x68; //Địa chỉ của DS1307
const byte NumberOfFields = 7; //Số byte dữ liệu sẽ được đọc từ DS1307
int second, minute, hour, day, weekday, month, year; //Các biến thời gian

//SD
File myFile;


float h,t;


unsigned long sendJsonMillis = 0;
unsigned long displayMillis = 0;

extern String RID;
extern String Rfull;

void setup()
{
  Serial.begin(115200);
  delay(10);
  dht.begin();
  Wire.begin(D5, D8);// Khai báo chân cho I2C chân 0(D3 -> D5) SDA, chân 2(D4 -> D8) SCL
  setTime(19, 6, 0, 2, 12, 6, 17); //Cài đặt thời gian 12:30:45 CN 08-02-2015 
  if(SD.begin(4))
  {
    openFirstFile(day, month, year);
  }

  delay(10);
  
  readIdPassEEPROM();
  
  if (WiFi.status() != WL_CONNECTED)
  {
    n = 1;
    Serial.println("Trang thai AP");
//    WiFi.mode(WIFI_STA);
//    WiFi.begin(ssid.c_str(), pass.c_str());
  }
  
  if (WiFi.status() == WL_CONNECTED)
  {
    n = 2;
    Serial.println("Trang thai client");
    WiFi.mode(WIFI_STA);
    WiFi.begin(ssid.c_str(), pass.c_str());

  }
  
}
       

void loop()
{
  readDHT();
  readDS1307();
  writeSD(h,t,hour,minute,second); // Ghi Nhiet do va do am vao the nho
  server.handleClient();

  if((millis()-displayMillis > 1000)&&(n==2))
  {
    displayMillis = millis();
    digitalClockDisplay();
    Serial.print("Nhiet do: ");
    Serial.println(t);
    Serial.print("Do am: ");
    Serial.println(h);  
  }
}




//Ket noi WiFi
void readIdPassEEPROM(void)  // Đọc dữ liệu từ eeprom để kết nối vào mạng wifi nhà
{

  EEPROM.begin(512); 
  delay(10);
  Serial.println("");
  Serial.println("Kiem tra EEPROM");
  String qsid;
  for(int i = 0; i < 32; i++)
  {
    qsid += char(EEPROM.read(i));
  }
  ssid = catChuoi(qsid); //Cat khoang trong sau chuoi
  Serial.print("SSID: ");
  Serial.println(ssid.c_str());

  String qpass;
  for (int i = 32; i < 96; i++)
  {
    qpass += char(EEPROM.read(i));
  }
  pass = catChuoi(qpass);

  String qhost;
  for (int i = 96; i < 115; i++)
  {
    qhost += char(EEPROM.read(i));
  }
  iphost = catChuoi(qhost);
  
  if(ssid.length() > 1)
  {
    Serial.println("Do dai ID");
    Serial.println(ssid.length());
    WiFi.begin(ssid.c_str(), pass.c_str());
      if (testWifi())
   {
     guiWeb(0);
     EEPROM.end();
     return;
   }
  }
      
  setupAP();
  EEPROM.end();
}


String catChuoi(String s1)
{
  String s2;
  String s3 = "";
  int len = s1.length();
  if(len <= 1) return s3;
  for (int i = 0; i< len; i++)
  {
    if (s1[i] == 124) return s2;
    s2 += s1[i];
    if (i == (len - 1)) return s3;
  }
}


bool testWifi(void)   // Xem có kết nối vào wifi được không.
{
  int c = 0;
  Serial.println("Xin vui long doi ket noi WiFi");
  while (c < 20)
  {
    if(WiFi.status() == WL_CONNECTED)
    {
      Serial.println("Da ket noi WiFi");
      return true;
    }
    delay(1000);
    c++;
  }
  Serial.println("Chua co ID va Pass WiFi, gui ID va Pass");
  return false;
}


void guiWeb(int webtype)
{
  Serial.print("WiFi ket noi ");
  Serial.print("Dia chi IP: ");
  Serial.println(WiFi.localIP());
  Serial.print("AP IP:");
  Serial.println(WiFi.softAPIP());
  createWebServer(webtype);

  server.begin();
  Serial.println("May chu bat dau");
}


void setupAP(void)
{
  WiFi.mode(WIFI_STA);
  WiFi.disconnect();
  delay(100);

  int n = WiFi.scanNetworks();
  Serial.println("Tim hoan tat");
  if(n==0)
  {
    Serial.println("Khong tim thay mang WiFi nao trong khu vuc nay");
  }
  else
  {
    Serial.print("So mang WiFi tim thay la: ");
    Serial.println(n);
    for (int i=0; i<n; ++i)
    {
      Serial.print(i + 1);
      Serial.print(": ");
      Serial.print(WiFi.SSID(i));
      Serial.print(" (");
      Serial.print(WiFi.RSSI(i));
      Serial.print(")");
      Serial.println((WiFi.encryptionType(i) == ENC_TYPE_NONE) ? " " : "*");
      delay(10);
    }
  }

  //In ra thu tu cac mang tren trang HTML
  st = "<ol>";
  for (int i=0; i< n; ++i)
  {
    st += "<li>";
    st += WiFi.SSID(i);
    st += " (";
    st += WiFi.RSSI(i);
    st += ")";
    st += (WiFi.encryptionType(i) == ENC_TYPE_NONE) ? " " : "*";
    st += "</li>";
  }
  st += "</ol>";
  delay(100);
  Serial.println("Mo AP");
  Serial.println(ssidESP);
  Serial.println(passESP);
  WiFi.softAP(ssidESP, passESP);

  guiWeb(1);
  Serial.println("Cho WiFI ket noi...");
}


void createWebServer(int webtype)
{
  
  if (webtype == 1)
  {
    server.on("/", [] ()
    {
      IPAddress ip = WiFi.softAPIP();
      String ipStr = String(ip[0]) + '.' + String(ip[1]) + '.' + String(ip[2]) + '.' + String(ip[3]);
      content = "<!DOCTYPE HTML>\r\n<html><h2>DoAn2</h2>";
      
      content += "<form method=\"get\" action=\"setting\">";
      
      content += "<div>Wifi</div>";
      content += "<div><input name=\"ssid\" size=\"40\"></div>";
      
      content += "<div>Mat Khau</div>";
      content += "<div><input name=\"pass\" size=\"40\"></div>";
      
      content += "<div>Dia chi host</div>";
      content += "<div><input name=\"host\" size=\"40\"></div>";
      
      content += "<div><input type='submit'></div>";
      
      content += "<p>";
      content += st;
      content += "</p>";
      content += "</html>";
      
      server.send(200, "text/html", content);
    });

    server.on("/setting", [] ()
    {
      hsid = server.arg("ssid");
      hpass = server.arg("pass");
      hhost = server.arg("host");
       

      
      if (hsid.length() > 0 && hpass.length()>0)
      {
        EEPROM.begin(512);
        for (int i = 0; i < 115; i++)
        {
          EEPROM.write(i, 0);
        }

        EEPROM.commit();
        
        Serial.print("Tai khoan WiFi nhan duoc: ");
        Serial.println(hsid);
        for(int i = 0; i < hsid.length(); i++)
        {
          EEPROM.write(i, hsid[i]);
        }
        EEPROM.write(hsid.length(),124);
        Serial.println("");
        
        Serial.print("Mat khau WiFi nhan duoc: ");
        Serial.println(hpass);
       for(int i = 0; i < hpass.length(); i++)
        {
          EEPROM.write(32 + i, hpass[i]);
        }
        EEPROM.write(hpass.length() + 32,124);
        Serial.println("");


        Serial.print("Dia chi Host cua Server: ");
        Serial.println(hhost);
        for(int i = 0; i <hhost.length(); i++)
        {
          EEPROM.write(96+i, hhost[i]);
        }
        EEPROM.write(hhost.length() + 96,124);
        Serial.println("");
        EEPROM.commit();
        EEPROM.end();


        content = "{\"Thanh Cong\":\"Luu vao he thong. Khoi dong lai ten wifi moi\"}";
        statusCode = 200;
      }
      
      else
      {

        content = "{\"Error\":\"404 not found\"}";
        statusCode = 404;
      }
      server.send(statusCode, "application/json", content);
    });
  }

  else if (webtype == 0)
  {
    return;
    server.on("/", []()
    {
      IPAddress ip = WiFi.localIP();
      String ipStr = String(ip[0]) + '.' + String(ip[1]) + '.' + String(ip[2]) + '.' + String(ip[3]);
      content = "<!DOCTYPE HTML>\r\n<html><h2>Nhap HOST SERVER</h2>";
      content += "<form method=\"get\"action=\"setting\">";
      content += "<div>Host</div>";
      content += "<div><input name=\"host\" size=\"40\"></div>";
      content += "<div><input type='submit'></div>";
      
      server.send(200, "text/html", content);
    });

    server.on("/setting", [] ()
    {
      EEPROM.begin(512);
      for (int i = 96; i < 115; i++)
      {
         EEPROM.write(i, 0);
      }
         
         
      hhost = server.arg("host");
      Serial.print("Dia chi Host nhan duoc la: ");
      Serial.println(hhost);
      for (int i = 96; i < hhost.length() + 96; i++)
      {
        EEPROM.write(96+i, hhost[i]);
      }

      EEPROM.commit();
      EEPROM.end();
    });
  }
}


//Ket noi SocketIO
void connectServer(void)
{
  iphost.toCharArray(host,20); 
  Serial.print("Conecting to ");
  Serial.print(host);
  Serial.print(":");
  Serial.println(port);
}


//DHT11
void readDHT()
{
  h = dht.readHumidity(); //Đọc độ ẩm
  t = dht.readTemperature(); //Đọc nhiệt độ
}





//DS1307
void readDS1307()
{
  Wire.beginTransmission(DS1307);
  Wire.write((byte)0x00);
  Wire.endTransmission();
  Wire.requestFrom(DS1307, NumberOfFields);
  second  = bcd2dec(Wire.read() & 0x7f);
  minute  = bcd2dec(Wire.read() );
  hour    = bcd2dec(Wire.read() & 0x3f);
  weekday = bcd2dec(Wire.read() );
  day     = bcd2dec(Wire.read() );
  month   = bcd2dec(Wire.read() );
  year    = bcd2dec(Wire.read() );
  year += 2000;
}
int bcd2dec(byte num)
{
  return ((num/16 * 10) + (num % 16));
}
int dec2bcd(byte num)
{
  return ((num/10 * 16) + (num % 10));
}
void setTime(byte hr, byte min, byte sec, byte wd, byte d, byte mth, byte yr)
{
  Wire.beginTransmission(DS1307);
  Wire.write(byte(0x00));
  Wire.write(dec2bcd(sec));
  Wire.write(dec2bcd(min));
  Wire.write(dec2bcd(hr));
  Wire.write(dec2bcd(wd));
  Wire.write(dec2bcd(d));
  Wire.write(dec2bcd(mth));
  Wire.write(dec2bcd(yr));
  Wire.endTransmission();
}
void digitalClockDisplay()
{
    Serial.print("Thoi gian: ");
    Serial.print(hour);
    printDigits(minute);
    printDigits(second);
    Serial.print("    Thu: ");
    Serial.print(weekday);
    Serial.print("    Ngay: ");
    Serial.print(day);
    Serial.print("-");
    Serial.print(month);
    Serial.print("-");
    Serial.println(year); 
}
void printDigits(int digits)
{
    // các thành phần thời gian được ngăn chách bằng dấu :
    Serial.print(":");
        
    if(digits < 10) Serial.print('0');
    Serial.print(digits);
}
//SDcard
void openSD(int d, int m, int y)
{
  myFile = SD.open(String(d,DEC) + "-" + String(m,DEC) + "-" + String(y,DEC) + ".txt",FILE_WRITE);
  myFile = SD.open(String(d,DEC) + "-" + String(m,DEC) + "-" + String(y,DEC) + ".txt");
}
void writeSD(float h, float t, int hr, int mi, int sec)
{
  if((hour==0)&&(minute==0)&&(second==0))
  {
    myFile.close();
    openSD(day,month,year);
  }
  while(sec==0)
  {
    myFile.println("Giờ: " + String(hr,DEC) + ":" + (mi,DEC) + ":" + (sec,DEC));
    myFile.println("Nhiệt độ: " + String(t,DEC));
    myFile.println("Độ ẩm: " + String(h,DEC));
  }
}


void openFirstFile(int d, int m, int y)
{
  myFile = SD.open(String(d,DEC) + "-" + String(m,DEC) + "-" + String(y,DEC) + ".txt");
  if(!myFile.available())
  {
    openSD(d,m,y);
  }
}

