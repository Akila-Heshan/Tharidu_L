#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <WiFiClient.h>
#include "DHT.h"
#include <Arduino_JSON.h>
#include <SPI.h>


#define DHTPIN D1
#define DHTTYPE DHT11
DHT dht(DHTPIN, DHTTYPE);


int temp = 34.9;
int hum = 76.3;

unsigned long lastTime = 0;
unsigned long last2Time = 1000;

void setup() {
  pinMode(D2, OUTPUT);
  pinMode(D3, OUTPUT);
  digitalWrite(D2, LOW);
  digitalWrite(D3, LOW);
  Serial.begin(115200);

  Serial.println("Conecting");
  WiFi.begin("Home WiFi", "25858ABcd12#@25858");


  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.println("connecting...");
  }
  Serial.println("Connected");
  dht.begin();
}

void loop() {

  float humidity = dht.readHumidity();
  float temperature = dht.readTemperature();
  if (isnan(humidity) || isnan(temperature)) {
    Serial.println("Failed to read from DHT sensor!");
    digitalWrite(D8, HIGH);
    return;
  }

    if ((millis() - lastTime) >= 1000) {
      Serial.println();
      HTTPClient request = HTTPClient();
      WiFiClient client = WiFiClient();
      String light = "";
      String fan = "";
      if (digitalRead(D2) == 1) {
        light = "OFF";
      } else {
        light = "ON";
      }
      if (digitalRead(D3) == 0) {
        fan = "OFF";
      } else {
        fan = "ON";
      }
      request.begin(client, "http://192.168.8.123:8080/Tharidu_IOT/SaveData?temperature=" + String((int)temperature) + "&humidity=" + String((int)humidity) + "&light=" + light + "&fan=" + fan);
      Serial.println(temperature);
      int status = request.GET();

      if (status == HTTP_CODE_OK) {
        String text = request.getString();
        JSONVar json = JSON.parse(text);
        
        if (String(json["light"]) == "ON") {
          digitalWrite(D2, LOW);
        } else if (String(json["light"]) == "OFF") {
          digitalWrite(D2, HIGH);
        }

        if (String(json["fan"]) == "AUTO") {
          if (temperature > 30) {
            digitalWrite(D3, HIGH);
          } else {
            digitalWrite(D3, LOW);
          }
        } else if (String(json["fan"]) == "ON") {
          digitalWrite(D3, HIGH);
        } else if (String(json["fan"]) == "OFF") {
          digitalWrite(D3, LOW);
        }
      } else {
        Serial.println("Error");
      }
      request.end();
      lastTime = millis();
    
  }
}