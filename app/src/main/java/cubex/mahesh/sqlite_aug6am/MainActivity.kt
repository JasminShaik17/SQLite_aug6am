package cubex.mahesh.sqlite_aug6am

import android.content.ContentValues
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var dBase = openOrCreateDatabase("and_aug6am",
                Context.MODE_PRIVATE, null)

        dBase.execSQL("create table if not exists employee(_id integer primary key autoincrement,id integer,name varchar(100),desig varchar(100),dept varchar(100)) ")

        insert.setOnClickListener {
  // String table, String nullColumnHack, ContentValues values -> long
            var values = ContentValues( )
            values.put("id",et1.text.toString().toInt())
            values.put("name",et2.text.toString())
            values.put("desig",et3.text.toString())
            values.put("dept",et4.text.toString())

     var status =  dBase.insert("employee",null,
             values)
     if(status==-1.toLong()){
         Toast.makeText(this@MainActivity,
                 "Failed to Insert Record", Toast.LENGTH_LONG).show()
     }else{
         Toast.makeText(this@MainActivity,
    "Record Inserted Successfully", Toast.LENGTH_LONG).show()
         et1.setText("") ; et2.setText(""); et3.setText(""); et4.setText("")
     }
        } // insert

        read.setOnClickListener {
 /* Cursor query(String table, String[] columns, String selection,
        String[] selectionArgs, String groupBy, String having,
            String orderBy)           */
       var cursor = dBase.query("employee",null,
                    "id=? and dept=?",
               arrayOf(et1.text.toString(),et4.text.toString()),
                    null,null,null)
/*Context context,int layout, Cursor c,String[] from,int[] to,int flags */
            var from = arrayOf("id","name","desig","dept")
            var to = intArrayOf(R.id.tv1,R.id.tv2,R.id.tv3,R.id.tv4)

       var cAdapter = SimpleCursorAdapter(
      this@MainActivity,R.layout.indiview,cursor,from,to,
               0)
            lview.adapter = cAdapter

        }


    }
}
