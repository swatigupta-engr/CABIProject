package com.app.cabi

import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.app.cabi.model.Model
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException


class MainActivity : AppCompatActivity() {
    lateinit var progress: ProgressBar
    lateinit var listView_details: ListView
    lateinit var obj_adapter: CustomAdapter

    var arrayList_details: ArrayList<Model> = ArrayList();

    //OkHttpClient creates connection pool between client and server
    val client = OkHttpClient()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //   progress = findViewById(R.id.progressBar)
        //     progress.visibility = View.VISIBLE
        listView_details = findViewById(R.id.listView) as ListView

        // run("http://10.0.0.7:8080/jsondata/index.html")
        Log.v("data", Utilities.getRunVersion(applicationContext))

        val version = Utilities.getRunVersion(applicationContext)

        if (version.equals("1")) {
            checkConnectivity()
        }
        if (version.equals("1"))
            run("https://datausa.io/api/data?drilldowns=Nation&measures=Population")
        else {
            Log.v("data", Utilities.getRunVersion(applicationContext) + "_____" + Utilities.getListOfData(applicationContext).toString())
            //stuff that updates ui
            obj_adapter = CustomAdapter(applicationContext, Utilities.getListOfData(applicationContext))
            listView_details.adapter = obj_adapter
        }


    }


    private fun checkConnectivity() {
        val manager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = manager.activeNetworkInfo

        val isConnected = activeNetwork != null &&
                activeNetwork.isConnected
        if (!isConnected) {
            Log.v("inside", "yy")

            val dialogBuilder = AlertDialog.Builder(this)
            val intent = Intent(this, MainActivity::class.java)
            // set message of alert dialog
            dialogBuilder.setMessage("Make sure that WI-FI or mobile data is turned on, then try again")
                    // if the dialog is cancelable
                    .setCancelable(false)
                    // positive button text and action
                    .setPositiveButton("Retry", DialogInterface.OnClickListener { dialog, id ->
                        recreate()
                    })
                    // negative button text and action
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                        finish()
                    })

            // create dialog box
            val alert = dialogBuilder.create()
            // set title for alert dialog box
            alert.setTitle("No Internet Connection")
            alert.setIcon(R.mipmap.ic_launcher)
            // show alert dialog
            alert.show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val searchItem: MenuItem? = menu?.findItem(R.id.action_search)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: SearchView? = searchItem?.actionView as SearchView

        searchView?.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String?): Boolean {
                if (TextUtils.isEmpty(s)) {
                    obj_adapter.filter("");
                    listView_details.clearTextFilter();
                } else {
                    if (s != null) {
                        obj_adapter.filter(s)
                    };
                }
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }


    fun run(url: String) {
//        progress.visibility = View.VISIBLE
        val request = Request.Builder()
                .url(url)
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                //progress.visibility = View.GONE
            }

            override fun onResponse(call: Call, response: Response) {
                var str_response = response.body()!!.string()

                //creating json object
                val json_contact: JSONObject = JSONObject(str_response)
                //creating json array
                var jsonarray_info: JSONArray = json_contact.getJSONArray("data")
                var i: Int = 0
                var size: Int = jsonarray_info.length()
                arrayList_details = ArrayList();
                for (i in 0..size - 1) {
                    var json_objectdetail: JSONObject = jsonarray_info.getJSONObject(i)
                    var model: Model = Model();
                    model.idNation = json_objectdetail.getString("ID Nation")
                    model.Nation = json_objectdetail.getString("Nation")
                    model.IDYear = json_objectdetail.getString("ID Year")

                    model.Year = json_objectdetail.getString("Year")
                    model.Population = json_objectdetail.getString("Population")
                    model.SlugNation = json_objectdetail.getString("Slug Nation")
                    arrayList_details.add(model)
                }

                val version = Utilities.getRunVersion(applicationContext)
                if (version.equals("1"))
                    Utilities.saveListOfData(applicationContext, arrayList_details)
                Utilities.saveRunVersion(applicationContext, "2")

                runOnUiThread {
                    //stuff that updates ui
                    val obj_adapter: CustomAdapter
                    obj_adapter = CustomAdapter(applicationContext, arrayList_details)
                    listView_details.adapter = obj_adapter
                }


            }
        })


    }

}