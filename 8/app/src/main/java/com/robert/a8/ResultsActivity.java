package com.robert.a8;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultsActivity extends AppCompatActivity {

    private MyAdapter adapter;
    private ProgressBar progress;
    private MaterialButton btnResult, btnAll;
    private final Map<String, Integer> entered = new HashMap<>();
    private boolean isShowAll = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        progress = findViewById(R.id.progress);
        btnResult = findViewById(R.id.button_result);
        btnAll = findViewById(R.id.button_all);

        RecyclerView rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter();
        rv.setAdapter(adapter);


        entered.put("math", getIntent().getIntExtra("math", 0));
        entered.put("rus", getIntent().getIntExtra("rus", 0));
        entered.put("social", getIntent().getIntExtra("social", 0));
        entered.put("inform", getIntent().getIntExtra("inform", 0));
        entered.put("chemistry", getIntent().getIntExtra("chemistry", 0));
        entered.put("physics", getIntent().getIntExtra("physics", 0));
        entered.put("eng", getIntent().getIntExtra("eng", 0));
        entered.put("geo", getIntent().getIntExtra("geo", 0));

        findViewById(R.id.button_back).setOnClickListener(v -> finish());
        btnResult.setOnClickListener(v -> { isShowAll = false; load(); });
        btnAll.setOnClickListener(v -> { isShowAll = true; load(); });

        load();
    }

    private void load() {
        progress.setVisibility(View.VISIBLE);
        updateUI();

        Map<String, Integer> body = new HashMap<>(entered);
        if (isShowAll) {
            for (String k : body.keySet()) body.put(k, 100);
        }

        Api.getApi().postScores(body).enqueue(new Callback<List<Api.SpecialtyDto>>() {
            @Override
            public void onResponse(@NonNull Call<List<Api.SpecialtyDto>> call, @NonNull Response<List<Api.SpecialtyDto>> response) {
                progress.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    processData(response.body());
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Api.SpecialtyDto>> call, @NonNull Throwable t) {
                progress.setVisibility(View.GONE);
                Toast.makeText(ResultsActivity.this, "Ошибка сети", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI() {
        int colorOn = ContextCompat.getColor(this, R.color.color_secondary);
        int colorOff = ContextCompat.getColor(this, R.color.color_primary);
        btnResult.setBackgroundTintList(ColorStateList.valueOf(isShowAll ? colorOff : colorOn));
        btnAll.setBackgroundTintList(ColorStateList.valueOf(isShowAll ? colorOn : colorOff));
    }

    private void processData(List<Api.SpecialtyDto> data) {
        List<Object> items = new ArrayList<>();
        for (Api.SpecialtyDto s : data) {
            items.add(s.specialtyName);
            if (s.mathPoint > 0) items.add(new Row("Математика", s.mathPoint, entered.get("math")));
            if (s.rusPoint > 0) items.add(new Row("Русский язык", s.rusPoint, entered.get("rus")));
            if (s.itkPoint > 0) items.add(new Row("Информатика", s.itkPoint, entered.get("inform")));
            if (s.physPoint > 0) items.add(new Row("Физика", s.physPoint, entered.get("physics")));
            if (s.chemPoint > 0) items.add(new Row("Химия", s.chemPoint, entered.get("chemistry")));
            if (s.socPoint > 0) items.add(new Row("Обществознание", s.socPoint, entered.get("social")));
        }
        adapter.setData(items);
    }

    static class Row {
        String name; int min, ent;
        Row(String n, int m, Integer e) { this.name=n; this.min=m; this.ent=(e==null?0:e); }
    }

    class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final List<Object> list = new ArrayList<>();
        void setData(List<Object> l) { list.clear(); list.addAll(l); notifyDataSetChanged(); }

        @Override public int getItemViewType(int p) { return (list.get(p) instanceof String) ? 0 : 1; }

        @NonNull @Override public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup p, int vt) {
            LayoutInflater inf = LayoutInflater.from(p.getContext());
            if (vt == 0) return new HeadVH(inf.inflate(R.layout.speciality_header_item, p, false));
            else return new ItemVH(inf.inflate(R.layout.speciality_row_item, p, false));
        }

        @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder h, int p) {
            if (h instanceof HeadVH) ((HeadVH) h).t.setText((String) list.get(p));
            else {
                Row d = (Row) list.get(p); ItemVH r = (ItemVH) h;
                r.n.setText(d.name); r.m.setText(String.valueOf(d.min)); r.e.setText(String.valueOf(d.ent));
            }
        }
        @Override public int getItemCount() { return list.size(); }
        class HeadVH extends RecyclerView.ViewHolder { TextView t; HeadVH(View v){super(v); t=v.findViewById(R.id.tvDisciplineTitle);} }
        class ItemVH extends RecyclerView.ViewHolder { TextView n, m, e; ItemVH(View v){super(v); n=v.findViewById(R.id.tvName); m=v.findViewById(R.id.tvMin); e=v.findViewById(R.id.tvEntered);} }
    }
}