package com.yatayatsulka;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AutocompleteArrayAdapter extends ArrayAdapter<String> {

    private Context context;
    private ArrayList<String> data;

    public AutocompleteArrayAdapter(Context context, ArrayList<String> data) {
        super(context, R.layout.list_item_suggestion, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView;

        rowView = inflater.inflate(R.layout.list_item_suggestion, parent, false);
        TextView suggestion = (TextView) rowView.findViewById(R.id.suggestion_text);
//		String address = getItem(position).toString();
//		String output = address.substring(0, 1).toUpperCase().trim() + address.substring(1).toLowerCase().trim();
//		output = toTitleCase(output);
        suggestion.setText(getItem(position));
//		suggestion.setMaxHeight(position);


        return rowView;

    }

    private String toTitleCase(String givenString) {
        String[] arr = givenString.split(" ");
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < arr.length; i++) {
            if (!arr[i].isEmpty()) {
                if (arr[i].contains("-")) {
                    char a = arr[i].charAt(0);
                    if (Character.isLetterOrDigit(a)) {
                        sb.append(toTitleCase_(arr[i])).append(" ");
                    } else {
                        String[] str = arr[i].split("-");
                        sb.append("-");
                        for (int j = 1; j < str.length; j++) {
                            sb.append(toTitleCase(str[j]));
                        }
                    }

                } else {
                    sb.append(Character.toUpperCase(arr[i].charAt(0)))
                            .append(arr[i].substring(1)).append(" ");
                }
            }
        }
        return sb.toString().trim();
    }

    private String toTitleCase_(String string) {
        String[] arr = string.split("-");
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < arr.length; i++) {
            if (i == arr.length - 1) {
                sb.append(Character.toUpperCase(arr[i].charAt(0)))
                        .append(arr[i].substring(1)).append("");
            } else {
                sb.append(Character.toUpperCase(arr[i].charAt(0)))
                        .append(arr[i].substring(1)).append("-");
            }

        }
        return sb.toString().trim();
    }

}