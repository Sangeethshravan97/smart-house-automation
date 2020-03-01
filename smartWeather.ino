#include "DHT.h"

#include <NTPClient.h>
#include <WiFiUdp.h>
#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>
#define DHTPIN 2
#define DHTTYPE DHT11
DHT dht(DHTPIN, DHTTYPE);


#define FIREBASE_HOST "iotc-994b6.firebaseio.com"
#define FIREBASE_AUTH "3deKcbWp2v5e07GGcoYyKPdlWobUQtADpASSFzUx"
#define WIFI_SSID "Hawkeye"
#define WIFI_PASSWORD "Human1936"



const long utcOffsetInSeconds = 9000;


WiFiUDP ntpUDP;
NTPClient timeClient(ntpUDP, "pool.ntp.org", utcOffsetInSeconds);


const int ldrPin = A0;

//long duration;
//int distance;

 String fireStatus;
 String fanStatus;
  
void setup() {
  Serial.begin(9600);
  pinMode(ldrPin, INPUT); 
dht.begin();
  // connect to wifi.

  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("connecting");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }
  Serial.println();
  Serial.print("connected: ");
  Serial.println(WiFi.localIP());
  
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
timeClient.begin();

}


void loop() {
timeClient.update();

 delay(2000);

  float h = dht.readHumidity();
  float t = dht.readTemperature();


 if (isnan(h) || isnan(t)) {
    Serial.println(F("Failed to read from DHT sensor!"));
    return;
  }



  Serial.print(F("Humidity: "));
  Serial.print(h);
  Serial.print(F("%  Temperature: "));
  Serial.print(t);
  Serial.print(F("°C "));


   String Temp = String(t) + String("c");
   Firebase.setString("TEMPERATURE",Temp);


    String Humd = String(h) + String("%");
    Firebase.setString("HUMIDITY",Humd);
//------ smart AC

  String  smartac=Firebase.getString("SMART_AC");
  if(smartac=="ON")
   { 
     Serial.println("SMART AC ACTIVE");
     //--------------
     if(t>30)
   
      {
        String txt22=String("ON");
      Serial.println("  AC ON\n");
     // digitalWrite(AC,HIGH);  
      Firebase.setString("AC_STATUS",txt22);
      }
      else
      {
        
        String txt2=String("OFF");
        Serial.print("AC OFF\n");
      //  digitalWrite(AC,HIGH); 
       Firebase.setString("AC_STATUS",txt2);
        }
        //-------------
   }
   else
   {
    
     Serial.println("SMART AC NOT ACTIVE");
    }

//-------Smart Heater
  String  smartheat=Firebase.getString("SMART_HEATER");
  if(smartheat=="ON")
   {
     Serial.println("SMART HEATER ACTIVE");
     //-------------------------------------
      if(t<15)
      {
        String txtt=String("ON");
      Serial.println (" HEATER ON\n");
     // digitalWrite(AC,HIGH);  
     Firebase.setString("HEATER_STATUS",txtt);
      }
      else
      {
        String txtt1=String("OFF");
        Serial.print("HEATER OFF\n");
      //  digitalWrite(AC,LOW); 
        Firebase.setString("HEATER_STATUS",txtt1);
        }
        //----------------------------------
   }
   else
   {
    Serial.println("SMART HEATER NOT ACTIVE");
    }
  
//------LDR
  int ldrStatus = analogRead(ldrPin);
  Serial.print("luminous:"); 
  Serial.print(ldrStatus);
  String light = String(ldrStatus) + String("cd");    //convert integer humidity to string humidity 
  Firebase.setString("LUMINOUS",light);
   
//------------------smartlight-----------

 String  smartlight=Firebase.getString("SMART_LIGHT");

 if(smartlight=="ON")
 {
  if(ldrStatus>700)
  {
    String txt=String("ON");
    Serial.println("LIGHTS ON");
    Firebase.setString("LED_STATUS",txt);
  }
  else
  {
    String txt1=String("OFF");
    Serial.println("LIGHTS OFF");
    Firebase.setString("LED_STATUS",txt1);
    }
 }
 else
 {
  
 Serial.println("SMART LIGHTS NOT ACTIVE");
  }
  
 }

 
 
 
  
  
