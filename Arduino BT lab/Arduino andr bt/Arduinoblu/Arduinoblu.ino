//Original code by
//https://github.com/manashmndl/ArduinoAndroidKetaiExample/blob/master/Arduino/ketai/ketai.ino

#include <SoftwareSerial.h>
#define BAUD 9600
#define RX 0
#define TX 1

SoftwareSerial bt(RX, TX);

bool hasReceived = false;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(BAUD);
  bt.begin(BAUD);
  pinMode(LED_BUILTIN, OUTPUT);
}

void loop() {
  char x = '\0';
  // put your main code here, to run repeatedly:
  if (bt.available() > 0){
    x = bt.read();
    hasReceived = true;
  }

  if (hasReceived){
    Serial.println(x);
    digitalWrite(LED_BUILTIN, HIGH);
    hasReceived = false;
    delay(500);
    digitalWrite(LED_BUILTIN, LOW);
  }
}
