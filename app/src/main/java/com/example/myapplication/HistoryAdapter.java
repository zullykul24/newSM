package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private int layout;
    private List<HistoryItem> historyItemList;
    private List<HistoryItem> displayedList;
    String spinnerSelected;
    String fromDate;
    String toDate;
    public HistoryAdapter(Context context, int layout, List<HistoryItem> historyItemList) {
        this.context = context;
        this.layout = layout;
        this.historyItemList = historyItemList;
        this.displayedList = historyItemList;
    }

    @Override
    public int getCount() {
        return displayedList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public Filter getFilter(final String spinnerSelected, final String fromDate, final String toDate) {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                ArrayList<HistoryItem> Filter = new ArrayList<>();
                SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
                long fromDateTime = 0;
                long toDateTime= 0;
                try {
                    fromDateTime = f.parse(fromDate).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    toDateTime = f.parse(toDate).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                toDateTime += 86400000 -1;
                Log.d("Time", "" + fromDateTime+ " " +toDateTime );
                if (spinnerSelected.equals("Tất cả bàn")){
                    for(HistoryItem item : historyItemList){
                        if(item.getDate() >=fromDateTime && item.getDate() <= toDateTime){

                            Filter.add(new HistoryItem(item.getPaymentId(), item.getAccountId(), item.getTableId(), item.getOrderId(), item.getDiscountId(), item.getAmount(), item.getDate(), item.getStatus()));
                        }
                    }
                }
                else{
                    for(HistoryItem item : historyItemList){
                        Log.d("check1111", ""+ fromDateTime + " " +  toDateTime + " " + item.getDate());
                        if(item.getDate() >=fromDateTime && item.getDate() <=toDateTime && spinnerSelected.equals("Bàn "+item.getTableId())){
                            Filter.add(new HistoryItem(item.getPaymentId(), item.getAccountId(), item.getTableId(), item.getOrderId(), item.getDiscountId(), item.getAmount(), item.getDate(), item.getStatus()));
                        }
                    }
                }
                results.values = Filter;
                results.count = Filter.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                    displayedList = (ArrayList<HistoryItem>)results.values;
                    notifyDataSetChanged();
            }
        };

    }

    @Override
    public Filter getFilter() {
        return null;
    }

    private class ViewHolder{
        TextView nameTable;
        TextView paymentDate;
        TextView paymentTime;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final  ViewHolder holder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder = new ViewHolder();
            holder.nameTable = (TextView) convertView.findViewById(R.id.ten_ban);
            holder.paymentDate = (TextView) convertView.findViewById(R.id.ngay_thanh_toan);
            holder.paymentTime = (TextView)convertView.findViewById(R.id.gio_thanh_toan);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        final  HistoryItem historyItem  = displayedList.get(position);
        holder.nameTable.setText("Bàn "+ historyItem.getTableId());
        java.sql.Timestamp date=new java.sql.Timestamp(historyItem.getDate());
        holder.paymentDate.setText(""+ date);
       // holder.paymentTime.setText(historyItem.getPaymentTime());
        holder.paymentTime.setText("20:11");

        return convertView;
    }
}
