import ketai.data.*;
import ketai.sensors.*;

KetaiSQLite db;
KetaiSensor sensor;
String CREATE_DB_SQL = "CREATE TABLE data ( _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, age INTEGER NOT NULL DEFAULT '0');";
PVector magneticField, accelerometer;
float light, proximity, rotation_x, rotation_y, rotation_z;
boolean capturando = false;

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
  if(capturando){
    text("Accelerometer :" + "\n"
    + "x: " + nfp(accelerometer.x, 1, 2) + "\n"
    + "y: " + nfp(accelerometer.y, 1, 2) + "\n"
    + "z: " + nfp(accelerometer.z, 1, 2) + "\n"
    + "MagneticField :" + "\n"
    + "x: " + nfp(magneticField.x, 1, 2) + "\n"
    + "y: " + nfp(magneticField.y, 1, 2) + "\n"
    + "z: " + nfp(magneticField.z, 1, 2) + "\n"
    +"Gyroscope: \n"  
    + "x: " + nfp(rotation_x, 1, 3) + "\n"
    + "y: " + nfp(rotation_y, 1, 3) + "\n"
    + "z: " + nfp(rotation_z, 1, 3) + "\n"
    + "Light Sensor : " + light + "\n"
    + "Proximity Sensor : " + proximity + "\n"
    , 20, 0, width/2, height/2);
    println("Capturando datos");
  }else{
    println("No se ha capturado");
    text("No se han capturado datos \n\n Presiona la pantalla \n Para capturar", width/2, height/2);
  }
}

void onAccelerometerEvent(float x, float y, float z, long time, int accuracy) {
  if (db.connect() && capturando) {
    accelerometer.set(x, y, z);
    if (!db.execute(
      "INSERT into data (`time`, `sen`,`x`,`y`,`z`) VALUES ('" + 
      System.currentTimeMillis() +  "', '" + 1 + "', '" + x + "', '" + y + "', '" + z + "')"
      )
    )
      println("Failed to record data!" ); 
  }  
}

void onMagneticFieldEvent(float x, float y, float z, long time, int accuracy) {
  if (db.connect() && capturando) {
    magneticField.set(x, y, z);
    if (!db.execute(
      "INSERT into data (`time`, `sen`,`x`,`y`,`z`) VALUES ('" + 
      System.currentTimeMillis() +  "', '" + 2 + "', '" + x + "', '" + y + "', '" + z + "')"
      )
    )
      println("Failed to record data!" ); 
  } 
  
}

void onGyroscopeEvent(float x, float y, float z) {
  //rotation.set(x, y, z);
  if (db.connect() && capturando) {
    rotation_x = x;
    rotation_y = y;
    rotation_z = z;
    if (!db.execute(
      "INSERT into data (`time`, `sen`,`x`,`y`,`z`) VALUES ('" + 
      System.currentTimeMillis() +  "', '" + 3 + "', '" + x + "', '" + y + "', '" + z + "')"
      )
    )
      println("Failed to record data!" ); 
  } 
  
}

void onLightEvent(float v) {
  light = v;
}

void onProximityEvent(float v) {
  proximity = v;
}

public void mousePressed() {
  if (sensor.isStarted())
    sensor.stop();
  else
    sensor.start();
  println("KetaiSensor isStarted: " + sensor.isStarted());
  
  if(capturando){
    capturando = false;
  }else{
    capturando = true;
  }
}
