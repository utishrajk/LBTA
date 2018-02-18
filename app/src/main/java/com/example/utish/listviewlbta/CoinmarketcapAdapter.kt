package com.example.utish.listviewlbta

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView


class CoinmarketcapAdapter (context: Context, dataList : List<CoinmarketcapDataSet>) : BaseAdapter() {
    private val TAG = "CoinmarketcapAdapter"

    private val mContext : Context

    private val mDataList : List<CoinmarketcapDataSet>

    init {
        mContext = context
        mDataList = dataList
    }

    override fun getItem(position: Int): Any {
        return mDataList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    override fun getCount(): Int {
        return mDataList.size
    }

    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(mContext)
        val rowMain = layoutInflater.inflate(R.layout.row_main, viewGroup, false)


        val nameTextView = rowMain.findViewById<TextView>(R.id.name_textview)
        val positionTextView = rowMain.findViewById<TextView>(R.id.position_textview)

        Log.i(TAG, "size of the list : ${mDataList.size}")

        nameTextView.text = mDataList.get(position).rank + " " + mDataList.get(position).symbol
        positionTextView.text = mDataList.get(position).name + " " + mDataList.get(position).price_usd


        return rowMain

    }

}