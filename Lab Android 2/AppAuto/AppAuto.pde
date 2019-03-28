import ketai.sensors.*;
KetaiSensor sensor;
PVector magneticField, accelerometer;
float light, proximity;
KetaiLocation location; // Para las actualizaciones de la localización
double longitude, latitude, altitude;
float accuracy;

KetaiSensor sensor;
float accelerometerX, accelerometerY, accelerometerZ;

void setup(){
  sensor = new KetaiSensor(this);
  sensor.start();
  sensor.list();
  accelerometer = new PVector();
  magneticField = new PVector();
  location = new KetaiLocation(this);  
  orientation(PORTRAIT);
  textAlign(CENTER, CENTER);
  textSize(60);
}

void draw(){
  background(78, 93, 75);
  if(location.getProvider() == "none"){      // Checar si hay un proveedor de la localización
    text("Location data is unavailable. \n" +
          "Please check your location settings. \n \n" +
          "Accelerometer: \n" +                        
          "x: " + nfp(accelerometer.x, 2, 3) + "\n" +
          "y: " + nfp(accelerometer.y, 2, 3) + "\n" +
          "z: " + nfp(accelerometer.z, 2, 3) + "\n" +
          "x: " + nfp(magneticField.x, 1, 2) + "\n" +
          "y: " + nfp(magneticField.y, 1, 2) + "\n" +
          "z: " + nfp(magneticField.z, 1, 2) + "\n" +
          "Light Sensor : " + light + "\n" +
          "Proximity Sensor : " + proximity + "\n", width/2, height/2);
  }else{
    text("Latitude: " + latitude + "\n" + 
          "Longitude: " + longitude + "\n" +
          "Altitude: " + altitude + "\n" +
          "Accuracy: " + accuracy + "\n" +
          "Provider: " + location.getProvider() + "\n \n" +
          "Accelerometer: \n" +                        
          "x: " + nfp(accelerometer.x, 2, 3) + "\n" +
          "y: " + nfp(accelerometer.y, 2, 3) + "\n" +
          "z: " + nfp(accelerometer.z, 2, 3) + "\n" +
          "x: " + nfp(magneticField.x, 1, 2) + "\n" +
          "y: " + nfp(magneticField.y, 1, 2) + "\n" +
          "z: " + nfp(magneticField.z, 1, 2) + "\n" +
          "Light Sensor : " + light + "\n" +
          "Proximity Sensor : " + proximity + "\n", width/2, height/2);
  }

}

/* para inciso a)
Actividad en el sensor de aceleración nos indica que el auto puede haber 
sido alcanzado por otro carro estando estacionado, o que podrían 
estarse robando las copas del auto  void onAccelerometerEvent

y para inciso b)
Actividad en el sensor de aceleración y 
en el GPS indica que se están robando el auto
void onAccelerometerEvent y void onLocationEvent

Para inciso c)
Actividad en el sensor de proximidad 
indica que alguien está asomandose por las ventanas del auto
para inciso d) 
 Actividad en el sensor touch indica que alguien esta 
 intentando abrir/romper los cristales del auto
para inciso e)
Actividad en el sensor touch y en el sensor
de luz indica que alguien entro al auto
*/

void onAccelerometerEvent(float x, float y, float z, long time, int accuracy)
{
accelerometer.set(x, y, z);
}

void onMagneticFieldEvent(float x, float y, float z, long time, int accuracy){
  magneticField.set(x, y, z);
}

void onLightEvent(float v){   //Intensidad de la luz
  light = v;
}

void onProximityEvent(float v){  // la distancia entre la pantalla del dispositivo y un objeto (oreja, mano, etc.)
  proximity = v;
}
void mousePressed(){
  println("Alguien entro al auto");
}

/*Cuando una actualización de la localización ocurra, este metodo recupera los datos
  de la localización y los imprime en consola */
void onLocationEvent(double _latitude, double _longitude, double _altitude, float _accuracy) {
  longitude = _longitude;
  latitude = _latitude;
  altitude = _altitude;
  accuracy = _accuracy;
  println("lat/lon/alt/acc: " + latitude + "/" + longitude + "/"
          + altitude + "/" + accuracy);
}
