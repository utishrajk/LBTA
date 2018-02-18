package com.example.utish.listviewlbta

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private var coinList: kotlin.collections.MutableList<CoinmarketcapDataSet> = java.util.ArrayList()

    private var disposable: Disposable? = null

    private val coinApiServe by lazy {
        CoinmarketcapService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.main_listview)

        loadData(listView)
    }

    private fun loadData(mListView : ListView) {
        disposable = coinApiServe.getCoins("10")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {result ->
                        Log.i(TAG, result.toString())
                        result.forEach{
                            Log.i(TAG, "adding the elements to the list...")
                            coinList.add(CoinmarketcapDataSet(it.id, it.name, it.symbol, it.rank, it.price_usd))
                        }
                        Log.i(TAG, "finished adding items")
                        mListView.adapter = CoinmarketcapAdapter(this, coinList)
                    },
                    {error -> Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()}
                )
    }





    //inner class
    private class MyCustomAdapter (context: Context, coinList : MutableList<CoinmarketcapDataSet>) : BaseAdapter() {
        private val TAG = "MyCustomAdapter"

        private val mContext : Context
        private val mCoinList : MutableList<CoinmarketcapDataSet>


        init {
            mContext = context
            mCoinList = coinList
        }

        override fun getItem(position: Int): Any {
            return "test string"
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
           return mCoinList.size
        }

        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
            //val textView = TextView(mContext)
            //textView.text = "Here is my row for my listview.."
            //return textView
            val layoutInflater = LayoutInflater.from(mContext)
            val rowMain = layoutInflater.inflate(R.layout.row_main, viewGroup, false)


            val nameTextView = rowMain.findViewById<TextView>(R.id.name_textview)
            val positionTextView = rowMain.findViewById<TextView>(R.id.position_textview)

            //nameTextView.text = names.get(position)
            //positionTextView.text = "Row Number: $position"

            Log.i(TAG, "size of the list... : ${mCoinList.size}")

            nameTextView.text = mCoinList.get(position).rank + " " + mCoinList.get(position).symbol
            positionTextView.text = mCoinList.get(position).name + " " + mCoinList.get(position).price_usd

            return rowMain

        }

    }
}
