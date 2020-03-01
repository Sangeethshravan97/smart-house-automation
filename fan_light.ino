#define FIREBASE_HOST "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
#define FIREBASE_AUTH "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
#define WIFI_SSID "xxxx"
#define WIFI_PASSWORD "xxxxxx"

#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>


int led1=0;   //D3-FAN
int led2=13;  //D7-LED
int ac=5;     //D1-AC
int heater=2; //D4-HEATER

int seconds=0;
int seconds2=0;

String timee;
String timee2;
void setup() {
  // put your setup code here, to run once:

    Serial.begin(9600);
    pinMode(led1,OUTPUT); //fan
    pinMode(led2,OUTPUT); //led
    pinMode(ac,OUTPUT); //ac
   pinMode(heater,OUTPUT); //heater

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


}

void loop() {


 
if (Firebase.failed()) {                          //check for firebase error...
     Serial.print("FIREBASE FAILED...."); 
     Serial.println(Firebase.error());   
     return; 
     delay(1000);
}

//-----fan-----------------------------------
 
  String  fanStatus=Firebase.getString("FAN_STATUS:");
  if(fanStatus=="ON")
 {
      Serial.print("FAN ON\n");
      digitalWrite(led1,HIGH);  

                seconds++;
                int hours=seconds/3600;
                int minutes=(seconds%3600)/60;
                int secs=seconds%60;
                timee=int(hours)+String(":")+String(minutes)+String(":")+String(secs);
                Serial.println(timee);
                Firebase.setString("FAN_TIME",timee);
      
   
  }
  else if(fanStatus=="OFF")
  {
    Serial.print("FAN OFF\n");
      digitalWrite(led1,LOW);  
      seconds=0;
    String timee11=String("00:00:00");
      Serial.println(timee11);
      Firebase.setString("FAN_TIME",timee11);
   
  }else {
    Serial.println("Wrong Credential! Please send ON/OFF");
  }
 
//---------LED---------------------------------------
  String fireStatus=Firebase.getString("LED_STATUS");
 if (fireStatus=="ON") {
      Serial.print("LED ON\n");
      digitalWrite(led2,HIGH);  
                
                seconds2++;
                int hours2=seconds2/3600;
                int minutes2=(seconds2%3600)/60;
                int secs2=seconds2%60;
                timee2=int(hours2)+String(":")+String(minutes2)+String(":")+String(secs2);
                Serial.println(timee2);
                Firebase.setString("LED_TIME",timee2);
 
      
  }
  else if(fireStatus=="OFF")
  {
    Serial.print("LED OFF\n");
      digitalWrite(led2,LOW);  
      seconds2=0;
     String timee22=String("00:00:00");
      Serial.println(timee22);
      Firebase.setString("LED_TIME",timee22);

  }else {
    Serial.println("Wrong Credential! Please send ON/OFF");
  } 

//--------------------SMART AC--------------------------------
 String acStatus=Firebase.getString("AC_STATUS");
 if (acStatus=="ON") {
      Serial.print("AC ON\n");
      digitalWrite(ac,HIGH); 
 }else if(acStatus=="OFF")
 {
      Serial.print("AC OFF\n");
      digitalWrite(ac,LOW);  
     
 }else {
    Serial.println("Wrong Credential! Please send ON/OFF");
  } 
//-----------------------SMART HEATER------------------
 String heaterStatus=Firebase.getString("HEATER_STATUS");
 if (heaterStatus=="ON") {
      Serial.print("HEATER ON\n");
      digitalWrite(heater,HIGH); 
 }else if(heaterStatus=="OFF")
 {
      Serial.print("HEATER OFF\n");
      digitalWrite(heater,LOW);  
     
 }else {
    Serial.println("Wrong Credential! Please send ON/OFF");
  } 
  delay(250);
}
