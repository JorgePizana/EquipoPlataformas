import ketai.data.*;
import ketai.sensors.*;

KetaiSQLite db;
String CREATE_DB_SQL = "CREATE TABLE data ( _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, age INTEGER NOT NULL DEFAULT '0');";

void setup()
{
  
  db = new KetaiSQLite( this);  // open database file

  if ( db.connect() ){
    // for initial app launch there are no tables so we make one
    if (!db.tableExists("data"))
        db.execute(CREATE_DB_SQL);
  }
}
