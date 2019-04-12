import ketai.data.*;
import ketai.sensors.*;

KetaiSQLite db;
KetaiSensor sensor;
String CREATE_DB_SQL = "CREATE TABLE data ( _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, age INTEGER NOT NULL DEFAULT '0');";
PVector magneticField, accelerometer;
float light, proximity;

void setup()
{
  db = new KetaiSQLite(this);  // open database file
  sensor = new KetaiSensor(this);
  sensor.start();
  sensor.list();
  accelerometer = new PVector();
  magneticField = new PVector();
  orientation(LANDSCAPE); 
  textAlign(CENTER, CENTER);
  textSize(72);
  if ( db.connect() ){
    // for initial app launch there are no tables so we make one
    if (!db.tableExists("data"))
        db.execute(CREATE_DB_SQL);
  }
}

void draw() {
  background(78, 93, 75);
  text("Accelerometer :" + "\n"
    + "x: " + nfp(accelerometer.x, 1, 2) + "\n"
    + "y: " + nfp(accelerometer.y, 1, 2) + "\n"
    + "z: " + nfp(accelerometer.z, 1, 2) + "\n"
    + "MagneticField :" + "\n"
    + "x: " + nfp(magneticField.x, 1, 2) + "\n"
    + "y: " + nfp(magneticField.y, 1, 2) + "\n"
    + "z: " + nfp(magneticField.z, 1, 2) + "\n"
    + "Light Sensor : " + light + "\n"
    + "Proximity Sensor : " + proximity + "\n"
    , 20, 0, width, height);
}

void onAccelerometerEvent(float x, float y, float z, long time, int accuracy) {
  accelerometer.set(x, y, z);
}
void onMagneticFieldEvent(float x, float y, float z, long time, int accuracy) { // 2
  magneticField.set(x, y, z);
}

void onLightEvent(float v) { // 3
  light = v;
}

void onProximityEvent(float v) { // 4
  proximity = v;
}
public void mousePressed() { // 5
  if (sensor.isStarted())
    sensor.stop();
  else
    sensor.start();
  println("KetaiSensor isStarted: " + sensor.isStarted());
}
