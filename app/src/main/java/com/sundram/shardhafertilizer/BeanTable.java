package com.sundram.shardhafertilizer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sundram.library.TableFixHeaders;
import com.sundram.shardhafertilizer.adapters.MatrixTableAdapter2;
import com.sundram.shardhafertilizer.bean.Human;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BeanTable extends Activity
{
	public static final String getRateList_Url = "https://eeragongoon001.000webhostapp.com/getRateListData.php";
	RequestQueue queue;
	ProgressDialog progressDialog;
	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.table);

		final TableFixHeaders tableFixHeaders = (TableFixHeaders) findViewById(R.id.table);

		final MatrixTableAdapter2<Human> adapter = new MatrixTableAdapter2<>(this);

		final ArrayList<Human> list = new ArrayList<>();
		Context context;
		progressDialog = new ProgressDialog(this);
		progressDialog.show();
		progressDialog.setMessage("Loading Product Rate List....");
		progressDialog.setCancelable(false);
		progressDialog.setCanceledOnTouchOutside(false);
		StringRequest stringRequest = new StringRequest(Request.Method.POST,getRateList_Url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						progressDialog.dismiss();
						Log.i("response", String.valueOf(response));
					 list.add(new Human("Product Name", "Quantity", "Packing", "Rate/MT", "GST", "MRP"));

						try {
							JSONArray jsonArray = new JSONArray(response);
							Log.i("response", String.valueOf(jsonArray));
							for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject jsonObject = jsonArray.getJSONObject(i);
								list.add(new Human(
										jsonObject.getString("ProductName"),
										jsonObject.getString("quantity"),
										jsonObject.getString("Packing"),
										jsonObject.getString("Rate/MT"),
										jsonObject.getString("Rem"),
										jsonObject.getString("MRP")
								));
								adapter.setData(list);

								tableFixHeaders.setRowSelectable(true);
								tableFixHeaders.setAdapter(adapter);
							}
							} catch (JSONException e) {
							progressDialog.dismiss();
							e.printStackTrace();
						}

					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
			progressDialog.dismiss();
			}
		});
		Volley.newRequestQueue(BeanTable.this).add(stringRequest);

		tableFixHeaders.setOnItemClickListener(new TableFixHeaders.OnItemClickListener() {
			@Override
			public void onItemClick(TableFixHeaders parent, View view, int row, int column, long id) {
				//Toast.makeText(BeanTable.this, adapter.getItem(row).getAttribute(column).toString(), Toast.LENGTH_SHORT).show();
				//Toast.makeText(BeanTable.this, adapter.getItem(row, column).toString(), Toast.LENGTH_SHORT).show();
			}
		});
	}
}
