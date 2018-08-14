package cubex.mahesh.sqlite_aug6am

import android.content.ContentValues
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
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
             // columns - we can all columns data
            // select *from employee where id=? and dept=?
      /* var cursor = dBase.query("employee",null,
                    "id=? and dept=?",
               arrayOf(et1.text.toString(),et4.text.toString()),
                    null,null,null)*/
      /*      var cursor = dBase.query("employee",null,
                    null,
                    null,
                    "name","id>125",null) */
            var cursor = dBase.query("employee",null,
                    null,
                    null,
                    null,null,"id desc")
/*Context context,int layout, Cursor c,String[] from,int[] to,int flags */
 /*           var from = arrayOf("id","name","desig","dept")
            var to = intArrayOf(R.id.tv1,R.id.tv2,R.id.tv3,R.id.tv4)

       var cAdapter = SimpleCursorAdapter(
      this@MainActivity,R.layout.indiview,cursor,from,to,
               0)
            lview.adapter = cAdapter */

            var list = arrayListOf<String>()
            while (cursor.moveToNext())
            {
                list.add(cursor.getInt(1).toString()+"\t"+cursor.getString(2)+"\n"+
                cursor.getString(3)+"\t"+cursor.getString(4))
            }
            var myadapter = ArrayAdapter<String>(this@MainActivity,
                    android.R.layout.simple_list_item_single_choice,list)
            lview.adapter = myadapter
        }

        // update
        update.setOnClickListener {
          // update set name=value,desig=value  table_name where  id=?
          // String table, ContentValues values, String whereClause, String[] whereArgs
            var cv = ContentValues( )
            cv.put("name",et2.text.toString())
            cv.put("desig",et3.text.toString())

           var count = dBase.update("employee",cv,"id=?",
                    arrayOf(et1.text.toString()))
            if(count>0){
                Toast.makeText(this@MainActivity,
                        "Record Updated Successfully", Toast.LENGTH_LONG).show()
                et1.setText("") ; et2.setText(""); et3.setText(""); et4.setText("")

            }else{
                Toast.makeText(this@MainActivity,
                        "Failed to Update Record", Toast.LENGTH_LONG).show()
            }
        }

        delete.setOnClickListener {
            // delete from table_name where id=?
            // String table, String whereClause, String[] whereArgs
        var count =    dBase.delete("employee","id=?",
                    arrayOf(et1.text.toString()))
            if(count>0){
                Toast.makeText(this@MainActivity,
                        "Record Deleted Successfully", Toast.LENGTH_LONG).show()
                et1.setText("") ; et2.setText(""); et3.setText(""); et4.setText("")

            }else{
                Toast.makeText(this@MainActivity,
                        "Failed to Delete Record", Toast.LENGTH_LONG).show()
            }

        }

    }
}
