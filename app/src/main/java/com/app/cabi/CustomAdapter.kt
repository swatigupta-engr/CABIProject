package com.app.cabi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.app.cabi.model.Model
import java.util.*

class CustomAdapter(context: Context, modellist: ArrayList<Model>) : BaseAdapter() {

    private val layoutInflater: LayoutInflater
    private val modellist: ArrayList<Model>
    var arrayList: ArrayList<Model>? = null

    init {
        this.layoutInflater = LayoutInflater.from(context)
        this.modellist = modellist
        arrayList = ArrayList()
        arrayList!!.addAll(modellist)
    }


    override fun getCount(): Int {
        return modellist.size
    }

    override fun getItem(position: Int): Any {
        return modellist.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val view: View?
        val listRowHolder: ListRowHolder
        if (convertView == null) {
            view = this.layoutInflater.inflate(R.layout.adapter_layout, parent, false)
            listRowHolder = ListRowHolder(view)
            view.tag = listRowHolder
        } else {
            view = convertView
            listRowHolder = view.tag as ListRowHolder
        }

        listRowHolder.tvIdNation.text = modellist.get(position).idNation
        listRowHolder.tvNation.text = modellist.get(position).Nation
        listRowHolder.tvIdYear.text = modellist.get(position).IDYear
        listRowHolder.tvYear.text = modellist.get(position).Year
        listRowHolder.tvPopulation.text = modellist.get(position).Population
        listRowHolder.tvSlugNation.text = modellist.get(position).SlugNation

        return view
    }

    /*   //filter
       fun filter(charText: String) {
           var charText = charText
           charText = charText.toLowerCase(Locale.getDefault())
           //arrayListDetails.clear();
           if (charText.length == 0) {
              // modellist.addAll(arrayListDetails)
               arrayList?.addAll(modellist);

           } else {
               for (model in modellist) {
                   if (model.Nation.toLowerCase(Locale.getDefault())
                           .contains(charText)
                   ) {
                       modellist.add(model)
                   }
               }
           }
           notifyDataSetChanged()
       }*/
    //filter
    open fun filter(charText: String) {
        var charText = charText
        charText = charText.toLowerCase(Locale.getDefault())
        modellist.clear()
        if (charText.length == 0) {
            modellist.addAll(arrayList!!)
        } else {
            for (model in arrayList!!) {
                if (model.Year.toLowerCase(Locale.getDefault())
                                .contains(charText)) {
                    modellist.add(model)
                }
            }
        }
        notifyDataSetChanged()
    }
}

private class ListRowHolder(row: View?) {
    public val tvIdNation: TextView
    public val tvNation: TextView
    public val tvIdYear: TextView

    public val tvYear: TextView
    public val tvPopulation: TextView
    public val tvSlugNation: TextView

    public val linearLayout: LinearLayout

    init {
        this.tvIdNation = row?.findViewById<TextView>(R.id.tvIdNation) as TextView
        this.tvNation = row?.findViewById<TextView>(R.id.tvNation) as TextView
        this.tvIdYear = row?.findViewById<TextView>(R.id.tvIdYear) as TextView



        this.tvYear = row?.findViewById<TextView>(R.id.tvYear) as TextView
        this.tvPopulation = row?.findViewById<TextView>(R.id.tvPopulation) as TextView
        this.tvSlugNation = row?.findViewById<TextView>(R.id.tvSlugNation) as TextView
        this.linearLayout = row?.findViewById<LinearLayout>(R.id.linearLayout) as LinearLayout
    }
}
//Function to set data according to Search Keyword in ListView


